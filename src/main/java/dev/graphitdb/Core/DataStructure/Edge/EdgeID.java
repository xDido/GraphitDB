package dev.graphitdb.Core.DataStructure.Edge;

import java.util.Objects;

/**
 * Represents an identifier for an edge in the graph database.
 * This ID consists of a source node ID, a destination node ID, and a label.
 */
public class EdgeID  {

    private final String sourceId;
    private final String destinationId;
    private final String label;

    /**
     * Constructs an EdgeId with the specified source ID, destination ID, and label.
     *
     * @param sourceId      the ID of the source node
     * @param destinationId the ID of the destination node
     * @param label         the label of the edge
     * @throws IllegalArgumentException if any of the arguments are null
     */
    public EdgeID(String sourceId, String destinationId, String label) {
        if (sourceId == null || destinationId == null || label == null) {
            throw new IllegalArgumentException("Source ID, destination ID, and label cannot be null.");
        }
        this.sourceId = sourceId;
        this.destinationId = destinationId;
        this.label = label;
    }

    /**
     * Gets the ID of the source node.
     *
     * @return the source node ID
     */
    public String getSourceId() {
        return sourceId;
    }

    /**
     * Gets the ID of the destination node.
     *
     * @return the destination node ID
     */
    public String getDestinationId() {
        return destinationId;
    }

    /**
     * Gets the label of the edge.
     *
     * @return the edge label
     */
    public String getLabel() {
        return label;
    }

    /**
     * Provides a string representation of this EdgeId.
     *
     * @return a formatted string representation of this EdgeId
     */
    @Override
    public String toString() {
        return String.format("sourceId: %s, destinationId: %s, label: %s", sourceId, destinationId, label);
    }

    /**
     * Compares this EdgeId to another object. Two EdgeIds are equal if they have the same
     * source ID, destination ID, and label.
     *
     * @param o the object to compare with
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EdgeID edgeId)) return false;
        return sourceId.equals(edgeId.sourceId) &&
                destinationId.equals(edgeId.destinationId) &&
                label.equals(edgeId.label);
    }

    /**
     * Computes the hash code for this EdgeId based on the source ID, destination ID, and label.
     *
     * @return the hash code value for this EdgeId
     */
    @Override
    public int hashCode() {
        return Objects.hash(sourceId, destinationId, label);
    }
}
