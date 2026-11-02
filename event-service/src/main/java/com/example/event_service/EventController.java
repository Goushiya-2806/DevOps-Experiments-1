
package com.example.event_service;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EventController {

    @GetMapping("/event")
    public Event getEvent() {
        return new Event("Python Workshop");
    }
}
