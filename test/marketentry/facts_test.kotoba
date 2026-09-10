(ns marketentry.facts-test
  (:require [clojure.test :refer [deftest is testing]]
            [marketentry.facts :as facts]))

(deftest brb-has-spec-basis
  (let [sb (facts/spec-basis "BRB")]
    (is (some? sb))
    (is (string? (:provenance sb)))
    (is (seq (:required-evidence sb)))
    (is (some? (facts/corporate-number-spec-basis "BRB")))
    (is (some? (facts/business-registration-spec-basis "BRB")))
    (is (some? (facts/supplier-registration-spec-basis "BRB")))))

(deftest brb-rep-spec-basis-is-populated
  (testing "BRB, unlike this family's BLZ/ATG siblings, has a verified personal-exclusion-grounds provision (Public Procurement Act 2021 s.88(2)) extending to a corporate supplier's directors/officers"
    (is (some? (facts/rep-spec-basis "BRB")))))

(deftest brb-business-registration-is-a-separate-body-from-tax-and-procurement
  (testing "business registration (CAIPO, under the Ministry of Energy, Business Development and Consumer Affairs) is a DIFFERENT body than the tax registrar (BRA) and the procurement regulator (Chief Procurement Officer) -- see namespace docstring"
    (let [reg (facts/business-registration-spec-basis "BRB")
          tax (facts/corporate-number-spec-basis "BRB")]
      (is (some? reg))
      (is (some? tax))
      (is (not= (:business-registration-owner-authority reg)
                (:corporate-number-owner-authority tax))))))

(deftest unknown-jurisdiction-has-no-spec-basis
  (is (nil? (facts/spec-basis "ATL")))
  (is (nil? (facts/spec-basis "ZZZ")))
  (is (nil? (facts/business-registration-spec-basis "ATL")))
  (is (nil? (facts/supplier-registration-spec-basis "ATL"))))

(deftest required-evidence-satisfied
  (let [sb (facts/spec-basis "BRB")
        all (:required-evidence sb)]
    (is (true? (facts/required-evidence-satisfied? "BRB" all)))
    (is (not (facts/required-evidence-satisfied? "BRB" (take 1 all))))
    (is (nil? (facts/required-evidence-satisfied? "ATL" all)))))

(deftest coverage-is-honest
  (let [c (facts/coverage ["BRB" "USA" "ATL"])]
    (is (= 3 (:requested c)))
    (is (= 2 (:covered c)))
    (is (= ["ATL"] (:missing-jurisdictions c)))))
