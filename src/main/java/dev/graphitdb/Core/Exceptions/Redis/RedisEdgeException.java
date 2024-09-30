package dev.graphitdb.Core.Exceptions.Redis;

/**
 * Custom exception class for Redis edge-related operations.
 */
public class RedisEdgeException extends RuntimeException {

    /**
     * Constructs a new RedisEdgeException with the specified detail message.
     *
     * @param message the detail message (which is saved for later retrieval
     *                by the {@link Throwable#getMessage()} method)
     */
    public RedisEdgeException(String message) {
        super(message);
    }

    /**
     * Constructs a new RedisEdgeException with the specified operation, edge ID,
     * and cause.
     *
     * @param operation the name of the operation that failed
     * @param edgeId    the ID of the edge related to the exception
     * @param cause     the cause of the exception (which is saved for later
     *                  retrieval by the {@link Throwable#getCause()} method)
     */
    public RedisEdgeException(String operation, String edgeId, Throwable cause) {
        super(String.format("Error during %s operation for Edge ID: %s", operation, edgeId), cause);
    }

    /**
     * Constructs a new RedisEdgeException with the specified operation and cause.
     *
     * @param operation the name of the operation that failed
     * @param cause     the cause of the exception (which is saved for later
     *                  retrieval by the {@link Throwable#getCause()} method)
     */
    public RedisEdgeException(String operation, Throwable cause) {
        super(String.format("Error during %s operation", operation), cause);
    }
}
