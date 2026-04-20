package com.lonleaf.validator;

import com.lonleaf.model.BlockData;
import com.lonleaf.model.BlockStateMeta;

import java.util.List;
import java.util.Map;

public class BlockStateValidator {

    /**
     * 验证方块状态元数据
     */
    public void validate(BlockData blockData, BlockStateMeta meta) {
        Map<String, String> properties = meta.getProperties();

        // 验证所有提供的属性都是有效的
        for (Map.Entry<String, String> entry : properties.entrySet()) {
            String propName = entry.getKey();
            String propValue = entry.getValue();

            if (!blockData.hasProperty(propName)) {
                throw new IllegalArgumentException(
                        "Block " + blockData.getBlockName() +
                                " does not have property: " + propName
                );
            }

            List<String> validValues = blockData.getPropertyValues(propName);
            if (!validValues.contains(propValue)) {
                throw new IllegalArgumentException(
                        "Invalid value '" + propValue + "' for property '" + propName +
                                "'. Valid values: " + validValues
                );
            }
        }

        // 检查是否有缺失的必需属性
        validateMissingProperties(blockData, properties);
    }

    /**
     * 验证缺失的属性
     */
    private void validateMissingProperties(BlockData blockData, Map<String, String> properties) {
        // 这里可以添加逻辑来检查是否有必需属性缺失
        // 对于Minecraft方块，所有属性都是可选的，缺失的属性会使用默认值
        // 所以这里暂时不实现
    }

    /**
     * 验证方块名称
     */
    public String validateAndNormalizeBlockName(String blockName) {
        if (blockName == null || blockName.trim().isEmpty()) {
            throw new IllegalArgumentException("Block name cannot be null or empty");
        }

        return blockName.startsWith("minecraft:") ? blockName : "minecraft:" + blockName;
    }
}
