package com.lonleaf.exception;

public class BlockStateException extends RuntimeException {

    public BlockStateException(String message) {
        super(message);
    }

    public BlockStateException(String message, Throwable cause) {
        super(message, cause);
    }

    public static class BlockNotFoundException extends BlockStateException {
        public BlockNotFoundException(String blockName) {
            super("Block not found: " + blockName);
        }
    }

    public static class PropertyNotFoundException extends BlockStateException {
        public PropertyNotFoundException(String blockName, String property) {
            super("Property '" + property + "' not found in block: " + blockName);
        }
    }

    public static class InvalidPropertyValueException extends BlockStateException {
        public InvalidPropertyValueException(String blockName, String property, String value) {
            super("Invalid value '" + value + "' for property '" + property + "' in block: " + blockName);
        }
    }

    public static class StateNotFoundException extends BlockStateException {
        public StateNotFoundException(String blockName, String properties) {
            super("State not found for block " + blockName + " with properties: " + properties);
        }
    }
}
