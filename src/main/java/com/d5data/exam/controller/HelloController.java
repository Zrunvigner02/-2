package com.d5data.exam.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    /**
     * 需要 Spring Security 认证后才能访问，返回 "Hello World"。
     */
    @GetMapping("/hello")
    public String hello() {
        return "Hello World";
    }
}
