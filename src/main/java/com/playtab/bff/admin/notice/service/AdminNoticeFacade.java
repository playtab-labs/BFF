package com.playtab.bff.admin.notice.service;

import com.playtab.bff.admin.notice.dto.request.AdminNoticeRequestDto;
import com.playtab.bff.admin.notice.dto.response.AdminNoticeDto;
import com.playtab.bff.grpc.client.ContentGrpcClient;
import com.playtab.contentservice.grpc.proto.v1.AdminCreateNoticeRequest;
import com.playtab.contentservice.grpc.proto.v1.AdminDeleteNoticeRequest;
import com.playtab.contentservice.grpc.proto.v1.AdminNotice;
import com.playtab.contentservice.grpc.proto.v1.AdminUpdateNoticeRequest;
import java.util.Map;
import java.util.Objects;
import org.springframework.stereotype.Service;

@Service
public class AdminNoticeFacade {

    private final ContentGrpcClient contentGrpcClient;

    public AdminNoticeFacade(ContentGrpcClient contentGrpcClient) {
        this.contentGrpcClient = contentGrpcClient;
    }

    public AdminNoticeDto createNotice(AdminNoticeRequestDto request) {
        AdminCreateNoticeRequest grpcRequest = AdminCreateNoticeRequest.newBuilder()
                .putAllTitle(nullSafe(request.title()))
                .putAllContent(nullSafe(request.content()))
                .setPostedAt(Objects.requireNonNullElse(request.postedAt(), ""))
                .setIsPinned(request.isPinned())
                .setIsVisible(request.isVisible())
                .setImageUrl(Objects.requireNonNullElse(request.imageUrl(), ""))
                .build();
        return toDto(contentGrpcClient.adminCreateNotice(grpcRequest).getNotice());
    }

    public AdminNoticeDto updateNotice(Long id, AdminNoticeRequestDto request) {
        AdminUpdateNoticeRequest grpcRequest = AdminUpdateNoticeRequest.newBuilder()
                .setId(id)
                .putAllTitle(nullSafe(request.title()))
                .putAllContent(nullSafe(request.content()))
                .setPostedAt(Objects.requireNonNullElse(request.postedAt(), ""))
                .setIsPinned(request.isPinned())
                .setIsVisible(request.isVisible())
                .setImageUrl(Objects.requireNonNullElse(request.imageUrl(), ""))
                .build();
        return toDto(contentGrpcClient.adminUpdateNotice(grpcRequest).getNotice());
    }

    public void deleteNotice(Long id) {
        contentGrpcClient.adminDeleteNotice(
                AdminDeleteNoticeRequest.newBuilder().setId(id).build()
        );
    }

    private AdminNoticeDto toDto(AdminNotice notice) {
        return new AdminNoticeDto(
                notice.getId(),
                notice.getTitleMap(),
                notice.getContentMap(),
                notice.getPostedAt(),
                notice.getIsPinned(),
                notice.getIsVisible(),
                notice.getImageUrl(),
                notice.getCreatedAt(),
                notice.getUpdatedAt()
        );
    }

    private Map<String, String> nullSafe(Map<String, String> map) {
        return map != null ? map : Map.of();
    }
}
