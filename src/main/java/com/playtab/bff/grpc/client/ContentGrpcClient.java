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

    public AdminCreateNoticeResponse adminCreateNotice(AdminCreateNoticeRequest request) {
        return contentServiceBlockingStub
                .withDeadlineAfter(deadlineSeconds, TimeUnit.SECONDS)
                .adminCreateNotice(request);
    }

    public AdminUpdateNoticeResponse adminUpdateNotice(AdminUpdateNoticeRequest request) {
        return contentServiceBlockingStub
                .withDeadlineAfter(deadlineSeconds, TimeUnit.SECONDS)
                .adminUpdateNotice(request);
    }

    public AdminDeleteNoticeResponse adminDeleteNotice(AdminDeleteNoticeRequest request) {
        return contentServiceBlockingStub
                .withDeadlineAfter(deadlineSeconds, TimeUnit.SECONDS)
                .adminDeleteNotice(request);
    }

    public AdminCreateFoodTruckResponse adminCreateFoodTruck(AdminCreateFoodTruckRequest request) {
        return contentServiceBlockingStub
                .withDeadlineAfter(deadlineSeconds, TimeUnit.SECONDS)
                .adminCreateFoodTruck(request);
    }

    public AdminUpdateFoodTruckResponse adminUpdateFoodTruck(AdminUpdateFoodTruckRequest request) {
        return contentServiceBlockingStub
                .withDeadlineAfter(deadlineSeconds, TimeUnit.SECONDS)
                .adminUpdateFoodTruck(request);
    }

    public AdminDeleteFoodTruckResponse adminDeleteFoodTruck(AdminDeleteFoodTruckRequest request) {
        return contentServiceBlockingStub
                .withDeadlineAfter(deadlineSeconds, TimeUnit.SECONDS)
                .adminDeleteFoodTruck(request);
    }

    public AdminCreatePubResponse adminCreatePub(AdminCreatePubRequest request) {
        return contentServiceBlockingStub
                .withDeadlineAfter(deadlineSeconds, TimeUnit.SECONDS)
                .adminCreatePub(request);
    }

    public AdminUpdatePubResponse adminUpdatePub(AdminUpdatePubRequest request) {
        return contentServiceBlockingStub
                .withDeadlineAfter(deadlineSeconds, TimeUnit.SECONDS)
                .adminUpdatePub(request);
    }

    public AdminDeletePubResponse adminDeletePub(AdminDeletePubRequest request) {
        return contentServiceBlockingStub
                .withDeadlineAfter(deadlineSeconds, TimeUnit.SECONDS)
                .adminDeletePub(request);
    }

    public AdminCreateMdItemResponse adminCreateMdItem(AdminCreateMdItemRequest request) {
        return contentServiceBlockingStub
                .withDeadlineAfter(deadlineSeconds, TimeUnit.SECONDS)
                .adminCreateMdItem(request);
    }

    public AdminUpdateMdItemResponse adminUpdateMdItem(AdminUpdateMdItemRequest request) {
        return contentServiceBlockingStub
                .withDeadlineAfter(deadlineSeconds, TimeUnit.SECONDS)
                .adminUpdateMdItem(request);
    }

    public AdminDeleteMdItemResponse adminDeleteMdItem(AdminDeleteMdItemRequest request) {
        return contentServiceBlockingStub
                .withDeadlineAfter(deadlineSeconds, TimeUnit.SECONDS)
                .adminDeleteMdItem(request);
    }

    public AdminCreateMdOptionGroupResponse adminCreateMdOptionGroup(AdminCreateMdOptionGroupRequest request) {
        return contentServiceBlockingStub
                .withDeadlineAfter(deadlineSeconds, TimeUnit.SECONDS)
                .adminCreateMdOptionGroup(request);
    }

    public AdminUpdateMdOptionGroupResponse adminUpdateMdOptionGroup(AdminUpdateMdOptionGroupRequest request) {
        return contentServiceBlockingStub
                .withDeadlineAfter(deadlineSeconds, TimeUnit.SECONDS)
                .adminUpdateMdOptionGroup(request);
    }

    public AdminDeleteMdOptionGroupResponse adminDeleteMdOptionGroup(AdminDeleteMdOptionGroupRequest request) {
        return contentServiceBlockingStub
                .withDeadlineAfter(deadlineSeconds, TimeUnit.SECONDS)
                .adminDeleteMdOptionGroup(request);
    }

    public AdminCreateMdOptionValueResponse adminCreateMdOptionValue(AdminCreateMdOptionValueRequest request) {
        return contentServiceBlockingStub
                .withDeadlineAfter(deadlineSeconds, TimeUnit.SECONDS)
                .adminCreateMdOptionValue(request);
    }

    public AdminUpdateMdOptionValueResponse adminUpdateMdOptionValue(AdminUpdateMdOptionValueRequest request) {
        return contentServiceBlockingStub
                .withDeadlineAfter(deadlineSeconds, TimeUnit.SECONDS)
                .adminUpdateMdOptionValue(request);
    }

    public AdminDeleteMdOptionValueResponse adminDeleteMdOptionValue(AdminDeleteMdOptionValueRequest request) {
        return contentServiceBlockingStub
                .withDeadlineAfter(deadlineSeconds, TimeUnit.SECONDS)
                .adminDeleteMdOptionValue(request);
    }
}
