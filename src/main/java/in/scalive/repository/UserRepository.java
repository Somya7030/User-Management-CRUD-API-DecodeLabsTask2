package in.scalive.repository;

import in.scalive.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * ──────────────────────────────────────────────
 * REPOSITORY LAYER — in.scalive.repository
 * ──────────────────────────────────────────────
 * Extends JpaRepository which provides all
 * standard CRUD methods out of the box:
 *
 *   save(entity)         → INSERT / UPDATE
 *   findById(id)         → SELECT by PK
 *   findAll()            → SELECT all rows
 *   deleteById(id)       → DELETE by PK
 *   existsById(id)       → existence check
 *
 * We add one custom query method below.
 * Spring Data JPA auto-generates the SQL from
 * the method name — no @Query annotation needed!
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Checks whether a user already exists with the given email.
     * Spring Data JPA translates this to:
     *   SELECT COUNT(*) > 0 FROM users WHERE email = ?
     *
     * Used by the service layer to prevent duplicate email registrations.
     */
    boolean existsByEmail(String email);

    /**
     * Finds a user by their email address.
     * Returns an Optional so the caller can handle "not found" gracefully.
     */
    Optional<User> findByEmail(String email);
}

