package com.playtab.bff.admin.foodtruck.service;

import com.playtab.bff.admin.foodtruck.dto.request.AdminFoodTruckRequestDto;
import com.playtab.bff.admin.foodtruck.dto.response.AdminFoodTruckDto;
import com.playtab.bff.grpc.client.ContentGrpcClient;
import com.playtab.contentservice.grpc.proto.v1.AdminCreateFoodTruckRequest;
import com.playtab.contentservice.grpc.proto.v1.AdminDeleteFoodTruckRequest;
import com.playtab.contentservice.grpc.proto.v1.AdminFoodTruck;
import com.playtab.contentservice.grpc.proto.v1.AdminUpdateFoodTruckRequest;
import java.util.Map;
import java.util.Objects;
import org.springframework.stereotype.Service;

@Service
public class AdminFoodTruckFacade {

    private final ContentGrpcClient contentGrpcClient;

    public AdminFoodTruckFacade(ContentGrpcClient contentGrpcClient) {
        this.contentGrpcClient = contentGrpcClient;
    }

    public AdminFoodTruckDto createFoodTruck(AdminFoodTruckRequestDto request) {
        AdminCreateFoodTruckRequest grpcRequest = AdminCreateFoodTruckRequest.newBuilder()
                .putAllName(nullSafe(request.name()))
                .putAllShortDescription(nullSafe(request.shortDescription()))
                .setThumbnailImageUrl(Objects.requireNonNullElse(request.thumbnailImageUrl(), ""))
                .setDisplayOrder(request.displayOrder())
                .setIsVisible(request.isVisible())
                .build();
        return toDto(contentGrpcClient.adminCreateFoodTruck(grpcRequest).getFoodTruck());
    }

    public AdminFoodTruckDto updateFoodTruck(Long id, AdminFoodTruckRequestDto request) {
        AdminUpdateFoodTruckRequest grpcRequest = AdminUpdateFoodTruckRequest.newBuilder()
                .setId(id)
                .putAllName(nullSafe(request.name()))
                .putAllShortDescription(nullSafe(request.shortDescription()))
                .setThumbnailImageUrl(Objects.requireNonNullElse(request.thumbnailImageUrl(), ""))
                .setDisplayOrder(request.displayOrder())
                .setIsVisible(request.isVisible())
                .build();
        return toDto(contentGrpcClient.adminUpdateFoodTruck(grpcRequest).getFoodTruck());
    }

    public void deleteFoodTruck(Long id) {
        contentGrpcClient.adminDeleteFoodTruck(
                AdminDeleteFoodTruckRequest.newBuilder().setId(id).build()
        );
    }

    private AdminFoodTruckDto toDto(AdminFoodTruck foodTruck) {
        return new AdminFoodTruckDto(
                foodTruck.getId(),
                foodTruck.getNameMap(),
                foodTruck.getShortDescriptionMap(),
                foodTruck.getThumbnailImageUrl(),
                foodTruck.getDisplayOrder(),
                foodTruck.getIsVisible(),
                foodTruck.getCreatedAt(),
                foodTruck.getUpdatedAt()
        );
    }

    private Map<String, String> nullSafe(Map<String, String> map) {
        return map != null ? map : Map.of();
    }
}
