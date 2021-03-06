package io.github.firefang.power.exception;

/**
 * 服务器内部异常，当发生未知错误时抛出
 * 
 * @author xinufo
 *
 */
public class InternalException extends RuntimeException {
    private static final long serialVersionUID = 7457726878601223969L;

    public InternalException() {
        super();
    }

    public InternalException(String message, Throwable cause) {
        super(message, cause);
    }

    public InternalException(String message) {
        super(message);
    }

    public InternalException(Throwable cause) {
        super(cause);
    }

}
