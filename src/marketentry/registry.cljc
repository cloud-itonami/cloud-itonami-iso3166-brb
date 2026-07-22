(ns marketentry.registry
  "Pure-function market-entry filing-draft + filing-submit record
  construction -- an append-only market-entry book-of-record draft.

  Like every sibling actor's registry, there is no single international
  reference-number standard for a public-procurement market-entry
  filing -- every jurisdiction assigns its own format. This namespace
  does NOT invent one; it builds a jurisdiction-scoped sequence number
  and validates the record's required fields, the same honest,
  non-fabricating discipline `marketentry.facts` uses.

  `engagement-fee-matches-claim?` is an HONEST reapplication of the
  SAME ground-truth-recompute DISCIPLINE sibling actors use (verify a
  claimed monetary total against the entity's own recorded quantity x
  unit fields), reapplied to a market-entry engagement fee line.

  `compute-registration-expiry` / `supplier-registration-expired?` are
  THIS vertical's own new ground-truth recompute, grounding BRB's
  flagship governor check (`marketentry.governor/supplier-registration-
  expired-violations`): the Public Procurement Act, 2021 (Act 2021-30)
  s.86(5) fixes Suppliers Register registration validity at 3 years from
  the registration date (own primary text, see `marketentry.facts`).
  Dates are plain ISO-8601 \"YYYY-MM-DD\" strings -- deliberately no
  external date/calendar library and no host date API (`java.time` /
  `js/Date`), so the recompute is byte-identical on every `.cljc`
  target. Expiry is computed by bumping the 4-digit year prefix (a
  calendar '3 years later, same month/day' reading of the statute, the
  ordinary legal meaning of a fixed-year validity period) and compared
  with plain string `compare`, which sorts zero-padded ISO-8601 dates in
  chronological order.

  This is a DIFFERENT check SHAPE from every prior sibling: not a
  turnover-scaled formula (Bulgaria), not a flat statutory threshold
  (Albania), not a boolean registry-membership read (Azerbaijan/
  Armenia/Bolivia), not a 3-tier contract-value classification (Antigua
  and Barbuda), not a bid-evaluation price-adjustment recompute (Benin),
  not a struck-off company-registry legal-capacity boolean (Belize) --
  it is a DATE recompute, grounded in the Act's own s.86(5) validity
  period rather than copied from a sibling's citation.

  This namespace is pure data + pure functions -- no I/O, no network
  call to any real procurement portal. It builds the RECORD an
  operator would keep, not the act of submitting a portal registration
  itself (that is `marketentry.operation`'s `:filing/submit`, always
  human-gated -- see README Actuation)."
  (:require [clojure.string :as str]))

(defn- unsigned-certificate
  "Every certificate this actor produces is UNSIGNED -- signature is
  the market-entry operator's act, not this actor's."
  [kind subject record-id]
  {"@context" ["https://www.w3.org/ns/credentials/v2"]
   "type" ["VerifiableCredential" kind]
   "credentialSubject" {"id" subject "record" record-id}
   "proof" nil
   "issued_by_registry" false
   "status" "draft-unsigned"})

(defn- zero-pad [n w]
  (let [s (str n)]
    (str (apply str (repeat (max 0 (- w (count s))) "0")) s)))

(defn compute-engagement-fee
  "The ground-truth engagement fee for `engagement`'s own `:base-fee`
  and `:monitoring-months` x `:monthly-rate` -- a single flat
  base + months x rate calculation, not a full pricing engine."
  [{:keys [base-fee monthly-rate monitoring-months]}]
  (+ (double base-fee)
     (* (double monthly-rate) (double monitoring-months))))

(defn engagement-fee-matches-claim?
  "Does `engagement`'s own `:claimed-fee` equal the independently
  recomputed `compute-engagement-fee`?"
  [{:keys [claimed-fee] :as engagement}]
  (== (double claimed-fee) (compute-engagement-fee engagement)))

(defn compute-registration-expiry
  "The ground-truth Suppliers Register expiry date for a `registration-
  date` (\"YYYY-MM-DD\") -- 3 calendar years later, same month/day, per
  the Public Procurement Act, 2021 (Act 2021-30) s.86(5)."
  [registration-date]
  (when (and registration-date (>= (count registration-date) 5))
    (let [year (#?(:clj Integer/parseInt :cljs js/parseInt) (subs registration-date 0 4))
          rest (subs registration-date 4)]
      (str (+ year 3) rest))))

(defn supplier-registration-expired?
  "Is `engagement`'s own declared `:supplier-registration-date`
  EXPIRED as of its own declared `:submission-date` -- i.e. does
  `submission-date` fall strictly AFTER `registration-date + 3 years`?
  Missing either date is never treated as expired here (that is the
  `evidence-incomplete` check's job, upstream in the phase where an
  assessment must already exist)."
  [{:keys [supplier-registration-date submission-date]}]
  (boolean
   (when-let [expiry (compute-registration-expiry supplier-registration-date)]
     (when submission-date
       (pos? (compare submission-date expiry))))))

(defn register-draft
  "Validate + construct the FILING-DRAFT registration DRAFT -- the
  market-entry operator's own act of preparing a portal registration
  package. Pure function -- does not touch any real procurement
  portal."
  [engagement-id jurisdiction sequence]
  (when-not (and engagement-id (not= engagement-id ""))
    (throw (ex-info "draft: engagement_id required" {})))
  (when-not (and jurisdiction (not= jurisdiction ""))
    (throw (ex-info "draft: jurisdiction required" {})))
  (when (< sequence 0)
    (throw (ex-info "draft: sequence must be >= 0" {})))
  (let [draft-number (str (str/upper-case jurisdiction) "-DFT-" (zero-pad sequence 6))
        record {"record_id" draft-number
                "kind" "filing-draft"
                "engagement_id" engagement-id
                "jurisdiction" jurisdiction
                "immutable" true}]
    {"record" record "draft_number" draft-number
     "certificate" (unsigned-certificate "FilingDraft" draft-number draft-number)}))

(defn register-submit
  "Validate + construct the FILING-SUBMIT registration DRAFT -- the
  market-entry operator's own act of actually submitting a portal
  registration (always human-gated upstream)."
  [engagement-id jurisdiction sequence]
  (when-not (and engagement-id (not= engagement-id ""))
    (throw (ex-info "submit: engagement_id required" {})))
  (when-not (and jurisdiction (not= jurisdiction ""))
    (throw (ex-info "submit: jurisdiction required" {})))
  (when (< sequence 0)
    (throw (ex-info "submit: sequence must be >= 0" {})))
  (let [submit-number (str (str/upper-case jurisdiction) "-SUB-" (zero-pad sequence 6))
        record {"record_id" submit-number
                "kind" "filing-submit"
                "engagement_id" engagement-id
                "jurisdiction" jurisdiction
                "immutable" true}]
    {"record" record "submit_number" submit-number
     "certificate" (unsigned-certificate "FilingSubmit" submit-number submit-number)}))

(defn append [history result]
  (conj (vec history) (get result "record")))
