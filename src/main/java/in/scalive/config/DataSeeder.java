package in.scalive.config;

import in.scalive.model.User;
import in.scalive.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * ──────────────────────────────────────────────
 * CONFIG LAYER — in.scalive.config
 * ──────────────────────────────────────────────
 * Seeds the database with sample data when the app starts.
 * This only runs if no users already exist — safe to keep
 * even after restarting when using H2.
 *
 * @Configuration → marks this as a source of @Bean definitions.
 * CommandLineRunner → runs the lambda after the app context is ready.
 */
@Configuration
public class DataSeeder {

    @Bean
    public CommandLineRunner seedData(UserRepository userRepository) {
        return args -> {
            // Only seed if the table is empty
            if (userRepository.count() == 0) {
                userRepository.save(new User("Alice",   "Johnson", "alice@example.com",   "9876543210"));
                userRepository.save(new User("Bob",     "Smith",   "bob@example.com",     "9123456780"));
                userRepository.save(new User("Charlie", "Brown",   "charlie@example.com", "9001234567"));

                System.out.println("──────────────────────────────────────────");
                System.out.println(" Sample data seeded: 3 users added.");
                System.out.println(" H2 Console: http://localhost:8080/h2-console");
                System.out.println(" API Base  : http://localhost:8080/users");
                System.out.println("──────────────────────────────────────────");
            }
        };
    }
}

