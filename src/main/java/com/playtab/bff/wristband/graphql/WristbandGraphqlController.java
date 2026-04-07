package com.playtab.bff.wristband.graphql;

import com.playtab.bff.wristband.dto.WristbandInfoDto;
import com.playtab.bff.wristband.service.WristbandFacade;
import java.util.List;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

@Controller
public class WristbandGraphqlController {

    private final WristbandFacade wristbandFacade;

    public WristbandGraphqlController(WristbandFacade wristbandFacade) {
        this.wristbandFacade = wristbandFacade;
    }

    @MutationMapping
    public WristbandInfoDto linkWristband(@Argument String rfid) {
        return wristbandFacade.linkWristband(rfid);
    }

    @QueryMapping
    public List<WristbandInfoDto> myWristbands() {
        return wristbandFacade.getMyWristbands();
    }
}
