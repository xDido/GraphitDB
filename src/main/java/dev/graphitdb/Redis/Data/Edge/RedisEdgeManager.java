package dev.graphitdb.Redis.Data.Edge;

import dev.graphitdb.Redis.Database.RedisDatabaseManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import dev.graphitdb.Core.DataStructure.Edge.Edge;


@Component
public class RedisEdgeManager implements RedisEdge {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private RedisDatabaseManager redisDatabaseManager;

    private final String OUTGOING_EDGE_HASH = "OutgoingEdges";
    private final String INCOMING_EDGE_HASH = "IncomingEdges";
    private final String EDGE_HASH = "Edge ";
    private final Lock lock = new ReentrantLock();

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
        String setKey = String.format("%s:%s",
                isOutgoing ? getPrefix(OUTGOING_EDGE_HASH) : getPrefix(INCOMING_EDGE_HASH),
                isOutgoing ? sourceNodeId : destinationNodeId);
        Edge edge = new Edge(sourceNodeId, destinationNodeId);
        edge.setLabel(label);
        try {
            return Boolean.TRUE.equals(redisTemplate.opsForSet().isMember(setKey, edge));
        } catch (Exception e) {
            System.err.println("Error checking edge existence: " + e.getMessage());
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
        String setKey = isOutgoing
                ? getPrefix(String.format("%s:%s", OUTGOING_EDGE_HASH, edge.getSourceNodeId()))
                : getPrefix(String.format("%s:%s", INCOMING_EDGE_HASH, edge.getDestinationNodeId()));
        lock.lock();
        try {
            redisTemplate.opsForSet().add(setKey, edge);
            return true;
        } catch (Exception e) {
            System.err.println(String.format("Error saving edge: %s", e.getMessage()));
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
        String setKey = isOutgoing
                ? getPrefix(String.format("%s:%s", OUTGOING_EDGE_HASH, sourceNodeId))
                : getPrefix(String.format("%s:%s", INCOMING_EDGE_HASH, destinationNodeId));
        Edge edge = new Edge(sourceNodeId, destinationNodeId);
        edge.setLabel(label);
        lock.lock();
        try {
            redisTemplate.opsForSet().remove(setKey, edge);
            return true;
        } catch (Exception e) {
            System.err.println(String.format("Error deleting edge: %s", e.getMessage()));
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
        String setKey = isOutgoing
                ? getPrefix(String.format("%s:%s", OUTGOING_EDGE_HASH, sourceNodeId))
                : getPrefix(String.format("%s:%s", INCOMING_EDGE_HASH, destinationNodeId));
        Edge edge = new Edge(sourceNodeId, destinationNodeId);
        edge.setLabel(label);
        try {
            return (Edge) Objects.requireNonNull(redisTemplate.opsForSet().members(setKey)).stream().filter(e -> e.equals(edge)).findFirst().orElse(null);
        } catch (Exception e) {
            System.err.println(String.format("Error retrieving edge: %s", e.getMessage()));
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
        String setKey = getPrefix(String.format("%s:%s", EDGE_HASH, edgeId));
        try {
            return redisTemplate.opsForSet().members(setKey).stream()
                    .filter(member -> member instanceof Edge)
                    .map(member -> (Edge) member)
                    .filter(e -> e.getId().equals(edgeId))
                    .findFirst()
                    .orElse(null);
        } catch (Exception e) {
            System.err.println(String.format("Error retrieving edge: %s", e.getMessage()));
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
        String setKey = isOutgoing
                ? getPrefix(String.format("%s:%s", OUTGOING_EDGE_HASH, associatedNodeId))
                : getPrefix(String.format("%s:%s", INCOMING_EDGE_HASH, associatedNodeId));
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
            System.err.println(String.format("Error retrieving edges: %s", e.getMessage()));
            return new ArrayList<>();
        }
    }

    /**
     * Deletes an edge by its ID.
     *
     * @param edgeId The ID of the edge to delete.
     */
    public void deleteEdgeById(String edgeId) {
        String setKey = getPrefix(String.format("%s:%s", EDGE_HASH, edgeId));
        lock.lock();
        try {
            redisTemplate.delete(setKey);
        } catch (Exception e) {
            System.err.println(String.format("Error deleting edge: %s", e.getMessage()));
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
        String setKey = isOutgoing
                ? getPrefix(String.format("%s:%s", OUTGOING_EDGE_HASH, nodeId))
                : getPrefix(String.format("%s:%s", INCOMING_EDGE_HASH, nodeId));
        lock.lock();
        try {
            redisTemplate.delete(setKey);
        } catch (Exception e) {
            System.err.println(String.format("Error deleting edges: %s", e.getMessage()));
        } finally {
            lock.unlock();
        }
    }
}
