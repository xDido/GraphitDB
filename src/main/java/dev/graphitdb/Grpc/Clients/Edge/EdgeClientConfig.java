package dev.graphitdb.Grpc.Clients.Edge;

import dev.graphitdb.Grpc.Stubs.Edge.EdgeServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EdgeClientConfig {

    @Bean
    public ManagedChannel edgeServiceChannel() {
        return ManagedChannelBuilder.forAddress("localhost", 50051)
                .usePlaintext()
                .build();
    }

    @Bean
    public EdgeServiceGrpc.EdgeServiceBlockingStub edgeServiceStub(ManagedChannel channel) {
        return EdgeServiceGrpc.newBlockingStub(channel);
    }
}
