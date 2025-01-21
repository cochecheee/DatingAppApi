package com.datingapp.controller.auth;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/testapi")
public class TestController {
	@GetMapping("/hello")
    public String sayHello() {
        return "Hello World";
    }
}
