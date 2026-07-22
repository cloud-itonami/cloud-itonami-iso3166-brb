(ns marketentry.facts
  "Per-jurisdiction public-procurement market-entry regulatory catalog
  -- the G2-style spec-basis table the Market-Entry Compliance Governor
  checks every `:jurisdiction/assess` proposal against ('did the advisor
  cite an OFFICIAL public source for this jurisdiction's requirements,
  or did it invent one?').

  Barbados's real market-entry surface (curl-verified 2026-07-22, HTTP
  200 on every `gov.bb`/`caipo.gov.bb`/`bra.gov.bb`/`gov-bb.bonfirehub.com`
  host this iteration fetched; HTML read via a tag-stripped `pandoc`
  dump, the Public Procurement Act's own PDF via `pdftotext -layout` --
  so every citation below is HIGH confidence primary text this
  iteration actually read, not a secondary summary, except where
  explicitly marked otherwise):

  - **Public procurement runs on a real, LIVE e-procurement portal, but
    the ENACTING statute's own commencement clause has NOT been
    independently confirmed as proclaimed -- an honest, load-bearing
    gap this catalog does not paper over.** `gov.bb`'s own front page
    (fetched directly) carries a 'PROCUREMENT PORTAL' panel stating in
    its own words: 'The Government of Barbados has modernized its
    national procurement system in an effort to improve the
    effectiveness of public procurement, save money through competitive
    prices and reduce process time', linking to
    `https://gov-bb.bonfirehub.com/portal/?tab=openOpportunities` -- a
    live Bonfire-brand e-procurement SaaS portal (fetched directly,
    self-titled 'Procurement Portal / Barbados Integrated Government')
    that already lists dozens of real Barbadian ministries/departments/
    state-owned enterprises as procuring entities, including 'Barbados
    Revenue Authority' and 'Government Procurement Department'. The
    statute this catalog cites as the portal's legal basis, the Public
    Procurement Act, 2021 (Act No. 30 of 2021) -- downloaded in full
    (166pp) from FAOLEX (`faolex.fao.org/docs/pdf/bar221061.pdf`, FAOLEX's
    own metadata records its source as `www.barbadosparliament.com`) and
    read via `pdftotext` -- is REAL, enacted primary law (its own long
    title: 'An Act to regulate public procurement and in particular to
    promote integrity, fairness, transparency, value for money and
    efficiency in public procurement'; passed by the Senate 20 December
    2021 per `barbadosparliament.com`'s own bill-tracker page, fetched
    directly), and was substantively AMENDED in 2023 (Public Procurement
    (Amendment) Act, 2023, touching exactly ss.85/86/87, the Suppliers
    Register provisions this catalog's flagship check is grounded in --
    confirmed via the amendment's own FAOLEX/Ecolex abstract). BUT the
    Act's own s.117, read directly in the primary PDF text, states
    verbatim: 'This Act shall come into operation on a day to be fixed
    by Proclamation' -- and this iteration could NOT independently
    locate a Gazette Proclamation notice fixing that day. The only
    corroborating signal found (a third-party LinkedIn post, NOT treated
    as a primary or authoritative source) described the Act as 'still
    awaiting formal commencement' as of that post. This catalog
    therefore cites the Act as the DESIGNED, enacted statutory regime
    (its Suppliers Register mechanism is real, specific, and is what the
    live Bonfire portal's own registered-vendor model implements in
    practice) while explicitly NOT claiming the Act is confirmed to be
    the currently-in-force instrument today -- the same honest-gap
    discipline prior siblings apply to a JS-blocked page or an
    unreachable regulation text, extended here to a statute's own
    commencement status.
  - `:owner-authority` is the Chief Procurement Officer -- the Act's own
    s.4/s.5 (read directly) make the Chief Procurement Officer
    'responsible for the management of public procurement', operating
    under 'the Minister with responsibility for Finance' (s.2
    Interpretation) and 'the Director of Finance and Economic Affairs'
    (s.2's definition of 'Director'). `gov.bb`'s own official Ministries
    listing (`gov.bb/Ministries`, fetched directly) confirms the current
    ministry name as 'Ministry of Finance, Economic Affairs and
    Investment' -- independently corroborated by the Bonfire portal's
    own procuring-entity dropdown, which lists 'Ministry of Finance,
    Economic Affairs and Investment' verbatim. 'Government Procurement
    Department' (the label the Bonfire portal itself uses for one of its
    listed procuring entities) is NOT a name this iteration found
    established anywhere in the Act's own text or in `gov.bb`'s own
    Departments directory (which instead lists an older, DIFFERENT body,
    the 'Central Purchasing Department', headed by a 'Chief Supply
    Officer' under the Ministry of Industry, Innovation, Science and
    Technology, responsible for physical goods/stores supply -- fetched
    directly, a genuinely separate function from the Chief Procurement
    Officer's Act-based remit). This catalog cites the Act's own legal
    title (Chief Procurement Officer) as `:owner-authority`, the same
    'cite the Act's own name, not the portal's informal label'
    discipline ATG's catalog already established for the Tenders
    Board/Procurement Board naming split.
  - **Supplier registration is a REAL, current, statutory register with
    an explicit 3-YEAR VALIDITY PERIOD -- this catalog's flagship check
    (see `marketentry.governor`) independently recomputes registration
    EXPIRY from a registration date, a genuinely different check SHAPE
    from every prior sibling.** The Act's own Part VII (ss.85-94, read
    directly in the primary PDF text) establishes the 'Suppliers
    Register': s.85(1) 'The Chief Procurement Officer shall establish
    and maintain a register to be called the Suppliers Register'; s.86(1)
    'A procuring entity shall not enter into a procurement contract with
    a supplier unless the supplier is registered under this section';
    s.86(5) 'Registration under this section is valid for 3 years and
    may be renewed ... for further periods of 3 years'; s.86(7) lets the
    Chief Procurement Officer refuse registration/renewal where the
    supplier 'is or becomes ineligible to participate in public
    procurement'. This is NOT a turnover-scaled formula (Bulgaria), not
    a flat statutory threshold (Albania), not a boolean registry-
    membership read (Azerbaijan/Armenia/Bolivia), not a 3-tier
    contract-value classification (Antigua and Barbuda), not a bid-
    evaluation price-adjustment recompute (Benin), not a struck-off
    company-registry legal-capacity boolean (Belize) -- it is a DATE
    recompute: does `registration-date + 3 years` still cover the
    engagement's own declared `submission-date`? `marketentry.registry`
    implements this as plain ISO-8601 string arithmetic (year-bump +
    lexicographic compare), the same 'no external date library, pure
    function, ground-truth recompute' discipline `engagement-fee-
    matches-claim?` already uses for the fee check.
  - **Barbados genuinely HAS personal-exclusion grounds extending
    disqualification to a corporate supplier's own directors/officers --
    a finding this catalog family's BLZ and ATG siblings each explicitly
    searched for in their own jurisdictions and did NOT find (their own
    `rep-spec-basis` returns nil).** The Act's own s.88(2) (read directly)
    provides that 'a supplier is ineligible to participate in public
    procurement where he, or in the case of a supplier that is a body
    corporate, any of its directors or officers', within 5 years of
    application/procurement-initiation, engaged in any of: knowingly/
    recklessly false information, interference with other suppliers'
    participation, corrupt/fraudulent/collusive/coercive practices or
    underpricing or confidentiality breaches, substantial non-
    performance of a prior contract, refusal to furnish a required
    security, conviction under the Act or the Prevention of Corruption
    Act, 2021 (Act 2021-24) or another enactment tied to obtaining a
    contract or to dishonesty, professional-body suspension/disbarment,
    or a verified foreign/international suspension. This catalog's
    `rep-spec-basis` for BRB is therefore NOT nil -- a genuinely
    different, positive finding from this family's own precedent of
    honest absence, reached by actually reading the Act's own text
    rather than assuming the same gap recurs.
  - **Business registration (CAIPO) and tax registration (BRA) are
    performed by two DIFFERENT bodies under two DIFFERENT statutes -- a
    two-act model, corroborated the same way ATG's ABIPCO/IRD finding
    was.** The Corporate Affairs and Intellectual Property Office
    (CAIPO, `caipo.gov.bb`, branded 'Business Barbados') -- fetched
    directly, confirmed in its own words to be 'a Department of the
    Ministry of Energy, Business Development and Consumer Affairs',
    established 'by Act of Parliament in 1988' (the Corporate Affairs
    and Intellectual Property Act, Cap. 21A, listed on CAIPO's own
    `/legislation/` page, fetched directly) -- administers the Corporate
    Registry under the Companies Act, Cap. 308 (downloaded in full from
    `caipo.gov.bb`'s own site and read via `pdftotext`; its own s.1067
    area defines '\"Registrar\" refers to the Registrar of Companies
    under this Act'). SEPARATELY, the Barbados Revenue Authority (BRA,
    `bra.gov.bb`) -- fetched directly, confirmed in its own words: 'The
    Barbados Revenue Authority was established on April 1, 2014, as a
    statutory corporation, subsequent to the merger of Barbados' various
    tax collecting agencies, including the Inland Revenue and Land Tax
    Departments, the Value Added Tax (VAT) and Excise Divisions of the
    Customs and Excise Department' -- issues a 13-digit Taxpayer
    Identification Number (TIN) through its own TAMIS (Tax
    Administration Management Information System) platform
    (`tamis.bra.gov.bb`, per BRA's own 'Register for a Taxpayer
    Identification Number (TIN)' guidance page, fetched directly). NOTE
    the honest scope of the BRA/Customs merger, stated in BRA's OWN
    words: BRA absorbed Inland Revenue, Land Tax, and the VAT/Excise
    Divisions of Customs and Excise, plus 'the cashiering functions of
    the Customs Department and providing information technology support
    to Customs' -- the Customs Department's own core border/import-
    export functions are NOT folded into BRA, a nuance this catalog does
    not flatten into a blanket 'BRA replaced Customs' claim. BRA's own
    TAMIS registration guidance page independently corroborates the
    two-act shape: company applicants select a 'CAIPO registration date
    of the entity you are registering' as an input field, confirming
    CAIPO's Certificate of Incorporation/registration act happens FIRST,
    with BRA's TIN issuance a separate, subsequent act -- the same
    'ABIPCO before IRD' two-act shape ATG's own catalog documents,
    genuinely re-verified here rather than assumed for Barbados.
  - `rep-spec-basis` NOTE: unlike the honest nil this family's BLZ/ATG
    siblings return, BRB's `rep-spec-basis` is populated -- see the
    Act s.88(2) finding above.

  Coverage is reported HONESTLY (see `coverage`): a jurisdiction not in
  this table has NO spec-basis, full stop -- the advisor must not
  fabricate one, and the governor holds if it tries.")

(def catalog
  "iso3 -> requirement map. `:required-evidence` mirrors the generic
  intake/portal-registration/filing evidence set; `:legal-basis` /
  `:owner-authority` / `:provenance` are the G2 citation the governor
  requires before any `:jurisdiction/assess` proposal can commit.
  `:supplier-registration-*` grounds this vertical's flagship governor
  check (`supplier-registration-spec-basis`) -- a DATE-recompute shape,
  genuinely different from every prior sibling's check shape (see
  namespace docstring). `:rep-owner-authority` etc. are, unusually for
  this family, POPULATED for BRB rather than nil -- see docstring."
  {"BRB" {:name "Barbados"
          :owner-authority "Chief Procurement Officer, under the Minister with responsibility for Finance and the Director of Finance and Economic Affairs (Ministry of Finance, Economic Affairs and Investment) -- Public Procurement Act, 2021 (Act 2021-30) ss.2/4/5, own primary text"
          :legal-basis "Public Procurement Act, 2021 (Act No. 30 of 2021), passed by the Senate 20 December 2021 per barbadosparliament.com's own bill tracker, amended by the Public Procurement (Amendment) Act, 2023 (touching ss.85/86/87, the Suppliers Register provisions) -- incorporates the Protocol on Public Procurement for the Caribbean Community (signed by Barbados 27 February 2019 at Basseterre, First Schedule, s.3(2) gives the Protocol precedence on conflict). NOTE s.117 requires commencement 'on a day to be fixed by Proclamation'; this iteration could NOT independently confirm that Proclamation has issued -- see namespace docstring's honest-gap note"
          :national-spec "gov-bb.bonfirehub.com 'Barbados Integrated Government' Procurement Portal (linked directly from gov.bb's own 'PROCUREMENT PORTAL' panel) -- live e-tendering + Suppliers Register-model vendor registration across dozens of real ministries/departments/state-owned enterprises"
          :provenance "https://www.gov.bb/ ; https://gov-bb.bonfirehub.com/portal/?tab=openOpportunities ; https://faolex.fao.org/docs/pdf/bar221061.pdf ; https://www.barbadosparliament.com/bills/details/607"
          :required-evidence ["Corporate Affairs and Intellectual Property Office (CAIPO) certificate of incorporation/registration (Companies Act, Cap. 308)"
                              "Barbados Revenue Authority (BRA) Taxpayer Identification Number (TIN), issued via TAMIS"
                              "Suppliers Register registration record, current and not expired (Public Procurement Act 2021 s.86, valid 3 years from registration)"
                              "Confirmation of authorized representative"]
          :corporate-number-owner-authority "Barbados Revenue Authority (BRA)"
          :corporate-number-legal-basis "Barbados Revenue Authority Act, 2014-1 -- BRA's own 'About' page: established 1 April 2014 as a statutory corporation, merging the Inland Revenue and Land Tax Departments and the VAT/Excise Divisions of the Customs and Excise Department (Customs' own core functions remain separate, see namespace docstring). Issues a 13-digit Taxpayer Identification Number (TIN) via TAMIS (Tax Administration Management Information System, tamis.bra.gov.bb) -- BRA's own registration guidance references the applicant's CAIPO registration date as an input, confirming CAIPO registration precedes TIN issuance"
          :corporate-number-provenance "https://bra.gov.bb/About/ ; https://bra.gov.bb/Popular-Topics/Registration/Register-for-Taxpayer-Identificati"
          :business-registration-owner-authority "Corporate Affairs and Intellectual Property Office (CAIPO), a Department of the Ministry of Energy, Business Development and Consumer Affairs"
          :business-registration-legal-basis "Corporate Affairs and Intellectual Property Office established by Act of Parliament in 1988 (Corporate Affairs and Intellectual Property Act, Cap. 21A); Corporate Registry operates under the Companies Act, Cap. 308 (as amended through 2020) -- CAIPO's own primary text: '\"Registrar\" refers to the Registrar of Companies under this Act'"
          :business-registration-provenance "https://caipo.gov.bb/about-us/ ; https://caipo.gov.bb/legislation/ ; https://caipo.gov.bb/shared/assets/documents/legislation/5.-Companies-Act-Cap.-308.pdf"
          :supplier-registration-owner-authority "Chief Procurement Officer"
          :supplier-registration-legal-basis "Public Procurement Act, 2021 (Act 2021-30) Part VII: s.85(1) establishes the Suppliers Register; s.86(1) bars a procuring entity from contracting with an unregistered supplier; s.86(5) fixes registration validity at 3 years, renewable for further 3-year periods; s.86(7) lets the Chief Procurement Officer refuse registration/renewal for an ineligible supplier. Amended by the Public Procurement (Amendment) Act, 2023, which specifically touches ss.85/86/87"
          :supplier-registration-provenance "https://faolex.fao.org/docs/pdf/bar221061.pdf"
          :rep-owner-authority "Chief Procurement Officer, with the Director of Finance and Economic Affairs's approval for an ineligibility determination"
          :rep-legal-basis "Public Procurement Act, 2021 (Act 2021-30) s.88(2): a supplier -- or, where the supplier is a body corporate, any of its directors or officers -- is ineligible to participate in public procurement where, within 5 years prior to registration or procurement initiation, he engaged in false-information provision, interference with other suppliers, corrupt/fraudulent/collusive/coercive practice, underpricing, confidentiality breach, substantial non-performance, refusal to furnish a required security, conviction under this Act or the Prevention of Corruption Act 2021 (Act 2021-24) or another dishonesty/contract-related enactment, professional-body suspension/disbarment, or a verified foreign/international suspension"
          :rep-provenance "https://faolex.fao.org/docs/pdf/bar221061.pdf"}
   "USA" {:name "United States"
          :owner-authority "U.S. General Services Administration (GSA) / SAM.gov"
          :legal-basis "Federal Acquisition Regulation (FAR); System for Award Management"
          :national-spec "SAM.gov entity registration + NAICS self-certification"
          :provenance "https://sam.gov/"
          :required-evidence ["EIN record"
                              "SAM.gov registration record"
                              "State business registration record"
                              "Authorized-representative record"]}
   "DEU" {:name "Germany"
          :owner-authority "Beschaffungsamt des BMI / e-Vergabe platforms"
          :legal-basis "Gesetz gegen Wettbewerbsbeschränkungen (GWB) / VgV"
          :national-spec "e-Vergabe supplier registration under EU procurement directives"
          :provenance "https://www.evergabe-online.de/"
          :required-evidence ["Handelsregister extract"
                              "e-Vergabe registration record"
                              "USt-IdNr record"
                              "Authorized-representative record"]}})

(defn spec-basis
  "The jurisdiction's requirement map, or nil -- nil means NO spec-basis,
  and the governor must hold any proposal that tries to assess or file
  on it."
  [iso3]
  (get catalog iso3))

(defn coverage
  "Honest coverage report: how many of the requested jurisdictions actually
  have a spec-basis entry. Never report a missing jurisdiction as covered."
  ([] (coverage (keys catalog)))
  ([iso3s]
   (let [have (filter catalog iso3s)
         missing (remove catalog iso3s)]
     {:requested (count iso3s)
      :covered (count have)
      :covered-jurisdictions (vec (sort have))
      :missing-jurisdictions (vec (sort missing))
      :note (str "cloud-itonami-iso3166-brb R0: " (count catalog)
                 " jurisdictions seeded with an official spec-basis. "
                 "This is a starting catalog for market-entry navigation, "
                 "not a survey of all ~194 jurisdictions -- extend "
                 "`marketentry.facts/catalog`, never fabricate a "
                 "jurisdiction's requirements.")})))

(defn required-evidence-satisfied?
  "Does `submitted` (a set/coll of evidence keywords or strings) satisfy
  every evidence item listed for `iso3`? Missing spec-basis -> never
  satisfied."
  [iso3 submitted]
  (when-let [{:keys [required-evidence]} (spec-basis iso3)]
    (let [need (count required-evidence)
          have (count (filter (set submitted) required-evidence))]
      (= need have))))

(defn evidence-checklist [iso3]
  (:required-evidence (spec-basis iso3) []))

(defn rep-spec-basis
  "The jurisdiction's representative-related requirement map, or nil when
  this catalog has no such regime. For BRB this is POPULATED -- see the
  `catalog` docstring's positive s.88(2) finding (unlike this family's
  BLZ/ATG honest-nil precedent)."
  [iso3]
  (when-let [sb (spec-basis iso3)]
    (when (:rep-owner-authority sb)
      (select-keys sb [:rep-owner-authority :rep-legal-basis :rep-provenance]))))

(defn corporate-number-spec-basis
  "The jurisdiction's corporate-number / tax-id regime (BRA TIN via
  TAMIS, for BRB), or nil."
  [iso3]
  (when-let [sb (spec-basis iso3)]
    (when (:corporate-number-owner-authority sb)
      (select-keys sb [:corporate-number-owner-authority
                       :corporate-number-legal-basis
                       :corporate-number-provenance]))))

(defn business-registration-spec-basis
  "The jurisdiction's business (state) registration regime, or nil.
  Barbados's business-registration act is performed by CAIPO (a
  DIFFERENT body, the Ministry of Energy, Business Development and
  Consumer Affairs) than BOTH the procurement regulator
  (`:owner-authority`, Chief Procurement Officer/Ministry of Finance)
  and the tax registrar (`corporate-number-spec-basis`, BRA) -- see the
  namespace docstring's two-act finding."
  [iso3]
  (when-let [sb (spec-basis iso3)]
    (when (:business-registration-owner-authority sb)
      (select-keys sb [:business-registration-owner-authority
                       :business-registration-legal-basis
                       :business-registration-provenance]))))

(defn supplier-registration-spec-basis
  "The jurisdiction's Suppliers Register regime, or nil. For BRB this is
  HIGH confidence, grounded directly in the Public Procurement Act,
  2021's own primary text (Part VII, ss.85-90) -- the flagship check
  this vertical adds (a DATE-recompute of the 3-year registration
  validity window, see `marketentry.registry`) is grounded here, not
  copied from a sibling's citation."
  [iso3]
  (when-let [sb (spec-basis iso3)]
    (when (:supplier-registration-owner-authority sb)
      (select-keys sb [:supplier-registration-owner-authority
                       :supplier-registration-legal-basis
                       :supplier-registration-provenance]))))
