Copy everything below and paste it directly into Microsoft Word. I've kept the headings, Java programs, commands and Postman examples in a simple lab-exam format.

# EXPERIMENT 1: USER REGISTRATION FORM USING MICROSERVICES

## AIM

To write a program for a simple user registration form for an event using Spring Boot Microservices.

## SOFTWARE REQUIREMENTS

1. Java JDK 17
2. Spring Boot
3. Maven
4. Eclipse / VS Code
5. Postman

## 1. CREATE EVENT SERVICE

Create a Spring Boot project using Spring Initializr.

- Project: Maven
- Language: Java
- Group: com.example
- Artifact: event-service
- Dependency: Spring Web
- Java: 17

### Event.java

```
package com.example.event_service;

public class Event {
    private String eventName;

    public Event() {
    }

    public Event(String eventName) {
        this.eventName = eventName;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }
}
```

### EventController.java

```
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
```

### EventServiceApplication.java

```
package com.example.event_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EventServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(EventServiceApplication.class, args);
    }
}
```

### application.properties

```
spring.application.name=event-service
server.port=8082
```

## 2. CREATE REGISTRATION SERVICE

Create another Spring Boot project using Spring Initializr.

- Project: Maven
- Language: Java
- Group: com.example
- Artifact: registration-service
- Dependency: Spring Web
- Java: 17

### Event.java

```
package com.example.registration_service;

public class Event {

    private String eventName;

    public Event() {
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }
}
```

### Registration.java

```
package com.example.registration_service;

public class Registration {

    private int id;
    private String name;
    private String email;
    private String event;

    public Registration() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }
}
```

### RegistrationController.java

```
package com.example.registration_service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
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
            registration.setEvent(event.getEventName());
        }

        return registration;
    }
}
```

### RegistrationServiceApplication.java

```
package com.example.registration_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class RegistrationServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(
                RegistrationServiceApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
```

### application.properties

```
spring.application.name=registration-service
server.port=8083
```

## 3. RUN THE APPLICATION

First, run the Event Service.

Linux Command:

```
./mvnw spring-boot:run
```

Windows Command:

```
.\mvnw.cmd spring-boot:run
```

Next, open another terminal and run the Registration Service.

Linux Command:

```
./mvnw spring-boot:run
```

Windows Command:

```
.\mvnw.cmd spring-boot:run
```

Keep both services running.

## 4. TEST USING POSTMAN

### Request 1: Get Event Details

Method: GET

URL:

```
http://localhost:8082/event
```

Expected Response:

```
{
    "eventName": "Python Workshop"
}
```

### Request 2: Check Registration Service

Method: GET

URL:

```
http://localhost:8083/registration
```

Expected Response:

```
Registration Service is running
```

### Request 3: Register a User

Method: POST

URL:

```
http://localhost:8083/registration
```

Select Body → raw → JSON.

Enter:

```
{
    "id": 2,
    "name": "Gabber",
    "email": "gabber@gmail.com"
}
```

Expected Response:

```
{
    "id": 2,
    "name": "Gabber",
    "email": "gabber@gmail.com",
    "event": "Python Workshop"
}
```

## 5. RESULT

Thus, a simple user registration application for an event was developed successfully using Spring Boot Microservices. The Registration Service communicates with the Event Service using RestTemplate and retrieves the event details.

Word formatting tip: After pasting, use Calibri or Times New Roman, font size 12 for normal text, 14 for headings, and Consolas for Java code.
