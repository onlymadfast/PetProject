package com.example.tsipadan.petproject.service.implementation;

import com.example.tsipadan.petproject.exception.EntityDuplicateException;
import com.example.tsipadan.petproject.model.Response;
import com.example.tsipadan.petproject.model.Role;
import com.example.tsipadan.petproject.repository.UserRoleRepository;
import com.example.tsipadan.petproject.service.api.RoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final UserRoleRepository roleRepository;

    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    @Override
    public Role createRole(Role role) {
        if (isRoleExist(role.getRoleName())) {
            throw new EntityDuplicateException("Role " + role.getRoleName() + " already exist");
        }
        return roleRepository.save(role);
    }

    @Override
    public Response deleteRole(UUID roleId) {
        roleRepository.deleteById(roleId);
        return Response.builder()
                .localDateTime(LocalDateTime.now())
                .status(true)
                .message("Role < " + roleId + " > was successfully deleted")
                .build();
    }

    @Override
    public boolean isRoleExist(String roleName) {
        List<Role> roleList = roleRepository.findAll();
        if (roleList.isEmpty()) {
            return false;
        }
        return roleList.stream().anyMatch(role -> role.getRoleName().equals(roleName));
    }

}
