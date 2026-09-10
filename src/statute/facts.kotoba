(ns statute.facts
  "General-law compliance catalog for Barbados (BRB) -- extends this
  repo's existing `marketentry.facts` (public-procurement market-entry
  only, narrow scope) with a second, orthogonal catalog of statutes a
  company operating in this jurisdiction must generally track for
  compliance. Mirrors cloud-itonami-iso3166-aze/-bih/-jpn/-deu/-bgr/
  -blr/-bol/-blz/-atg's `statute.facts` (ADR-2607141700,
  cloud-itonami-compliance-fact-federation).

  Every entry below cites an OFFICIAL Barbadian source, curl-verified
  2026-07-22, and every citation reflects PRIMARY TEXT this iteration
  actually fetched (`curl` + `pdftotext -layout`) and read in full, not
  a secondary summary:

  - **Companies Act, Cap. 308** -- downloaded in full as a PDF directly
    from the Corporate Affairs and Intellectual Property Office's
    (CAIPO) own official site
    (`caipo.gov.bb/shared/assets/documents/legislation/5.-Companies-Act-
    Cap.-308.pdf`) and read via `pdftotext -layout`. HIGH confidence,
    primary text: its own cover page reads 'CHAPTER 308 COMPANIES',
    marked 'L.R.O. 2002' (Law Revision Office 2002 consolidated
    edition), and defines '\"Registrar\" refers to the Registrar of
    Companies under this Act'. CAIPO's own `/legislation/` page (fetched
    directly) lists it together with the Companies Regulations, 1984
    and amendments through 2020 (2011, 2012, 2016, 2018, 2019 x2, 2020).
    This is the statute this repo's `marketentry.facts`
    `business-registration-spec-basis` cites for CAIPO's Corporate
    Registry function.
  - **Employment Rights Act, 2012 (Act 2012-9)** -- downloaded in full
    as a PDF directly from the Barbados Judiciary's own official site
    (`barbadoslawcourts.gov.bb/assets/content/pdfs/annuals/actsannual/
    acts2012/Act2012-9.pdf`) and read via `pdftotext -layout`. HIGH
    confidence, primary text: its own cover page reads 'Supplement to
    Official Gazette No. 52 dated 18th June, 2012 -- EMPLOYMENT RIGHTS
    ACT, 2012 - 9 (Corrected Copy)', and its own s.5 establishes
    'Functions of Chief Labour Officer', naming the Chief Labour Officer
    as the officer 'responsible for the application, administration and
    enforcement of this Act'. Its own Part III establishes an Employment
    Rights Tribunal for unfair-dismissal and related disputes. This is a
    GENUINELY DIFFERENT administering body (Chief Labour Officer,
    Ministry of Labour) from either the procurement regulator (Chief
    Procurement Officer) or the tax/business registrars (BRA/CAIPO) this
    repo's `marketentry.facts` already documents.
  - **Data Protection Act, 2019 (Act 2019-29)** -- primary citation is
    `gov.bb`'s OWN official 'Data Protection Commission' department page
    (`gov.bb/Departments/data-protection-commissioner`, fetched
    directly), which states in its own words: 'These rights, obligations
    and responsibilities are set out in the Data Protection Act,
    2019-29 of the laws of Barbados', names the administering unit the
    'Data Protection Commission' (sited within the Ministry of Industry,
    Innovation, Science & Technology), and describes its main activities
    as including 'Registration of the Data Controller and Data
    Processor' and investigating data-breach complaints -- a
    GDPR-adjacent structure. This iteration did NOT itself download and
    read the Act's own PDF text (unlike the Companies Act and Employment
    Rights Act above, which this iteration fetched and read directly);
    the citation here rests on `gov.bb`'s own department-page
    description of the Act's own effect, a slightly lower confidence
    tier than the other two entries, reported honestly rather than
    silently treated as equally primary.

  A law not in this table has NO spec-basis, full stop; extend
  `catalog`, do not invent an id/url.")

(def catalog
  "iso3 -> vector of statute entries. `:statute/url` + `:statute/law-number`
  are the citation the governor requires before any compliance-fact
  proposal referencing this law can commit."
  {"BRB"
   [{:statute/id "brb.companies-act-cap308"
     :statute/title "Companies Act, Cap. 308 (Barbados)"
     :statute/jurisdiction "BRB"
     :statute/kind :law
     :statute/law-number "Chapter 308, L.R.O. 2002 consolidated edition, plus the Companies Regulations, 1984 and amendments (2011, 2012, 2016, 2018, 2019 (x2), 2020) -- administered by the Registrar of Companies at the Corporate Affairs and Intellectual Property Office (CAIPO), a Department of the Ministry of Energy, Business Development and Consumer Affairs established by Act of Parliament in 1988 (Corporate Affairs and Intellectual Property Act, Cap. 21A)"
     :statute/url "https://caipo.gov.bb/shared/assets/documents/legislation/5.-Companies-Act-Cap.-308.pdf"
     :statute/url-provenance :caipo-official-site
     :statute/enacted-date "2002-01-01"
     :statute/retrieved-at "2026-07-22"
     :statute/topic #{:corporate-governance :incorporation}}
    {:statute/id "brb.employment-rights-act-2012"
     :statute/title "Employment Rights Act, 2012 (Barbados)"
     :statute/jurisdiction "BRB"
     :statute/kind :law
     :statute/law-number "Act 2012-9, Supplement to Official Gazette No. 52 dated 18 June 2012 -- s.5 establishes the Chief Labour Officer's administration/enforcement function; Part III establishes the Employment Rights Tribunal"
     :statute/url "https://www.barbadoslawcourts.gov.bb/assets/content/pdfs/annuals/actsannual/acts2012/Act2012-9.pdf"
     :statute/url-provenance :barbados-judiciary-official-site
     :statute/enacted-date "2012-06-18"
     :statute/retrieved-at "2026-07-22"
     :statute/topic #{:labor :employment}}
    {:statute/id "brb.data-protection-act-2019"
     :statute/title "Data Protection Act, 2019 (Barbados)"
     :statute/jurisdiction "BRB"
     :statute/kind :law
     :statute/law-number "Act 2019-29 -- per gov.bb's own Data Protection Commission department page: establishes a GDPR-adjacent regime (Data Controller/Processor registration, data-breach complaint investigation) administered by the Data Protection Commission, Ministry of Industry, Innovation, Science & Technology. NOTE lower confidence tier: this iteration cited gov.bb's own department-page description of the Act rather than the Act's own PDF text directly -- see namespace docstring"
     :statute/url "https://www.gov.bb/Departments/data-protection-commissioner"
     :statute/url-provenance :gov-bb-official-department-page
     :statute/enacted-date "2019-07-01"
     :statute/retrieved-at "2026-07-22"
     :statute/topic #{:data-protection :privacy}}]})

(defn spec-basis
  "The jurisdiction's statute vector, or nil -- nil means NO spec-basis
  for that jurisdiction yet."
  [iso3]
  (get catalog iso3))

(defn coverage
  "Honest coverage report, same shape/discipline as `marketentry.facts/coverage`:
  never report a missing jurisdiction as covered."
  ([] (coverage (keys catalog)))
  ([iso3s]
   (let [have (filter catalog iso3s)
         missing (remove catalog iso3s)]
     {:requested (count iso3s)
      :covered (count have)
      :covered-jurisdictions (vec (sort have))
      :missing-jurisdictions (vec (sort missing))
      :note (str "cloud-itonami-iso3166-brb statute.facts Wave 0 (ADR-2607141700): "
                 (count (get catalog "BRB")) " BRB statutes seeded with an "
                 "official citation. Extend "
                 "`statute.facts/catalog`, never fabricate a law-id or URL.")})))

(defn by-topic
  "Statutes for `iso3` tagged with `topic` (e.g. :labor, :data-protection)."
  [iso3 topic]
  (filterv #(contains? (:statute/topic %) topic) (spec-basis iso3)))
