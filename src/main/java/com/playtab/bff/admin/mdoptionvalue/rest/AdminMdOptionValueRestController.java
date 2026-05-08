package com.playtab.bff.admin.mdoptionvalue.rest;

import com.playtab.bff.admin.mdoptionvalue.dto.request.AdminMdOptionValueRequestDto;
import com.playtab.bff.admin.mdoptionvalue.dto.response.AdminMdOptionValueDto;
import com.playtab.bff.admin.mdoptionvalue.service.AdminMdOptionValueFacade;
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
@RequestMapping("/admin/md-option-values")
public class AdminMdOptionValueRestController {

    private final AdminMdOptionValueFacade adminMdOptionValueFacade;

    public AdminMdOptionValueRestController(AdminMdOptionValueFacade adminMdOptionValueFacade) {
        this.adminMdOptionValueFacade = adminMdOptionValueFacade;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AdminMdOptionValueDto createMdOptionValue(@RequestBody AdminMdOptionValueRequestDto request) {
        return adminMdOptionValueFacade.createMdOptionValue(request);
    }

    @PutMapping("/{id}")
    public AdminMdOptionValueDto updateMdOptionValue(@PathVariable Long id, @RequestBody AdminMdOptionValueRequestDto request) {
        return adminMdOptionValueFacade.updateMdOptionValue(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMdOptionValue(@PathVariable Long id) {
        adminMdOptionValueFacade.deleteMdOptionValue(id);
    }
}
