package com.lukaszk.notification;

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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.kujawski.AbstractRemoteService;
//import com.kujawski.Settings;
import com.kujawski.chat.message.Message;
import com.kujawski.chat.message.MessageService;

@RestController
@RequestMapping("/api/read-notification")
public class SendReadNotificationController extends AbstractRemoteService {

    @Value("${remote.baseUrl}")
    private String remoteBaseUrl;

    @Autowired
    private MessageService messageService;

    private static final String USER_RESOURCE_URL = "/api/read-notification";


    @RequestMapping(method = RequestMethod.POST)
    public void sendReadNotification(@RequestBody Message message) {

        NotificationToServer notificationToServer = new NotificationToServer();

        notificationToServer.setMessageId(message.getMessageId());

//		System.out.println("READ NOTIFICATION");


        HttpHeaders headers = getDefaultHeaders();
        URI url = prepareUrl(USER_RESOURCE_URL+"?receiverId="+message.getSenderId());
//		System.out.println("URL: "+url);
        System.out.println("READ Notification to sent: "+notificationToServer +" "+ url);
        RequestEntity<NotificationToServer> request = new RequestEntity<>(notificationToServer, headers, HttpMethod.POST, url);
        ResponseEntity<NotificationFromServer> result = restTemplate.exchange(request, NotificationFromServer.class);
//		System.out.println("Notification response from remote server "+result);
//		message.setReadNotification("SENT");
        messageService.saveSendReadNotification(message.getMessageId());
    }

}
