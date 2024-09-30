package dev.graphitdb.Redis.Data.Edge;

import dev.graphitdb.Core.DataStructure.Edge.Edge;
import org.springframework.cache.annotation.Cacheable;

public interface RedisEdge {
    // Check if an edge exists between two nodes
    public boolean isEdgeExists(String sourceNodeId, String destinationNodeId, String label, boolean isOutgoing) throws Exception;

    // Save an edge, returns a boolean indicating success or failure
    public boolean saveEdge(Edge edge, boolean isOutgoing) throws Exception;

    // Delete an edge by its source and destination IDs, returns a boolean indicating success or failure
    public boolean deleteEdge(String sourceNodeId, String destinationNodeId, String label, boolean isOutgoing) throws Exception;


    @Cacheable(value = "edges", key = "#sourceNodeId + #destinationNodeId + #label + #isOutgoing")
    Edge getEdge(String sourceNodeId, String destinationNodeId, String label, boolean isOutgoing) throws Exception;

    @Cacheable(value = "edges", key = "#edgeId")
    Edge getEdgeById(String edgeId) throws Exception;


    public Iterable<Edge> getEdges(String associatedNodeId, boolean isOutgoing, int page, int size) throws Exception;

    // Delete all edges associated with a node
    public void deleteEdges(String associatedNodeId, boolean isOutgoing) throws Exception;
}
