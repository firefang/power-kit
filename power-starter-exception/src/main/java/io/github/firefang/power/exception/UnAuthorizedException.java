package io.github.firefang.power.exception;

/**
 * Thrown when authentication fails
 * 
 * @author xinufo
 *
 */
public class UnAuthorizedException extends RuntimeException {
    private static final long serialVersionUID = 4929289729936666332L;

    public UnAuthorizedException() {
    }

    public UnAuthorizedException(String message) {
        super(message);
    }

}
