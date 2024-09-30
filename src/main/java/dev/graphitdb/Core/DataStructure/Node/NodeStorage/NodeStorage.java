package dev.graphitdb.Core.DataStructure.Node.NodeStorage;

import dev.graphitdb.Core.DataStructure.Node.Node;
import dev.graphitdb.Core.Exceptions.Redis.RedisNodeException;
import dev.graphitdb.Redis.Data.Node.RedisNodeManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.Optional;

@Component
public class NodeStorage implements NodeRepository {

    @Autowired
    private RedisNodeManager redisNodeManager;

    public NodeStorage(RedisNodeManager redisNodeManager) {
        this.redisNodeManager = redisNodeManager;
    }

    /**
     * Save a node entity.
     *
     * @param entity the node entity to save
     * @return the saved node entity
     * @throws RedisNodeException if the save operation fails
     */
    @Override
    public <S extends Node> S save(S entity) {
        try {
            System.out.println("Saving node: " + entity);
            redisNodeManager.saveNode(entity);  // Custom Redis logic
            return entity;
        } catch (Exception e) {
            System.err.println("Failed to save node: " + e.getMessage());
        }
        return entity;
    }

    @Override
    public <S extends Node> Iterable<S> saveAll(Iterable<S> entities) {
        for (Node entity : entities) {
            save(entity);
        }
        return entities;
    }

    @Override
    public Optional<Node> findById(String id) {
        try {
            Node node = redisNodeManager.getNode(id);
            return Optional.ofNullable(node);
        } catch (Exception e) {
            System.err.println("Failed to find node by ID: " + e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public boolean existsById(String id) {
        return redisNodeManager.isNodeExists(id);
    }

    @Override
    public Iterable<Node> findAll() {
        try {
            return redisNodeManager.getAllNodes(5, 20);
        } catch (Exception e) {
            System.err.println("Failed to find all nodes: " + e.getMessage());
        }
        return new LinkedList<>();
    }

    @Override
    public Iterable<Node> findAllById(Iterable<String> ids) {
        try {
            return redisNodeManager.getNodesByIds(ids);
        } catch (Exception e) {
            System.err.println("Failed to find nodes by IDs: " + e.getMessage());
        }
        return new LinkedList<>();
    }

    @Override
    public long count() {
        try {
            return redisNodeManager.countNodes();
        } catch (Exception e) {
            System.err.println("Failed to count nodes: " + e.getMessage());
        }
        return 0;
    }

    @Override
    public void deleteById(String id) {
        redisNodeManager.deleteNode(id);
    }

    @Override
    public void delete(Node entity) {
        redisNodeManager.deleteNode(entity.getId()); // Custom logic to delete from Redis
    }

    @Override
    public void deleteAllById(Iterable<? extends String> ids) {
        for (String id : ids) {
            redisNodeManager.deleteNode(id);
        }
    }

    @Override
    public void deleteAll() {
        try {
            redisNodeManager.deleteAllNodes();
        } catch (Exception e) {
            System.err.println("Failed to delete all nodes: " + e.getMessage());
        }
    }

    @Override
    public void deleteAll(Iterable<? extends Node> entities) {
        throw new UnsupportedOperationException("Unimplemented method 'deleteAll'");
    }
}
