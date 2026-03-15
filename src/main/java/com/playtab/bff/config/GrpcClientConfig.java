package com.playtab.bff.config;

import com.playtab.userservice.proto.v1.AuthServiceGrpc;
import com.playtab.userservice.proto.v1.UserServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GrpcClientConfig {

    @Bean(destroyMethod = "shutdown")
    public ManagedChannel userServiceChannel(GrpcProperties properties) {
        ManagedChannelBuilder<?> builder = ManagedChannelBuilder
                .forAddress(properties.getHost(), properties.getPort());

        if (properties.isPlaintext()) {
            builder.usePlaintext();
        }

        return builder.build();
    }

    @Bean
    public UserServiceGrpc.UserServiceBlockingStub userServiceBlockingStub(
            ManagedChannel userServiceChannel
    ) {
        return UserServiceGrpc.newBlockingStub(userServiceChannel);
    }

    @Bean
    public AuthServiceGrpc.AuthServiceBlockingStub authServiceBlockingStub(
            ManagedChannel userServiceChannel
    ) {
        return AuthServiceGrpc.newBlockingStub(userServiceChannel);
    }
}