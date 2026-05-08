package com.playtab.bff.admin.foodtruck.rest;

import com.playtab.bff.admin.foodtruck.dto.request.AdminFoodTruckRequestDto;
import com.playtab.bff.admin.foodtruck.dto.response.AdminFoodTruckDto;
import com.playtab.bff.admin.foodtruck.service.AdminFoodTruckFacade;
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
@RequestMapping("/admin/food-trucks")
public class AdminFoodTruckRestController {

    private final AdminFoodTruckFacade adminFoodTruckFacade;

    public AdminFoodTruckRestController(AdminFoodTruckFacade adminFoodTruckFacade) {
        this.adminFoodTruckFacade = adminFoodTruckFacade;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AdminFoodTruckDto createFoodTruck(@RequestBody AdminFoodTruckRequestDto request) {
        return adminFoodTruckFacade.createFoodTruck(request);
    }

    @PutMapping("/{id}")
    public AdminFoodTruckDto updateFoodTruck(@PathVariable Long id, @RequestBody AdminFoodTruckRequestDto request) {
        return adminFoodTruckFacade.updateFoodTruck(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteFoodTruck(@PathVariable Long id) {
        adminFoodTruckFacade.deleteFoodTruck(id);
    }
}
