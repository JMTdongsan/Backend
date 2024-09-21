package com.matdongsan.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    @GetMapping("/main")
    public String main() {
        return "main controller";
    }

    @GetMapping("/admin")
    public String admin() {
        return "admin controller";
    }
}
