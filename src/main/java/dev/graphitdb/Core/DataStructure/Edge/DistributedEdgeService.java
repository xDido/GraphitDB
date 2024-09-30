package dev.graphitdb.Core.DataStructure.Edge;

import dev.graphitdb.Core.Distribute.PartitionManager;
import dev.graphitdb.Core.Exceptions.Edge.EdgeNotFoundException;
import dev.graphitdb.Core.Exceptions.Node.NodeNotFoundException;
import dev.graphitdb.Grpc.Clients.Edge.EdgeClient;
import io.grpc.ManagedChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Component
public class DistributedEdgeService implements EdgeService {

    @Value("${server.numOfServers}")
    private int numOfServers;
    @Autowired
    private PartitionManager partitionManager;

    @Value("${myserver.serverId}")
    private String serverId;
    private final Map<String, ManagedChannel> grpcChannels;
    private final ExecutorService executorService;
    @Autowired
    private EdgeClient edgeClient;
    @Autowired
    private LocalEdgeService edgeService;

    public DistributedEdgeService(@Qualifier("grpcChannels") Map<String, ManagedChannel> grpcChannels) {
        this.grpcChannels = grpcChannels;
        this.executorService = Executors.newFixedThreadPool(numOfServers);
    }

    @Override
    public void createEdge(String sourceId, String destinationId, String label, Map<String, String> properties) throws NodeNotFoundException {
        Edge edge = new Edge(sourceId, destinationId, label, properties);
        int partitionId = partitionManager.getPartitionId(edge.getId());
        if (partitionId == Integer.parseInt(serverId)) {
            edgeService.createEdge(sourceId, destinationId, label, properties);
        } else {
            edgeClient = new EdgeClient(grpcChannels, Integer.toString(partitionId));
            edgeClient.createEdge(sourceId, destinationId, label, properties);
        }

    }

    @Override
    public void updateEdge(String edgeId, String sourceId, String destinationId, String label, Map<String, String> properties) {
        int partitionId = partitionManager.getPartitionId(edgeId);
        if (partitionId == Integer.parseInt(serverId)) {
            edgeService.updateEdge(edgeId, sourceId, destinationId, label, properties);
        } else {
            edgeClient = new EdgeClient(grpcChannels, Integer.toString(partitionId));
            edgeClient.updateEdge(edgeId, sourceId, destinationId, label, properties);
        }
    }

    @Override
    public void deleteEdge(String edgeId) throws NodeNotFoundException {
        int partitionId = partitionManager.getPartitionId(edgeId);
        if (partitionId == Integer.parseInt(serverId)) {
            edgeService.deleteEdge(edgeId);
        } else {
            edgeClient = new EdgeClient(grpcChannels, Integer.toString(partitionId));
            edgeClient.deleteEdge(edgeId);
        }

    }

    public Iterable<Edge> getEdgesById(Iterable<String> edgeIds) throws Exception {
        List<Edge> edges = new ArrayList<>();
        List<List<Edge>> edgesByPartitionId = new ArrayList<>();


        for (int i = 0; i < numOfServers; i++) {
            edgesByPartitionId.add(new ArrayList<>());
        }

        ExecutorService executorService = Executors.newFixedThreadPool(numOfServers);
        List<List<String>> edgeIdsByPartitionId = partitionManager.groupEdgesByPartitionId(edgeIds);
        List<Future<?>> futures = new ArrayList<>();

        for (int i = 0; i < numOfServers; i++) {
            if (!edgeIdsByPartitionId.get(i).isEmpty()) {

                int finalI = i;
                Future<?> future = executorService.submit(new Callable<Void>() {
                    @Override
                    public Void call() throws Exception {
                        for (String edgeId : edgeIdsByPartitionId.get(finalI)) {
                            EdgeClient edgeClient; // Declare edgeClient variable


                            int partitionId = partitionManager.getPartitionId(edgeId);
                            if (partitionId == Integer.parseInt(serverId)) {

                                try {
                                    List<Edge> fetchedEdges = (List<Edge>) edgeService.getEdgesById(List.of(edgeId));
                                    synchronized (edgesByPartitionId.get(finalI)) {
                                        edgesByPartitionId.get(finalI).addAll(fetchedEdges);
                                    }
                                } catch (EdgeNotFoundException e) {

                                    System.err.println(e.getMessage());
                                }
                            } else {

                                edgeClient = new EdgeClient(grpcChannels, Integer.toString(partitionId));
                                List<Edge> fetchedEdges = edgeClient.getEdges(edgeId);
                                synchronized (edgesByPartitionId.get(finalI)) {
                                    edgesByPartitionId.get(finalI).addAll(fetchedEdges);
                                }
                            }
                        }
                        return null;
                    }
                });
                futures.add(future);
            }
        }


        for (Future<?> future : futures) {
            try {
                future.get();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // Collect all edges from all partitions
        for (int i = 0; i < numOfServers; i++) {
            edges.addAll(edgesByPartitionId.get(i));
        }

        return edges;
    }


}
