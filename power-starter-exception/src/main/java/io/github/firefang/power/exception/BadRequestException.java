package io.github.firefang.power.exception;

/**
 * Thrown when the request is invalid
 * 
 * @author xinufo
 *
 */
public class BadRequestException extends RuntimeException {
    private static final long serialVersionUID = 4870310879204137903L;

    public BadRequestException(String message, Throwable cause) {
        super(message, cause);
    }

    public BadRequestException(String message) {
        super(message);
    }

}
