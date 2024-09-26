//package com.server.graph_db.core.vertex.runnables;
//
//import java.util.List;
//import dev.graphitdb.Core.DataStructure.Edge.Edge;
//import dev.graphitdb.Core.DataStructure.Edge.EdgeID;
//import com.server.graph_db.core.vertex.LocalVertexService;
//import com.server.graph_db.grpc.clients.VertexClient;
//
///**
// * Runnable task that retrieves edges asynchronously based on their IDs.
// * It determines whether the edge retrieval needs to be done locally or sent to
// * a remote server partition.
// */
//public class EdgeRetrieverByIds implements Runnable {
//
//    private LocalVertexService vertexService;
//    private Iterable<EdgeId> edgeIds;
//    private String serverId;
//    private VertexClient vertexClient;
//    private List<Edge> edges;
//    private int partitionId;
//
//    /**
//     * Constructor for AsyncEdgeRetrieverByIds.
//     *
//     * @param vertexService  Local service for vertex operations.
//     * @param edgeIds        The iterable list of EdgeIds to be retrieved.
//     * @param serverId       The ID of the current server.
//     * @param vertexClient   The client used to communicate with remote vertex servers.
//     * @param edges          The list where retrieved edges will be added.
//     * @param partitionId    The partition ID responsible for the edges.
//     */
//    public AsyncEdgeRetrieverByIds(LocalVertexService vertexService, Iterable<EdgeId> edgeIds, String serverId,
//                                   VertexClient vertexClient, List<Edge> edges, int partitionId) {
//        this.edgeIds = edgeIds;
//        this.serverId = serverId;
//        this.vertexClient = vertexClient;
//        this.edges = edges;
//        this.partitionId = partitionId;
//        this.vertexService = vertexService;
//    }
//
//    /**
//     * Runs the task to retrieve edges. Determines if the edges should be retrieved
//     * locally or from another partition, and appends them to the edges list.
//     */
//    @Override
//    public void run() {
//        if (partitionId != Integer.parseInt(serverId)) {
//            // Retrieve edges from a remote partition
//            Iterable<Edge> edgesFromOtherServer;
//            try {
//                edgesFromOtherServer = vertexClient.getEdgesById(edgeIds, String.valueOf(partitionId));
//                // Append edges to the list
//                for (Edge edge : edgesFromOtherServer) {
//                    edges.add(edge);
//                }
//            } catch (Exception e) {
//                e.printStackTrace(); // Handle remote server retrieval errors
//            }
//        } else {
//            // Retrieve edges from the local server
//            Iterable<Edge> edgesFromMyServer = vertexService.getEdgesById(edgeIds);
//            // Append edges to the list
//            for (Edge edge : edgesFromMyServer) {
//                edges.add(edge);
//            }
//        }
//    }
//}
