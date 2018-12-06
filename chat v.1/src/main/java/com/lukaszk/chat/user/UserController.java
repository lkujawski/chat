package com.lukaszk.chat.user;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    UserRemoteService userRemoteService;



    @RequestMapping(method=RequestMethod.GET)
    public List<User> getUserList(){
        List <User> userList = new ArrayList();
        List<String> stringList= userRemoteService.getAllUsernames();


        for (int i=0;i<stringList.size();i++){

            userList.add(new User(stringList.get(i)));

        }
        return userList;
    }
}
