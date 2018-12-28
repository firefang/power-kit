package io.github.firefang.power.permission.test;

import static org.mockito.Mockito.mock;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import io.github.firefang.power.permission.IPermissionChecker;
import io.github.firefang.power.permission.serialize.IPermissionService;

@SpringBootApplication
public class PowerPermissionConfiguration {

    @Bean
    public IPermissionChecker checker() {
        return mock(IPermissionChecker.class);
    }

    @Bean
    public IPermissionService srv() {
        return mock(IPermissionService.class);
    }

}
