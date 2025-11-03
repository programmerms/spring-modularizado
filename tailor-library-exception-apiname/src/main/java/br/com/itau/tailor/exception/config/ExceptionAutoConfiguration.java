package br.com.itau.tailor.exception.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import br.com.itau.tailor.exception.handler.GlobalExceptionHandler;
import br.com.itau.tailor.exception.util.ApiNameResolver;

@Configuration
@Import({GlobalExceptionHandler.class, ApiNameResolver.class})
public class ExceptionAutoConfiguration {
    // Auto-import the GlobalExceptionHandler and ApiNameResolver when the library is on the classpath.
}