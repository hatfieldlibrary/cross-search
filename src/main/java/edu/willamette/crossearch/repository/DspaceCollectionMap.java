package edu.willamette.crossearch.repository;

import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class DspaceCollectionMap {

    private static HashMap<String, String> map = new HashMap<>();

    private static DspaceCollectionMap dspaceCollectionMap;

    private DspaceCollectionMap() {}

    public static DspaceCollectionMap getInstance() {
        if (dspaceCollectionMap == null) {
            dspaceCollectionMap = new DspaceCollectionMap();
        }
        return dspaceCollectionMap;
    }

    public void addCollection(String uuid, String name) {
        map.put(uuid, name);
    }

    public String getCollectionName(String uuid) {
        return map.get(uuid);
    }
}
