package io.github.firefang.power.permission.test;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.firefang.power.permission.annotations.Permission;
import io.github.firefang.power.permission.annotations.PermissionGroup;

@PermissionGroup(value = "classParam", extraParams = "{'class': true}")
@RestController
public class PowerPermissionTestController3 {

    @GetMapping("/classParam")
    @Permission(value = "classParam", verticalCheck = true, horizontalCheck = true, extraParams = "{'method': true}")
    public String p() {
        return "a";
    }

}
