package dev.graphitdb.Redis.Interfaces;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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

import dev.graphitdb.Core.DataStructure.Edge.Edge;
import dev.graphitdb.Core.DataStructure.Node.Node;

/**
 * Implementation of the DataAccessor interface for managing graph nodes and edges in Redis.
 */
@Component
public class RedisDataManager implements DataAccessor {

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
            throw new RedisNodeException(STR."Error deleting all nodes: \{e.getMessage()}", e.getCause());
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
            System.err.println(STR."Error counting nodes: \{e.getMessage()}");
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
            throw new RedisNodeException(STR."Error retrieving all node IDs: \{e.getMessage()}", e.getCause());
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
            throw new RedisNodeException(STR."Error checking node existence: \{nodeId}\{e.getCause()}");
        }
    }

    /**
     * Checks if an edge exists between two nodes.
     *
     * @param sourceNodeId      The source node ID.
     * @param destinationNodeId The destination node ID.
     * @param label             The label of the edge.
     * @param isOutgoing        Indicates if the edge is outgoing.
     * @return true if the edge exists, false otherwise.
     */
    @Override
    public boolean isEdgeExists(String sourceNodeId, String destinationNodeId, String label, boolean isOutgoing) throws Exception {
        String setKey = isOutgoing ? getPrefix(STR."\{OUTGOING_EDGE_HASH}:\{sourceNodeId}") : getPrefix(INCOMING_EDGE_HASH + ":" + destinationNodeId);
        Edge edge = new Edge(sourceNodeId, destinationNodeId);
        edge.setLabel(label);
        try {
            return Boolean.TRUE.equals(redisTemplate.opsForSet().isMember(setKey, edge));
        } catch (Exception e) {
            System.err.println(STR."Error checking edge existence: \{e.getMessage()}");
            return false;
        }
    }

    /**
     * Saves an edge in the Redis database.
     *
     * @param edge       The edge to save.
     * @param isOutgoing Indicates if the edge is outgoing.
     * @return true if the edge was saved successfully, false otherwise.
     */
    @Override
    public boolean saveEdge(Edge edge, boolean isOutgoing) throws Exception {
        String setKey = isOutgoing ? getPrefix(STR."\{OUTGOING_EDGE_HASH}:\{edge.getSourceNodeId()}") : getPrefix(STR."\{INCOMING_EDGE_HASH}:\{edge.getDestinationNodeId()}");
        lock.lock();
        try {
            redisTemplate.opsForSet().add(setKey, edge);
            return true;
        } catch (Exception e) {
            System.err.println(STR."Error saving edge: \{e.getMessage()}");
            return false;
        } finally {
            lock.unlock();
        }
    }

    /**
     * Deletes an edge between two nodes.
     *
     * @param sourceNodeId      The source node ID.
     * @param destinationNodeId The destination node ID.
     * @param label             The label of the edge.
     * @param isOutgoing        Indicates if the edge is outgoing.
     * @return true if the edge was deleted successfully, false otherwise.
     */
    @Override
    public boolean deleteEdge(String sourceNodeId, String destinationNodeId, String label, boolean isOutgoing) throws Exception {
        String setKey = isOutgoing ? getPrefix(STR."\{OUTGOING_EDGE_HASH}:\{sourceNodeId}") : getPrefix(STR."\{INCOMING_EDGE_HASH}:\{destinationNodeId}");
        Edge edge = new Edge(sourceNodeId, destinationNodeId);
        edge.setLabel(label);
        lock.lock();
        try {
            redisTemplate.opsForSet().remove(setKey, edge);
            return true;
        } catch (Exception e) {
            System.err.println(STR."Error deleting edge: \{e.getMessage()}");
            return false;
        } finally {
            lock.unlock();
        }
    }

    /**
     * Retrieves an edge between two nodes.
     *
     * @param sourceNodeId      The source node ID.
     * @param destinationNodeId The destination node ID.
     * @param label             The label of the edge.
     * @param isOutgoing        Indicates if the edge is outgoing.
     * @return The edge, or null if not found.
     */
    @Override
    @Cacheable(value = "edges", key = "#sourceNodeId + #destinationNodeId + #label + #isOutgoing")
    public Edge getEdge(String sourceNodeId, String destinationNodeId, String label, boolean isOutgoing) throws Exception {
        String setKey = isOutgoing ? getPrefix(STR."\{OUTGOING_EDGE_HASH}:\{sourceNodeId}") : getPrefix(STR."\{INCOMING_EDGE_HASH}:\{destinationNodeId}");
        Edge edge = new Edge(sourceNodeId, destinationNodeId);
        edge.setLabel(label);
        try {
            return (Edge) Objects.requireNonNull(redisTemplate.opsForSet().members(setKey)).stream().filter(e -> e.equals(edge)).findFirst().orElse(null);
        } catch (Exception e) {
            System.err.println(STR."Error retrieving edge: \{e.getMessage()}");
            return null;
        }
    }


    /**
     * Retrieves an edge by its ID.
     *
     * @param edgeId The unique ID of the edge.
     * @return The edge, or null if not found.
     */
    @Cacheable(value = "edges", key = "#edgeId")
    @Override
    public Edge getEdgeById(String edgeId) throws Exception {
        String setKey = getPrefix(STR."{EDGE_HASH}:\{edgeId}");
        try {
            return redisTemplate.opsForSet().members(setKey).stream()
                    .filter(member -> member instanceof Edge)
                    .map(member -> (Edge) member)
                    .filter(e -> e.getId().equals(edgeId))
                    .findFirst()
                    .orElse(null);
        } catch (Exception e) {
            System.err.println(STR."Error retrieving edge: \{e.getMessage()}");
            return null;
        }

    }

    /**
     * Retrieves edges associated with a node, applying pagination.
     *
     * @param associatedNodeId The ID of the associated node.
     * @param isOutgoing       Indicates if the edges are outgoing.
     * @param page             The page number.
     * @param size             The number of edges per page.
     * @return A list of edges for the specified page.
     */
    @Override
    public Iterable<Edge> getEdges(String associatedNodeId, boolean isOutgoing, int page, int size) throws Exception {
        String setKey = isOutgoing ? getPrefix(STR."\{OUTGOING_EDGE_HASH}:\{associatedNodeId}") : getPrefix(STR."\{INCOMING_EDGE_HASH}:\{associatedNodeId}");
        try {
            Set<Object> members = redisTemplate.opsForSet().members(setKey);
            if (members == null || members.isEmpty()) {
                return new ArrayList<>();
            }
            List<Edge> edges = members.stream()
                    .map(edge -> (Edge) edge)
                    .collect(Collectors.toList());

            int fromIndex = Math.min(page * size, edges.size());
            int toIndex = Math.min(fromIndex + size, edges.size());
            return edges.subList(fromIndex, toIndex);
        } catch (Exception e) {
            System.err.println(STR."Error retrieving edges: \{e.getMessage()}");
            return new ArrayList<>();
        }
    }

    /**
     * Deletes an edge by its ID.
     *
     * @param edgeId The ID of the edge to delete.
     */
    public void deleteEdgeById(String edgeId) {
        String setKey = getPrefix(STR."{EDGE_HASH}:\{edgeId}");
        lock.lock();
        try {
            redisTemplate.delete(setKey);
        } catch (Exception e) {
            System.err.println(STR."Error deleting edge: \{e.getMessage()}");
        } finally {
            lock.unlock();
        }
    }

    /**
     * Deletes all edges associated with a node.
     *
     * @param nodeId     The ID of the node.
     * @param isOutgoing Indicates if the edges to delete are outgoing.
     */
    public void deleteEdges(String nodeId, boolean isOutgoing) {
        String setKey = isOutgoing ? getPrefix(STR."\{OUTGOING_EDGE_HASH}:\{nodeId}") : getPrefix(STR."\{INCOMING_EDGE_HASH}:\{nodeId}");
        lock.lock();
        try {
            redisTemplate.delete(setKey);
        } catch (Exception e) {
            System.err.println(STR."Error deleting edges: \{e.getMessage()}");
        } finally {
            lock.unlock();
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
        return STR."\{databaseName}:\{name}";
    }
}
