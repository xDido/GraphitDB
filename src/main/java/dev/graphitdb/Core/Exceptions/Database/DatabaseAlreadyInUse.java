package dev.graphitdb.Core.Exceptions.Database;
/**
 * Exception thrown when a database is already in use.
 */
public class DatabaseAlreadyInUse extends Exception {

    /**
     * Constructs a new DatabaseAlreadyInUse exception with a specified database name.
     *
     * @param databaseName the name of the database that is currently in use
     */
    public DatabaseAlreadyInUse(String databaseName) {
        super("Database with name: " + databaseName + " is already in use");
    }
}
