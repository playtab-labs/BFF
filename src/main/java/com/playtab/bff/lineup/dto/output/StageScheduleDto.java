package com.playtab.bff.lineup.dto.output;

import java.util.List;

public class StageScheduleDto {

    private StageDto stage;
    private List<ArtistScheduleDto> artists;

    public StageDto getStage() {
        return stage;
    }

    public void setStage(StageDto stage) {
        this.stage = stage;
    }

    public List<ArtistScheduleDto> getArtists() {
        return artists;
    }

    public void setArtists(List<ArtistScheduleDto> artists) {
        this.artists = artists;
    }
}
