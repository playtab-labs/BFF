package com.playtab.bff.admin.mdoptionvalue.service;

import com.playtab.bff.admin.mdoptionvalue.dto.request.AdminMdOptionValueRequestDto;
import com.playtab.bff.admin.mdoptionvalue.dto.response.AdminMdOptionValueDto;
import com.playtab.bff.grpc.client.ContentGrpcClient;
import com.playtab.contentservice.grpc.proto.v1.AdminCreateMdOptionValueRequest;
import com.playtab.contentservice.grpc.proto.v1.AdminDeleteMdOptionValueRequest;
import com.playtab.contentservice.grpc.proto.v1.AdminMdOptionValue;
import com.playtab.contentservice.grpc.proto.v1.AdminUpdateMdOptionValueRequest;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class AdminMdOptionValueFacade {

    private final ContentGrpcClient contentGrpcClient;

    public AdminMdOptionValueFacade(ContentGrpcClient contentGrpcClient) {
        this.contentGrpcClient = contentGrpcClient;
    }

    public AdminMdOptionValueDto createMdOptionValue(AdminMdOptionValueRequestDto request) {
        AdminCreateMdOptionValueRequest grpcRequest = AdminCreateMdOptionValueRequest.newBuilder()
                .setOptionGroupId(request.optionGroupId())
                .putAllValueName(nullSafe(request.valueName()))
                .setExtraPrice(request.extraPrice())
                .setIsSoldOut(request.isSoldOut())
                .setDisplayOrder(request.displayOrder())
                .build();
        return toDto(contentGrpcClient.adminCreateMdOptionValue(grpcRequest).getOptionValue());
    }

    public AdminMdOptionValueDto updateMdOptionValue(Long id, AdminMdOptionValueRequestDto request) {
        AdminUpdateMdOptionValueRequest grpcRequest = AdminUpdateMdOptionValueRequest.newBuilder()
                .setId(id)
                .putAllValueName(nullSafe(request.valueName()))
                .setExtraPrice(request.extraPrice())
                .setIsSoldOut(request.isSoldOut())
                .setDisplayOrder(request.displayOrder())
                .build();
        return toDto(contentGrpcClient.adminUpdateMdOptionValue(grpcRequest).getOptionValue());
    }

    public void deleteMdOptionValue(Long id) {
        contentGrpcClient.adminDeleteMdOptionValue(
                AdminDeleteMdOptionValueRequest.newBuilder().setId(id).build()
        );
    }

    private AdminMdOptionValueDto toDto(AdminMdOptionValue value) {
        return new AdminMdOptionValueDto(
                value.getId(),
                value.getOptionGroupId(),
                value.getValueNameMap(),
                value.getExtraPrice(),
                value.getIsSoldOut(),
                value.getDisplayOrder(),
                value.getCreatedAt(),
                value.getUpdatedAt()
        );
    }

    private Map<String, String> nullSafe(Map<String, String> map) {
        return map != null ? map : Map.of();
    }
}
