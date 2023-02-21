package com.example.tsipadan.petproject.service.api;

import com.example.tsipadan.petproject.model.Response;
import com.example.tsipadan.petproject.model.Role;

import java.util.List;
import java.util.UUID;

public interface RoleService {

    /**
     * Get all roles
     *
     * @return - list of Roles
     */
    List<Role> getAllRoles();

    /**
     * Create role
     *
     * @param role - role object
     * @return - role what we create
     */
    Role createRole(Role role);

    /**
     * Delete role
     *
     * @param id - role identifier
     * @return - response of operation
     */
    Response deleteRole(UUID id);

    /**
     * If role exist - true, else false
     *
     * @param roleName - equals by role name
     * @return - true/false
     */
    boolean isRoleExist(String roleName);

}
