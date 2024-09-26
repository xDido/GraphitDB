package dev.graphitdb.Core.Exceptions.Database;

/**
 * Exception thrown when attempting to create a database that already exists.
 */
public class DatabaseAlreadyExist extends Exception {

    /**
     * Constructs a new DatabaseAlreadyExists exception with a specified database name.
     *
     * @param databaseName the name of the database that already exists
     */
    public DatabaseAlreadyExist(String databaseName) {
        super("Database with name: " + databaseName + " already exists");
    }
}
