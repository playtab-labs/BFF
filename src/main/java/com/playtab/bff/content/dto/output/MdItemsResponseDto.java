package com.playtab.bff.content.dto.output;

import java.util.List;

public class MdItemsResponseDto {

    private List<MdItemSummaryDto> items;
    private PageInfoDto pageInfo;

    public List<MdItemSummaryDto> getItems() {
        return items;
    }

    public void setItems(List<MdItemSummaryDto> items) {
        this.items = items;
    }

    public PageInfoDto getPageInfo() {
        return pageInfo;
    }

    public void setPageInfo(PageInfoDto pageInfo) {
        this.pageInfo = pageInfo;
    }
}
