package com.playtab.bff.stamptour.dto.output;

import java.util.List;

public class MyStampsResponseDto {

    private int totalCount;
    private int visitedCount;
    private List<StampSpotDto> spots;

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getVisitedCount() {
        return visitedCount;
    }

    public void setVisitedCount(int visitedCount) {
        this.visitedCount = visitedCount;
    }

    public List<StampSpotDto> getSpots() {
        return spots;
    }

    public void setSpots(List<StampSpotDto> spots) {
        this.spots = spots;
    }
}
