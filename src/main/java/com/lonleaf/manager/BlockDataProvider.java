package com.lonleaf.manager;

import com.lonleaf.model.BlockData;

import java.util.Set;

public interface BlockDataProvider {

    void loadData();

    BlockData getBlockData(String blockName);

    Set<String> getAllBlockNames();

    boolean hasBlockData(String blockName);

    void clear();
}

