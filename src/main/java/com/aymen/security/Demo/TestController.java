package com.aymen.security.Demo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestController.class);

    @GetMapping("/test")
    public String testEndpoint() {
        // Print authentication details
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            LOGGER.info("No authentication details in the security context");
        } else {
            LOGGER.info("Authenticated user: {}", authentication.getName());
        }

        // Your business logic for the endpoint
        return "Hello, this is a test endpoint!";
    }
}