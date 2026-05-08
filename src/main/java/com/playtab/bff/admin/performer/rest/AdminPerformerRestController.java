package com.playtab.bff.admin.performer.rest;

import com.playtab.bff.admin.performer.dto.request.AdminPerformerRequestDto;
import com.playtab.bff.admin.performer.dto.response.AdminPerformerDto;
import com.playtab.bff.admin.performer.service.AdminPerformerFacade;
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
@RequestMapping("/admin/performers")
public class AdminPerformerRestController {

    private final AdminPerformerFacade adminPerformerFacade;

    public AdminPerformerRestController(AdminPerformerFacade adminPerformerFacade) {
        this.adminPerformerFacade = adminPerformerFacade;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AdminPerformerDto createPerformer(@RequestBody AdminPerformerRequestDto request) {
        return adminPerformerFacade.createPerformer(request);
    }

    @PutMapping("/{id}")
    public AdminPerformerDto updatePerformer(@PathVariable Long id, @RequestBody AdminPerformerRequestDto request) {
        return adminPerformerFacade.updatePerformer(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePerformer(@PathVariable Long id) {
        adminPerformerFacade.deletePerformer(id);
    }
}
