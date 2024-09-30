package dev.graphitdb.Core.Traverser.Heuristics;

import dev.graphitdb.Core.DataStructure.Node.Node;

/**
 * Manhattan heuristic for calculating the distance between two vertices in a graph.
 */
public class Manhattan implements Heuristic {

    private final String xPropertyKey; // Key for the x-coordinate property
    private final String yPropertyKey; // Key for the y-coordinate property

    /**
     * Constructs a new Manhattan heuristic using the specified property keys.
     *
     * @param xPropertyKey the property key for the x-coordinate
     * @param yPropertyKey the property key for the y-coordinate
     */
    public Manhattan(String xPropertyKey, String yPropertyKey) {
        this.xPropertyKey = xPropertyKey;
        this.yPropertyKey = yPropertyKey;
    }

    /**
     * Calculates the Manhattan distance between two vertices.
     *
     * @param source      the source vertex
     * @param destination the destination vertex
     * @return the calculated Manhattan distance
     * @throws IllegalArgumentException if the x or y properties are not present or not valid numbers
     */
    public long getHeuristic(Node source, Node destination) {
        // Retrieve the properties for the source and destination vertices
        String sourceX = source.getProperty(xPropertyKey);
        String sourceY = source.getProperty(yPropertyKey);
        String destinationX = destination.getProperty(xPropertyKey);
        String destinationY = destination.getProperty(yPropertyKey);

        // Validate properties
        if (sourceX == null || sourceY == null || destinationX == null || destinationY == null) {
            throw new IllegalArgumentException("Vertex properties cannot be null.");
        }

        try {
            long xDistance = Math.abs(Long.parseLong(sourceX) - Long.parseLong(destinationX));
            long yDistance = Math.abs(Long.parseLong(sourceY) - Long.parseLong(destinationY));
            return xDistance + yDistance; // Manhattan distance
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Vertex properties must be valid numbers.", e);
        }
    }
}