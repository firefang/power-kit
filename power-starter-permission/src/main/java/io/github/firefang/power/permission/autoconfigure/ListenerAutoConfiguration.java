package io.github.firefang.power.permission.autoconfigure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.github.firefang.power.permission.PermissionProperties;
import io.github.firefang.power.permission.serialize.ContextRefreshedListener;
import io.github.firefang.power.permission.serialize.IPermissionService;

/**
 * Auto-configuration of ContextRefreshedListener
 * 
 * @author xinufo
 *
 */
@Configuration
@ConditionalOnBean(IPermissionService.class)
@ConditionalOnProperty(value = PermissionProperties.SERIALIZE_SWITCH, matchIfMissing = true)
public class ListenerAutoConfiguration {
    @Autowired
    private IPermissionService permSrv;

    @Bean
    public ContextRefreshedListener contextRefreshListener() {
        return new ContextRefreshedListener(permSrv);
    }

}
