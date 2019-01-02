package io.github.firefang.power.exception;

/**
 * 认证异常，当认证失败时抛出
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
