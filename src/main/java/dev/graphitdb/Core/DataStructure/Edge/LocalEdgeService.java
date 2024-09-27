package dev.graphitdb.Core.DataStructure.Edge;

import dev.graphitdb.Core.DataStructure.Edge.EdgeStorage.EdgeStorage;
import dev.graphitdb.Core.Exceptions.Edge.EdgeNotFoundException;
import dev.graphitdb.Core.Exceptions.Node.NodeNotFoundException;
import dev.graphitdb.Core.DataStructure.Node.NodeStorage.NodeStorage;

import java.util.*;

/**
 * LocalEdgeService provides an in-memory implementation of the EdgeService interface
 * for managing edges in a graph. This includes functionality to create, update,
 * retrieve, and delete edges, as well as counting edges and traversing the graph.
 */
public class LocalEdgeService implements EdgeService {

    private final EdgeStorage edgeStorage;
    private final NodeStorage nodeStorage; // For node-related operations

    public LocalEdgeService(NodeStorage nodeStorage, EdgeStorage edgeStorage) {
        this.edgeStorage = edgeStorage;
        this.nodeStorage = nodeStorage;
    }

    @Override
    public void createEdge(String sourceId, String destinationId, String label, Object properties) throws NodeNotFoundException {
        if (!(properties instanceof Map)) {
            throw new IllegalArgumentException("Properties must be a Map");
        }

        if (!nodeStorage.existsById(sourceId) || !nodeStorage.existsById(destinationId)) {
            throw new NodeNotFoundException();
        }

        Edge edge = new Edge(sourceId, destinationId, label, (Map<String, String>) properties);
        edgeStorage.save(edge);
    }

    @Override
    public void updateEdge(String edgeId, String sourceId, String destinationId, String label, Object properties) {
        if (!(properties instanceof Map)) {
            throw new IllegalArgumentException("Properties must be a Map");
        }

        Optional<Edge> existingEdge = edgeStorage.findById(edgeId);
        if (existingEdge.isEmpty()) {
            throw new EdgeNotFoundException(STR."Edge not found for update: \{edgeId}");
        }

        Edge updatedEdge = new Edge(sourceId, destinationId, label, (Map<String, String>) properties);
        edgeStorage.save(updatedEdge);
        edgeStorage.deleteById(edgeId);
    }


    @Override
    public void deleteEdge(String edgeId) {
        if (edgeStorage.existsById(edgeId)) {
            throw new EdgeNotFoundException(STR."Edge not found for deletion: \{edgeId}");
        }
        edgeStorage.deleteById(edgeId);
    }


    @Override
    public Edge getEdge(String edgeId) {
        Edge edge = edgeStorage.findById(edgeId).orElse(null);
        if (edge == null) {
            throw new EdgeNotFoundException(STR."Edge not found: \{edgeId}");
        }
        return edge;
    }


    @Override
    public List<Edge> getEdgesById(String edgeId) {
        List<Edge> edges = new ArrayList<>();

        edgeStorage.findAllById(Collections.singletonList(edgeId)).forEach(edges::add);
        if (edges == null) {
            throw new EdgeNotFoundException(STR."Edge not found with ID: \{edgeId}");
        }
        return edges;
    }

    @Override
    public Iterable<Edge> getEdgesById(Iterable<String> edgeIds) throws Exception {
        List<Edge> edges = new ArrayList<>();
        List<String> notFoundIds = new ArrayList<>();

        // Iterate through the provided edge IDs
        for (String edgeId : edgeIds) {
            // Attempt to find the edge by its ID
            Optional<Edge> edge = edgeStorage.findById(edgeId);

            if (edge.isPresent()) {
                edges.add(edge.get());
            } else {
                notFoundIds.add(edgeId); // Collect IDs that were not found
            }
        }

        // If any IDs were not found, throw an exception
        if (!notFoundIds.isEmpty()) {
            throw new EdgeNotFoundException("Edges not found with IDs: " + String.join(", ", notFoundIds));
        }

        return edges; // Return the found edges
    }


    @Override
    public Iterable<Edge> getOutgoingEdges(String nodeId) throws Exception {
        if (!nodeStorage.existsById(nodeId)) {
            throw new NodeNotFoundException();
        }

        List<Edge> outgoingEdges = new ArrayList<>();
        edgeStorage.findAllByNodeId(nodeId, true).forEach(outgoingEdges::add);
        return outgoingEdges;
    }

    @Override
    public Iterable<Edge> getIncomingEdges(String nodeId) throws Exception {
        if (!nodeStorage.existsById(nodeId)) {
            throw new NodeNotFoundException();
        }

        List<Edge> incomingEdges = new ArrayList<>();
        edgeStorage.findAllByNodeId(nodeId, false).forEach(incomingEdges::add);
        return incomingEdges;
    }

    @Override
    public void updateEdgeProperties(String edgeId, Object properties) {
        if (!(properties instanceof Map)) {
            throw new IllegalArgumentException("Properties must be a Map");
        }

        Edge edge = edgeStorage.findById(edgeId).orElseThrow(() -> new EdgeNotFoundException(STR."Edge not found with ID: \{edgeId}"));
        if (edge == null) {
            throw new EdgeNotFoundException(STR."Edge not found with ID: \{edgeId}");
        }

        edge.setProperties((Map<String, String>) properties);
        edgeStorage.save(edge);
    }

    /**
     * Deletes an edge from the node's outgoing or incoming edges.
     *
     * @param sourceNodeId      the source node ID
     * @param destinationNodeId the destination node ID
     * @param label             the label of the edge
     * @param isOutgoing        if true, deletes the outgoing edge; otherwise, deletes the incoming edge
     */
    @Override
    public void deleteEdge(String sourceNodeId, String destinationNodeId, String label, boolean isOutgoing) throws NodeNotFoundException {
        if (!nodeStorage.existsById(sourceNodeId)) {
            throw new NodeNotFoundException();
        }
        try {
            edgeStorage.delete(new Edge(sourceNodeId, destinationNodeId, label, null), isOutgoing);
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete edge", e);
        }
    }

    public void deleteEdgesBySourceNodeId(String sourceNodeId) throws NodeNotFoundException {
        if (!nodeStorage.existsById(sourceNodeId)) {
            throw new NodeNotFoundException();
        }
        try {
            Iterable<Edge> outgoingEdges = getOutgoingEdges(sourceNodeId);
            for (Edge edge : outgoingEdges) {
                edgeStorage.delete(edge, true);
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete edges by source node ID", e);
        }
    }
}
