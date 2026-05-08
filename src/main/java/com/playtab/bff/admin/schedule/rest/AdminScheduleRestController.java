package com.playtab.bff.admin.schedule.rest;

import com.playtab.bff.admin.schedule.dto.request.AdminScheduleRequestDto;
import com.playtab.bff.admin.schedule.dto.response.AdminScheduleDto;
import com.playtab.bff.admin.schedule.service.AdminScheduleFacade;
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
@RequestMapping("/admin/schedules")
public class AdminScheduleRestController {

    private final AdminScheduleFacade adminScheduleFacade;

    public AdminScheduleRestController(AdminScheduleFacade adminScheduleFacade) {
        this.adminScheduleFacade = adminScheduleFacade;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AdminScheduleDto createSchedule(@RequestBody AdminScheduleRequestDto request) {
        return adminScheduleFacade.createSchedule(request);
    }

    @PutMapping("/{id}")
    public AdminScheduleDto updateSchedule(@PathVariable Long id, @RequestBody AdminScheduleRequestDto request) {
        return adminScheduleFacade.updateSchedule(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSchedule(@PathVariable Long id) {
        adminScheduleFacade.deleteSchedule(id);
    }
}
