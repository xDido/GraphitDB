package dev.graphitdb.Core.DataStructure.Node;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import dev.graphitdb.Grpc.Clients.Edge.EdgeClient;
import io.grpc.ManagedChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import dev.graphitdb.Core.DataStructure.Edge.Edge;
import dev.graphitdb.Core.Exceptions.Node.NodeAlreadyExistsException;
import dev.graphitdb.Core.Exceptions.Node.NodeNotFoundException;
import dev.graphitdb.Core.Distribute.PartitionManager;

import dev.graphitdb.Grpc.Clients.Node.NodeClient;

@Component
public class DistributedNodeService implements NodeService {

    @Autowired
    private LocalNodeService nodeService;

    @Autowired
    private PartitionManager partitionManager;

    @Autowired
    private NodeClient nodeClient;

    @Value("${server.numOfServers}")
    private int numOfServers;

    @Value("${myserver.serverId}")
    private String serverId;
    private final Map<String, ManagedChannel> grpcChannels;

    private final ExecutorService executorService;

    public DistributedNodeService(@Qualifier("grpcChannels") Map<String, ManagedChannel> grpcChannels) {
        this.grpcChannels = grpcChannels;
        this.executorService = Executors.newFixedThreadPool(numOfServers);
    }

    public Node getNode(String nodeId) throws NodeNotFoundException {
        int partitionId = partitionManager.getPartitionId(nodeId);
        nodeClient = new NodeClient(grpcChannels, Integer.toString(partitionId));
        // If the node is in the local partition
        if (partitionId == Integer.parseInt(serverId)) {
            // Retrieve the node from the local service
            Node node = nodeService.getNode(nodeId);
            if (node == null) {
                throw new NodeNotFoundException();
            }
            return node; // Return the first node
        } else {
            // Retrieve the node from the remote service
            Node node = nodeClient.getNode(nodeId);
            if (node == null) {
                throw new NodeNotFoundException();
            }
            return node; // Return the first node
        }
    }


    @Override
    public void deleteNode(String id) throws NodeNotFoundException {
        int partitionId = partitionManager.getPartitionId(id);
        if (partitionId == Integer.parseInt(serverId)) {
            nodeService.deleteNode(id);
        } else {
            nodeClient = new NodeClient(grpcChannels, Integer.toString(partitionId));
            nodeClient.deleteNode(id);
        }
    }


    @Override
    public void updateNode(String id, String label, Map<String, String> properties) throws NodeNotFoundException {
        int partitionId = partitionManager.getPartitionId(id);
        if (partitionId == Integer.parseInt(serverId)) {
            nodeService.updateNode(id, label, properties);
        } else {
            nodeClient = new NodeClient(grpcChannels, Integer.toString(partitionId));
            nodeClient.updateNode(id, label, properties);
        }
    }
    @Override
    public Iterable<Node> getAllNodes() {
        return null;
    }

    public void createNode(Node node) throws NodeAlreadyExistsException {
        String nodeId = node.getId();
        String nodeLabel = node.getLabel();
        Map<String, String> nodeProperties = node.getProperties();
        int partitionId = partitionManager.getPartitionId(nodeId);
        if (partitionId == Integer.parseInt(serverId)) {
            nodeService.createNode(node);
        } else {
            nodeClient = new NodeClient(grpcChannels, Integer.toString(partitionId));
            nodeClient.createNode(nodeId, nodeLabel, nodeProperties);
        }
    }


    @Override
    public Iterable<Edge> getOutgoingEdges(String nodeId) throws Exception {
        int partitionId = partitionManager.getPartitionId(nodeId);
        if (partitionId == Integer.parseInt(serverId)) {
            return nodeService.getOutgoingEdges(nodeId);
        } else {
            nodeClient = new NodeClient(grpcChannels, Integer.toString(partitionId));
            return nodeClient.getOutgoingEdges(nodeId);
        }
    }

    @Override
    public Iterable<Edge> getIncomingEdges(String nodeId) throws Exception {
        if (partitionManager.getPartitionId(nodeId) == Integer.parseInt(serverId)) {
            return nodeService.getIncomingEdges(nodeId);
        } else {
            return nodeClient.getIncomingEdges(nodeId);
        }
    }


    public Iterable<String> getAllNodesIds() {
        List<String> nodesIds = new ArrayList<>();
        Iterable<Node> nodesIdsFromMyServer = nodeService.getAllNodes();

        for (Node nodeId : nodesIdsFromMyServer) {
            nodesIds.add(nodeId.getId());
        }

        for (int i = 0; i < numOfServers; i++) {
            if (i != Integer.parseInt(serverId)) {
                Iterable<String> nodesIdsFromOtherServer = nodeClient.getAllNodesIds();
                for (String nodeId : nodesIdsFromOtherServer) {
                    nodesIds.add(nodeId);
                }
            }
        }

        return nodesIds;
    }

    public Iterable<Node> getNodesByIds(Iterable<String> nodesIds) {
        List<Node> nodes = new ArrayList<>();
        List<List<Node>> nodesByPartitionId = new ArrayList<>();
        for (int i = 0; i < numOfServers; i++) {
            nodesByPartitionId.add(new ArrayList<>());
        }

        ExecutorService executorService = Executors.newFixedThreadPool(numOfServers);
        List<List<String>> nodesIdsByPartitionId = partitionManager.groupNodesByPartitionId(nodesIds);
        List<Future<?>> futures = new ArrayList<>();

        for (int i = 0; i < numOfServers; i++) {
            if (!nodesIdsByPartitionId.get(i).isEmpty()) {
                // Submit a task to fetch nodes for the current partition
                int finalI = i;
                Future<?> future = executorService.submit(new Callable<Void>() {
                    @Override
                    public Void call() throws Exception {
                        List<Node> fetchedNodes = (List<Node>) nodeService.getNode(String.valueOf(nodesIdsByPartitionId.get(finalI)));
                        synchronized (nodesByPartitionId.get(finalI)) {
                            nodesByPartitionId.get(finalI).addAll(fetchedNodes);
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
        executorService.shutdown();
        for (int i = 0; i < numOfServers; i++) {
            nodes.addAll(nodesByPartitionId.get(i));
        }
        return nodes;
    }


    // Shutdown the ExecutorService when the application context is closed
    public void shutdown() {
        executorService.shutdown();
    }
}
