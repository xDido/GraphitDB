package dev.graphitdb.Redis.Interfaces;


import dev.graphitdb.Core.DataStructure.Edge.Edge;
import dev.graphitdb.Core.DataStructure.Node.Node;

public interface DataAccessor {

    // Save a node, returns a boolean indicating success or failure
    public boolean saveNode(Node node) throws Exception;

    // Get a node by its ID, returns null if not found
    public Node getNode(String nodeId) throws Exception;

    // Delete all nodes
    public void deleteAllNodes() throws Exception;

    // Delete a node by its ID, returns a boolean indicating success or failure
    public boolean deleteNode(String nodeId) throws Exception;

    // Count total nodes
    public long countNodes() throws Exception;

    // Get IDs of all nodes, supports pagination
    public Iterable<String> getAllNodeIds(int page, int size) throws Exception;

    // Get nodes by a list of IDs, returns a collection of nodes
    public Iterable<Node> getNodesByIds(Iterable<String> ids) throws Exception;

    // Get all nodes, supports pagination
    public Iterable<Node> getAllNodes(int page, int size) throws Exception;

    // Check if a node exists by its ID
    public boolean isNodeExists(String nodeId) throws Exception;

    // Check if an edge exists between two nodes
    public boolean isEdgeExists(String sourceNodeId, String destinationNodeId, String label, boolean isOutgoing) throws Exception;

    // Save an edge, returns a boolean indicating success or failure
    public boolean saveEdge(Edge edge, boolean isOutgoing) throws Exception;

    // Delete an edge by its source and destination IDs, returns a boolean indicating success or failure
    public boolean deleteEdge(String sourceNodeId, String destinationNodeId, String label, boolean isOutgoing) throws Exception;

    // Get an edge by its source and destination IDs
    public Edge getEdge(String sourceNodeId, String destinationNodeId, String label, boolean isOutgoing) throws Exception;

    // Get edges associated with a node, supports pagination
    public Iterable<Edge> getEdges(String associatedNodeId, boolean isOutgoing, int page, int size) throws Exception;

    // Delete all edges associated with a node
    public void deleteEdges(String associatedNodeId, boolean isOutgoing) throws Exception;
}
