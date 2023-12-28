package com.spring.finaldemo.repository;

import com.spring.finaldemo.entity.UserRole;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
    Optional<UserRole> findByRoleName(String roleName);
}