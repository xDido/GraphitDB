package dev.graphitdb.Core.Exceptions.Redis;

/**
 * Custom exception class for Redis node-related operations.
 */
public class RedisNodeException extends RuntimeException {

    /**
     * Constructs a new RedisNodeException with the specified detail message.
     *
     * @param message the detail message.
     */
    public RedisNodeException(String message) {
        super(message);
    }

    /**
     * Constructs a new RedisNodeException with a formatted message based on the operation and node ID.
     *
     * @param operation the Redis operation during which the exception occurred.
     * @param nodeId the ID of the node that caused the exception.
     * @param cause the underlying cause of the exception.
     */
    public RedisNodeException(String operation, String nodeId, Throwable cause) {
        super(String.format("Error during %s operation for Node ID: %s", operation, nodeId), cause);
    }

    /**
     * Constructs a new RedisNodeException with a formatted message based on the operation.
     *
     * @param operation the Redis operation during which the exception occurred.
     * @param cause the underlying cause of the exception.
     */
    public RedisNodeException(String operation, Throwable cause) {
        super(String.format("Error during %s operation", operation), cause);
    }
}
