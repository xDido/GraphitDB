package dev.graphitdb.Redis.Data.Node;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

import dev.graphitdb.Core.Exceptions.Redis.RedisNodeException;
import dev.graphitdb.Redis.Database.RedisDatabaseManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import dev.graphitdb.Core.DataStructure.Node.Node;

/**
 * Implementation of the DataAccessor interface for managing graph nodes and edges in Redis.
 */
@Component
public class RedisNodeManager implements RedisNode {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private RedisDatabaseManager redisDatabaseManager;

    private final String NODE_HASH = "Node";
    private final String EDGE_HASH = "Edge ";
    private final String OUTGOING_EDGE_HASH = "OutgoingEdges";
    private final String INCOMING_EDGE_HASH = "IncomingEdges";

    private final Lock lock = new ReentrantLock();

    /**
     * Saves a node in the Redis database.
     *
     * @param node The node to save.
     * @return true if the node was saved successfully, false otherwise.
     */
    @Override
    @CachePut(value = "nodes", key = "#node.id")
    public boolean saveNode(Node node) throws RedisNodeException {
        lock.lock();
        try {
            redisTemplate.opsForHash().put(getPrefix(NODE_HASH), node.getId(), node);
            return true;
        } catch (Exception e) {
            throw new RedisNodeException("save", node.getId(), e.getCause());  // Throwing custom exception
        } finally {
            lock.unlock();
        }
    }

    /**
     * Retrieves a node by its ID from the Redis database.
     *
     * @param nodeId The ID of the node to retrieve.
     * @return The node, or null if not found.
     */
    @Override
    @Cacheable(value = "nodes", key = "#nodeId")
    public Node getNode(String nodeId) throws Exception {
        try {
            return (Node) redisTemplate.opsForHash().get(getPrefix(NODE_HASH), nodeId);
        } catch (Exception e) {
            throw new RedisNodeException("delete", nodeId, e.getCause());
        }
    }

    /**
     * Deletes all nodes from the Redis database.
     */
    @Override
    public void deleteAllNodes() throws Exception {
        lock.lock();
        try {
            redisTemplate.delete(getPrefix(NODE_HASH));
        } catch (Exception e) {
            throw new RedisNodeException(String.format("Error deleting all nodes %s}", e.getMessage()), e.getCause());
        } finally {
            lock.unlock();
        }
    }

    /**
     * Deletes a node by its ID from the Redis database.
     *
     * @param nodeId The ID of the node to delete.
     * @return true if the node was deleted successfully, false otherwise.
     */
    @Override
    public boolean deleteNode(String nodeId) throws RedisNodeException {
        lock.lock();
        try {
            redisTemplate.opsForHash().delete(getPrefix(NODE_HASH), nodeId);
            return true;
        } catch (Exception e) {
            throw new RedisNodeException("delete", nodeId, e.getCause());
        } finally {
            lock.unlock();
        }
    }

    /**
     * Counts the total number of nodes in the Redis database.
     *
     * @return The number of nodes.
     */
    @Override
    public long countNodes() throws Exception {
        try {
            return redisTemplate.opsForHash().size(getPrefix(NODE_HASH));
        } catch (Exception e) {
            System.err.println(String.format("Error counting nodes: %s", e.getMessage()));
            return 0;
        }
    }

    /**
     * Retrieves all node IDs, applying pagination.
     *
     * @param page The page number.
     * @param size The number of IDs per page.
     * @return A list of node IDs for the specified page.
     */
    @Override
    public Iterable<String> getAllNodeIds(int page, int size) throws RedisNodeException {
        try {
            Set<Object> keys = redisTemplate.opsForHash().keys(getPrefix(NODE_HASH));
            List<String> ids = keys.stream()
                    .map(Object::toString)
                    .collect(Collectors.toList());

            int fromIndex = Math.min(page * size, ids.size());
            int toIndex = Math.min(fromIndex + size, ids.size());
            return ids.subList(fromIndex, toIndex);
        } catch (Exception e) {
            throw new RedisNodeException(String.format("Error retrieving all node IDs: %s", e.getMessage()), e.getCause());

        }
    }

    /**
     * Retrieves nodes by their IDs.
     *
     * @param ids The IDs of the nodes to retrieve.
     * @return A list of nodes.
     */
    @Override
    public Iterable<Node> getNodesByIds(Iterable<String> ids) throws Exception {
        List<Node> nodes = new ArrayList<>();
        for (String id : ids) {
            Node node = getNode(id);
            if (node != null) {
                nodes.add(node);
            }
        }
        return nodes;
    }

    /**
     * Retrieves all nodes, applying pagination.
     *
     * @param page The page number.
     * @param size The number of nodes per page.
     * @return A list of nodes for the specified page.
     */
    @Override
    public Iterable<Node> getAllNodes(int page, int size) throws Exception {

        return getNodesByIds(getAllNodeIds(page, size));
    }

    /**
     * Checks if a node exists by its ID.
     *
     * @param nodeId The ID of the node.
     * @return true if the node exists, false otherwise.
     */
    @Override
    public boolean isNodeExists(String nodeId) throws RedisNodeException {
        try {
            return redisTemplate.opsForHash().hasKey(getPrefix(NODE_HASH), nodeId);
        } catch (Exception e) {

            throw new RedisNodeException(String.format("Error checking node existence: %s", nodeId), e.getCause());
        }
    }

    /**
     * Constructs the key prefix for Redis based on the current database name.
     *
     * @param name The base name to use for the key.
     * @return The constructed key with the database name prefix.
     */
    private String getPrefix(String name) {
        String databaseName = redisDatabaseManager.getCurrentDatabase();
        return String.format("%s:%s", databaseName, name);
    }
}
