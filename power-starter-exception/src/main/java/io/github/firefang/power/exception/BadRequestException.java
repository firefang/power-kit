package io.github.firefang.power.exception;

/**
 * 非法请求异常，当请求不合法（例如参数错误）时抛出
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
