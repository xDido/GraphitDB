package dev.graphitdb.Core.Distribute;

import java.util.ArrayList;
import java.util.List;

/**
 * The PartitionManager class is responsible for managing the distribution of nodes
 * and edges across multiple servers using hash partitioning.
 */
public class PartitionManager {
    private final int numberOfServers;
    private final String serverId;

    private static final int FNV_OFFSET_BASIS = 216613621; // FNV offset basis
    private static final int FNV_PRIME = 16777619; // FNV prime

    /**
     * Constructs a PartitionManager with the specified number of servers and the current server ID.
     *
     * @param numberOfServers the total number of servers/partitions
     * @param serverId the ID of the current server
     */
    public PartitionManager(int numberOfServers, String serverId) {
        this.numberOfServers = numberOfServers;
        this.serverId = serverId;
    }

    /**
     * Calculates the partition ID for a given node ID using FNV hash partitioning.
     *
     * @param nodeId the ID of the node
     * @return the partition ID associated with the node
     */
    public int getPartitionId(String nodeId) {
        int hash = FNV_OFFSET_BASIS;
        for (char c : nodeId.toCharArray()) {
            hash ^= c;
            hash *= FNV_PRIME;
        }
        return (hash % numberOfServers + numberOfServers) % numberOfServers;
    }

    /**
     * Groups the provided node IDs by their corresponding partition IDs.
     *
     * @param nodeIds an iterable collection of node IDs
     * @return a list of lists, where each inner list contains node IDs for a specific partition
     */
    public List<List<String>> groupNodesByPartitionId(Iterable<String> nodeIds) {
        List<List<String>> nodesByPartitionId = new ArrayList<>();
        for (int i = 0; i < numberOfServers; i++) {
            nodesByPartitionId.add(new ArrayList<>());
        }

        for (String nodeId : nodeIds) {
            int partitionId = getPartitionId(nodeId);
            nodesByPartitionId.get(partitionId).add(nodeId);
        }

        return nodesByPartitionId;
    }

    /**
     * Returns the total number of servers/partitions.
     *
     * @return the number of servers
     */
    public int getNumberOfServers() {
        return numberOfServers;
    }

    /**
     * Returns the ID of the current server.
     *
     * @return the current server ID
     */
    public String getServerId() {
        return serverId;
    }
}
