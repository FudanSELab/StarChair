package fudan.se.lab2;

import fudan.se.lab2.domain.Authority;
import fudan.se.lab2.domain.User;
import fudan.se.lab2.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.HashSet;

/**
 * Welcome to 2020 Software Engineering Lab2.
 * This is your first lab to write your own code and build a spring boot application.
 * Enjoy it :)
 *
 * @author LBW
 */
@SpringBootApplication
public class Lab2Application {

    public static void main(String[] args) {
        SpringApplication.run(Lab2Application.class, args);
    }

    /**
     * This is a function to create some basic entities when the application starts.
     * Now we are using a In-Memory database, so you need it.
     */
    @Bean
    public CommandLineRunner dataLoader(UserRepository userRepository,
                                        AuthorityRepository authorityRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            // Create an admin if not exists.
            if (userRepository.findByUsername("admin") == null) {
                Authority adminAuthority = new Authority("ROLE_ADMIN");
                User admin = new User(
                        "admin",
                        "administrator",
                        passwordEncoder.encode("password"),
                        "1400208597@qq.com",
                        "fudan",
                        "shanghai",
                        new HashSet<>(Collections.singletonList(adminAuthority))
                );
                adminAuthority.setUser(admin);
                userRepository.save(admin);
                authorityRepository.save(adminAuthority);
            }
        };
    }
}

