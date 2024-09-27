package dev.graphitdb.Redis.Indices;

/**
 * Interface for managing index data in a Redis datastore.
 * Provides methods for creating, deleting, and accessing indices,
 * as well as associating nodes with specific indices.
 */
public interface IndexDataManager {

    /**
     * Checks if the specified index exists in the Redis datastore.
     *
     * @param indexName the name of the index to check
     * @return true if the index exists, false otherwise
     */
    boolean isIndexExist(String indexName);

    /**
     * Creates a new index in the Redis datastore.
     *
     * @param indexName the name of the index to create
     */
    void createIndex(String indexName);

    /**
     * Deletes the specified index from the Redis datastore.
     *
     * @param index the name of the index to delete
     */
    void deleteIndex(String index);

    /**
     * Adds a node to the specified index.
     *
     * @param fieldName the field name of the index
     * @param fieldValue the value of the field
     * @param vertexId the ID of the node to associate with the index
     */
    void addNodeToIndex(String fieldName, String fieldValue, String vertexId);

    /**
     * Removes a node from the specified index.
     *
     * @param fieldName the field name of the index
     * @param fieldValue the value of the field
     * @param vertexId the ID of the node to disassociate from the index
     */
    void removeNodeFromIndex(String fieldName, String fieldValue, String vertexId);

    /**
     * Retrieves the IDs of nodes associated with the specified index.
     *
     * @param indexName the name of the index
     * @param fieldValue the value of the field
     * @return an iterable of node IDs associated with the index
     */
    Iterable<String> getNodeIdsFromIndex(String indexName, String fieldValue);

    /**
     * Checks if the specified node exists in the given index.
     *
     * @param indexName the name of the index
     * @param fieldValue the value of the field
     * @param nodeId the ID of the node to check
     * @return true if the node exists in the index, false otherwise
     */
    boolean isIndexContainsNode(String indexName, String fieldValue, String nodeId);
}
