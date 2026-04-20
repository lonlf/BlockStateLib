package com.lonleaf.parser;

import com.lonleaf.model.BlockData;

import java.util.Map;

public interface BlockDataParser {
    /**
     * 解析方块数据
     * @return 方块名称到BlockData的映射
     */
    Map<String, BlockData> parse();
}

