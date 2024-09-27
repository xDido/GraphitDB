package dev.graphitdb.Core.Database;

import dev.graphitdb.Core.Distribute.PartitionManager;
import dev.graphitdb.Core.Exceptions.Database.DatabaseAlreadyExistException;
import dev.graphitdb.Core.Exceptions.Database.DatabaseNotFoundException;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Logger;
import java.util.List;

/**
 * DistributedDatabaseService is responsible for managing databases across
 * the entire distributed system. It coordinates operations with multiple
 * LocalDatabaseService instances across nodes in a non-blocking manner
 * using asynchronous operations.
 */
public class DistributedDatabaseService implements DatabaseService {

    private LocalDatabaseService localDatabaseService;

    @Override
    public void switchDatabase(String database) throws DatabaseNotFoundException {
        Logger logger = Logger.getLogger(DistributedDatabaseService.class.getName());

        int numberOfServers = PartitionManager.getNumberOfServers();
        int currentServerId = Integer.parseInt(PartitionManager.getServerId());

        // Create a list of CompletableFutures for parallel execution
        List<CompletableFuture<Void>> futures = new ArrayList<>();

        for (int i = 0; i < numberOfServers; i++) {
            if (i == currentServerId) {
                // Local server - switch the database directly
                try {
                    localDatabaseService.switchDatabase(database);
                    logger.info("Switched database locally to: " + database);
                } catch (DatabaseNotFoundException e) {
                    logger.severe("Database not found locally: " + e.getMessage());
                    throw e;
                }
            } else {
                // Remote servers - switch database asynchronously
                final int serverId = i;
                CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                    try {
                        localDatabaseService.switchDatabase(database);
                        logger.info("Switched database on server " + serverId + " to: " + database);
                    } catch (Exception e) {
                        logger.severe("Failed to switch database on server " + serverId + ": " + e.getMessage());
                    }
                });
                futures.add(future);
            }
        }

        // Wait for all remote operations to complete
        CompletableFuture<Void> allOf = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
        try {
            allOf.get();
        } catch (Exception e) {
            logger.severe("Error waiting for all database switches: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public void createDatabase(String database) throws DatabaseAlreadyExistException {
        Logger logger = Logger.getLogger(DistributedDatabaseService.class.getName());
        int numberOfServers = PartitionManager.getNumberOfServers();
        int currentServerId = Integer.parseInt(PartitionManager.getServerId());

        List<CompletableFuture<Void>> futures = new ArrayList<>();

        for (int i = 0; i < numberOfServers; i++) {
            if (i == currentServerId) {
                try {
                    localDatabaseService.createDatabase(database);
                    logger.info("Created database locally: " + database);
                } catch (DatabaseAlreadyExistException e) {
                    logger.severe("Database already exists locally: " + e.getMessage());
                    throw e;
                }
            } else {
                final int serverId = i;
                CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                    try {
                        localDatabaseService.createDatabase(database);
                        logger.info("Created database on server " + serverId + ": " + database);
                    } catch (Exception e) {
                        logger.severe("Failed to create database on server " + serverId + ": " + e.getMessage());
                    }
                });
                futures.add(future);
            }
        }

        CompletableFuture<Void> allOf = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
        try {
            allOf.get();
        } catch (Exception e) {
            logger.severe("Error waiting for all database creations: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteDatabase(String database) throws DatabaseNotFoundException {
        Logger logger = Logger.getLogger(DistributedDatabaseService.class.getName());
        int numberOfServers = PartitionManager.getNumberOfServers();
        int currentServerId = Integer.parseInt(PartitionManager.getServerId());

        List<CompletableFuture<Void>> futures = new ArrayList<>();

        for (int i = 0; i < numberOfServers; i++) {
            if (i == currentServerId) {
                try {
                    localDatabaseService.deleteDatabase(database);
                    logger.info("Deleted database locally: " + database);
                } catch (DatabaseNotFoundException e) {
                    logger.severe("Database not found locally: " + e.getMessage());
                    throw e;
                }
            } else {
                final int serverId = i;
                CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                    try {
                        localDatabaseService.deleteDatabase(database);
                        logger.info("Deleted database on server " + serverId + ": " + database);
                    } catch (Exception e) {
                        logger.severe("Failed to delete database on server " + serverId + ": " + e.getMessage());
                    }
                });
                futures.add(future);
            }
        }

        CompletableFuture<Void> allOf = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
        try {
            allOf.get();
        } catch (Exception e) {
            logger.severe("Error waiting for all database deletions: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public void dropDatabase(String database) throws DatabaseNotFoundException {
        Logger logger = Logger.getLogger(DistributedDatabaseService.class.getName());
        int numberOfServers = PartitionManager.getNumberOfServers();
        int currentServerId = Integer.parseInt(PartitionManager.getServerId());

        List<CompletableFuture<Void>> futures = new ArrayList<>();

        for (int i = 0; i < numberOfServers; i++) {
            if (i == currentServerId) {
                try {
                    localDatabaseService.dropDatabase(database);
                    logger.info("Dropped database locally: " + database);
                } catch (DatabaseNotFoundException e) {
                    logger.severe("Database not found locally: " + e.getMessage());
                    throw e;
                }
            } else {
                final int serverId = i;
                CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                    try {
                        localDatabaseService.dropDatabase(database);
                        logger.info("Dropped database on server " + serverId + ": " + database);
                    } catch (Exception e) {
                        logger.severe("Failed to drop database on server " + serverId + ": " + e.getMessage());
                    }
                });
                futures.add(future);
            }
        }

        CompletableFuture<Void> allOf = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
        try {
            allOf.get();
        } catch (Exception e) {
            logger.severe("Error waiting for all database drops: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getCurrentDatabase() {
        // Return the global state of the current database, possibly a union of all local instances
        return localDatabaseService.getCurrentDatabase();
    }

    @Override
    public void switchToDefaultDatabase() {
        Logger logger = Logger.getLogger(DistributedDatabaseService.class.getName());
        int numberOfServers = PartitionManager.getNumberOfServers();
        int currentServerId = Integer.parseInt(PartitionManager.getServerId());

        List<CompletableFuture<Void>> futures = new ArrayList<>();

        for (int i = 0; i < numberOfServers; i++) {
            if (i == currentServerId) {
                localDatabaseService.switchToDefaultDatabase();
                logger.info("Switched to default database locally");
            } else {

                final int serverId = i;
                CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                    try {
                        localDatabaseService.switchToDefaultDatabase();
                        logger.info("Switched to default database on server " + serverId);
                    } catch (Exception e) {
                        logger.severe("Failed to switch to default database on server " + serverId + ": " + e.getMessage());
                    }
                });
                futures.add(future);
            }
        }

        CompletableFuture<Void> allOf = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
        try {
            allOf.get();
        } catch (Exception e) {
            logger.severe("Error waiting for all default database switches: " + e.getMessage());
        }
    }

    @Override
    public void dropDefaultDatabase() {
        Logger logger = Logger.getLogger(DistributedDatabaseService.class.getName());
        int numberOfServers = PartitionManager.getNumberOfServers();
        int currentServerId = Integer.parseInt(PartitionManager.getServerId());

        List<CompletableFuture<Void>> futures = new ArrayList<>();

        for (int i = 0; i < numberOfServers; i++) {
            if (i == currentServerId) {
                localDatabaseService.dropDefaultDatabase();
                logger.info("Dropped default database locally");
            } else {
                CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                    try {
                        localDatabaseService.dropDefaultDatabase();
                        logger.info("Dropped default database on server ");
                    } catch (Exception e) {
                        logger.severe("Failed to drop default database on server " + e.getMessage());
                    }
                });
                futures.add(future);
            }
        }

        CompletableFuture<Void> allOf = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
        try {
            allOf.get();
        } catch (Exception e) {
            logger.severe("Error waiting for all default database drops: " + e.getMessage());
        }
    }
}
