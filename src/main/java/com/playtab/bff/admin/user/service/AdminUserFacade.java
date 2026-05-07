package com.playtab.bff.admin.user.service;

import com.playtab.bff.admin.user.dto.response.AdminUserDetailDto;
import com.playtab.bff.admin.user.dto.response.AdminUserListDto;
import com.playtab.bff.admin.user.dto.response.AdminUserSummaryDto;
import com.playtab.bff.grpc.client.UserGrpcClient;
import com.playtab.userservice.proto.v1.AdminGetUserRequest;
import com.playtab.userservice.proto.v1.AdminListUsersRequest;
import com.playtab.userservice.proto.v1.AdminUserDetail;
import com.playtab.userservice.proto.v1.AdminUserSummary;
import java.time.Instant;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class AdminUserFacade {

    private final UserGrpcClient userGrpcClient;

    public AdminUserFacade(UserGrpcClient userGrpcClient) {
        this.userGrpcClient = userGrpcClient;
    }

    public AdminUserListDto listUsers(String emailFilter, int page, int size) {
        AdminListUsersRequest request = AdminListUsersRequest.newBuilder()
                .setEmailFilter(emailFilter != null ? emailFilter : "")
                .setPage(page)
                .setSize(size > 0 ? size : 20)
                .build();

        var response = userGrpcClient.adminListUsers(request);

        List<AdminUserSummaryDto> users = response.getUsersList().stream()
                .map(this::toSummaryDto)
                .toList();

        return new AdminUserListDto(users, response.getTotal(), response.getPage(), response.getSize());
    }

    public AdminUserDetailDto getUser(String identityId) {
        AdminGetUserRequest request = AdminGetUserRequest.newBuilder()
                .setIdentityId(identityId)
                .build();

        return toDetailDto(userGrpcClient.adminGetUser(request).getUser());
    }

    private AdminUserSummaryDto toSummaryDto(AdminUserSummary user) {
        return new AdminUserSummaryDto(
                user.getIdentityId(),
                user.getProfileId(),
                user.getEmail(),
                user.getName(),
                user.getRole(),
                user.getStatus(),
                Instant.ofEpochSecond(user.getCreatedAt().getSeconds(), user.getCreatedAt().getNanos()).toString()
        );
    }

    private AdminUserDetailDto toDetailDto(AdminUserDetail user) {
        return new AdminUserDetailDto(
                user.getIdentityId(),
                user.getProfileId(),
                user.getEmail(),
                user.getName(),
                user.getGender(),
                user.getPhoneNumber(),
                user.getBirthDate(),
                user.getIsAdult(),
                user.getNationality(),
                user.getRole(),
                user.getStatus(),
                Instant.ofEpochSecond(user.getCreatedAt().getSeconds(), user.getCreatedAt().getNanos()).toString(),
                Instant.ofEpochSecond(user.getUpdatedAt().getSeconds(), user.getUpdatedAt().getNanos()).toString()
        );
    }
}
