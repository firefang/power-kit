package io.github.firefang.power.permission.test;

import org.springframework.stereotype.Component;

import io.github.firefang.power.permission.annotations.Permission;
import io.github.firefang.power.permission.annotations.PermissionGroup;

@Component
@PermissionGroup("test")
public class PowerPermissionTestBean {

    @Permission("test")
    public void b() {
    }

}
