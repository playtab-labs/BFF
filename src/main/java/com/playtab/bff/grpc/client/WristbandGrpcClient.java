package com.playtab.bff.grpc.client;

import com.playtab.bff.config.CloudGateServiceProperties;
import com.playtab.cloudgateservice.grpc.GetMyWristbandsRequest;
import com.playtab.cloudgateservice.grpc.GetMyWristbandsResponse;
import com.playtab.cloudgateservice.grpc.LinkWristbandRequest;
import com.playtab.cloudgateservice.grpc.LinkWristbandResponse;
import com.playtab.cloudgateservice.grpc.WristbandServiceGrpc;
import java.util.concurrent.TimeUnit;
import org.springframework.stereotype.Component;

@Component
public class WristbandGrpcClient {

    private final long deadlineSeconds;
    private final WristbandServiceGrpc.WristbandServiceBlockingStub wristbandServiceBlockingStub;

    public WristbandGrpcClient(
            WristbandServiceGrpc.WristbandServiceBlockingStub wristbandServiceBlockingStub,
            CloudGateServiceProperties properties
    ) {
        this.wristbandServiceBlockingStub = wristbandServiceBlockingStub;
        this.deadlineSeconds = properties.getDeadlineSeconds();
    }

    public LinkWristbandResponse linkWristband(LinkWristbandRequest request) {
        return wristbandServiceBlockingStub
                .withDeadlineAfter(deadlineSeconds, TimeUnit.SECONDS)
                .linkWristband(request);
    }

    public GetMyWristbandsResponse getMyWristbands() {
        return wristbandServiceBlockingStub
                .withDeadlineAfter(deadlineSeconds, TimeUnit.SECONDS)
                .getMyWristbands(GetMyWristbandsRequest.newBuilder().build());
    }
}
