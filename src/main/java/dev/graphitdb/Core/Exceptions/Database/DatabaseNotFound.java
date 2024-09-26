package dev.graphitdb.Core.Exceptions.Database;

/**
 * Exception thrown when a database is not found.
 */
public class DatabaseNotFound extends Exception {

    public DatabaseNotFound(String databaseName) {
        super("Database with name: " + databaseName + " not found");
    }
}
