package com.playtab.bff.admin.notice.rest;

import com.playtab.bff.admin.notice.dto.request.AdminNoticeRequestDto;
import com.playtab.bff.admin.notice.dto.response.AdminNoticeDto;
import com.playtab.bff.admin.notice.service.AdminNoticeFacade;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/notices")
public class AdminNoticeRestController {

    private final AdminNoticeFacade adminNoticeFacade;

    public AdminNoticeRestController(AdminNoticeFacade adminNoticeFacade) {
        this.adminNoticeFacade = adminNoticeFacade;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AdminNoticeDto createNotice(@RequestBody AdminNoticeRequestDto request) {
        return adminNoticeFacade.createNotice(request);
    }

    @PutMapping("/{id}")
    public AdminNoticeDto updateNotice(@PathVariable Long id, @RequestBody AdminNoticeRequestDto request) {
        return adminNoticeFacade.updateNotice(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteNotice(@PathVariable Long id) {
        adminNoticeFacade.deleteNotice(id);
    }
}
