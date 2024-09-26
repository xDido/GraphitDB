package dev.graphitdb.Grpc.Clients;

import dev.graphitdb.Grpc.proto.EdgeServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EdgeClientConfig {

    @Bean
    public ManagedChannel edgeServiceChannel() {
        return ManagedChannelBuilder.forAddress("localhost", 50051) // Adjust the address and port
                .usePlaintext() // Use plaintext for development; use TLS in production
                .build();
    }

    @Bean
    public EdgeServiceGrpc.EdgeServiceBlockingStub edgeServiceStub(ManagedChannel channel) {
        return EdgeServiceGrpc.newBlockingStub(channel);
    }
}
