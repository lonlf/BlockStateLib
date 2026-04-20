package com.lonleaf;

import com.lonleaf.calculator.StateIdCalculator;
import com.lonleaf.manager.BlockStateDataManager;
import com.lonleaf.model.BlockData;
import com.lonleaf.model.BlockStateMeta;
import com.lonleaf.model.BlockStateResult;
import com.lonleaf.parser.BlockDataParser;
import com.lonleaf.parser.JsonBlockDataParser;
import com.lonleaf.resource.ResourceLoader;
import com.lonleaf.validator.BlockStateValidator;
import com.lonleaf.version.Version;

import java.io.*;
import java.util.Map;
import java.util.Set;

public class BlockStateLib {

    private final BlockStateDataManager dataManager;
    private final BlockStateValidator validator;
    private final StateIdCalculator calculator;
    // 单例
    private static BlockStateLib instance;

    private BlockStateLib(BlockDataParser parser) {
        this.dataManager = new BlockStateDataManager(parser);
        this.validator = new BlockStateValidator();
        this.calculator = new StateIdCalculator();
    }

    public static synchronized void init(Version version){
        try {
            String jsonData = ResourceLoader.loadResource(ResourceLoader.getResourcePath(version));
            init(jsonData);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static synchronized void init(String jsonData) {
        if (instance != null) {
            throw new IllegalStateException("BlockStateLib is already initialized");
        }

        BlockDataParser parser = new JsonBlockDataParser(jsonData);
        instance = new BlockStateLib(parser);
        instance.dataManager.loadData();
    }

    public static synchronized void initFromFile(String filePath) throws IOException {
        String jsonData = readFile(filePath);
        init(jsonData);
    }

    public static synchronized BlockStateLib getInstance() {
        if (instance == null) {
            throw new IllegalStateException("BlockStateLib not initialized. Call init() first.");
        }
        return instance;
    }

    public int getId(String block, BlockStateMeta meta) {
        BlockData blockData = dataManager.getBlockData(block);
        validator.validate(blockData, meta);
        BlockStateResult result = calculator.calculateStateId(blockData, meta);
        return result.getStateId();
    }

    public int getId(String block, Map<String, String> properties) {
        BlockStateMeta meta = BlockStateMeta.builder()
                .addProperties(properties)
                .build();
        return getId(block, meta);
    }

    public BlockStateResult getStateResult(String block, BlockStateMeta meta) {
        BlockData blockData = dataManager.getBlockData(block);
        validator.validate(blockData, meta);
        return calculator.calculateStateId(blockData, meta);
    }

    public BlockData getBlockData(String block) {
        return dataManager.getBlockData(block);
    }

    public Set<String> getAllBlockNames() {
        return dataManager.getAllBlockNames();
    }

    public boolean hasBlock(String block) {
        return dataManager.hasBlockData(block);
    }

    public int getDefaultStateId(String block) {
        BlockData blockData = dataManager.getBlockData(block);
        return blockData.getDefaultStateId();
    }

    public int getBlockCount() {
        return dataManager.getBlockCount();
    }

    public void clear() {
        dataManager.clear();
    }

    private static String readFile(String filePath) throws IOException {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        }
        return sb.toString();
    }

    public static int getStateId(String block, BlockStateMeta meta) {
        block = block.toLowerCase();
        return getInstance().getId(block, meta);
    }

    public static int getStateId(String block, Map<String, String> properties) {
        block = block.toLowerCase();
        return getInstance().getId(block, properties);
    }
}
