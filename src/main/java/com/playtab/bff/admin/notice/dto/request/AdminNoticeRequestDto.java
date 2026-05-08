package com.playtab.bff.admin.notice.dto.request;

import java.util.Map;

public record AdminNoticeRequestDto(
        Map<String, String> title,
        Map<String, String> content,
        String postedAt,
        boolean isPinned,
        boolean isVisible,
        String imageUrl
) {}
