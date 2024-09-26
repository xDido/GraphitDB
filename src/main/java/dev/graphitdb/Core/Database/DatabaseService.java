package dev.graphitdb.Core.Database;

import dev.graphitdb.Core.Exceptions.Database.DatabaseAlreadyExist;
import dev.graphitdb.Core.Exceptions.Database.DatabaseNotFound;

/**
 * Interface for managing database operations in the graph database system.
 * Provides methods for creating, deleting, switching, and retrieving database information.
 */
public interface DatabaseService {

    /**
     * Switches to the specified database.
     *
     * @param database the name of the database to switch to
     * @throws DatabaseNotFound if the specified database does not exist
     */
    public void switchDatabase(String database) throws DatabaseNotFound;

    /**
     * Creates a new database with the specified name.
     *
     * @param database the name of the database to create
     * @throws DatabaseAlreadyExist if a database with the specified name already exists
     */
    public void createDatabase(String database) throws DatabaseAlreadyExist;

    /**
     * Deletes the specified database.
     *
     * @param database the name of the database to delete
     * @throws DatabaseNotFound if the specified database does not exist
     */
    public void deleteDatabase(String database) throws DatabaseNotFound;

    /**
     * Drops the specified database permanently.
     *
     * @param database the name of the database to drop
     * @throws DatabaseNotFound if the specified database does not exist
     */
    public void dropDatabase(String database) throws DatabaseNotFound;

    /**
     * Switches to the default database configured in the system.
     *
     * @throws DatabaseNotFound if the default database is not found
     */
    public void switchToDefaultDatabase() throws DatabaseNotFound;

    /**
     * Drops the default database permanently.
     *
     * @throws DatabaseNotFound if the default database does not exist
     */
    public void dropDefaultDatabase() throws DatabaseNotFound;

    /**
     * Retrieves the name of the currently active database.
     *
     * @return the name of the current database
     */
    public String getCurrentDatabase();
}
