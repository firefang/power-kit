package io.github.firefang.power.exception;

/**
 * 业务异常，当业务执行失败时抛出
 * 
 * @author xinufo
 *
 */
public class BusinessException extends RuntimeException {
    private static final long serialVersionUID = -6928110947306000370L;

    public BusinessException(String message) {
        super(message);
    }

}
