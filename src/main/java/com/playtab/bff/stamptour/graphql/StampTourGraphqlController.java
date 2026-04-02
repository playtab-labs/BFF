package com.playtab.bff.stamptour.graphql;

import com.playtab.bff.stamptour.dto.output.MyStampsResponseDto;
import com.playtab.bff.stamptour.service.StampTourFacade;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

@Controller
public class StampTourGraphqlController {

    private final StampTourFacade stampTourFacade;

    public StampTourGraphqlController(StampTourFacade stampTourFacade) {
        this.stampTourFacade = stampTourFacade;
    }

    @QueryMapping
    public MyStampsResponseDto myStamps() {
        return stampTourFacade.getMyStamps();
    }
}
