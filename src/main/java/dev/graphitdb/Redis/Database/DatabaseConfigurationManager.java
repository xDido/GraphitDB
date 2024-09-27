package dev.graphitdb.Redis.Database;


public interface DatabaseConfigurationManager {

    /**
     * Switches to the specified database.
     *
     * @param database the name of the database to switch to.
     */
    void switchDatabase(String database);

    /**
     * Creates a new database.
     *
     * @param database the name of the database to create.
     */
    void createDatabase(String database);

    /**
     * Deletes a specified database.
     *
     * @param database the name of the database to delete.
     */
    void deleteDatabase(String database);

    /**
     * Drops all keys associated with the specified database.
     *
     * @param database the name of the database to drop.
     */
    void dropDatabase(String database);

    /**
     * Retrieves the name of the current database.
     *
     * @return the name of the current database.
     */
    String getCurrentDatabase();

    /**
     * Checks if the specified database exists.
     *
     * @param database the name of the database to check.
     * @return true if the database exists, false otherwise.
     */
    boolean isDatabaseExist(String database);

    /**
     * Retrieves the name of the default database.
     *
     * @return the default database name.
     */
    String getDefaultDatabase();

    /**
     * Switches to the default database.
     */
    void switchToDefaultDatabase();

    /**
     * Drops all keys associated with the default database.
     */
    void dropDefaultDatabase();
}
