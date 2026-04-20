package com.lonleaf.parser;

import com.lonleaf.model.BlockData;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.*;

/**
 * JSON格式的方块数据解析器
 */
public class JsonBlockDataParser implements BlockDataParser {
    private final String jsonData;

    public JsonBlockDataParser(String jsonData) {
        this.jsonData = Objects.requireNonNull(jsonData, "JSON data cannot be null");
    }

    @Override
    public Map<String, BlockData> parse() {
        Map<String, BlockData> result = new HashMap<>();

        try {
            JSONObject root = new JSONObject(jsonData);

            for (String blockName : root.keySet()) {
                JSONObject blockJson = root.getJSONObject(blockName);
                BlockData blockData = parseBlockData(blockName, blockJson);
                result.put(blockName, blockData);
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse JSON data", e);
        }

        return result;
    }

    private BlockData parseBlockData(String blockName, JSONObject blockJson) {
        Map<String, List<String>> properties = new LinkedHashMap<>();
        Map<String, Integer> stateToIdMap = new HashMap<>();
        int defaultStateId = -1;

        // 解析属性定义
        if (blockJson.has("properties")) {
            JSONObject propsJson = blockJson.getJSONObject("properties");
            for (String propName : propsJson.keySet()) {
                JSONArray valuesArray = propsJson.getJSONArray(propName);
                List<String> values = new ArrayList<>();
                for (int i = 0; i < valuesArray.length(); i++) {
                    values.add(valuesArray.getString(i));
                }
                properties.put(propName, values);
            }
        }

        // 解析状态ID映射
        if (blockJson.has("states")) {
            JSONArray statesArray = blockJson.getJSONArray("states");
            for (int i = 0; i < statesArray.length(); i++) {
                JSONObject stateJson = statesArray.getJSONObject(i);
                int stateId = stateJson.getInt("id");

                // 检查是否为默认状态
                if (stateJson.has("default") && stateJson.getBoolean("default")) {
                    defaultStateId = stateId;
                }

                // 构建属性值组合的键
                if (stateJson.has("properties")) {
                    JSONObject props = stateJson.getJSONObject("properties");
                    String key = buildStateKey(props);
                    stateToIdMap.put(key, stateId);
                } else {
                    // 没有属性的方块状态
                    stateToIdMap.put("", stateId);
                    if (defaultStateId == -1) {
                        defaultStateId = stateId;
                    }
                }
            }
        }

        // 确保有默认状态
        if (defaultStateId == -1 && !stateToIdMap.isEmpty()) {
            defaultStateId = stateToIdMap.values().iterator().next();
        }

        return new BlockData(blockName, properties, stateToIdMap, defaultStateId);
    }

    private String buildStateKey(JSONObject properties) {
        List<String> keys = new ArrayList<>();
        for (String key : properties.keySet()) {
            keys.add(key);
        }
        Collections.sort(keys);

        StringBuilder sb = new StringBuilder();
        for (String key : keys) {
            if (sb.length() > 0) {
                sb.append(",");
            }
            sb.append(key).append("=").append(properties.getString(key));
        }
        return sb.toString();
    }
}
