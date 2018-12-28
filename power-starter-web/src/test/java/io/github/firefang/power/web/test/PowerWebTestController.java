package io.github.firefang.power.web.test;

import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.github.firefang.power.web.util.CurrentRequestUtil;

/**
 * @author xinufo
 *
 */
@RestController
public class PowerWebTestController {

    @GetMapping("/header")
    public String header() {
        return CurrentRequestUtil.getHeader("test");
    }

    @GetMapping("/session")
    public Object session(HttpSession session, @RequestParam("test") Integer id) {
        session.setAttribute("test", id);
        return CurrentRequestUtil.getSessionAttribute("test");
    }

}
