package com.playtab.bff.stamptour.dto.output;

public class StampSpotDto {

    private Long spotId;
    private String spotName;
    private String spotDescription;
    private boolean visited;
    private String visitedAt;

    public Long getSpotId() {
        return spotId;
    }

    public void setSpotId(Long spotId) {
        this.spotId = spotId;
    }

    public String getSpotName() {
        return spotName;
    }

    public void setSpotName(String spotName) {
        this.spotName = spotName;
    }

    public String getSpotDescription() {
        return spotDescription;
    }

    public void setSpotDescription(String spotDescription) {
        this.spotDescription = spotDescription;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public String getVisitedAt() {
        return visitedAt;
    }

    public void setVisitedAt(String visitedAt) {
        this.visitedAt = visitedAt;
    }
}
