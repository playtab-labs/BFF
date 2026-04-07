package com.playtab.bff.wristband.service;

import com.playtab.bff.grpc.client.WristbandGrpcClient;
import com.playtab.bff.wristband.dto.WristbandInfoDto;
import com.playtab.cloudgateservice.grpc.GetMyWristbandsResponse;
import com.playtab.cloudgateservice.grpc.LinkWristbandRequest;
import com.playtab.cloudgateservice.grpc.LinkWristbandResponse;
import com.playtab.cloudgateservice.grpc.WristbandInfo;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class WristbandFacade {

    private final WristbandGrpcClient wristbandGrpcClient;

    public WristbandFacade(WristbandGrpcClient wristbandGrpcClient) {
        this.wristbandGrpcClient = wristbandGrpcClient;
    }

    public WristbandInfoDto linkWristband(String rfid) {
        LinkWristbandRequest request = LinkWristbandRequest.newBuilder()
                .setRfid(rfid)
                .build();

        LinkWristbandResponse response = wristbandGrpcClient.linkWristband(request);

        WristbandInfoDto dto = new WristbandInfoDto();
        dto.setRfid(response.getRfid());
        dto.setActiveDate(response.getActiveDate());
        dto.setLinkedAt(response.getLinkedAt());
        return dto;
    }

    public List<WristbandInfoDto> getMyWristbands() {
        GetMyWristbandsResponse response = wristbandGrpcClient.getMyWristbands();

        return response.getWristbandsList().stream()
                .map(this::toWristbandInfoDto)
                .toList();
    }

    private WristbandInfoDto toWristbandInfoDto(WristbandInfo proto) {
        WristbandInfoDto dto = new WristbandInfoDto();
        dto.setRfid(proto.getRfid());
        dto.setActiveDate(proto.getActiveDate());
        dto.setLinkedAt(proto.getLinkedAt());
        return dto;
    }
}
