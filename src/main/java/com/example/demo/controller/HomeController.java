package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @GetMapping("/order")
    public String order() {
        return "order";
    }

    @GetMapping("/points")
    public String points() {
        return "points";
    }

    @GetMapping("/leaderboard")
    public String leaderboard() {
        return "leaderboard";
    }
}