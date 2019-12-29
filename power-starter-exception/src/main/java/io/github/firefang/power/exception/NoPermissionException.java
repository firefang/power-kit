package io.github.firefang.power.exception;

/**
 * 权限不足异常，当用户无权访问某个资源时抛出
 * 
 * @author xinufo
 *
 */
public class NoPermissionException extends RuntimeException {
    private static final long serialVersionUID = -6659349365466202662L;

    public NoPermissionException() {
        super();
    }

    public NoPermissionException(String message) {
        super(message);
    }

}
