package com.playtab.bff.admin.stage.rest;

import com.playtab.bff.admin.stage.dto.request.AdminStageRequestDto;
import com.playtab.bff.admin.stage.dto.response.AdminStageDto;
import com.playtab.bff.admin.stage.service.AdminStageFacade;
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
@RequestMapping("/admin/stages")
public class AdminStageRestController {

    private final AdminStageFacade adminStageFacade;

    public AdminStageRestController(AdminStageFacade adminStageFacade) {
        this.adminStageFacade = adminStageFacade;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AdminStageDto createStage(@RequestBody AdminStageRequestDto request) {
        return adminStageFacade.createStage(request);
    }

    @PutMapping("/{id}")
    public AdminStageDto updateStage(@PathVariable Long id, @RequestBody AdminStageRequestDto request) {
        return adminStageFacade.updateStage(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteStage(@PathVariable Long id) {
        adminStageFacade.deleteStage(id);
    }
}
