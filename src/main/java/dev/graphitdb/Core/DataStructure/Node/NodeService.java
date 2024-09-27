package dev.graphitdb.Core.DataStructure.Node;

import dev.graphitdb.Core.DataStructure.Edge.Edge;
import dev.graphitdb.Core.Exceptions.Node.NodeNotFoundException;

import java.util.Map;

public interface NodeService {
    public void createNode(Node node);

    public void updateNode(String id, String label, Map<String, String> properties) throws NodeNotFoundException;

    public void deleteNode(String id) throws NodeNotFoundException;

    public Iterable<Node> getNode(Iterable<String> Ids);

}
