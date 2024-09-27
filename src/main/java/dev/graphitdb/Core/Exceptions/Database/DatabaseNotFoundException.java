package dev.graphitdb.Core.Exceptions.Database;

/**
 * Exception thrown when a database is not found.
 */
public class DatabaseNotFoundException extends Exception {

    public DatabaseNotFoundException(String databaseName) {
        super("Database with name: " + databaseName + " not found");
    }
}
