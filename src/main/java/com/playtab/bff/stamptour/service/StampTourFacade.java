package com.playtab.bff.stamptour.service;

import com.playtab.bff.grpc.client.StampTourGrpcClient;
import com.playtab.bff.stamptour.dto.output.MyStampsResponseDto;
import com.playtab.bff.stamptour.dto.output.StampSpotDto;
import com.playtab.bff.stamptour.dto.response.VisitResultDto;
import com.playtab.stamptourservice.grpc.proto.GetMyStampsResponse;
import com.playtab.stamptourservice.grpc.proto.StampSpot;
import com.playtab.stamptourservice.grpc.proto.VisitRequest;
import com.playtab.stamptourservice.grpc.proto.VisitResponse;
import org.springframework.stereotype.Service;

@Service
public class StampTourFacade {

    private final StampTourGrpcClient stampTourGrpcClient;

    public StampTourFacade(StampTourGrpcClient stampTourGrpcClient) {
        this.stampTourGrpcClient = stampTourGrpcClient;
    }

    public VisitResultDto visit(long spotId) {
        VisitRequest request = VisitRequest.newBuilder()
                .setSpotId(spotId)
                .build();

        VisitResponse response = stampTourGrpcClient.visit(request);

        VisitResultDto dto = new VisitResultDto();
        dto.setSuccess(response.getSuccess());
        dto.setMessage(response.getMessage());
        return dto;
    }

    public MyStampsResponseDto getMyStamps() {
        GetMyStampsResponse response = stampTourGrpcClient.getMyStamps();

        MyStampsResponseDto dto = new MyStampsResponseDto();
        dto.setTotalCount(response.getTotalCount());
        dto.setVisitedCount(response.getVisitedCount());
        dto.setSpots(response.getSpotsList().stream()
                .map(this::toStampSpotDto)
                .toList());
        return dto;
    }

    private StampSpotDto toStampSpotDto(StampSpot proto) {
        StampSpotDto dto = new StampSpotDto();
        dto.setSpotId(proto.getSpotId());
        dto.setSpotName(proto.getSpotName());
        dto.setSpotDescription(proto.getSpotDescription());
        dto.setVisited(proto.getVisited());
        dto.setVisitedAt(proto.getVisitedAt().isEmpty() ? null : proto.getVisitedAt());
        return dto;
    }
}
