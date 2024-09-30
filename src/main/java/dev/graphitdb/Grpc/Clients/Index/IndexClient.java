package dev.graphitdb.Grpc.Clients.Index;

import dev.graphitdb.Grpc.Index.*;
import io.grpc.ManagedChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class IndexClient {

    private final Map<String, ManagedChannel> grpcChannels;

    @Autowired
    public IndexClient(@Qualifier("grpcChannels") Map<String, ManagedChannel> grpcChannels) {
        this.grpcChannels = grpcChannels;
    }

    private IndexServiceGrpc.IndexServiceBlockingStub getBlockingStub(String serverId) {
        ManagedChannel channel = grpcChannels.get(serverId);
        if (channel == null) {
            throw new IllegalArgumentException("No gRPC channel found for server ID: " + serverId);
        }
        return IndexServiceGrpc.newBlockingStub(channel);
    }

    public boolean createIndex(String indexName, String serverId) {
        IndexServiceGrpc.IndexServiceBlockingStub blockingStub = getBlockingStub(serverId);
        CreateIndexRequest request = CreateIndexRequest.newBuilder()
                .setIndexName(indexName)
                .build();
        CreateIndexResponse response = blockingStub.createIndex(request);
        return response.getSuccess();
    }

    public boolean deleteIndex(String indexName, String serverId) {
        IndexServiceGrpc.IndexServiceBlockingStub blockingStub = getBlockingStub(serverId);
        DeleteIndexRequest request = DeleteIndexRequest.newBuilder()
                .setIndexName(indexName)
                .build();
        DeleteIndexResponse response = blockingStub.deleteIndex(request);
        return response.getSuccess();
    }

    public boolean createIndices(Iterable<String> indexNames, String serverId) {
        IndexServiceGrpc.IndexServiceBlockingStub blockingStub = getBlockingStub(serverId);
        CreateIndicesRequest request = CreateIndicesRequest.newBuilder()
                .addAllIndicesNames(indexNames)
                .build();
        CreateIndicesResponse response = blockingStub.createIndices(request);
        return response.getSuccess();
    }

    public boolean deleteIndices(Iterable<String> indexNames, String serverId) {
        IndexServiceGrpc.IndexServiceBlockingStub blockingStub = getBlockingStub(serverId);
        DeleteIndicesRequest request = DeleteIndicesRequest.newBuilder()
                .addAllIndicesNames(indexNames)
                .build();
        DeleteIndicesResponse response = blockingStub.deleteIndices(request);
        return response.getSuccess();
    }

    public void shutdownChannel(String serverId) throws InterruptedException {
        ManagedChannel channel = grpcChannels.get(serverId);
        if (channel != null) {
            channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
        }
    }
}
