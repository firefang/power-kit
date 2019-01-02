package io.github.firefang.power.exception.handler.test;

import javax.validation.constraints.NotNull;

/**
 * @author xinufo
 *
 */
public class PowerExceptionTestEntity {
    private Integer id;
    @NotNull(message = "name can't be null")
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
