package com.haibo.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class HelloController {

    @GetMapping("/admin")
    @Secured("ROLE_ADMIN")
    public String AdminHello() {
        return "admin hello";
    }

    @GetMapping("/user")
    @Secured("ROLE_USER")
    public String UserHello() {
        return "user hello";
    }
}
