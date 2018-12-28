package io.github.firefang.power.login.test;

import org.mockito.Mockito;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import io.github.firefang.power.login.session.ISessionAuthService;
import io.github.firefang.power.login.token.ITokenAuthService;

/**
 * @author xinufo
 *
 */
@SpringBootApplication
public class PowerLoginTestConfiguration {

    @Bean
    public ITokenAuthService tokenService() {
        return Mockito.mock(ITokenAuthService.class);
    }

    @Bean
    public ISessionAuthService sessionService() {
        return Mockito.mock(ISessionAuthService.class);
    }

}
