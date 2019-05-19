package io.github.firefang.power.login.autoconfigure;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import io.github.firefang.power.login.AuthController;
import io.github.firefang.power.login.IAuthService;
import io.github.firefang.power.login.LoginProperties;
import io.github.firefang.power.login.LoginProperties.AuthType;
import io.github.firefang.power.login.session.SessionHandlerInterceptor;
import io.github.firefang.power.login.token.TokenHandlerInterceptor;

/**
 * 自动配置类，自动配置拦截器和登录Controller
 * 
 * @author xinufo
 *
 */
@Configuration
@EnableConfigurationProperties(LoginProperties.class)
@ConditionalOnWebApplication
@ConditionalOnBean(IAuthService.class)
@ConditionalOnProperty(value = LoginProperties.SWITCH, matchIfMissing = true)
@AutoConfigureBefore(value = WebMvcAutoConfiguration.class)
public class LoginAutoConfiguration implements WebMvcConfigurer {
    @Autowired
    private LoginProperties properties;

    @Autowired
    private IAuthService authSrv;

    @Bean
    @ConditionalOnProperty(value = LoginProperties.CONTROLLER_SWITCH, matchIfMissing = true)
    public AuthController authController() {
        return new AuthController(authSrv);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        AuthType type = properties.getType();
        String key = getKey(type);
        HandlerInterceptor interceptor;
        if (type == AuthType.TOKEN) {
            interceptor = new TokenHandlerInterceptor(key, authSrv);
        } else {
            interceptor = new SessionHandlerInterceptor(key, authSrv);
        }
        InterceptorRegistration reg = registry.addInterceptor(interceptor).addPathPatterns("/**");
        Set<String> excludePaths = properties.getExcludePaths();
        if (excludePaths != null && !excludePaths.isEmpty()) {
            String[] eps = new String[excludePaths.size()];
            reg.excludePathPatterns(excludePaths.toArray(eps));
        }
    }

    private String getKey(AuthType type) {
        String key = properties.getKey();
        if (key == null || key.length() == 0) {
            if (properties.getType() == AuthType.TOKEN) {
                key = LoginProperties.DEFAULT_TOKEN_KEY;
            } else {
                key = LoginProperties.DEFAULT_SESSION_KEY;
            }
        }
        return key;
    }

}
