package dev.graphitdb.Core.Exceptions.Database;

/**
 * Exception thrown when database creation fails.
 */
public class DatabaseCreationFailed extends Exception {

    /**
     * Constructs a new DatabaseCreationFailed exception with a specified message.
     *
     * @param message the reason for the failure
     */
    public DatabaseCreationFailed(String message) {
        super("Database creation failed: " + message);
    }
}
