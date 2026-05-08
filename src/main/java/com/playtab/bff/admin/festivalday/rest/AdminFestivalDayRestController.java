package com.playtab.bff.admin.festivalday.rest;

import com.playtab.bff.admin.festivalday.dto.request.AdminFestivalDayRequestDto;
import com.playtab.bff.admin.festivalday.dto.response.AdminFestivalDayDto;
import com.playtab.bff.admin.festivalday.service.AdminFestivalDayFacade;
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
@RequestMapping("/admin/festival-days")
public class AdminFestivalDayRestController {

    private final AdminFestivalDayFacade adminFestivalDayFacade;

    public AdminFestivalDayRestController(AdminFestivalDayFacade adminFestivalDayFacade) {
        this.adminFestivalDayFacade = adminFestivalDayFacade;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AdminFestivalDayDto createFestivalDay(@RequestBody AdminFestivalDayRequestDto request) {
        return adminFestivalDayFacade.createFestivalDay(request);
    }

    @PutMapping("/{id}")
    public AdminFestivalDayDto updateFestivalDay(@PathVariable Long id, @RequestBody AdminFestivalDayRequestDto request) {
        return adminFestivalDayFacade.updateFestivalDay(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteFestivalDay(@PathVariable Long id) {
        adminFestivalDayFacade.deleteFestivalDay(id);
    }
}
