package com.playtab.bff.lineup.dto.output;

import java.util.Map;

public class StageDto {

    private Long id;
    private Map<String, String> name;
    private Map<String, String> locationDesc;
    private int displayOrder;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Map<String, String> getName() {
        return name;
    }

    public void setName(Map<String, String> name) {
        this.name = name;
    }

    public Map<String, String> getLocationDesc() {
        return locationDesc;
    }

    public void setLocationDesc(Map<String, String> locationDesc) {
        this.locationDesc = locationDesc;
    }

    public int getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(int displayOrder) {
        this.displayOrder = displayOrder;
    }
}
