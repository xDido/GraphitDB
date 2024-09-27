package dev.graphitdb.Core.DataStructure.Edge;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serial;
import java.io.Serializable;
import java.util.*;

/**
 * Represents an edge in a graph connecting two nodes.
 * <p>
 * The edge has a source node, a destination node, a label, and a set of properties.
 * </p>
 */
@RedisHash("Edge")
public class Edge implements Serializable {

    @Serial
    private static final long serialVersionUID = -1839257342348572342L;


    private String edgeId;
    private String sourceNodeId;
    private String destinationNodeId;
    private String label;
    private Map<String, String> properties;

    /**
     * Constructs an Edge with the specified source and destination node IDs.
     * <p>
     * Initializes the label to an empty string and properties to an empty map.
     * </p>
     *
     * @param sourceNodeId      the ID of the source node
     * @param destinationNodeId the ID of the destination node
     * @throws IllegalArgumentException if sourceNodeId or destinationNodeId is null
     */
    public Edge(String sourceNodeId, String destinationNodeId) {
        if (sourceNodeId == null || destinationNodeId == null) {
            throw new IllegalArgumentException("Source and destination node IDs cannot be null");
        }
        this.edgeId = UUID.randomUUID().toString();
        this.sourceNodeId = sourceNodeId;
        this.destinationNodeId = destinationNodeId;
        this.label = "";
        this.properties = new HashMap<>();
    }

    /**
     * Constructs an Edge from JSON properties.
     * <p>
     * Initializes the label and properties based on the provided values. If any value is null, defaults are used.
     * </p>
     *
     * @param sourceNodeId      the ID of the source node
     * @param destinationNodeId the ID of the destination node
     * @param label             the label of the edge
     * @param properties        the properties of the edge
     * @throws IllegalArgumentException if sourceNodeId or destinationNodeId is null
     */
    @JsonCreator
    public Edge(@JsonProperty("sourceNodeId") String sourceNodeId,
                @JsonProperty("destinationNodeId") String destinationNodeId,
                @JsonProperty("label") String label,
                @JsonProperty("properties") Map<String, String> properties) {
        if (sourceNodeId == null || destinationNodeId == null) {
            throw new IllegalArgumentException("Source and destination node IDs cannot be null");
        }
        this.edgeId = UUID.randomUUID().toString();
        this.sourceNodeId = sourceNodeId;
        this.destinationNodeId = destinationNodeId;
        this.label = label != null ? label : "";
        this.properties = properties != null ? new HashMap<>(properties) : new HashMap<>();
    }

    /**
     * Gets the ID of the source node.
     *
     * @return the source node ID
     */
    public String getSourceNodeId() {
        return sourceNodeId;
    }

    /**
     * Gets the ID of the destination node.
     *
     * @return the destination node ID
     */
    public String getDestinationNodeId() {
        return destinationNodeId;
    }

    /**
     * Gets the label of the edge.
     *
     * @return the label of the edge
     */
    public String getLabel() {
        return label;
    }

    /**
     * Gets the properties of the edge.
     * <p>
     * Returns an unmodifiable view of the properties map.
     * </p>
     *
     * @return the properties of the edge
     */
    public Map<String, String> getProperties() {
        return Collections.unmodifiableMap(properties);
    }

    /**
     * Adds a property to the edge.
     *
     * @param key   the property key
     * @param value the property value
     */
    public void addProperty(String key, String value) {
        this.properties.put(key, value);
    }

    /**
     * Gets the value of a specific property.
     *
     * @param key the property key
     * @return the value of the property, or null if it does not exist
     */
    public String getProperty(String key) {
        return properties.get(key);
    }

    /**
     * Checks if a specific property exists.
     *
     * @param key the property key
     * @return true if the property exists, false otherwise
     */
    public boolean isPropertyExist(String key) {
        return properties.containsKey(key);
    }

    /**
     * Sets the label of the edge.
     *
     * @param label the new label of the edge
     */
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * Sets a property of the edge.
     *
     * @param key   the property key
     * @param value the property value
     */
    public void setProperty(String key, String value) {
        properties.put(key, value);
    }

    /**
     * Sets multiple properties of the edge.
     *
     * @param properties the properties to set
     */
    public void setProperties(Map<String, String> properties) {
        for (Map.Entry<String, String> entry : properties.entrySet()) {
            setProperty(entry.getKey(), entry.getValue());
        }
    }

    public String getId() {
        return edgeId;
    }

    @Override
    public String toString() {
        return String.format("%s --%s--> %s %s", sourceNodeId, label, destinationNodeId, properties);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Edge edge = (Edge) o;
        return sourceNodeId.equals(edge.sourceNodeId) &&
                destinationNodeId.equals(edge.destinationNodeId) &&
                label.equals(edge.label);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sourceNodeId, destinationNodeId, label);
    }
}
