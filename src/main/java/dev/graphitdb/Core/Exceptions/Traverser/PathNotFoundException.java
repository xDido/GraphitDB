package dev.graphitdb.Core.Exceptions.Traverser;

/**
 * Exception thrown when a path is not found during graph traversal.
 */
public class PathNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L; // For serialization compatibility
    private final int errorCode;

    /**
     * Constructs a new PathNotFoundException with the specified detail message.
     *
     * @param message The detail message.
     */
    public PathNotFoundException(String message) {
        super(message);
        this.errorCode = 404; // Example error code (could be made configurable)
    }

    /**
     * Constructs a new PathNotFoundException with the specified detail message and error code.
     *
     * @param message   The detail message.
     * @param errorCode The error code for this exception.
     */
    public PathNotFoundException(String message, int errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    /**
     * Constructs a new PathNotFoundException with the specified detail message and cause.
     *
     * @param message The detail message.
     * @param cause   The cause of the exception.
     */
    public PathNotFoundException(String message, Throwable cause) {
        super(message, cause);
        this.errorCode = 404;
    }

    /**
     * Constructs a new PathNotFoundException with the specified cause.
     *
     * @param cause The cause of the exception.
     */
    public PathNotFoundException(Throwable cause) {
        super(cause);
        this.errorCode = 404;
    }

    /**
     * Returns the error code associated with this exception.
     *
     * @return The error code.
     */
    public int getErrorCode() {
        return errorCode;
    }

    /**
     * Returns a detailed message for this exception, including the error code.
     *
     * @return A string representation of the exception.
     */
    @Override
    public String toString() {
        return "PathNotFoundException{" +
                "message=" + getMessage() +
                ", errorCode=" + errorCode +
                '}';
    }
}
