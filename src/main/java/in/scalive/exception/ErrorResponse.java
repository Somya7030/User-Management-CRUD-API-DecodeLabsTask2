package in.scalive.exception;

import java.time.LocalDateTime;

/**
 * A plain Java class (no framework annotations) used as the
 * JSON response body whenever an error occurs.
 *
 * Clients will always receive a consistent error shape:
 * {
 *   "timestamp": "2024-04-18T10:30:00",
 *   "status":    404,
 *   "error":     "Not Found",
 *   "message":   "User not found with id: 5",
 *   "path":      "/users/5"
 * }
 */
public class ErrorResponse {

    private LocalDateTime timestamp;
    private int status;
    private String error;
    private String message;
    private String path;

    // ── Constructor ────────────────────────────────────────

    public ErrorResponse(int status, String error, String message, String path) {
        this.timestamp = LocalDateTime.now();
        this.status    = status;
        this.error     = error;
        this.message   = message;
        this.path      = path;
    }

    // ── Getters ────────────────────────────────────────────
    // (No setters needed — this is a read-only response object)

    public LocalDateTime getTimestamp() { return timestamp; }
    public int           getStatus()    { return status;    }
    public String        getError()     { return error;     }
    public String        getMessage()   { return message;   }
    public String        getPath()      { return path;      }
}
