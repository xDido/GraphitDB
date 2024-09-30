package dev.graphitdb.Redis.Data.Node;


import dev.graphitdb.Core.DataStructure.Node.Node;
import org.springframework.cache.annotation.Cacheable;
public interface RedisNode {

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



}
