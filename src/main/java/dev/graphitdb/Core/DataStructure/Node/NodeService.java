package dev.graphitdb.Core.DataStructure.Node;

import dev.graphitdb.Core.DataStructure.Edge.Edge;
import dev.graphitdb.Core.Exceptions.Node.NodeNotFoundException;

import java.util.Map;

public interface NodeService {
    void createNode(Node node);

    public void updateNode(String id, String label, Map<String, String> properties) throws NodeNotFoundException;

    void deleteNode(String id) throws NodeNotFoundException;

    Node getNode(String id) throws NodeNotFoundException;

    Iterable<Node> getAllNodes();

    Iterable<Edge> getOutgoingEdges(String nodeId) throws Exception;

    Iterable<Edge> getIncomingEdges(String nodeId) throws Exception;
}
