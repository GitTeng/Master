package tv.huan.master.common.model;

import java.util.HashMap;
import java.util.Map;

public class Searchable {

    private Map<String, String> searchMap = new HashMap<String, String>();

     public static Searchable newSearchable() {
        return new Searchable();
    }


    public Searchable addSearchParam(final String key, final String value) {
        searchMap.put(key, value);
        return this;
    }

    public Searchable addSearchParams(Map<String, String> searchParams) {
        for (Map.Entry<String, String> entry : searchParams.entrySet()) {
            searchMap.put(entry.getKey(), entry.getValue());
        }
        return this;
    }


    public boolean hasSearchFilter() {
        return searchMap.size() > 0;
    }

    public Map<String, String> getMap() {
        return searchMap;
    }

    @Override
    public String toString() {
        return "SearchRequest{" +
                "searchMap=" + searchMap +
                '}';
    }

}
