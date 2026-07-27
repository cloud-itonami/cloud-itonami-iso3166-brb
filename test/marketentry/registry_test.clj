(ns marketentry.registry-test
  (:require [clojure.test :refer [deftest is testing]]
            [marketentry.registry :as registry]))

(deftest engagement-fee-recompute
  (let [e {:base-fee 500000 :monthly-rate 30000 :monitoring-months 12 :claimed-fee 860000.0}]
    (is (== 860000.0 (registry/compute-engagement-fee e)))
    (is (true? (registry/engagement-fee-matches-claim? e))))
  (let [bad {:base-fee 500000 :monthly-rate 30000 :monitoring-months 12 :claimed-fee 999000.0}]
    (is (false? (registry/engagement-fee-matches-claim? bad)))))

(deftest register-draft-and-submit
  (let [d (registry/register-draft "eng-1" "BRB" 0)
        s (registry/register-submit "eng-1" "BRB" 0)]
    (is (= "BRB-DFT-000000" (get d "draft_number")))
    (is (= "BRB-SUB-000000" (get s "submit_number")))
    (is (nil? (get-in d ["certificate" "proof"])))
    (is (= "draft-unsigned" (get-in s ["certificate" "status"])))))

(deftest register-requires-ids
  (is (thrown? Exception (registry/register-draft "" "BRB" 0)))
  (is (thrown? Exception (registry/register-submit "eng-1" "" 0))))

(deftest registration-expiry-recompute
  (testing "3-year validity window, Public Procurement Act 2021 s.86(5) -- same month/day, year+3"
    (is (= "2027-01-15" (registry/compute-registration-expiry "2024-01-15")))
    (is (= "2023-03-01" (registry/compute-registration-expiry "2020-03-01")))
    (is (nil? (registry/compute-registration-expiry nil)))))

(deftest supplier-registration-expired-recompute
  (testing "submission strictly after the 3-year expiry -> expired"
    (is (true? (registry/supplier-registration-expired?
                {:supplier-registration-date "2020-03-01" :submission-date "2026-07-22"}))))
  (testing "submission still within the 3-year window -> not expired"
    (is (false? (registry/supplier-registration-expired?
                 {:supplier-registration-date "2024-01-15" :submission-date "2026-07-22"}))))
  (testing "submission exactly on the expiry date -> not yet expired (not STRICTLY after)"
    (is (false? (registry/supplier-registration-expired?
                 {:supplier-registration-date "2023-07-22" :submission-date "2026-07-22"}))))
  (testing "missing either date -> never treated as expired here (evidence-incomplete's job)"
    (is (false? (registry/supplier-registration-expired? {:submission-date "2026-07-22"})))
    (is (false? (registry/supplier-registration-expired? {:supplier-registration-date "2020-03-01"})))
    (is (false? (registry/supplier-registration-expired? {})))))

;; ---------------------------------------------------------------------------
;; Money is compared at money precision, not at double precision
;; ---------------------------------------------------------------------------

(deftest whole-unit-fees-were-already-correct-and-stay-correct
  (testing "the seeded shape: base + rate x months in whole currency units"
    (is (registry/engagement-fee-matches-claim?
         {:base-fee 500000 :monthly-rate 30000 :monitoring-months 12
           :claimed-fee 860000.0}))))

(deftest cent-denominated-fees-are-no-longer-rejected-while-correct
  (testing "`(== (double claimed) (+ (double base) (* (double rate) (double months))))`
            rejected CORRECT totals once an amount carried cents -- 40,989 of
            327,060 combinations (12.5%), against 0 of 327,060 in whole units"
    (let [bad (for [m (range 1 37)
                    bc (range 10000 90000 2100)
                    rc (range 500 6000 210)
                    :let [truth (/ (+ bc (* rc m)) 100.0)]
                    :when (not (registry/engagement-fee-matches-claim?
                                {:base-fee (/ bc 100.0) :monthly-rate (/ rc 100.0)
                                  :monitoring-months m :claimed-fee truth}))]
                [m (/ bc 100.0) (/ rc 100.0) truth])]
      (is (empty? bad) (str "false rejections: " (count bad) " e.g. " (first bad))))))

(deftest a-genuinely-wrong-fee-is-still-caught
  (testing "rounding to money precision must not blunt the check"
    (is (not (registry/engagement-fee-matches-claim?
              {:base-fee 500000 :monthly-rate 30000 :monitoring-months 12
                :claimed-fee 860000.01})))
    (is (not (registry/engagement-fee-matches-claim?
              {:base-fee 500000 :monthly-rate 30000 :monitoring-months 12
                :claimed-fee 859999.99})))))

(deftest an-unverifiable-fee-never-matches
  (testing "un-verifiable is not the same as correct, and not a crash"
    (is (not (registry/engagement-fee-matches-claim?
              {:base-fee 500000 :monthly-rate 30000 :monitoring-months 12})))
    (is (not (registry/engagement-fee-matches-claim?
              {:base-fee "500000" :monthly-rate 30000 :monitoring-months 12
                :claimed-fee 860000.0})))
    (is (nil? (registry/compute-engagement-fee {:base-fee 500000 :monthly-rate 30000})))))
