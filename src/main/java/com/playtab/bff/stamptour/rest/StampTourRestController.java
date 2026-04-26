package com.playtab.bff.stamptour.rest;

import com.playtab.bff.stamptour.dto.request.VisitRequestDto;
import com.playtab.bff.stamptour.dto.response.VisitResultDto;
import com.playtab.bff.stamptour.service.StampTourFacade;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Stamp Tour", description = "스탬프 투어 API")
@RestController
@RequestMapping("/api/v1/stamp-tour")
public class StampTourRestController {

    private final StampTourFacade stampTourFacade;

    public StampTourRestController(StampTourFacade stampTourFacade) {
        this.stampTourFacade = stampTourFacade;
    }

    @Operation(summary = "스탬프 투어 스팟 방문 처리")
    @SecurityRequirement(name = "bearerAuth")
    @PostMapping("/visit")
    public VisitResultDto visit(@RequestBody VisitRequestDto request) {
        return stampTourFacade.visit(request.getSpotId(), request.getLatitude(), request.getLongitude());
    }
}
