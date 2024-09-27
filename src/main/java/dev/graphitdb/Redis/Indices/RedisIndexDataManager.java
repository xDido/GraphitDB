package dev.graphitdb.Redis.Indices;

import dev.graphitdb.Redis.Database.RedisDatabaseManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
/**
 * RedisIndexDataManager is a component that manages secondary indices in a Redis datastore.
 * It provides methods to create, delete, and manage indices as well as to associate nodes with specific indices.
 */
@Component
public class RedisIndexDataManager implements IndexDataManager {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private RedisDatabaseManager redisDatabaseManager;

    /**
     * Checks if the specified index exists in the Redis datastore.
     *
     * @param indexName the name of the index to check
     * @return true if the index exists, false otherwise
     */
    @Override
    public boolean isIndexExist(String indexName) {
        return Objects.requireNonNull(redisTemplate.opsForList().range(getSecondaryIndicesListName(), 0, -1)).contains(indexName);
    }

    /**
     * Creates a new index by adding it to the list of secondary indices.
     *
     * @param indexName the name of the index to create
     */
    @Override
    public void createIndex(String indexName) {
        redisTemplate.opsForList().leftPush(getSecondaryIndicesListName(), indexName);
    }

    /**
     * Deletes the specified index from the Redis datastore.
     * This method also removes all associated keys.
     *
     * @param indexName the name of the index to delete
     */
    @Override
    public void deleteIndex(String indexName) {
        redisTemplate.opsForList().remove(getSecondaryIndicesListName(), 0, indexName);
        Set<String> keys = redisTemplate.keys(getPrefix(indexName) + "*");
        assert keys != null;
        for (String key : keys) {
            redisTemplate.delete(key);
        }
    }

    /**
     * Adds a node to the specified index.
     *
     * @param fieldName the field name of the index
     * @param fieldValue the value of the field
     * @param nodeId the ID of the node to associate with the index
     */
    @Override
    public void addNodeToIndex(String fieldName, String fieldValue, String nodeId) {
        String indexName = fieldName + ":" + fieldValue;
        redisTemplate.opsForSet().add(getPrefix(indexName), nodeId);
    }

    /**
     * Removes a node from the specified index.
     *
     * @param fieldName the field name of the index
     * @param fieldValue the value of the field
     * @param nodeId the ID of the node to disassociate from the index
     */
    @Override
    public void removeNodeFromIndex(String fieldName, String fieldValue, String nodeId) {
        String indexName = fieldName + ":" + fieldValue;
        redisTemplate.opsForSet().remove(getPrefix(indexName), nodeId);
    }

    /**
     * Retrieves the IDs of nodes associated with the specified index.
     *
     * @param indexName the name of the index
     * @param fieldValue the value of the field
     * @return an iterable of node IDs associated with the index
     */
    @Override
    public Iterable<String> getNodeIdsFromIndex(String indexName, String fieldValue) {
        String index = indexName + ":" + fieldValue;
        return Objects.requireNonNull(redisTemplate.opsForSet().members(getPrefix(index))).stream()
                .map(key -> (String) key)
                .collect(Collectors.toList());
    }
    /**
     * Filters and returns the indices that do not exist.
     *
     * @param indexNames an iterable of index names to check
     * @return an iterable of index names that do not exist
     */
    public Iterable<String> filterNotExistingIndices(Iterable<String> indexNames) {
        List<String> existingIndices = Objects.requireNonNull(redisTemplate.opsForList().range(getSecondaryIndicesListName(), 0, -1)).stream()
                .map(key -> (String) key).toList();
        List<String> notExistingIndices = new ArrayList<>();
        for (String indexName : indexNames) {
            if (!existingIndices.contains(indexName)) {
                notExistingIndices.add(indexName);
            }
        }
        return notExistingIndices;
    }

    /**
     * Retrieves all secondary indices from the Redis datastore.
     *
     * @return an iterable of all index names
     */
    public Iterable<String> getAllIndices() {
        return Objects.requireNonNull(redisTemplate.opsForList().range(getSecondaryIndicesListName(), 0, -1)).stream()
                .map(key -> (String) key)
                .collect(Collectors.toList());
    }

    /**
     * Constructs a prefix for the given index name based on the current database.
     *
     * @param indexName the name of the index
     * @return the prefixed index name
     */
    public String getPrefix(String indexName) {
        return redisDatabaseManager.getCurrentDatabase() + ":" + indexName;
    }

    /**
     * Retrieves the name of the secondary indices list.
     *
     * @return the name of the secondary indices list
     */
    public String getSecondaryIndicesListName() {
        String LIST_SECONDARY_INDEX_NAME = "secondaryIndex";
        return redisDatabaseManager.getCurrentDatabase() + ":" + LIST_SECONDARY_INDEX_NAME;
    }

    /**
     * Checks if the specified vertex exists in the given index.
     *
     * @param indexName the name of the index
     * @param fieldValue the value of the field
     * @param nodeId the ID of the node to check
     * @return true if the node exists in the index, false otherwise
     */
    @Override
    public boolean isIndexContainsNode(String indexName, String fieldValue, String nodeId) {
        String index = indexName + ":" + fieldValue;
        return Boolean.TRUE.equals(redisTemplate.opsForSet().isMember(getPrefix(index), nodeId));
    }
}
