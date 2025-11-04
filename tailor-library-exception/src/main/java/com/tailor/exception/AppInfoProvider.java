package com.tailor.exception;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AppInfoProvider {

    @Value("${app.name:unknown-app}")
    private String appName;

    public String getAppName() {
        return appName;
    }
}
