package dev.graphitdb.Core.Database;


import dev.graphitdb.Redis.Database.RedisDatabaseManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import dev.graphitdb.Core.Exceptions.Database.DatabaseAlreadyExistException;
import dev.graphitdb.Core.Exceptions.Database.DatabaseNotFoundException;


/**
 * LocalDatabaseService is responsible for managing database operations
 * in the local environment. It provides methods to create, delete,
 * switch, and manage the current database.
 */
@Component
public class LocalDatabaseService implements DatabaseService {

    @Autowired
    private RedisDatabaseManager redisDatabaseConfig;

    @Override
    public void switchDatabase(String database) throws DatabaseNotFoundException {
        if (redisDatabaseConfig.isDatabaseExist(database)) {
            redisDatabaseConfig.switchDatabase(database);
        } else {
            throw new DatabaseNotFoundException(database);
        }
    }

    @Override
    public void createDatabase(String database) throws DatabaseAlreadyExistException {
        if (!redisDatabaseConfig.isDatabaseExist(database)) {
            redisDatabaseConfig.createDatabase(database);
        } else {
            throw new DatabaseAlreadyExistException(database);
        }
    }

    @Override
    public void deleteDatabase(String database) throws DatabaseNotFoundException {
        if (redisDatabaseConfig.isDatabaseExist(database)) {
            redisDatabaseConfig.deleteDatabase(database);
        } else {
            throw new DatabaseNotFoundException(database);
        }
    }

    @Override
    public void dropDatabase(String database) throws DatabaseNotFoundException {
        if (redisDatabaseConfig.isDatabaseExist(database)) {
            redisDatabaseConfig.dropDatabase(database);
        } else {
            throw new DatabaseNotFoundException(database);
        }
    }

    @Override
    public String getCurrentDatabase() {
        String currDatabase = redisDatabaseConfig.getCurrentDatabase();
        return (currDatabase != null) ? currDatabase : redisDatabaseConfig.getDefaultDatabase();
    }

    @Override
    public void switchToDefaultDatabase() {
        redisDatabaseConfig.switchToDefaultDatabase();
    }

    @Override
    public void dropDefaultDatabase() {
        redisDatabaseConfig.dropDatabase(redisDatabaseConfig.getDefaultDatabase());
    }

}
