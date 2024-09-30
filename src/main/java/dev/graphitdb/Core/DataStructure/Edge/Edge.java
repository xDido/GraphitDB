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

    public String getSourceNodeId() {
        return sourceNodeId;
    }

    public String getDestinationNodeId() {
        return destinationNodeId;
    }

    public String getLabel() {
        return label;
    }

    public Map<String, String> getProperties() {
        return Collections.unmodifiableMap(properties);
    }

    public void addProperty(String key, String value) {
        this.properties.put(key, value);
    }

    public String getProperty(String key) {
        return properties.get(key);
    }

    public boolean isPropertyExist(String key) {
        return properties.containsKey(key);
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setProperty(String key, String value) {
        properties.put(key, value);
    }

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
