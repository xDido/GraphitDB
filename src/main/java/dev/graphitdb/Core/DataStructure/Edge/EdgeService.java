package dev.graphitdb.Core.DataStructure.Edge;

import dev.graphitdb.Core.Exceptions.Edge.EdgeNotFoundException;
import dev.graphitdb.Core.Exceptions.Node.NodeNotFoundException;

import java.util.List;

/**
 * EdgeService defines operations for managing edges within a graph.
 * This includes creating, retrieving, updating, deleting edges,
 * and performing graph-specific operations like traversals and counting edges.
 */
public interface EdgeService {

    /**
     * Creates a new edge between two nodes with a specified label and properties.
     *
     * @param sourceId      The ID of the source node.
     * @param destinationId The ID of the destination node.
     * @param label         The label for the edge.
     * @param properties    Additional properties of the edge.
     */
    void createEdge(String sourceId, String destinationId, String label, Object properties) throws NodeNotFoundException;

    /**
     * Deletes an edge by its unique identifier.
     *
     * @param id The unique ID of the edge to delete.
     * @throws EdgeNotFoundException If no edge with the specified ID exists.
     */
    void deleteEdge(String id) throws EdgeNotFoundException;

    /**
     * Deletes an edge by its source, destination, and label.
     *
     * @param sourceNodeId      The ID of the source node.
     * @param destinationNodeId The ID of the destination node.
     * @param label             The label of the edge.
     * @param isOutgoing        Whether the edge is outgoing from the source node.
     * @throws EdgeNotFoundException If no edge with the specified criteria exists.
     */
    void deleteEdge(String sourceNodeId, String destinationNodeId, String label, boolean isOutgoing) throws NodeNotFoundException;


    /**
     * Updates an existing edge identified by its ID, source, destination, and label.
     *
     * @param edgeId        The ID of the edge to update.
     * @param sourceId      The ID of the source node.
     * @param destinationId The ID of the destination node.
     * @param label         The label of the edge.
     * @param properties    The updated properties for the edge.
     */
    void updateEdge(String edgeId, String sourceId, String destinationId, String label, Object properties);

    Edge getEdge(String edgeId);


    List<Edge> getEdgesById(String edgeId);

    /**
     * Retrieves edges by their IDs.
     *
     * @param edgeIds The iterable of edge IDs to retrieve.
     * @return An iterable collection of edges with the specified IDs.
     * @throws Exception If any edge cannot be retrieved.
     */
    Iterable<Edge> getEdgesById(Iterable<String> edgeIds) throws Exception;

    /**
     * Retrieves outgoing edges for a given node ID.
     *
     * @param nodeId The iterable of node IDs for which to retrieve outgoing edges.
     * @return An iterable collection of outgoing edges for the specified node IDs.
     * @throws Exception If any edge cannot be retrieved.
     */
    Iterable<Edge> getOutgoingEdges(String nodeId) throws Exception;

    Iterable<Edge> getIncomingEdges(String nodeId) throws Exception;

    void updateEdgeProperties(String edgeId, Object properties);

}
