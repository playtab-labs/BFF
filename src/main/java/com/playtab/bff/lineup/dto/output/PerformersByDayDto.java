package com.playtab.bff.lineup.dto.output;

import java.util.List;

public class PerformersByDayDto {

    private FestivalDayDto day;
    private List<PerformerDto> performers;

    public FestivalDayDto getDay() {
        return day;
    }

    public void setDay(FestivalDayDto day) {
        this.day = day;
    }

    public List<PerformerDto> getPerformers() {
        return performers;
    }

    public void setPerformers(List<PerformerDto> performers) {
        this.performers = performers;
    }
}
