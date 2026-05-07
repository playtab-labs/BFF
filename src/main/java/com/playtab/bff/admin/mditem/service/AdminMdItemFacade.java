package com.playtab.bff.admin.mditem.service;

import com.playtab.bff.admin.mditem.dto.request.AdminMdItemRequestDto;
import com.playtab.bff.admin.mditem.dto.response.AdminMdItemDto;
import com.playtab.bff.grpc.client.ContentGrpcClient;
import com.playtab.contentservice.grpc.proto.v1.AdminCreateMdItemRequest;
import com.playtab.contentservice.grpc.proto.v1.AdminDeleteMdItemRequest;
import com.playtab.contentservice.grpc.proto.v1.AdminMdItem;
import com.playtab.contentservice.grpc.proto.v1.AdminUpdateMdItemRequest;
import java.util.Map;
import java.util.Objects;
import org.springframework.stereotype.Service;

@Service
public class AdminMdItemFacade {

    private final ContentGrpcClient contentGrpcClient;

    public AdminMdItemFacade(ContentGrpcClient contentGrpcClient) {
        this.contentGrpcClient = contentGrpcClient;
    }

    public AdminMdItemDto createMdItem(AdminMdItemRequestDto request) {
        AdminCreateMdItemRequest grpcRequest = AdminCreateMdItemRequest.newBuilder()
                .putAllName(nullSafe(request.name()))
                .setPrice(request.price())
                .putAllProductDescription(nullSafe(request.productDescription()))
                .putAllDetailDescription(nullSafe(request.detailDescription()))
                .setThumbnailImageUrl(Objects.requireNonNullElse(request.thumbnailImageUrl(), ""))
                .setDetailImageUrl(Objects.requireNonNullElse(request.detailImageUrl(), ""))
                .setIsSoldOut(request.isSoldOut())
                .setDisplayOrder(request.displayOrder())
                .setIsVisible(request.isVisible())
                .build();
        return toDto(contentGrpcClient.adminCreateMdItem(grpcRequest).getMdItem());
    }

    public AdminMdItemDto updateMdItem(Long id, AdminMdItemRequestDto request) {
        AdminUpdateMdItemRequest grpcRequest = AdminUpdateMdItemRequest.newBuilder()
                .setId(id)
                .putAllName(nullSafe(request.name()))
                .setPrice(request.price())
                .putAllProductDescription(nullSafe(request.productDescription()))
                .putAllDetailDescription(nullSafe(request.detailDescription()))
                .setThumbnailImageUrl(Objects.requireNonNullElse(request.thumbnailImageUrl(), ""))
                .setDetailImageUrl(Objects.requireNonNullElse(request.detailImageUrl(), ""))
                .setIsSoldOut(request.isSoldOut())
                .setDisplayOrder(request.displayOrder())
                .setIsVisible(request.isVisible())
                .build();
        return toDto(contentGrpcClient.adminUpdateMdItem(grpcRequest).getMdItem());
    }

    public void deleteMdItem(Long id) {
        contentGrpcClient.adminDeleteMdItem(
                AdminDeleteMdItemRequest.newBuilder().setId(id).build()
        );
    }

    private AdminMdItemDto toDto(AdminMdItem mdItem) {
        return new AdminMdItemDto(
                mdItem.getId(),
                mdItem.getNameMap(),
                mdItem.getPrice(),
                mdItem.getProductDescriptionMap(),
                mdItem.getDetailDescriptionMap(),
                mdItem.getThumbnailImageUrl(),
                mdItem.getDetailImageUrl(),
                mdItem.getIsSoldOut(),
                mdItem.getDisplayOrder(),
                mdItem.getIsVisible(),
                mdItem.getCreatedAt(),
                mdItem.getUpdatedAt()
        );
    }

    private Map<String, String> nullSafe(Map<String, String> map) {
        return map != null ? map : Map.of();
    }
}
