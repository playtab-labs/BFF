package com.playtab.bff.admin.pub.rest;

import com.playtab.bff.admin.pub.dto.request.AdminPubRequestDto;
import com.playtab.bff.admin.pub.dto.response.AdminPubDto;
import com.playtab.bff.admin.pub.service.AdminPubFacade;
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
@RequestMapping("/admin/pubs")
public class AdminPubRestController {

    private final AdminPubFacade adminPubFacade;

    public AdminPubRestController(AdminPubFacade adminPubFacade) {
        this.adminPubFacade = adminPubFacade;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AdminPubDto createPub(@RequestBody AdminPubRequestDto request) {
        return adminPubFacade.createPub(request);
    }

    @PutMapping("/{id}")
    public AdminPubDto updatePub(@PathVariable Long id, @RequestBody AdminPubRequestDto request) {
        return adminPubFacade.updatePub(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePub(@PathVariable Long id) {
        adminPubFacade.deletePub(id);
    }
}
