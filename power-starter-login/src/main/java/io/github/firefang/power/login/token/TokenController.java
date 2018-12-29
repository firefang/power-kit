package io.github.firefang.power.login.token;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.github.firefang.power.login.ILoginController;
import io.github.firefang.power.web.CommonResponse;

/**
 * Token认证Controller
 * 
 * @author xinufo
 *
 */
@RestController
@RequestMapping("/auth")
public class TokenController implements ILoginController {
    private String key;
    private ITokenAuthService tokenSrv;

    public TokenController(String key, ITokenAuthService tokenSrv) {
        this.key = key;
        this.tokenSrv = tokenSrv;
    }

    @PostMapping("/login")
    public CommonResponse<String> login(@RequestParam("username") String username,
            @RequestParam("password") String password) {
        String token = tokenSrv.login(username, password);
        return CommonResponse.<String>success().data(token);
    }

    @PostMapping("/logout")
    public CommonResponse<Void> logout(HttpServletRequest request) {
        String token = request.getHeader(key);
        tokenSrv.logout(token);
        return CommonResponse.success();
    }

}
