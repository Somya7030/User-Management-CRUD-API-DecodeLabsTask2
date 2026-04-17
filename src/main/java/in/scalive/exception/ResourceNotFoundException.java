package in.scalive.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * ──────────────────────────────────────────────
 * EXCEPTION LAYER — in.scalive.exception
 * ──────────────────────────────────────────────
 * Thrown when a requested resource (e.g., User by ID) does not exist.
 *
 * @ResponseStatus(HttpStatus.NOT_FOUND) →
 *   Tells Spring to return HTTP 404 automatically
 *   when this exception bubbles up from the controller.
 *   (GlobalExceptionHandler also handles it explicitly
 *    for a richer JSON error response.)
 *
 * RuntimeException is used (unchecked) so callers don't
 * need to declare it in their method signatures.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }
}

