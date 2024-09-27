package dev.graphitdb.Core.Distribute;

import dev.graphitdb.Core.DataStructure.Node.LocalNodeService;
import dev.graphitdb.Grpc.Clients.Node.NodeClient;
import org.springframework.beans.factory.annotation.Value;

import java.util.ArrayList;
import java.util.List;

/**
 * The PartitionManager class is responsible for managing the distribution of nodes
 * and edges across multiple servers using hash partitioning.
 */
public class PartitionManager {

    private static int numberOfServers = 2;
    @Value("${myserver.serverId}")
    private static String serverId = "0";
    LocalNodeService nodeService;
    NodeClient nodeClient;
    private static final int FNV_OFFSET_BASIS = 216613621; // FNV offset basis
    private static final int FNV_PRIME = 16777619; // FNV prime

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
     * Groups the provided Object IDs by their corresponding partition IDs.
     *
     * @param Ids an iterable collection of node IDs
     * @return a list of lists, where each inner list contains IDs for a specific partition
     */
    public List<List<String>> groupNodesByPartitionId(Iterable<String> Ids) {
        List<List<String>> objectsByPartitionId = new ArrayList<>();
        for (int i = 0; i < numberOfServers; i++) {
            objectsByPartitionId.add(new ArrayList<>());
        }

        for (String id : Ids) {
            int partitionId = getPartitionId(id);
            objectsByPartitionId.get(partitionId).add(id);
        }

        return objectsByPartitionId;
    }


    /**
     * Returns the total number of servers/partitions.
     *
     * @return the number of servers
     */
    public static int getNumberOfServers() {
        return numberOfServers;
    }

    /**
     * Returns the ID of the current server.
     *
     * @return the current server ID
     */
    public static String getServerId() {
        return serverId;
    }
}
