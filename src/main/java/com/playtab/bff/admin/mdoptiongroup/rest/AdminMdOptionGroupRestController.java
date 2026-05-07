package com.playtab.bff.admin.mdoptiongroup.rest;

import com.playtab.bff.admin.mdoptiongroup.dto.request.AdminMdOptionGroupRequestDto;
import com.playtab.bff.admin.mdoptiongroup.dto.response.AdminMdOptionGroupDto;
import com.playtab.bff.admin.mdoptiongroup.service.AdminMdOptionGroupFacade;
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
@RequestMapping("/admin/md-option-groups")
public class AdminMdOptionGroupRestController {

    private final AdminMdOptionGroupFacade adminMdOptionGroupFacade;

    public AdminMdOptionGroupRestController(AdminMdOptionGroupFacade adminMdOptionGroupFacade) {
        this.adminMdOptionGroupFacade = adminMdOptionGroupFacade;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AdminMdOptionGroupDto createMdOptionGroup(@RequestBody AdminMdOptionGroupRequestDto request) {
        return adminMdOptionGroupFacade.createMdOptionGroup(request);
    }

    @PutMapping("/{id}")
    public AdminMdOptionGroupDto updateMdOptionGroup(@PathVariable Long id, @RequestBody AdminMdOptionGroupRequestDto request) {
        return adminMdOptionGroupFacade.updateMdOptionGroup(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMdOptionGroup(@PathVariable Long id) {
        adminMdOptionGroupFacade.deleteMdOptionGroup(id);
    }
}
