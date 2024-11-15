package org.dataSourceConnector.entities;

import java.util.HashMap;
import java.util.Map;

public class DataModel {
    private Map<String, Object> data;

    public DataModel() {
        this.data = new HashMap<>();
    }

    public DataModel(Map<String, Object> data) {
        this.data = data;
    }

    public void setValue(String key, Object value) {
        data.put(key, value);
    }

    public Object getValue(String key) {
        return data.get(key);
    }

    public Map<String, Object> getData() {
        return data;
    }

    public boolean hasKey(String key) {
        return data.containsKey(key);
    }

    @Override
    public String toString() {
        return "DataModel{" +
                "data=" + data +
                '}';
    }
}
