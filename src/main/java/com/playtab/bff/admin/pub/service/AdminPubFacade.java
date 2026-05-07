package com.playtab.bff.admin.pub.service;

import com.playtab.bff.admin.pub.dto.request.AdminPubRequestDto;
import com.playtab.bff.admin.pub.dto.response.AdminPubDto;
import com.playtab.bff.grpc.client.ContentGrpcClient;
import com.playtab.contentservice.grpc.proto.v1.AdminCreatePubRequest;
import com.playtab.contentservice.grpc.proto.v1.AdminDeletePubRequest;
import com.playtab.contentservice.grpc.proto.v1.AdminPub;
import com.playtab.contentservice.grpc.proto.v1.AdminUpdatePubRequest;
import java.util.Map;
import java.util.Objects;
import org.springframework.stereotype.Service;

@Service
public class AdminPubFacade {

    private final ContentGrpcClient contentGrpcClient;

    public AdminPubFacade(ContentGrpcClient contentGrpcClient) {
        this.contentGrpcClient = contentGrpcClient;
    }

    public AdminPubDto createPub(AdminPubRequestDto request) {
        AdminCreatePubRequest grpcRequest = AdminCreatePubRequest.newBuilder()
                .putAllCollegeName(nullSafe(request.collegeName()))
                .setIsNameConfirmed(request.isNameConfirmed())
                .setThumbnailImageUrl(Objects.requireNonNullElse(request.thumbnailImageUrl(), ""))
                .setDisplayOrder(request.displayOrder())
                .setIsVisible(request.isVisible())
                .build();
        return toDto(contentGrpcClient.adminCreatePub(grpcRequest).getPub());
    }

    public AdminPubDto updatePub(Long id, AdminPubRequestDto request) {
        AdminUpdatePubRequest grpcRequest = AdminUpdatePubRequest.newBuilder()
                .setId(id)
                .putAllCollegeName(nullSafe(request.collegeName()))
                .setIsNameConfirmed(request.isNameConfirmed())
                .setThumbnailImageUrl(Objects.requireNonNullElse(request.thumbnailImageUrl(), ""))
                .setDisplayOrder(request.displayOrder())
                .setIsVisible(request.isVisible())
                .build();
        return toDto(contentGrpcClient.adminUpdatePub(grpcRequest).getPub());
    }

    public void deletePub(Long id) {
        contentGrpcClient.adminDeletePub(
                AdminDeletePubRequest.newBuilder().setId(id).build()
        );
    }

    private AdminPubDto toDto(AdminPub pub) {
        return new AdminPubDto(
                pub.getId(),
                pub.getCollegeNameMap(),
                pub.getIsNameConfirmed(),
                pub.getThumbnailImageUrl(),
                pub.getDisplayOrder(),
                pub.getIsVisible(),
                pub.getCreatedAt(),
                pub.getUpdatedAt()
        );
    }

    private Map<String, String> nullSafe(Map<String, String> map) {
        return map != null ? map : Map.of();
    }
}
