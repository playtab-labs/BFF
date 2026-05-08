package com.playtab.bff.admin.mditem.rest;

import com.playtab.bff.admin.mditem.dto.request.AdminMdItemRequestDto;
import com.playtab.bff.admin.mditem.dto.response.AdminMdItemDto;
import com.playtab.bff.admin.mditem.service.AdminMdItemFacade;
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
@RequestMapping("/admin/md-items")
public class AdminMdItemRestController {

    private final AdminMdItemFacade adminMdItemFacade;

    public AdminMdItemRestController(AdminMdItemFacade adminMdItemFacade) {
        this.adminMdItemFacade = adminMdItemFacade;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AdminMdItemDto createMdItem(@RequestBody AdminMdItemRequestDto request) {
        return adminMdItemFacade.createMdItem(request);
    }

    @PutMapping("/{id}")
    public AdminMdItemDto updateMdItem(@PathVariable Long id, @RequestBody AdminMdItemRequestDto request) {
        return adminMdItemFacade.updateMdItem(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMdItem(@PathVariable Long id) {
        adminMdItemFacade.deleteMdItem(id);
    }
}
