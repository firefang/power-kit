package io.github.firefang.power.login.session;

import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.github.firefang.power.login.ILoginController;
import io.github.firefang.power.login.PublicEndPoint;
import io.github.firefang.power.web.CommonResponse;

/**
 * Session认证Controller
 * 
 * @author xinufo
 *
 */
@RestController
@RequestMapping("/auth")
public class SessionController implements ILoginController {
    private String key;
    private ISessionAuthService sessionSrv;

    public SessionController(String key, ISessionAuthService sessionSrv) {
        this.key = key;
        this.sessionSrv = sessionSrv;
    }

    @PublicEndPoint
    @PostMapping("/login")
    public CommonResponse<Void> login(@RequestParam("username") String username,
            @RequestParam("password") String password, HttpSession session) {
        sessionSrv.login(username, password, session);
        return CommonResponse.success();
    }

    @PostMapping("/logout")
    public CommonResponse<Void> logout(HttpSession session) {
        Object userInfo = session.getAttribute(key);
        sessionSrv.logout(userInfo, session);
        session.invalidate();
        return CommonResponse.success();
    }

}
