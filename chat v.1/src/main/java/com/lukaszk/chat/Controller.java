package com.lukaszk.chat;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class Controller {
//port deafult 8080

    @RequestMapping(method=RequestMethod.GET, value ="/say")
    public String hello(){
        return "hello";
    }
}
