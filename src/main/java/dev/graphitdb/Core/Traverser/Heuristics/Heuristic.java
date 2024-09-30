package dev.graphitdb.Core.Traverser.Heuristics;

import dev.graphitdb.Core.DataStructure.Node.Node;

/**
 * Interface representing a heuristic for calculating distances between vertices.
 */
public interface Heuristic {

    /**
     * Calculates the heuristic value between two vertices.
     *
     * @param source      the source vertex
     * @param destination the destination vertex
     * @return the calculated heuristic value
     */
    long getHeuristic(Node source, Node destination);
}
