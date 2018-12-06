package com.lukaszk.chat.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class UserServiceController {

    @Autowired
    UserService userService;

    @RequestMapping(method=RequestMethod.GET)
    public String getAuthenticatedUser(){
        return userService.getAuthenticatedUsername();
    }


}