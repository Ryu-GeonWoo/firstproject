package com.example.firstproject.api;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController //RestAPI용 컨트롤러!
public class FirstApiController {

    @GetMapping("/api/hello")
    public String hello() {
        return "hello";
    }

    @GetMapping("/api/hi")
    public String hi(){ return "hi";}
}
