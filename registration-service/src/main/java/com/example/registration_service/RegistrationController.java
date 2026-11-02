
package com.example.registration_service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/registration")
public class RegistrationController {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping
    public String testRegistrationService() {
        return "Registration Service is running";
    }

    @PostMapping
    public Registration register(
            @RequestBody Registration registration) {

        Event event = restTemplate.getForObject(
                "http://localhost:8082/event",
                Event.class
        );

        if (event != null) {
            registration.setEvent(
                    event.getEventName()
            );
        }

        return registration;
    }
}
