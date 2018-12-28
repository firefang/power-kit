package io.github.firefang.power.exception.autocinfigure;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication.Type;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.github.firefang.power.exception.handler.GlobalExcptionHandler;
import io.github.firefang.power.web.CommonResponse;

/**
 * Auto-configuration for registering a global exception handler
 * 
 * @author xinufo
 *
 */
@Configuration
@ConditionalOnClass(CommonResponse.class)
@ConditionalOnWebApplication(type = Type.SERVLET)
public class ExceptionHandlerAutoConfiguration {

    @Bean
    public GlobalExcptionHandler globalExcptionHandler() {
        return new GlobalExcptionHandler();
    }

}