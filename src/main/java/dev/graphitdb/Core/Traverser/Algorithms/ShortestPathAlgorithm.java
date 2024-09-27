package dev.graphitdb.Core.Traverser.Algorithms;

import java.util.List;


import dev.graphitdb.Core.DataStructure.Edge.Edge;
import dev.graphitdb.Core.Exceptions.Node.NodeNotFoundException;
import dev.graphitdb.Core.Exceptions.Traverser.PathNotFoundException;

public interface ShortestPathAlgorithm {
    /**
     * Computes the shortest path between two nodes in the graph.
     *
     * @param sourceNodeId      The ID of the source node.
     * @param destinationNodeId The ID of the destination node.
     * @param costField         The field representing the cost of traversing edges.
     * @throws NodeNotFoundException If one of the specified nodes is not found.
     * @throws PathNotFoundException If no path exists between the nodes.
     */
    void compute(String sourceNodeId, String destinationNodeId, String costField)
            throws NodeNotFoundException, PathNotFoundException;

    /**
     * Returns the total cost of the shortest path computed between two nodes.
     *
     * @return The total cost as a long value.
     */
    long getShortestPathCost();

    /**
     * Retrieves the nodes involved in the shortest path.
     *
     * @return An iterable list of edges forming the path.
     * @throws PathNotFoundException If no path has been computed or if the path does not exist.
     */
    List<Edge> getShortestPath() throws PathNotFoundException;
}
