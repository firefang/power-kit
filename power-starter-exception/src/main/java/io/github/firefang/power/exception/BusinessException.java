package io.github.firefang.power.exception;

/**
 * 业务异常，当业务执行失败时抛出
 * 
 * @author xinufo
 *
 */
public class BusinessException extends RuntimeException {
    private static final long serialVersionUID = -6928110947306000370L;
    private String operation;

    public BusinessException(String operation, String message) {
        super(message);
        this.operation = operation;
    }

    public String getOperation() {
        return operation;
    }

}
