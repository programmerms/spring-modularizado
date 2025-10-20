package com.tailor.starters;

import com.tailor.exception.TailorExceptionHandlerAutoConfiguration;
import com.tailor.logger.TailorLoggerAutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Import;

@AutoConfiguration
@Import({TailorExceptionHandlerAutoConfiguration.class, TailorLoggerAutoConfiguration.class})
public class TailorStartersAutoConfiguration {
}