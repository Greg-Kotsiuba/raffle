package com.gk.springbootskeleton.exceptionhandling.config;

import brave.Tracer;
import com.gk.springbootskeleton.exceptionhandling.handler.ApiExceptionHandler;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Global exception handler configuration.
 */
@Configuration
@ConditionalOnProperty(value = "ts.exception-handling.enabled", matchIfMissing = true)
public class ExceptionHandlerConfiguration {

    /**
     * Returns global exception handler instance.
     *
     * @param tracer {@link Tracer} instance from context
     * @return global exception handler instance.
     */
    @Bean
    public ApiExceptionHandler globalExceptionHandler(Tracer tracer) {
        return new ApiExceptionHandler(tracer);
    }
}
