package com.playtab.bff.admin.mdoptiongroup.service;

import com.playtab.bff.admin.mdoptiongroup.dto.request.AdminMdOptionGroupRequestDto;
import com.playtab.bff.admin.mdoptiongroup.dto.response.AdminMdOptionGroupDto;
import com.playtab.bff.grpc.client.ContentGrpcClient;
import com.playtab.contentservice.grpc.proto.v1.AdminCreateMdOptionGroupRequest;
import com.playtab.contentservice.grpc.proto.v1.AdminDeleteMdOptionGroupRequest;
import com.playtab.contentservice.grpc.proto.v1.AdminMdOptionGroup;
import com.playtab.contentservice.grpc.proto.v1.AdminUpdateMdOptionGroupRequest;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class AdminMdOptionGroupFacade {

    private final ContentGrpcClient contentGrpcClient;

    public AdminMdOptionGroupFacade(ContentGrpcClient contentGrpcClient) {
        this.contentGrpcClient = contentGrpcClient;
    }

    public AdminMdOptionGroupDto createMdOptionGroup(AdminMdOptionGroupRequestDto request) {
        AdminCreateMdOptionGroupRequest grpcRequest = AdminCreateMdOptionGroupRequest.newBuilder()
                .setMdItemId(request.mdItemId())
                .putAllName(nullSafe(request.name()))
                .setDisplayOrder(request.displayOrder())
                .build();
        return toDto(contentGrpcClient.adminCreateMdOptionGroup(grpcRequest).getOptionGroup());
    }

    public AdminMdOptionGroupDto updateMdOptionGroup(Long id, AdminMdOptionGroupRequestDto request) {
        AdminUpdateMdOptionGroupRequest grpcRequest = AdminUpdateMdOptionGroupRequest.newBuilder()
                .setId(id)
                .putAllName(nullSafe(request.name()))
                .setDisplayOrder(request.displayOrder())
                .build();
        return toDto(contentGrpcClient.adminUpdateMdOptionGroup(grpcRequest).getOptionGroup());
    }

    public void deleteMdOptionGroup(Long id) {
        contentGrpcClient.adminDeleteMdOptionGroup(
                AdminDeleteMdOptionGroupRequest.newBuilder().setId(id).build()
        );
    }

    private AdminMdOptionGroupDto toDto(AdminMdOptionGroup group) {
        return new AdminMdOptionGroupDto(
                group.getId(),
                group.getMdItemId(),
                group.getNameMap(),
                group.getDisplayOrder(),
                group.getCreatedAt(),
                group.getUpdatedAt()
        );
    }

    private Map<String, String> nullSafe(Map<String, String> map) {
        return map != null ? map : Map.of();
    }
}
