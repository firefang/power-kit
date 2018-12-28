package io.github.firefang.power.exception;

/**
 * Thrown when running business logic fails
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
