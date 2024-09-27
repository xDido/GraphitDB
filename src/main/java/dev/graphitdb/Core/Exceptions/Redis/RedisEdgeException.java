package dev.graphitdb.Core.Exceptions.Redis;

/**
 * Custom exception class for Redis edge-related operations.
 */
public class RedisEdgeException extends RuntimeException {

    public RedisEdgeException(String message) {
        super(message);
    }

    public RedisEdgeException(String operation, String edgeId, Throwable cause) {
        super(STR."Error during \{operation} operation for Edge ID: \{edgeId}", cause);
    }

    public RedisEdgeException(String operation, Throwable cause) {
        super(STR."Error during \{operation} operation", cause);
    }
}
