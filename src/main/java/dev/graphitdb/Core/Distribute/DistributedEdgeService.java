//package dev.graphitdb.Core.Distribute;
//
//
//import dev.graphitdb.Core.Exceptions.Edge.EdgeNotFoundException;
//import dev.graphitdb.Core.DataStructure.Edge.Edge;
//import dev.graphitdb.Core.DataStructure.Node.NodeService;
//import dev.graphitdb.Core.Distribute.Clients.EdgeClient;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Map;
//
///**
// * The DistributedEdgeService class provides distributed management of edges
// * across multiple servers, using a partitioning strategy.
// */
//@Service
//public class DistributedEdgeService {
//    @Autowired
//    private NodeService vertexService;
//
//    @Autowired
//    private EdgeClient edgeClient;
//
//    @Value("${server.numOfServers}")
//    private int numOfServers;
//
//    @Value("${myserver.serverId}")
//    private String serverId;
//
//    private final PartitionManager partitionManager;
//
//    /**
//     * Constructs a DistributedEdgeService with the specified partition manager.
//     *
//     * @param partitionManager the partition manager instance
//     */
//    public DistributedEdgeService(PartitionManager partitionManager) {
//        this.partitionManager = partitionManager;
//    }
//
//    /**
//     * Creates an edge in the appropriate partition based on the source vertex ID.
//     *
//     * @param sourceId the ID of the source vertex
//     * @param edge the edge to be created
//     */
//    public void createEdge(String sourceId, Edge edge) {
//        int partitionId = partitionManager.getPartitionId(sourceId);
//        if (partitionId == Integer.parseInt(serverId)) {
//            vertexService.addEdge(sourceId, edge, true);
//        } else {
//            edgeClient.createEdge(sourceId, edge, true, String.valueOf(partitionId));
//        }
//    }
//
//    /**
//     * Deletes an edge based on the source and destination vertex IDs.
//     *
//     * @param sourceId the ID of the source vertex
//     * @param destinationVertexId the ID of the destination vertex
//     * @param label the label of the edge
//     * @throws EdgeNotFoundException if the edge does not exist
//     */
//    public void deleteEdge(String sourceId, String destinationVertexId, String label) throws EdgeNotFoundException {
//        int partitionId = partitionManager.getPartitionId(sourceId);
//        if (partitionId == Integer.parseInt(serverId)) {
//            vertexService.deleteEdge(sourceId, destinationVertexId, label, true);
//        } else {
//            edgeClient.deleteEdge(sourceId, destinationVertexId, label, true, String.valueOf(partitionId));
//        }
//
//        partitionId = partitionManager.getPartitionId(destinationVertexId);
//        if (partitionId == Integer.parseInt(serverId)) {
//            vertexService.deleteEdge(sourceId, destinationVertexId, label, false);
//        } else {
//            edgeClient.deleteEdge(sourceId, destinationVertexId, label, false, String.valueOf(partitionId));
//        }
//    }
//
//    /**
//     * Updates an edge's properties based on the source and destination vertex IDs.
//     *
//     * @param sourceId the ID of the source vertex
//     * @param destinationVertexId the ID of the destination vertex
//     * @param label the label of the edge
//     * @param properties the new properties of the edge
//     */
//    public void updateEdge(String sourceId, String destinationVertexId, String label, Map<String, String> properties) {
//        int partitionId = partitionManager.getPartitionId(sourceId);
//        if (partitionId == Integer.parseInt(serverId)) {
//            vertexService.updateEdge(sourceId, destinationVertexId, label, properties, true);
//        } else {
//            edgeClient.updateEdge(sourceId, destinationVertexId, label, properties, true, String.valueOf(partitionId));
//        }
//
//        partitionId = partitionManager.getPartitionId(destinationVertexId);
//        if (partitionId == Integer.parseInt(serverId)) {
//            vertexService.updateEdge(sourceId, destinationVertexId, label, properties, false);
//        } else {
//            edgeClient.updateEdge(sourceId, destinationVertexId, label, properties, false, String.valueOf(partitionId));
//        }
//    }
//
//    /**
//     * Retrieves outgoing edges for a given source vertex ID.
//     *
//     * @param vertexId the ID of the source vertex
//     * @return an iterable collection of outgoing edges
//     */
//    public Iterable<Edge> getOutgoingEdges(String vertexId) {
//        int partitionId = partitionManager.getPartitionId(vertexId);
//        if (partitionId == Integer.parseInt(serverId)) {
//            return vertexService.getOutgoingEdges(vertexId);
//        } else {
//            return edgeClient.getOutgoingEdges(vertexId, String.valueOf(partitionId));
//        }
//    }
//
//    /**
//     * Retrieves incoming edges for a given destination vertex ID.
//     *
//     * @param vertexId the ID of the destination vertex
//     * @return an iterable collection of incoming edges
//     */
//    public Iterable<Edge> getIncomingEdges(String vertexId) {
//        int partitionId = partitionManager.getPartitionId(vertexId);
//        if (partitionId == Integer.parseInt(serverId)) {
//            return vertexService.getIncomingEdges(vertexId);
//        } else {
//            return edgeClient.getIncomingEdges(vertexId, String.valueOf(partitionId));
//        }
//    }
//}
