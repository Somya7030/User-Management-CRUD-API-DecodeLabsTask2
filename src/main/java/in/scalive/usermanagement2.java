package in.scalive;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Entry point of the User Management Spring Boot application.
 *
 * @SpringBootApplication enables:
 *   - @Configuration       (marks this as a config class)
 *   - @EnableAutoConfiguration (auto-configures Spring beans)
 *   - @ComponentScan       (scans all sub-packages automatically)
 */
@SpringBootApplication
public class usermanagement2{

    public static void main(String[] args) {
        SpringApplication.run(usermanagement2.class, args);
    }
}