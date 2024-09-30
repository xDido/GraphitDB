package dev.graphitdb.Core.DataStructure.Edge;

import dev.graphitdb.Core.DataStructure.Edge.EdgeStorage.EdgeStorage;
import dev.graphitdb.Core.DataStructure.Node.LocalNodeService;
import dev.graphitdb.Core.DataStructure.Node.Node;
import dev.graphitdb.Core.Exceptions.Edge.EdgeNotFoundException;
import dev.graphitdb.Core.Exceptions.Node.NodeNotFoundException;
import dev.graphitdb.Core.DataStructure.Node.NodeStorage.NodeStorage;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * LocalEdgeService provides an in-memory implementation of the EdgeService interface
 * for managing edges in a graph. This includes functionality to create, update,
 * retrieve, and delete edges, as well as counting edges and traversing the graph.
 */
@Component
public class LocalEdgeService implements EdgeService {

    private final EdgeStorage edgeStorage;
    private final NodeStorage nodeStorage; // For node-related operations
    private final LocalNodeService localNodeService;

    public LocalEdgeService(NodeStorage nodeStorage, EdgeStorage edgeStorage, LocalNodeService localNodeService) {
        this.edgeStorage = edgeStorage;
        this.nodeStorage = nodeStorage;
        this.localNodeService = localNodeService;
    }

    @Override
    public void createEdge(String sourceId, String destinationId, String label, Map<String, String> properties) throws NodeNotFoundException {
        if (!nodeStorage.existsById(sourceId) || !nodeStorage.existsById(destinationId)) {
            throw new NodeNotFoundException();
        }
        Edge edge = new Edge(sourceId, destinationId, label, properties);
        edgeStorage.save(edge);


    }

    @Override
    public void updateEdge(String edgeId, String sourceId, String destinationId, String label, Map<String, String> properties) {
        // Fetch the existing edge by edgeId
        Edge existingEdge = edgeStorage.findById(edgeId).orElseThrow(() ->
                new EdgeNotFoundException("Edge not found for update: " + edgeId)
        );

        if (label != null && !label.isEmpty()) {
            existingEdge.setLabel(label);
        }

        // If properties are provided, update the properties
        if (properties != null && !properties.isEmpty()) {
            existingEdge.setProperties(properties);
        }

        // Save the updated edge back to storage
        edgeStorage.save(existingEdge);
    }

    @Override
    public void deleteEdge(String edgeId) throws EdgeNotFoundException {

        if (edgeId != null && !edgeId.isEmpty()) {
            if (!edgeStorage.existsById(edgeId)) {
                throw new EdgeNotFoundException("Edge not found for deletion: " + edgeId);
            }
            edgeStorage.deleteById(edgeId);
            return;
        }

        throw new IllegalArgumentException("edgeId must not be null or empty"); // Handle case where edgeId is null or empty
    }


    @Override
    public Iterable<Edge> getEdgesById(Iterable<String> edgeIds) throws Exception {
        List<Edge> edges = new ArrayList<>();
        List<String> notFoundIds = new ArrayList<>();

        List<String> idsList = new ArrayList<>();
        edgeIds.forEach(idsList::add);

        if (idsList.size() == 1) {
            String edgeId = idsList.get(0);
            Optional<Edge> edge = edgeStorage.findById(edgeId);

            if (edge.isPresent()) {
                edges.add(edge.get());
            } else {
                throw new EdgeNotFoundException("Edge not found with ID: " + edgeId);
            }
        } else {
            // Handle the case for multiple edgeIds
            for (String edgeId : idsList) {
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
        }

        return edges;
    }
}



