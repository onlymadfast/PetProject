package com.example.tsipadan.petproject.repository;

import com.example.tsipadan.petproject.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRoleRepository extends JpaRepository<Role, UUID> {

}
