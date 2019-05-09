package io.github.firefang.power.permission.test;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.github.firefang.power.permission.annotations.Permission;
import io.github.firefang.power.permission.annotations.PermissionGroup;
import io.github.firefang.power.permission.annotations.PermissionParam;

@PermissionGroup("test")
@RestController
public class PowerPermissionTestController {

    @GetMapping("/check")
    @Permission(value = "test", extraParams = "{'id': 1}")
    public String a(@RequestParam(value = "a", required = false) String a) {
        return "a";
    }

    @GetMapping("/checkId")
    @Permission("testId")
    public String b1(@PermissionParam(name = "id") @RequestParam("id") Integer id) {
        return "a";
    }

    @PostMapping("/checkExp")
    @Permission("testExp")
    public String b2(@PermissionParam(name = "id", exp = "#arg['id']") @RequestBody Map<String, Object> params) {
        return "a";
    }

    @GetMapping("/verticalCheck")
    @Permission(value = "testVertical", verticalCheck = true, horizontalCheck = false)
    public String b3() {
        return "a";
    }

    @GetMapping("/horizontalCheck")
    @Permission(value = "testHorizontal", verticalCheck = false, horizontalCheck = true)
    public String b4() {
        return "a";
    }

    @GetMapping("/noCheck")
    @Permission(value = "testNoChcek", verticalCheck = false, horizontalCheck = false)
    public String b5() {
        return "a";
    }

    @GetMapping("/c")
    public String c() {
        return "c";
    }

}
