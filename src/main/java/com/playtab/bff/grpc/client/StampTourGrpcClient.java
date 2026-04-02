package com.playtab.bff.grpc.client;

import com.playtab.bff.config.StampTourServiceProperties;
import com.playtab.stamptourservice.grpc.proto.GetMyStampsRequest;
import com.playtab.stamptourservice.grpc.proto.GetMyStampsResponse;
import com.playtab.stamptourservice.grpc.proto.StampTourServiceGrpc;
import com.playtab.stamptourservice.grpc.proto.VisitRequest;
import com.playtab.stamptourservice.grpc.proto.VisitResponse;
import java.util.concurrent.TimeUnit;
import org.springframework.stereotype.Component;

@Component
public class StampTourGrpcClient {

    private final long deadlineSeconds;
    private final StampTourServiceGrpc.StampTourServiceBlockingStub stampTourServiceBlockingStub;

    public StampTourGrpcClient(
            StampTourServiceGrpc.StampTourServiceBlockingStub stampTourServiceBlockingStub,
            StampTourServiceProperties properties
    ) {
        this.stampTourServiceBlockingStub = stampTourServiceBlockingStub;
        this.deadlineSeconds = properties.getDeadlineSeconds();
    }

    public VisitResponse visit(VisitRequest request) {
        return stampTourServiceBlockingStub
                .withDeadlineAfter(deadlineSeconds, TimeUnit.SECONDS)
                .visit(request);
    }

    public GetMyStampsResponse getMyStamps() {
        return stampTourServiceBlockingStub
                .withDeadlineAfter(deadlineSeconds, TimeUnit.SECONDS)
                .getMyStamps(GetMyStampsRequest.newBuilder().build());
    }
}
