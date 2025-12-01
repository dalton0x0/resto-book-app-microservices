package com.restobook.authservice.configs;

import com.restobook.authservice.entities.Role;
import com.restobook.authservice.entities.User;
import com.restobook.authservice.enums.RoleName;
import com.restobook.authservice.repositories.RoleRepository;
import com.restobook.authservice.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void run(String... args) {
        log.info("Initialisation des données de base...");

        initializeRoles();
        initializeAdminUser();

        log.info("Initialisation des données terminée.");
    }

    private String getDescription(RoleName roleName) {
        return switch (roleName) {
            case CLIENT -> "Client de QuickEat";
            case STAFF -> "Employé de restaurant QuickEat";
            case OWNER -> "Propriétaire/Gérant de restaurant";
            case ADMIN -> "Administrateur système RestoBook";
        };
    }

    private void initializeRoles() {
        for (RoleName roleName : RoleName.values()) {
            if (!roleRepository.existsByName(roleName)) {
                Role role = Role.builder()
                        .name(roleName)
                        .description(getDescription(roleName))
                        .build();
                roleRepository.save(role);
                log.info("Rôle créé: {}", roleName);
            } else {
                log.debug("Rôle déjà existant: {}", roleName);
            }
        }
    }

    private void initializeAdminUser() {
        String adminEmail = "admin@example.fr";
        String adminPassword = "StrongP@ssw0rd";

        if (!userRepository.existsByEmail(adminEmail)) {
            Role adminRole = roleRepository.findByName(RoleName.ADMIN)
                    .orElseThrow(() -> new RuntimeException("Rôle ADMIN non trouvé"));

            User adminUser = User.builder()
                    .firstName("Admin")
                    .lastName("QuickEat")
                    .email(adminEmail)
                    .password(passwordEncoder.encode(adminPassword))
                    .phone("+33123456789")
                    .role(adminRole)
                    .enabled(true)
                    .emailVerified(true)
                    .accountNonLocked(true)
                    .build();

            userRepository.save(adminUser);
            log.info("Utilisateur admin créé: {} / Mot de passe {}: ", adminEmail, adminPassword);
        } else {
            log.debug("Utilisateur admin déjà existant: {}", adminEmail);
        }
    }
}
