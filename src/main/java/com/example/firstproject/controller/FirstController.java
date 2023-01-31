package com.example.firstproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FirstController {

    @GetMapping("/hi")
    public String niceToMeetYou(Model model){
        model.addAttribute("username", "hong");
        return "greetings";// templates/greetings.mustache 반환
    }

    @GetMapping("/bye")
    public String goodBye(Model model){
        model.addAttribute("nickname", "홍길동");
        return "goodbye";// templates/goodbye.mustache 반환
    }

}
