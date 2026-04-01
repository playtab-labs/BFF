package com.playtab.bff.grpc.client;

import com.playtab.bff.config.ContentServiceProperties;
import com.playtab.contentservice.grpc.proto.v1.*;
import java.util.concurrent.TimeUnit;
import org.springframework.stereotype.Component;

@Component
public class ContentGrpcClient {

    private final long deadlineSeconds;
    private final ContentServiceGrpc.ContentServiceBlockingStub contentServiceBlockingStub;

    public ContentGrpcClient(
            ContentServiceGrpc.ContentServiceBlockingStub contentServiceBlockingStub,
            ContentServiceProperties properties
    ) {
        this.contentServiceBlockingStub = contentServiceBlockingStub;
        this.deadlineSeconds = properties.getDeadlineSeconds();
    }

    public GetFoodTrucksResponse getFoodTrucks(GetFoodTrucksRequest request) {
        return contentServiceBlockingStub
                .withDeadlineAfter(deadlineSeconds, TimeUnit.SECONDS)
                .getFoodTrucks(request);
    }

    public GetPubsResponse getPubs(GetPubsRequest request) {
        return contentServiceBlockingStub
                .withDeadlineAfter(deadlineSeconds, TimeUnit.SECONDS)
                .getPubs(request);
    }

    public GetMdItemsResponse getMdItems(GetMdItemsRequest request) {
        return contentServiceBlockingStub
                .withDeadlineAfter(deadlineSeconds, TimeUnit.SECONDS)
                .getMdItems(request);
    }

    public GetMdItemDetailResponse getMdItemDetail(GetMdItemDetailRequest request) {
        return contentServiceBlockingStub
                .withDeadlineAfter(deadlineSeconds, TimeUnit.SECONDS)
                .getMdItemDetail(request);
    }

    public GetNoticesResponse getNotices(GetNoticesRequest request) {
        return contentServiceBlockingStub
                .withDeadlineAfter(deadlineSeconds, TimeUnit.SECONDS)
                .getNotices(request);
    }

    public GetNoticeDetailResponse getNoticeDetail(GetNoticeDetailRequest request) {
        return contentServiceBlockingStub
                .withDeadlineAfter(deadlineSeconds, TimeUnit.SECONDS)
                .getNoticeDetail(request);
    }
}
