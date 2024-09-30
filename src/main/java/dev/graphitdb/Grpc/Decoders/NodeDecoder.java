package dev.graphitdb.Grpc.Decoders;

import dev.graphitdb.Core.DataStructure.Edge.Edge;
import dev.graphitdb.Core.DataStructure.Node.Node;
import dev.graphitdb.Core.Operators.Select.SelectOperatorManager;
import dev.graphitdb.Grpc.Node.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class NodeDecoder {

    @Autowired
    SelectOperatorManager selectOperatorManager;

    // Helper method to decode Node from a generic gRPC Node response
    private Node decodeNode(dev.graphitdb.Grpc.Node.Node grpcNode) {
        String nodeId = grpcNode.getId();
        String label = grpcNode.getLabel();
        Map<String, String> properties = grpcNode.getPropertiesMap();
        return new Node(nodeId, label, new HashMap<>(properties));
    }

    // Helper method to decode a list of edges from a generic gRPC response
    private List<Edge> decodeEdges(List<dev.graphitdb.Grpc.Edge.Edge> grpcEdges) {
        List<Edge> coreEdges = new ArrayList<>();
        for (dev.graphitdb.Grpc.Edge.Edge grpcEdge : grpcEdges) {
            Edge coreEdge = new Edge(grpcEdge.getSourceNodeId(), grpcEdge.getDestinationNodeId());
            coreEdge.setProperties(grpcEdge.getPropertiesMap());
            coreEdge.setLabel(grpcEdge.getLabel());
            coreEdges.add(coreEdge);
        }
        return coreEdges;
    }

    // Method to decode the CreateNodeResponse
    public Node createDecodeNode(CreateNodeResponse nodeResponse) {
        return decodeNode(nodeResponse.getNode());
    }

    // Method to decode the UpdateNodeResponse
    public Node updateDecodeNode(UpdateNodeResponse nodeResponse) {
        return decodeNode(nodeResponse.getNode());
    }

    // Method to decode the GetNodeResponse
    public Node getDecodeNode(GetNodeResponse nodeResponse) {
        return decodeNode(nodeResponse.getNode());
    }

    // Method to decode the outgoing edges from a GetOutgoingEdgesResponse
    public List<Edge> getOutgoingEdgesDecodeNode(getOutgoingEdgesResponse nodeResponse) {
        return decodeEdges(nodeResponse.getEdgesList());
    }

    // Method to decode the incoming edges from a GetIncomingEdgesResponse
    public List<Edge> getIncomingEdgesDecodeNode(getIncomingEdgesResponse nodeResponse) {
        return decodeEdges(nodeResponse.getEdgesList());
    }

    public List<String> getAllNodesIdsDecode(getAllNodesIdsResponse nodesIdsResponse) {
        return nodesIdsResponse.getNodeIdsList();
    }

}
