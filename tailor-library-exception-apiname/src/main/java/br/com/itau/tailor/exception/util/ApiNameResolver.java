package br.com.itau.tailor.exception.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Enumeration;
import java.util.jar.Manifest;

@Component
public class ApiNameResolver {

    @Value("${app.name:}")
    private String configuredName;

    private final ApplicationContext context;

    public ApiNameResolver(ApplicationContext context) {
        this.context = context;
    }

    public String resolve() {
        // 1. Try configured property
        if (StringUtils.hasText(configuredName)) {
            return configuredName;
        }

        // 2. Try Spring ApplicationContext name
        try {
            String ctxName = context.getApplicationName();
            if (StringUtils.hasText(ctxName)) {
                return ctxName;
            }
        } catch (Exception ignored) {}

        // 3. Try MANIFEST.MF Implementation-Title
        String manifestName = detectFromManifest();
        if (StringUtils.hasText(manifestName)) {
            return manifestName;
        }

        return "unknown-api";
    }

    private String detectFromManifest() {
        try {
            Enumeration<URL> resources = Thread.currentThread()
                    .getContextClassLoader().getResources("META-INF/MANIFEST.MF");
            while (resources.hasMoreElements()) {
                URL url = resources.nextElement();
                try (InputStream is = url.openStream()) {
                    Manifest manifest = new Manifest(is);
                    String title = manifest.getMainAttributes().getValue("Implementation-Title");
                    if (title != null && !title.isBlank()) {
                        return title;
                    }
                    String impl = manifest.getMainAttributes().getValue("Implementation-Vendor-Id");
                    if (impl != null && !impl.isBlank()) {
                        return impl;
                    }
                } catch (IOException ignored) {}
            }
        } catch (IOException ignored) {}
        return null;
    }
}