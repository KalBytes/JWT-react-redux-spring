package com.jwt.main.user.repositories;

import com.jwt.main.user.enums.ERole;
import com.jwt.main.user.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}
