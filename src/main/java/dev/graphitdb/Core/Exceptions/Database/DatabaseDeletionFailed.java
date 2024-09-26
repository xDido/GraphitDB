package dev.graphitdb.Core.Exceptions.Database;
/**
 * Exception thrown when database deletion fails.
 */
public class DatabaseDeletionFailed extends Exception {

    /**
     * Constructs a new DatabaseDeletionFailed exception with a specified message.
     *
     * @param message the reason for the failure
     */
    public DatabaseDeletionFailed(String message) {
        super("Database deletion failed: " + message);
    }
}
