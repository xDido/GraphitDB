package dev.graphitdb.Redis.Database;


import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Set;

@Component
public class RedisDatabaseManager implements DatabaseConfigurationManager {

    private static final String CURRENT_DATABASE_KEY = "current_database";
    private static final String DATABASES_KEY = "databases";
    private static final String DEFAULT_DATABASE = "default";

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * Retrieves the name of the default database.
     *
     * @return the default database name.
     */
    public String getDefaultDatabase() {
        return DEFAULT_DATABASE;
    }

    /**
     * Switches to a specified database.
     *
     * @param database the name of the database to switch to.
     */
    public void switchDatabase(String database) {
        redisTemplate.opsForValue().set(CURRENT_DATABASE_KEY, database);
    }

    /**
     * Retrieves the current database name. If not set, returns the default.
     *
     * @return the current database name.
     */
    public String getCurrentDatabase() {
        String database = (String) redisTemplate.opsForValue().get(CURRENT_DATABASE_KEY);
        return database != null ? database : DEFAULT_DATABASE;
    }

    /**
     * Creates a new database and adds it to the set of databases.
     *
     * @param database the name of the database to create.
     */
    public void createDatabase(String database) {
        redisTemplate.opsForSet().add(DATABASES_KEY, database);
    }

    /**
     * Checks if a specified database exists.
     *
     * @param database the name of the database to check.
     * @return true if the database exists, false otherwise.
     */
    public boolean isDatabaseExist(String database) {
        return Boolean.TRUE.equals(redisTemplate.opsForSet().isMember(DATABASES_KEY, database));
    }

    /**
     * Deletes a specified database and drops all its keys.
     *
     * @param database the name of the database to delete.
     */
    public void deleteDatabase(String database) {
        dropDatabase(database);
        redisTemplate.opsForSet().remove(DATABASES_KEY, database);
        if (database.equals(getCurrentDatabase())) {
            switchToDefaultDatabase();
        }
    }

    /**
     * Resets the current database to the default.
     */
    public void switchToDefaultDatabase() {
        redisTemplate.opsForValue().set(CURRENT_DATABASE_KEY, DEFAULT_DATABASE);
    }

    /**
     * Drops all keys associated with a specified database.
     *
     * @param database the name of the database whose keys are to be dropped.
     */
    public void dropDatabase(String database) {
        Set<String> keys = redisTemplate.keys(database + "*");
        if (keys != null) {
            for (String key : keys) {
                redisTemplate.delete(key);
            }
        }
    }

    /**
     * Drops all keys associated with the default database.
     */
    public void dropDefaultDatabase() {
        dropDatabase(DEFAULT_DATABASE);
    }
}
