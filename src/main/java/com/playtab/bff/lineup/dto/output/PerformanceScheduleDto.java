package com.playtab.bff.lineup.dto.output;

public class PerformanceScheduleDto {

    private Long id;
    private PerformerDto performer;
    private StageDto stage;
    private FestivalDayDto festivalDay;
    private String startAt;
    private String endAt;
    private String status;
    private PerformanceDurationDto duration;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PerformerDto getPerformer() {
        return performer;
    }

    public void setPerformer(PerformerDto performer) {
        this.performer = performer;
    }

    public StageDto getStage() {
        return stage;
    }

    public void setStage(StageDto stage) {
        this.stage = stage;
    }

    public FestivalDayDto getFestivalDay() {
        return festivalDay;
    }

    public void setFestivalDay(FestivalDayDto festivalDay) {
        this.festivalDay = festivalDay;
    }

    public String getStartAt() {
        return startAt;
    }

    public void setStartAt(String startAt) {
        this.startAt = startAt;
    }

    public String getEndAt() {
        return endAt;
    }

    public void setEndAt(String endAt) {
        this.endAt = endAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public PerformanceDurationDto getDuration() {
        return duration;
    }

    public void setDuration(PerformanceDurationDto duration) {
        this.duration = duration;
    }
}
