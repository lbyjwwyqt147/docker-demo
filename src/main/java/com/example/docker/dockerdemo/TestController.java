package com.example.docker.dockerdemo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping(name = "init")
     public  String init(){
         return "欢迎你访问!";
     }
}
