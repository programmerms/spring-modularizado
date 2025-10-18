package com.tailor.demo;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class TestController {

    @GetMapping("/ping")
    public Map<String, String> ping() {
        return Map.of("status", "pong");
    }

    @PostMapping("/echo")
    public ResponseEntity<Map<String, String>> echo(@Valid @RequestBody EchoRequest request) {
        return ResponseEntity.ok(Map.of("message", request.message()));
    }

    public record EchoRequest(@NotBlank String message) {}
}