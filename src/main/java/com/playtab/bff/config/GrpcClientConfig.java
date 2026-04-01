package com.playtab.bff.config;

import com.playtab.lineupservice.grpc.proto.LineupServiceGrpc;
import com.playtab.userservice.proto.v1.AuthServiceGrpc;
import com.playtab.userservice.proto.v1.UserServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GrpcClientConfig {

    @Bean(destroyMethod = "shutdown")
    @Qualifier("userServiceChannel")
    public ManagedChannel userServiceChannel(
            GrpcProperties properties,
            GrpcAuthInterceptor grpcAuthInterceptor
    ) {
        ManagedChannelBuilder<?> builder = ManagedChannelBuilder
                .forAddress(properties.getHost(), properties.getPort())
                .intercept(grpcAuthInterceptor);

        if (properties.isPlaintext()) {
            builder.usePlaintext();
        }

        return builder.build();
    }

    @Bean(destroyMethod = "shutdown")
    @Qualifier("lineupServiceChannel")
    public ManagedChannel lineupServiceChannel(
            LineupServiceProperties properties,
            GrpcAuthInterceptor grpcAuthInterceptor
    ) {
        ManagedChannelBuilder<?> builder = ManagedChannelBuilder
                .forAddress(properties.getHost(), properties.getPort())
                .intercept(grpcAuthInterceptor);

        if (properties.isPlaintext()) {
            builder.usePlaintext();
        }

        return builder.build();
    }

    @Bean
    public UserServiceGrpc.UserServiceBlockingStub userServiceBlockingStub(
            @Qualifier("userServiceChannel") ManagedChannel userServiceChannel
    ) {
        return UserServiceGrpc.newBlockingStub(userServiceChannel);
    }

    @Bean
    public AuthServiceGrpc.AuthServiceBlockingStub authServiceBlockingStub(
            @Qualifier("userServiceChannel") ManagedChannel userServiceChannel
    ) {
        return AuthServiceGrpc.newBlockingStub(userServiceChannel);
    }

    @Bean
    public LineupServiceGrpc.LineupServiceBlockingStub lineupServiceBlockingStub(
            @Qualifier("lineupServiceChannel") ManagedChannel lineupServiceChannel
    ) {
        return LineupServiceGrpc.newBlockingStub(lineupServiceChannel);
    }
}