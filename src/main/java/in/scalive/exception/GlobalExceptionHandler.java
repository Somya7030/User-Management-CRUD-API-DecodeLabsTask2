package in.scalive.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * ──────────────────────────────────────────────
 * EXCEPTION LAYER — GlobalExceptionHandler
 * ──────────────────────────────────────────────
 * Centralises error handling for ALL controllers.
 *
 * @RestControllerAdvice →
 *   Combines @ControllerAdvice + @ResponseBody.
 *   Any exception thrown anywhere in the app reaches
 *   one of the @ExceptionHandler methods here, which
 *   then builds and returns a clean JSON error response.
 *
 * Without this, Spring would return its default white-label
 * error page or a messy stack trace.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    // ── 1. Resource Not Found (HTTP 404) ──────────────────
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFound(
            ResourceNotFoundException ex,
            HttpServletRequest request) {

        ErrorResponse error = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                "Not Found",
                ex.getMessage(),
                request.getRequestURI()
        );
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    // ── 2. Duplicate Email (HTTP 409) ─────────────────────
    @ExceptionHandler(DuplicateEmailException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateEmail(
            DuplicateEmailException ex,
            HttpServletRequest request) {

        ErrorResponse error = new ErrorResponse(
                HttpStatus.CONFLICT.value(),
                "Conflict",
                ex.getMessage(),
                request.getRequestURI()
        );
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    // ── 3. Validation Errors (HTTP 400) ───────────────────
    // Triggered when @Valid fails on a @RequestBody parameter
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationErrors(
            MethodArgumentNotValidException ex,
            HttpServletRequest request) {

        // Collect all field-level validation messages into a map
        Map<String, String> fieldErrors = new HashMap<>();
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            fieldErrors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }

        // Build a richer response that includes the per-field breakdown
        Map<String, Object> response = new HashMap<>();
        response.put("status",  HttpStatus.BAD_REQUEST.value());
        response.put("error",   "Validation Failed");
        response.put("message", "One or more fields are invalid. See 'errors' for details.");
        response.put("errors",  fieldErrors);
        response.put("path",    request.getRequestURI());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    // ── 4. Generic Fallback (HTTP 500) ────────────────────
    // Catches any other unexpected exception
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(
            Exception ex,
            HttpServletRequest request) {

        ErrorResponse error = new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Internal Server Error",
                "An unexpected error occurred. Please try again later.",
                request.getRequestURI()
        );
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
