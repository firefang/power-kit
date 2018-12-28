package io.github.firefang.power.login.test;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xinufo
 *
 */
@RestController
public class PowerLoginTestController {

    @GetMapping("/test")
    public String test() {
        return "ok";
    }

}
