package com.playtab.bff.content.dto.output;

import java.util.List;

public class MdOptionGroupDto {

    private Long id;
    private String name;
    private int displayOrder;
    private List<MdOptionValueDto> values;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(int displayOrder) {
        this.displayOrder = displayOrder;
    }

    public List<MdOptionValueDto> getValues() {
        return values;
    }

    public void setValues(List<MdOptionValueDto> values) {
        this.values = values;
    }
}
