package io.github.firefang.power.login.autoconfigure;

import java.util.function.Supplier;

import javax.annotation.PostConstruct;

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
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import io.github.firefang.power.login.IAuthService;
import io.github.firefang.power.login.ILoginController;
import io.github.firefang.power.login.LoginProperties;
import io.github.firefang.power.login.LoginProperties.AuthType;
import io.github.firefang.power.login.session.ISessionAuthService;
import io.github.firefang.power.login.session.SessionController;
import io.github.firefang.power.login.session.SessionHandlerInterceptor;
import io.github.firefang.power.login.token.ITokenAuthService;
import io.github.firefang.power.login.token.TokenController;
import io.github.firefang.power.login.token.TokenHandlerInterceptor;

/**
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

    @Autowired(required = false)
    private ITokenAuthService tokenSrv;

    @Autowired(required = false)
    private ISessionAuthService sessionSrv;

    @PostConstruct
    public void checkService() {
        IAuthService authSrv;
        Supplier<String> msg;

        if (properties.getType() == AuthType.TOKEN) {
            authSrv = tokenSrv;
            msg = () -> "Can't find an ITokenAuthService";
        } else {
            authSrv = sessionSrv;
            msg = () -> "Can't find an ISessionAuthService";
        }
        if (authSrv == null) {
            throw new IllegalStateException(msg.get());
        }
    }

    @Bean
    @ConditionalOnProperty(value = LoginProperties.CONTROLLER_SWITCH, matchIfMissing = true)
    public ILoginController loginController() {
        AuthType type = properties.getType();
        String key = getKey(type);
        if (type == AuthType.TOKEN) {
            return new TokenController(key, tokenSrv);
        } else {
            return new SessionController(key, sessionSrv);
        }
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        AuthType type = properties.getType();
        String key = getKey(type);
        HandlerInterceptor interceptor;
        if (type == AuthType.TOKEN) {
            interceptor = new TokenHandlerInterceptor(key, tokenSrv);
        } else {
            interceptor = new SessionHandlerInterceptor(key, sessionSrv);
        }
        registry.addInterceptor(interceptor);
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
