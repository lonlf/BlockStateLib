package com.lonleaf.model;

import java.util.*;

public class BlockStateMeta {
    private final Map<String, String> properties;

    private BlockStateMeta(Map<String, String> properties) {
        this.properties = Collections.unmodifiableMap(properties);
    }

    public Map<String, String> getProperties() {
        return properties;
    }

    public String getProperty(String name) {
        return properties.get(name);
    }

    public boolean hasProperty(String name) {
        return properties.containsKey(name);
    }

    public boolean isEmpty() {
        return properties.isEmpty();
    }

    @Override
    public String toString() {
        return "BlockStateMeta{" + properties + '}';
    }

    // 构建器模式
    public static class Builder {
        private final Map<String, String> properties = new HashMap<>();

        public Builder addProperty(String name, String value) {
            properties.put(name, value);
            return this;
        }

        public Builder addProperties(Map<String, String> properties) {
            this.properties.putAll(properties);
            return this;
        }

        public BlockStateMeta build() {
            return new BlockStateMeta(new HashMap<>(properties));
        }
    }

    public static Builder builder() {
        return new Builder();
    }
}

