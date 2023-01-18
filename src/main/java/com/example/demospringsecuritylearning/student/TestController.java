package com.example.demospringsecuritylearning.student;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/tests")
public class TestController {

    @GetMapping(path = "/")
    public String getStudent() {
        return "hello";
    }

}
