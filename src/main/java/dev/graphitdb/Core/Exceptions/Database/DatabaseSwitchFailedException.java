package dev.graphitdb.Core.Exceptions.Database;
/**
 * Exception thrown when switching databases fails.
 */
public class DatabaseSwitchFailedException extends Exception {

    /**
     * Constructs a new DatabaseSwitchFailed exception with a specified database name.
     *
     * @param databaseName the name of the database that could not be switched to
     */
    public DatabaseSwitchFailedException(String databaseName) {
        super("Switching to database with name: " + databaseName + " failed");
    }
}
