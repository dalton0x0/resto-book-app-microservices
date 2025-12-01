package com.restobook.authservice.repositories;

import com.restobook.authservice.entities.Role;
import com.restobook.authservice.enums.RoleName;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<@NonNull Role,@NonNull  Long> {

    Optional<Role> findByName(RoleName name);

    boolean existsByName(RoleName name);
}
