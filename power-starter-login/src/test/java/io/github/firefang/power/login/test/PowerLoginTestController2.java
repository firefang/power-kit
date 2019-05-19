package io.github.firefang.power.login.test;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xinufo
 *
 */
@RestController
@RequestMapping("/excludes")
public class PowerLoginTestController2 {

    @GetMapping("/test")
    public String test() {
        return "ok";
    }

}
