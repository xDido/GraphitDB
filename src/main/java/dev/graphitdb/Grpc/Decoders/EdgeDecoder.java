package dev.graphitdb.Grpc.Decoders;

import dev.graphitdb.Core.DataStructure.Edge.Edge;
import dev.graphitdb.Grpc.Edge.GetEdgesResponse;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class EdgeDecoder {

    // Helper method to decode a gRPC Edge to a core Edge
    private Edge decodeEdge(dev.graphitdb.Grpc.Edge.Edge grpcEdge) {
        String sourceNodeId = grpcEdge.getSourceNodeId();
        String destinationNodeId = grpcEdge.getDestinationNodeId();
        Map<String, String> properties = grpcEdge.getPropertiesMap();
        String label = grpcEdge.getLabel();

        Edge edge = new Edge(sourceNodeId, destinationNodeId);
        edge.setProperties(properties);
        edge.setLabel(label);

        return edge;
    }

    // Method to decode a list of gRPC edges from a GetEdgesResponse
    public List<Edge> decodeEdges(GetEdgesResponse edgesResponse) {
        List<Edge> coreEdges = new ArrayList<>();
        for (dev.graphitdb.Grpc.Edge.Edge grpcEdge : edgesResponse.getEdgesList()) {
            coreEdges.add(decodeEdge(grpcEdge));  // Decode each gRPC edge to a core edge
        }
        return coreEdges;
    }
}
