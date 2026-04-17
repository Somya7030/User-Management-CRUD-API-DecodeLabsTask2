package in.scalive.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Thrown when a user attempts to register or update with an email
 * address that already belongs to another user in the system.
 *
 * Maps to HTTP 409 Conflict — semantically correct because
 * the request conflicts with the current state of the resource.
 */
@ResponseStatus(HttpStatus.CONFLICT)
public class DuplicateEmailException extends RuntimeException {

    public DuplicateEmailException(String message) {
        super(message);
    }
}
