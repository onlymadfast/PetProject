package com.example.tsipadan.petproject.controller;

import com.example.tsipadan.petproject.model.Response;
import com.example.tsipadan.petproject.model.Role;
import com.example.tsipadan.petproject.service.api.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/role/access")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @GetMapping("/list")
    public List<Role> getAllRolesFromSystem() {
        return roleService.getAllRoles();
    }

    @PostMapping("/edit")
    public Role createRoleInSystem(@RequestBody Role role) {
        return roleService.createRole(role);
    }

    @DeleteMapping("/edit/{roleId}")
    public Response deleteRoleFromSystem(@PathVariable UUID roleId) {
        return roleService.deleteRole(roleId);
    }

}
