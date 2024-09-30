package dev.graphitdb.Core.DataStructure.Edge;

import dev.graphitdb.Core.Exceptions.Edge.EdgeNotFoundException;
import dev.graphitdb.Core.Exceptions.Node.NodeNotFoundException;

import java.util.List;
import java.util.Map;

/**
 * EdgeService defines operations for managing edges within a graph.
 * This includes creating, retrieving, updating, deleting edges,
 * and performing graph-specific operations like traversals and counting edges.
 */
public interface EdgeService {


    void createEdge(String sourceId, String destinationId, String label, Map<String, String> properties) throws NodeNotFoundException;

    void updateEdge(String edgeId, String sourceId, String destinationId, String label, Map<String, String> properties);

    void deleteEdge(String edgeId) throws NodeNotFoundException;

    Iterable<Edge> getEdgesById(Iterable<String> edgeIds) throws Exception;


}
