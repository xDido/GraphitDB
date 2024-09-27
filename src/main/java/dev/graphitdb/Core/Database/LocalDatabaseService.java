package dev.graphitdb.Core.Database;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import dev.graphitdb.Core.Exceptions.Database.DatabaseAlreadyExistException;
import dev.graphitdb.Core.Exceptions.Database.DatabaseNotFoundException;
import dev.graphitdb.Redis.Database.DatabaseConfigurationManager;

/**
 * LocalDatabaseService is responsible for managing database operations
 * in the local environment. It provides methods to create, delete,
 * switch, and manage the current database.
 */
@Component
public class LocalDatabaseService implements DatabaseService {

    @Autowired
    private DatabaseConfigurationManager redisDatabaseConfig;

    /**
     * Switches to the specified database if it exists.
     *
     * @param database the name of the database to switch to
     * @throws DatabaseNotFoundException if the database does not exist
     */
    @Override
    public void switchDatabase(String database) throws DatabaseNotFoundException {
        if (redisDatabaseConfig.isDatabaseExist(database)) {
            redisDatabaseConfig.switchDatabase(database);
        } else {
            throw new DatabaseNotFoundException(database);
        }
    }

    /**
     * Creates a new database with the specified name if it does not already exist.
     *
     * @param database the name of the database to create
     * @throws DatabaseAlreadyExistException if the database already exists
     */
    @Override
    public void createDatabase(String database) throws DatabaseAlreadyExistException {
        if (!redisDatabaseConfig.isDatabaseExist(database)) {
            redisDatabaseConfig.createDatabase(database);
        } else {
            throw new DatabaseAlreadyExistException(database);
        }
    }

    /**
     * Deletes the specified database if it exists.
     *
     * @param database the name of the database to delete
     * @throws DatabaseNotFoundException if the database does not exist
     */
    @Override
    public void deleteDatabase(String database) throws DatabaseNotFoundException {
        if (redisDatabaseConfig.isDatabaseExist(database)) {
            redisDatabaseConfig.deleteDatabase(database);
        } else {
            throw new DatabaseNotFoundException(database);
        }
    }

    /**
     * Drops the specified database if it exists.
     *
     * @param database the name of the database to drop
     * @throws DatabaseNotFoundException if the database does not exist
     */
    @Override
    public void dropDatabase(String database) throws DatabaseNotFoundException {
        if (redisDatabaseConfig.isDatabaseExist(database)) {
            redisDatabaseConfig.dropDatabase(database);
        } else {
            throw new DatabaseNotFoundException(database);
        }
    }

    /**
     * Retrieves the name of the current database. If no database is set,
     * returns the default database name.
     *
     * @return the name of the current database
     */
    @Override
    public String getCurrentDatabase() {
        String currDatabase = redisDatabaseConfig.getCurrentDatabase();
        return (currDatabase != null) ? currDatabase : redisDatabaseConfig.getDefaultDatabase();
    }

    /**
     * Switches to the default database.
     */
    @Override
    public void switchToDefaultDatabase() {
        redisDatabaseConfig.switchToDefaultDatabase();
    }

    /**
     * Drops the default database.
     */
    @Override
    public void dropDefaultDatabase() {
        redisDatabaseConfig.dropDatabase(redisDatabaseConfig.getDefaultDatabase());
    }

}
