package com.playtab.bff.stamptour.rest;

import com.playtab.bff.stamptour.dto.request.VisitRequestDto;
import com.playtab.bff.stamptour.dto.response.VisitResultDto;
import com.playtab.bff.stamptour.service.StampTourFacade;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/stamp-tour")
public class StampTourRestController {

    private final StampTourFacade stampTourFacade;

    public StampTourRestController(StampTourFacade stampTourFacade) {
        this.stampTourFacade = stampTourFacade;
    }

    @PostMapping("/visit")
    public VisitResultDto visit(@RequestBody VisitRequestDto request) {
        return stampTourFacade.visit(request.getSpotId());
    }
}
