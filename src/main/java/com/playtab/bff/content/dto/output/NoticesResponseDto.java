package com.playtab.bff.content.dto.output;

import java.util.List;

public class NoticesResponseDto {

    private List<NoticeSummaryDto> notices;
    private PageInfoDto pageInfo;

    public List<NoticeSummaryDto> getNotices() {
        return notices;
    }

    public void setNotices(List<NoticeSummaryDto> notices) {
        this.notices = notices;
    }

    public PageInfoDto getPageInfo() {
        return pageInfo;
    }

    public void setPageInfo(PageInfoDto pageInfo) {
        this.pageInfo = pageInfo;
    }
}
