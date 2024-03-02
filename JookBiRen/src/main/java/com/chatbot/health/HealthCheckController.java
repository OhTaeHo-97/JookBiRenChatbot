package com.chatbot.health;

import org.springframework.web.bind.annotation.GetMapping;

public class HealthCheckController {
    @GetMapping("/healthcheck")
    public String healthCheck() {
        return "OK";
    }
}
