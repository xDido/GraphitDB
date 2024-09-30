package dev.graphitdb.Core.DataStructure.Node;


import dev.graphitdb.Core.DataStructure.Edge.Edge;
import dev.graphitdb.Core.DataStructure.Edge.EdgeStorage.EdgeStorage;
import dev.graphitdb.Core.DataStructure.Node.NodeStorage.NodeStorage;
import dev.graphitdb.Core.Exceptions.Node.NodeAlreadyExistsException;
import dev.graphitdb.Core.Exceptions.Node.NodeNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * The LocalNodeService class provides methods to manage nodes and their edges locally.
 */
@Service
public class LocalNodeService implements NodeService {

    private final NodeStorage nodeStorage;
    private final EdgeStorage edgeStorage;

    @Autowired
    public LocalNodeService(NodeStorage nodeStorage, EdgeStorage edgeStorage) {
        this.nodeStorage = nodeStorage;
        this.edgeStorage = edgeStorage;
    }

    @Override
    public void createNode(Node node) {
        if (node.getId() == null) {
            throw new IllegalArgumentException("Node ID must not be null.");
        }

        if (nodeStorage.existsById(node.getId())) {
            throw new NodeAlreadyExistsException(String.format("Node with ID %s already exists.", node.getId()));
        }

        nodeStorage.save(node);
    }

    @Override
    public void updateNode(String id, String label, Map<String, String> properties) throws NodeNotFoundException {
        Node node = nodeStorage.findById(id).orElseThrow(NodeNotFoundException::new);

        if (label != null && !label.isEmpty()) {
            node.setLabel(label);
        }
        if (properties != null && !properties.isEmpty()) {
            node.setProperties(properties);
        }
        nodeStorage.save(node);
    }

    @Override
    public void deleteNode(String id) throws NodeNotFoundException {
        if (!nodeStorage.existsById(id)) {
            throw new NodeNotFoundException();
        }
        nodeStorage.deleteById(id);
    }


    @Override
    public Node getNode(String id) throws NodeNotFoundException {
        return nodeStorage.findById(id)
                .orElseThrow(NodeNotFoundException::new); // Throw exception if node not found
    }

    @Override
    public Iterable<Node> getAllNodes() {
        return nodeStorage.findAll();
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

}
