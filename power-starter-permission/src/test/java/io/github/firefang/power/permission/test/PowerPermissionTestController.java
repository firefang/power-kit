package io.github.firefang.power.permission.test;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.github.firefang.power.permission.annotations.EntityId;
import io.github.firefang.power.permission.annotations.Permission;
import io.github.firefang.power.permission.annotations.PermissionGroup;

@PermissionGroup("test")
@RestController
public class PowerPermissionTestController {

    @RequestMapping("/check")
    @Permission("test")
    public String a() {
        return "a";
    }

    @GetMapping("/checkId")
    @Permission("testId")
    public String b1(@EntityId @RequestParam("id") Integer id) {
        return "a";
    }

    @PostMapping("/checkExp")
    @Permission("testExp")
    public String b2(@EntityId("#arg0['id']") @RequestBody Map<String, Object> params) {
        return "a";
    }

    @RequestMapping("/c")
    public String c() {
        return "c";
    }

}
