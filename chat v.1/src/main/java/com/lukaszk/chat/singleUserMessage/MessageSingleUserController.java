package com.lukaszk.chat.singleUserMessage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.kujawski.AbstractRemoteService;
//import com.kujawski.Settings;
import com.kujawski.chat.message.Message;
import com.kujawski.chat.message.MessageDao;
import com.kujawski.chat.message.MessageService;

@RestController
@RequestMapping("/api/singleuser")
public class MessageSingleUserController extends AbstractRemoteService {

    @Autowired
    private MessageService messageService;

    @Autowired
    private MessageDao messageDao;


    @RequestMapping(method = RequestMethod.GET,value = "/{singleUser}")
    public List<Message>  showCorrespondencewithSingleUser(@PathVariable String singleUser){
        return messageService.getMessagesToFromSingleUser(singleUser);
    }

}
