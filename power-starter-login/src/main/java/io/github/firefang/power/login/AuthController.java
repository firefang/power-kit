package io.github.firefang.power.login;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.github.firefang.power.web.CommonResponse;

/**
 * @author xinufo
 *
 */
@RestController
@RequestMapping("/auth")
public class AuthController {
    private IAuthService authSrv;

    public AuthController(IAuthService authSrv) {
        this.authSrv = authSrv;
    }

    @PublicEndPoint
    @PostMapping("/login")
    public CommonResponse<Object> login(@RequestParam("username") String username,
            @RequestParam("password") String password, HttpServletRequest request) {
        Object result = authSrv.login(username, password, request);
        return CommonResponse.success().data(result);
    }

    @PostMapping("/logout")
    public CommonResponse<Void> logout(HttpServletRequest request) {
        authSrv.logout(request);
        return CommonResponse.success();
    }

}
