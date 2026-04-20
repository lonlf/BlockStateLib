package com.lonleaf.model;

public class BlockStateResult {
    private final int stateId;
    private final boolean isDefault;
    private final String normalizedBlockName;
    private final BlockStateMeta appliedMeta;

    public BlockStateResult(int stateId, boolean isDefault,
                            String normalizedBlockName, BlockStateMeta appliedMeta) {
        this.stateId = stateId;
        this.isDefault = isDefault;
        this.normalizedBlockName = normalizedBlockName;
        this.appliedMeta = appliedMeta;
    }

    public int getStateId() { return stateId; }
    public boolean isDefault() { return isDefault; }
    public String getNormalizedBlockName() { return normalizedBlockName; }
    public BlockStateMeta getAppliedMeta() { return appliedMeta; }

    @Override
    public String toString() {
        return "BlockStateResult{" +
                "stateId=" + stateId +
                ", isDefault=" + isDefault +
                ", normalizedBlockName='" + normalizedBlockName + '\'' +
                ", appliedMeta=" + appliedMeta +
                '}';
    }
}
