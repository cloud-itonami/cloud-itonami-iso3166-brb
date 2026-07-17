(ns culture.facts
  "Country-level regional-culture catalog for Barbados (BRB) -- national
  dishes, protected products, beverages, crafts, festivals and heritage
  sites, per ADR-2607171400 addendum 2 (cloud-itonami-municipality-
  culture-catalog Wave 1, in com-junkawasaki/root). Sibling namespace to
  `marketentry.facts` / `statute.facts` (ADR-2607141700); city-level
  counterparts live in the cloud-itonami-municipality-* repos.

  Catalog is keyed by UPPERCASE ISO3 (mirrors `statute.facts`); entries
  carry no :culture/municipality (that attribute is city-level only).

  Every entry cites a source URL that was actually fetched and read on
  :culture/retrieved-at -- never fabricated. Summaries state only what the
  cited source confirms. An item not in this table has NO spec-basis, full
  stop; extend `catalog`, do not invent an id/url.")

(def catalog
  "iso3 -> vector of culture entries."
  {"BRB"
   [{:culture/id "brb.dish.cou-cou"
     :culture/name "Cou-cou"
     :culture/country "BRB"
     :culture/kind :dish
     :culture/summary "Caribbean dish of cornmeal and okra; cou-cou and flying fish is Barbados' national dish, traditionally served on Fridays at homes across Barbados."
     :culture/url "https://en.wikipedia.org/wiki/Cou-cou"
     :culture/url-provenance :wikipedia-en
     :culture/retrieved-at "2026-07-17"}
    {:culture/id "brb.dish.pudding-and-souse"
     :culture/name "Pudding and souse"
     :culture/country "BRB"
     :culture/kind :dish
     :culture/summary "Traditional Barbadian dish of pickled pork with spiced sweet potatoes."
     :culture/url "https://en.wikipedia.org/wiki/Barbadian_cuisine"
     :culture/url-provenance :wikipedia-en
     :culture/retrieved-at "2026-07-17"}
    {:culture/id "brb.dish.conkies"
     :culture/name "Conkies"
     :culture/country "BRB"
     :culture/kind :dish
     :culture/summary "Sweet cornmeal-based food with coconut, sweet potato, raisins and pumpkin, steamed in banana leaves; in modern Barbados eaten during Independence Day celebrations on November 30."
     :culture/url "https://en.wikipedia.org/wiki/Conkie"
     :culture/url-provenance :wikipedia-en
     :culture/retrieved-at "2026-07-17"}
    {:culture/id "brb.dish.cutter"
     :culture/name "Cutter"
     :culture/country "BRB"
     :culture/kind :dish
     :culture/summary "Sandwich of fried flying fish in a bap, listed among the lighter meals of Barbadian cuisine."
     :culture/url "https://en.wikipedia.org/wiki/Barbadian_cuisine"
     :culture/url-provenance :wikipedia-en
     :culture/retrieved-at "2026-07-17"}
    {:culture/id "brb.beverage.mount-gay-rum"
     :culture/name "Mount Gay Rum"
     :culture/country "BRB"
     :culture/kind :beverage
     :culture/summary "Rum produced by Mount Gay Distilleries Ltd. of Barbados; the company's oldest surviving deed from 1703 makes it the world's oldest commercial rum distillery."
     :culture/url "https://en.wikipedia.org/wiki/Mount_Gay_Rum"
     :culture/url-provenance :wikipedia-en
     :culture/retrieved-at "2026-07-17"}
    {:culture/id "brb.beverage.falernum"
     :culture/name "Falernum"
     :culture/country "BRB"
     :culture/kind :beverage
     :culture/summary "Caribbean syrup liqueur (or nonalcoholic syrup) flavoured with ginger, lime and almond, used in tropical cocktails; it may date back to an 18th-century punch made in the areas around Barbados."
     :culture/url "https://en.wikipedia.org/wiki/Falernum"
     :culture/url-provenance :wikipedia-en
     :culture/retrieved-at "2026-07-17"}
    {:culture/id "brb.festival.crop-over"
     :culture/name "Crop Over"
     :culture/country "BRB"
     :culture/kind :festival
     :culture/summary "Traditional harvest festival which began in Barbados on the sugar-cane plantations, originating in 1687; revived as a national festival in 1974, running June to the first Monday in August and culminating in the Grand Kadooment."
     :culture/url "https://en.wikipedia.org/wiki/Crop_Over"
     :culture/url-provenance :wikipedia-en
     :culture/retrieved-at "2026-07-17"}
    {:culture/id "brb.heritage.historic-bridgetown"
     :culture/name "Historic Bridgetown and its Garrison"
     :culture/country "BRB"
     :culture/kind :heritage
     :culture/summary "The historic centre of Barbados' capital and its garrison, listed as a UNESCO World Heritage Site on 25 June 2011."
     :culture/url "https://en.wikipedia.org/wiki/Bridgetown"
     :culture/url-provenance :wikipedia-en
     :culture/retrieved-at "2026-07-17"}]})

(defn spec-basis [iso3] (get catalog iso3))

(defn coverage
  ([] (coverage (keys catalog)))
  ([iso3s]
   (let [have (filter catalog iso3s)
         missing (remove catalog iso3s)]
     {:requested (count iso3s)
      :covered (count have)
      :covered-jurisdictions (vec (sort have))
      :missing-jurisdictions (vec (sort missing))
      :note (str "cloud-itonami-iso3166-brb culture catalog "
                 "(ADR-2607171400 addendum 2, Wave 1): " (count (get catalog "BRB"))
                 " BRB entries, each with a fetched-and-read citation. "
                 "Extend `culture.facts/catalog`, never fabricate an id/url.")})))

(defn by-kind [iso3 kind]
  (filterv #(= (:culture/kind %) kind) (spec-basis iso3)))
