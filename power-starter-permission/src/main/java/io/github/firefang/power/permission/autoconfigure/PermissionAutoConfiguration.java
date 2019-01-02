package io.github.firefang.power.permission.autoconfigure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import io.github.firefang.power.permission.IPermissionChecker;
import io.github.firefang.power.permission.PermissionProperties;
import io.github.firefang.power.permission.aspect.PermissionAspect;

/**
 * Auto-configuration of registering aspect
 * 
 * @author xinufo
 *
 */
@Configuration
@EnableAspectJAutoProxy
@ConditionalOnBean(IPermissionChecker.class)
@ConditionalOnProperty(value = PermissionProperties.SWITCH, matchIfMissing = true)
public class PermissionAutoConfiguration {

    @Autowired
    private IPermissionChecker checker;

    @Bean
    public PermissionAspect permissionAspect() {
        return new PermissionAspect(checker);
    }

}
