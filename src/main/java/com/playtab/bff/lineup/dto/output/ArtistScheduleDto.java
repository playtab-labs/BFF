package com.playtab.bff.lineup.dto.output;

public class ArtistScheduleDto {

    private Long scheduleId;
    private PerformerDto performer;
    private String startAt;
    private String endAt;
    private String status;
    private PerformanceDurationDto duration;

    public Long getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(Long scheduleId) {
        this.scheduleId = scheduleId;
    }

    public PerformerDto getPerformer() {
        return performer;
    }

    public void setPerformer(PerformerDto performer) {
        this.performer = performer;
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
