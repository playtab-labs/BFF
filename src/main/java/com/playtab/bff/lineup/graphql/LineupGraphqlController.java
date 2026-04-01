package com.playtab.bff.lineup.graphql;

import com.playtab.bff.lineup.dto.output.FavoriteDto;
import com.playtab.bff.lineup.dto.output.PerformanceScheduleDto;
import com.playtab.bff.lineup.dto.output.PerformerDto;
import com.playtab.bff.lineup.service.LineupFacade;
import java.util.List;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

@Controller
public class LineupGraphqlController {

    private final LineupFacade lineupFacade;

    public LineupGraphqlController(LineupFacade lineupFacade) {
        this.lineupFacade = lineupFacade;
    }

    @QueryMapping
    public List<PerformerDto> performers(
            @Argument Boolean activeOnly,
            @Argument String stageName
    ) {
        return lineupFacade.getPerformers(activeOnly, stageName);
    }

    @QueryMapping
    public List<PerformanceScheduleDto> schedulesByDay(
            @Argument int dayNumber,
            @Argument String stageName
    ) {
        return lineupFacade.getSchedulesByDay(dayNumber, stageName);
    }

    @QueryMapping
    public List<PerformerDto> myFavorites() {
        return lineupFacade.getMyFavorites();
    }

    @MutationMapping
    public FavoriteDto addFavorite(@Argument Long performerId) {
        return lineupFacade.addFavorite(performerId);
    }

    @MutationMapping
    public Boolean removeFavorite(@Argument Long performerId) {
        return lineupFacade.removeFavorite(performerId);
    }
}
