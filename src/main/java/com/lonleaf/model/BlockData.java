package com.lonleaf.model;

import java.util.*;

public class BlockData {
    private final String blockName;
    private final Map<String, List<String>> properties;
    private final Map<String, Integer> stateToIdMap;
    private final int defaultStateId;

    public BlockData(String blockName, Map<String, List<String>> properties,
                     Map<String, Integer> stateToIdMap, int defaultStateId) {
        this.blockName = blockName;
        this.properties = Collections.unmodifiableMap(properties);
        this.stateToIdMap = Collections.unmodifiableMap(stateToIdMap);
        this.defaultStateId = defaultStateId;
    }

    public String getBlockName() { return blockName; }
    public Map<String, List<String>> getProperties() { return properties; }
    public Map<String, Integer> getStateToIdMap() { return stateToIdMap; }
    public int getDefaultStateId() { return defaultStateId; }

    public boolean hasProperty(String property) {
        return properties.containsKey(property);
    }

    public List<String> getPropertyValues(String property) {
        return properties.getOrDefault(property, Collections.emptyList());
    }

    @Override
    public String toString() {
        return "BlockData{" +
                "blockName='" + blockName + '\'' +
                ", properties=" + properties.keySet() +
                ", stateCount=" + stateToIdMap.size() +
                ", defaultStateId=" + defaultStateId +
                '}';
    }
}

