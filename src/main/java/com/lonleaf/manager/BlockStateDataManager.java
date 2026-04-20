package com.lonleaf.manager;

import com.lonleaf.model.BlockData;
import com.lonleaf.parser.BlockDataParser;

import java.util.*;

public class BlockStateDataManager implements BlockDataProvider {
    private final Map<String, BlockData> blockDataMap = new HashMap<>();
    private final BlockDataParser parser;

    public BlockStateDataManager(BlockDataParser parser) {
        this.parser = Objects.requireNonNull(parser, "Parser cannot be null");
    }

    @Override
    public void loadData() {
        blockDataMap.clear();
        blockDataMap.putAll(parser.parse());
    }

    @Override
    public BlockData getBlockData(String blockName) {
        String normalized = normalizeBlockName(blockName);
        BlockData data = blockDataMap.get(normalized);
        if (data == null) {
            throw new IllegalArgumentException("Unknown block: " + normalized);
        }
        return data;
    }

    @Override
    public Set<String> getAllBlockNames() {
        return Collections.unmodifiableSet(blockDataMap.keySet());
    }

    @Override
    public boolean hasBlockData(String blockName) {
        return blockDataMap.containsKey(normalizeBlockName(blockName));
    }

    @Override
    public void clear() {
        blockDataMap.clear();
    }

    public int getBlockCount() {
        return blockDataMap.size();
    }

    private String normalizeBlockName(String blockName) {
        if (blockName == null || blockName.trim().isEmpty()) {
            throw new IllegalArgumentException("Block name cannot be null or empty");
        }
        return blockName.startsWith("minecraft:") ? blockName : "minecraft:" + blockName;
    }
}
