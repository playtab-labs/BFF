package com.playtab.bff.admin.notice.dto.response;

import java.util.Map;

public record AdminNoticeDto(
        Long id,
        Map<String, String> title,
        Map<String, String> content,
        String postedAt,
        boolean isPinned,
        boolean isVisible,
        String imageUrl,
        String createdAt,
        String updatedAt
) {}
