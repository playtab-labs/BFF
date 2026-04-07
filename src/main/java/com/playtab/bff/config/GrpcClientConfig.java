package com.playtab.bff.config;

import com.playtab.cloudgateservice.grpc.WristbandServiceGrpc;
import com.playtab.contentservice.grpc.proto.v1.ContentServiceGrpc;
import com.playtab.lineupservice.grpc.proto.LineupServiceGrpc;
import com.playtab.stamptourservice.grpc.proto.StampTourServiceGrpc;
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

    @Bean(destroyMethod = "shutdown")
    @Qualifier("contentServiceChannel")
    public ManagedChannel contentServiceChannel(
            ContentServiceProperties properties,
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
    public ContentServiceGrpc.ContentServiceBlockingStub contentServiceBlockingStub(
            @Qualifier("contentServiceChannel") ManagedChannel contentServiceChannel
    ) {
        return ContentServiceGrpc.newBlockingStub(contentServiceChannel);
    }

    @Bean(destroyMethod = "shutdown")
    @Qualifier("stampTourServiceChannel")
    public ManagedChannel stampTourServiceChannel(
            StampTourServiceProperties properties,
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
    public StampTourServiceGrpc.StampTourServiceBlockingStub stampTourServiceBlockingStub(
            @Qualifier("stampTourServiceChannel") ManagedChannel stampTourServiceChannel
    ) {
        return StampTourServiceGrpc.newBlockingStub(stampTourServiceChannel);
    }

    @Bean(destroyMethod = "shutdown")
    @Qualifier("cloudGateServiceChannel")
    public ManagedChannel cloudGateServiceChannel(
            CloudGateServiceProperties properties,
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
    public WristbandServiceGrpc.WristbandServiceBlockingStub wristbandServiceBlockingStub(
            @Qualifier("cloudGateServiceChannel") ManagedChannel cloudGateServiceChannel
    ) {
        return WristbandServiceGrpc.newBlockingStub(cloudGateServiceChannel);
    }
}