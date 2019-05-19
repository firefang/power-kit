package io.github.firefang.power.login.test;

import org.mockito.Mockito;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import io.github.firefang.power.login.IAuthService;

/**
 * @author xinufo
 *
 */
@SpringBootApplication
public class PowerLoginTestConfiguration {

    @Bean
    public IAuthService authService() {
        return Mockito.mock(IAuthService.class);
    }

}
