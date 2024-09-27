package dev.graphitdb.Core.Database;

import dev.graphitdb.Core.Exceptions.Database.DatabaseAlreadyExistException;
import dev.graphitdb.Core.Exceptions.Database.DatabaseNotFoundException;

/**
 * Interface for managing database operations in the graph database system.
 * Provides methods for creating, deleting, switching, and retrieving database information.
 */
public interface DatabaseService {

    /**
     * Switches to the specified database.
     *
     * @param database the name of the database to switch to
     * @throws DatabaseNotFoundException if the specified database does not exist
     */
    public void switchDatabase(String database) throws DatabaseNotFoundException;

    /**
     * Creates a new database with the specified name.
     *
     * @param database the name of the database to create
     * @throws DatabaseAlreadyExistException if a database with the specified name already exists
     */
    public void createDatabase(String database) throws DatabaseAlreadyExistException;

    /**
     * Deletes the specified database.
     *
     * @param database the name of the database to delete
     * @throws DatabaseNotFoundException if the specified database does not exist
     */
    public void deleteDatabase(String database) throws DatabaseNotFoundException;

    /**
     * Drops the specified database permanently.
     *
     * @param database the name of the database to drop
     * @throws DatabaseNotFoundException if the specified database does not exist
     */
    public void dropDatabase(String database) throws DatabaseNotFoundException;

    /**
     * Switches to the default database configured in the system.
     *
     * @throws DatabaseNotFoundException if the default database is not found
     */
    public void switchToDefaultDatabase() throws DatabaseNotFoundException;

    /**
     * Drops the default database permanently.
     *
     * @throws DatabaseNotFoundException if the default database does not exist
     */
    public void dropDefaultDatabase() throws DatabaseNotFoundException;

    /**
     * Retrieves the name of the currently active database.
     *
     * @return the name of the current database
     */
    public String getCurrentDatabase();
}
