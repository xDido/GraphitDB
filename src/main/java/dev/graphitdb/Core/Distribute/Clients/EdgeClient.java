package dev.graphitdb.Core.Distribute.Clients;

import dev.graphitdb.Core.DataStructure.Edge.Edge;
import org.springframework.stereotype.Component;
import dev.graphitdb.Grpc.proto.EdgeServiceGrpc;
import dev.graphitdb.Grpc.proto.EdgeServiceProto;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * The EdgeClient class provides methods to interact with remote edge management services via gRPC.
 */
@Component
public class EdgeClient {

    private final EdgeServiceGrpc.EdgeServiceBlockingStub edgeServiceStub;

    public EdgeClient(EdgeServiceGrpc.EdgeServiceBlockingStub edgeServiceStub) {
        this.edgeServiceStub = edgeServiceStub;
    }

    public void createEdge(String sourceId, Edge edge, boolean isLocal, String partitionId) {
        // Creating gRPC request for edge creation
        EdgeServiceProto.CreateEdgeRequest request = EdgeServiceProto.CreateEdgeRequest.newBuilder()
                .setEdge(EdgeServiceProto.Edge.newBuilder()
                        .setSourceNodeId(sourceId)
                        .setDestinationNodeId(edge.getDestinationNodeId())
                        .setLabel(edge.getLabel())
                        .putAllProperties(edge.getProperties()) // Assuming Edge has a method to get properties
                        .build())
                .build();

        // Call the gRPC method
        EdgeServiceProto.CreateEdgeResponse response = edgeServiceStub.createEdge(request);
        // Handle the response if needed
    }


    public void deleteEdge(String sourceId, String destinationVertexId, String label, boolean isLocal, String partitionId) {
        // Creating gRPC request for edge deletion
        EdgeServiceProto.DeleteEdgeRequest request = EdgeServiceProto.DeleteEdgeRequest.newBuilder()
                .setEdgeId(sourceId + "-" + destinationVertexId + "-" + label) // Adjust based on your edge ID logic
                .build();

        edgeServiceStub.deleteEdge(request);
    }

    public void updateEdge(String sourceId, String destinationVertexId, String label, Map<String, String> properties, boolean isLocal, String partitionId) {
        // Creating gRPC request for edge update
        EdgeServiceProto.UpdateEdgeRequest.Builder requestBuilder = EdgeServiceProto.UpdateEdgeRequest.newBuilder()
                .setEdge(EdgeServiceProto.Edge.newBuilder()
                        .setSourceNodeId(sourceId)
                        .setDestinationNodeId(destinationVertexId)
                        .setLabel(label)
                        .putAllProperties(properties) // Assuming you want to update properties
                        .build());

        edgeServiceStub.updateEdge(requestBuilder.build());
    }

    public Iterable<Edge> getOutgoingEdges(String vertexId, String partitionId) {
        // Creating gRPC request to fetch outgoing edges
        EdgeServiceProto.GetEdgesRequest request = EdgeServiceProto.GetEdgesRequest.newBuilder()
                .setVertexId(vertexId)
                .build();

        EdgeServiceProto.GetEdgesResponse response = edgeServiceStub.getOutgoingEdges(request);

        // Convert gRPC response to Edge objects
        return response.getEdgesList().stream().map(edgeProto -> new Edge(
                edgeProto.getSourceNodeId(),
                edgeProto.getDestinationNodeId(),
                edgeProto.getLabel(),
                edgeProto.getPropertiesMap() // Assuming Edge has a constructor that accepts properties
        )).collect(Collectors.toList());
    }

    public Iterable<Edge> getIncomingEdges(String vertexId, String partitionId) {
        // Creating gRPC request to fetch incoming edges
        EdgeServiceProto.GetEdgesRequest request = EdgeServiceProto.GetEdgesRequest.newBuilder()
                .setVertexId(vertexId)
                .build();

        EdgeServiceProto.GetEdgesResponse response = edgeServiceStub.getIncomingEdges(request);

        // Convert gRPC response to Edge objects
        return response.getEdgesList().stream().map(edgeProto -> new Edge(
                edgeProto.getSourceNodeId(),
                edgeProto.getDestinationNodeId(),
                edgeProto.getLabel(),
                edgeProto.getPropertiesMap() // Assuming Edge has a constructor that accepts properties
        )).collect(Collectors.toList());
    }
}
