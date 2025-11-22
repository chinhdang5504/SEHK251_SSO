package com.example.sso_demo.config;

import com.example.sso_demo.entity.Role;
import com.example.sso_demo.entity.User;
import com.example.sso_demo.repository.RoleRepository;
import com.example.sso_demo.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ApplicationInitConfig {
    PasswordEncoder passwordEncoder;
    @NonFinal
    static final String ADMIN_USER_NAME = "admin";
    @NonFinal
    static final String ADMIN_PASSWORD = "admin";

    @Bean
    @ConditionalOnProperty(name = "app.seed-admin", havingValue = "true", matchIfMissing = true)
    ApplicationRunner applicationRunner(UserRepository userRepository, RoleRepository roleRepository) {
        log.info("Initializing application.....");
        return args -> {
            if (userRepository.findByUsername(ADMIN_USER_NAME).isEmpty()) {
                // Create roles
                Role userRole = roleRepository
                        .save(Role.builder().name(PredefinedRole.USER_ROLE).description("User role").build());
                Role adminRole = roleRepository
                        .save(Role.builder().name(PredefinedRole.ADMIN_ROLE).description("Admin role").build());
                Role studentRole = roleRepository
                        .save(Role.builder().name(PredefinedRole.STUDENT_ROLE).description("Student role").build());
                Role tutorRole = roleRepository
                        .save(Role.builder().name(PredefinedRole.TUTOR_ROLE).description("Tutor role").build());
                Role headerRole = roleRepository
                        .save(Role.builder().name(PredefinedRole.HEADER_ROLE).description("Header role").build());
                Role managerRole = roleRepository
                        .save(Role.builder().name(PredefinedRole.MANAGER_ROLE).description("Manager role").build());
                Role coordinatorRole = roleRepository.save(
                        Role.builder().name(PredefinedRole.COORDINATOR_ROLE).description("Coordinator role").build());

                // Create Admin
                var adminRoles = new HashSet<Role>();
                adminRoles.add(adminRole);
                User admin = User.builder()
                        .username(ADMIN_USER_NAME)
                        .password(passwordEncoder.encode(ADMIN_PASSWORD))
                        .ms("CB-IT-001")
                        .roles(adminRoles)
                        .build();
                userRepository.save(admin);
                log.warn("admin user has been created with default password: admin, please change it");

                // Create 5 Students
                for (int i = 1; i <= 5; i++) {
                    var studentRoles = new HashSet<Role>();
                    studentRoles.add(studentRole);
                    User student = User.builder()
                            .username("student" + i)
                            .password(passwordEncoder.encode("password"))
                            .ms("STU-00" + i)
                            .roles(studentRoles)
                            .build();
                    userRepository.save(student);
                }

                // Create 5 Tutors
                for (int i = 1; i <= 5; i++) {
                    var tutorRoles = new HashSet<Role>();
                    tutorRoles.add(tutorRole);
                    User tutor = User.builder()
                            .username("tutor" + i)
                            .password(passwordEncoder.encode("password"))
                            .ms("TUT-00" + i)
                            .roles(tutorRoles)
                            .build();
                    userRepository.save(tutor);
                }

                // Create 1 Header
                var headerRoles = new HashSet<Role>();
                headerRoles.add(headerRole);
                User header = User.builder()
                        .username("header")
                        .password(passwordEncoder.encode("password"))
                        .ms("HEA-001")
                        .roles(headerRoles)
                        .build();
                userRepository.save(header);

                // Create 1 Manager
                var managerRoles = new HashSet<Role>();
                managerRoles.add(managerRole);
                User manager = User.builder()
                        .username("manager")
                        .password(passwordEncoder.encode("password"))
                        .ms("MAN-001")
                        .roles(managerRoles)
                        .build();
                userRepository.save(manager);

                // Create 1 Coordinator
                var coordinatorRoles = new HashSet<Role>();
                coordinatorRoles.add(coordinatorRole);
                User coordinator = User.builder()
                        .username("coordinator")
                        .password(passwordEncoder.encode("password"))
                        .ms("COO-001")
                        .roles(coordinatorRoles)
                        .build();
                userRepository.save(coordinator);
            }
            log.info("Application initialization completed .....");
        };
    }
}
