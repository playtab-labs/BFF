package com.playtab.bff.admin.user.rest;

import com.playtab.bff.admin.user.dto.response.AdminUserDetailDto;
import com.playtab.bff.admin.user.dto.response.AdminUserListDto;
import com.playtab.bff.admin.user.service.AdminUserFacade;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/users")
public class AdminUserRestController {

    private final AdminUserFacade adminUserFacade;

    public AdminUserRestController(AdminUserFacade adminUserFacade) {
        this.adminUserFacade = adminUserFacade;
    }

    @GetMapping
    public AdminUserListDto listUsers(
            @RequestParam(required = false) String emailFilter,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        return adminUserFacade.listUsers(emailFilter, page, size);
    }

    @GetMapping("/{identityId}")
    public AdminUserDetailDto getUser(@PathVariable String identityId) {
        return adminUserFacade.getUser(identityId);
    }
}
