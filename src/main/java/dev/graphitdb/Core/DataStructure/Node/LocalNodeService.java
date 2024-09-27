package dev.graphitdb.Core.DataStructure.Node;


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

    @Autowired
    public LocalNodeService(NodeStorage nodeStorage) {
        this.nodeStorage = nodeStorage;
    }


    /**
     * Creates a new node in the graph.
     *
     * @param node the node to be created
     * @throws NodeAlreadyExistsException if a node with the same ID already exists
     */
    @Override
    public void createNode(Node node) {
        if (node.getId() == null) {
            throw new IllegalArgumentException("Node ID must not be null.");
        }

        if (nodeStorage.existsById(node.getId())) {
            throw new NodeAlreadyExistsException(STR."Node with ID \{node.getId()} already exists.");
        }

        nodeStorage.save(node);
    }

    /**
     * Updates the properties and label of a node by its ID.
     *
     * @param id         the ID of the node to be updated
     * @param label      the new label for the node
     * @param properties the new properties for the node
     * @throws NodeNotFoundException if the node does not exist
     */
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

    /**
     * Deletes a node and all associated edges by its ID.
     *
     * @param id the ID of the node to be deleted
     * @throws NodeNotFoundException if the node does not exist
     */
    @Override
    public void deleteNode(String id) throws NodeNotFoundException {
        if (!nodeStorage.existsById(id)) {
            throw new NodeNotFoundException();
        }

        nodeStorage.deleteById(id);
    }

    /**
     * Retrieves nodes by their IDs.
     *
     * @param ids the IDs of the nodes to retrieve
     * @return an iterable collection of nodes
     */
    @Override
    public Iterable<Node> getNode(Iterable<String> ids) {
        List<Node> result = new ArrayList<>();
        for (String id : ids) {
            nodeStorage.findById(id).ifPresent(result::add);
        }
        return result;
    }

}
