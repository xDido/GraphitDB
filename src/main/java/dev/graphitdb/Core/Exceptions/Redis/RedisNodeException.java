package dev.graphitdb.Core.Exceptions.Redis;

/**
 * Custom exception class for Redis node-related operations.
 */
public class RedisNodeException extends RuntimeException {

    public RedisNodeException(String message) {
        super(message);
    }

    public RedisNodeException(String operation, String nodeId, Throwable cause) {
        super(STR."Error during \{operation} operation for Node ID: \{nodeId}", cause);
    }

    public RedisNodeException(String operation, Throwable cause) {
        super(STR."Error during \{operation} operation", cause);
    }
}
