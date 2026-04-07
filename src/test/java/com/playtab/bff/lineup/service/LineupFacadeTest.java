package com.playtab.bff.lineup.service;

import com.playtab.bff.grpc.client.LineupGrpcClient;
import com.playtab.bff.lineup.dto.output.PerformerDto;
import com.playtab.lineupservice.grpc.proto.GetPerformersRequest;
import com.playtab.lineupservice.grpc.proto.GetPerformersResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class LineupFacadeTest {

    private LineupGrpcClient lineupGrpcClient;
    private LineupFacade lineupFacade;

    @BeforeEach
    void setUp() {
        lineupGrpcClient = Mockito.mock(LineupGrpcClient.class);
        lineupFacade = new LineupFacade(lineupGrpcClient);

        when(lineupGrpcClient.getPerformers(any(GetPerformersRequest.class)))
                .thenReturn(GetPerformersResponse.newBuilder().build());
    }

    @Test
    void shouldSetActiveOnlyTrueWhenArgumentIsNull() {
        List<PerformerDto> result = lineupFacade.getPerformers(null, null);

        ArgumentCaptor<GetPerformersRequest> captor =
                ArgumentCaptor.forClass(GetPerformersRequest.class);
        verify(lineupGrpcClient).getPerformers(captor.capture());

        GetPerformersRequest request = captor.getValue();

        assertTrue(request.getActiveOnly());
        assertEquals(List.of(), result);
    }

    @Test
    void shouldPassTrueWhenActiveOnlyIsTrue() {
        lineupFacade.getPerformers(true, null);

        ArgumentCaptor<GetPerformersRequest> captor =
                ArgumentCaptor.forClass(GetPerformersRequest.class);
        verify(lineupGrpcClient).getPerformers(captor.capture());

        GetPerformersRequest request = captor.getValue();

        assertTrue(request.getActiveOnly());
    }

    @Test
    void shouldPassFalseWhenActiveOnlyIsFalse() {
        lineupFacade.getPerformers(false, null);

        ArgumentCaptor<GetPerformersRequest> captor =
                ArgumentCaptor.forClass(GetPerformersRequest.class);
        verify(lineupGrpcClient).getPerformers(captor.capture());

        GetPerformersRequest request = captor.getValue();

        assertEquals(false, request.getActiveOnly());
    }

    @Test
    void shouldPassStageNameTogether() {
        lineupFacade.getPerformers(null, "pentaport");

        ArgumentCaptor<GetPerformersRequest> captor =
                ArgumentCaptor.forClass(GetPerformersRequest.class);
        verify(lineupGrpcClient).getPerformers(captor.capture());

        GetPerformersRequest request = captor.getValue();

        assertTrue(request.getActiveOnly());
        assertEquals("pentaport", request.getStageName());
    }
}