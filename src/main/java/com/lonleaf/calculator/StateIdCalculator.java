package com.lonleaf.calculator;

import com.lonleaf.model.BlockData;
import com.lonleaf.model.BlockStateMeta;
import com.lonleaf.model.BlockStateResult;
import org.json.JSONObject;

import java.util.*;

/**
 * 状态ID计算器
 */
public class StateIdCalculator {

    /**
     * 计算方块状态ID
     */
    public BlockStateResult calculateStateId(BlockData blockData, BlockStateMeta meta) {
        String normalizedBlockName = blockData.getBlockName();

        // 如果没有任何属性，返回默认状态
        if (meta.isEmpty()) {
            return new BlockStateResult(
                    blockData.getDefaultStateId(),
                    true,
                    normalizedBlockName,
                    meta
            );
        }

        // 构建状态键并查找ID
        JSONObject propsJson = new JSONObject();
        for (Map.Entry<String, String> entry : meta.getProperties().entrySet()) {
            propsJson.put(entry.getKey(), entry.getValue());
        }

        String stateKey = buildStateKey(propsJson);
        Integer stateId = blockData.getStateToIdMap().get(stateKey);

        if (stateId == null) {
            // 如果找不到精确匹配，尝试使用默认值填充缺失的属性
            stateId = findClosestState(blockData, meta.getProperties());
        }

        if (stateId == null) {
            throw new IllegalArgumentException(
                    "No state ID found for block " + normalizedBlockName +
                            " with properties: " + meta.getProperties()
            );
        }

        return new BlockStateResult(
                stateId,
                stateId == blockData.getDefaultStateId(),
                normalizedBlockName,
                meta
        );
    }

    /**
     * 构建状态键
     */
    private String buildStateKey(JSONObject properties) {
        List<String> keys = new ArrayList<>(properties.keySet());
        Collections.sort(keys);

        StringBuilder sb = new StringBuilder();
        for (String key : keys) {
            if (!sb.isEmpty()) {
                sb.append(",");
            }
            sb.append(key).append("=").append(properties.getString(key));
        }
        return sb.toString();
    }

    /**
     * 查找最接近的状态
     */
    private Integer findClosestState(BlockData blockData, Map<String, String> providedProps) {
        // 获取所有可能的属性组合
        List<Map<String, String>> allCombinations = generateAllCombinations(blockData.getProperties());

        for (Map<String, String> combination : allCombinations) {
            boolean matches = true;
            for (Map.Entry<String, String> entry : providedProps.entrySet()) {
                if (!combination.get(entry.getKey()).equals(entry.getValue())) {
                    matches = false;
                    break;
                }
            }

            if (matches) {
                JSONObject propsJson = new JSONObject();
                for (Map.Entry<String, String> entry : combination.entrySet()) {
                    propsJson.put(entry.getKey(), entry.getValue());
                }
                String stateKey = buildStateKey(propsJson);
                return blockData.getStateToIdMap().get(stateKey);
            }
        }

        return null;
    }

    /**
     * 生成所有可能的属性组合
     */
    private List<Map<String, String>> generateAllCombinations(Map<String, List<String>> properties) {
        List<Map<String, String>> result = new ArrayList<>();
        if (properties.isEmpty()) {
            result.add(new HashMap<>());
            return result;
        }

        List<String> propNames = new ArrayList<>(properties.keySet());
        generateCombinationsRecursive(properties, propNames, 0, new HashMap<>(), result);
        return result;
    }

    private void generateCombinationsRecursive(
            Map<String, List<String>> properties,
            List<String> propNames,
            int index,
            Map<String, String> current,
            List<Map<String, String>> result) {

        if (index >= propNames.size()) {
            result.add(new HashMap<>(current));
            return;
        }

        String propName = propNames.get(index);
        List<String> values = properties.get(propName);

        for (String value : values) {
            current.put(propName, value);
            generateCombinationsRecursive(properties, propNames, index + 1, current, result);
            current.remove(propName);
        }
    }
}

