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

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.kujawski.AbstractRemoteService;
//import com.kujawski.Settings;
import com.kujawski.chat.message.Message;

@RestController
@RequestMapping("/api/received-notification")
public class SendReceivedNotificationController extends AbstractRemoteService {

    @Value("${remote.baseUrl}")
    private String remoteBaseUrl;


    private static final String USER_RESOURCE_URL = "/api/received-notification";

    @RequestMapping(method = RequestMethod.POST)
    public void sendReceivedNotification(Message message) {

        NotificationToServer notificationToServer = new NotificationToServer();

        notificationToServer.setMessageId(message.getMessageId());

//		System.out.println("RECEIVED NOTIFICATION");


        HttpHeaders headers = getDefaultHeaders();
        URI url = prepareUrl(USER_RESOURCE_URL+"?receiverId="+message.getSenderId());
//		System.out.println("URL: "+url);
        System.out.println("RECEIVED Notification to sent: "+notificationToServer+" "+url);
        RequestEntity<NotificationToServer> request = new RequestEntity<>(notificationToServer, headers, HttpMethod.POST, url);
        ResponseEntity<NotificationFromServer> result = restTemplate.exchange(request, NotificationFromServer.class);
//		System.out.println("Notification response from remote server "+result);;

    }

    public void sendRN(Message message) {
        try {

            URL url = new URL("http://ec2-52-57-234-211.eu-central-1.compute.amazonaws.com:8090/api/received-notification?receiverId="+message.getSenderId());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Authorization", com.kujawski.Authentication.getAuthenticationHeader());

            // String input = "{\"qty\":100,\"name\":\"iPad 4\"}";
            NotificationToServer notificationToServer = new NotificationToServer();
//			notification.setCreated(new Date());
            notificationToServer.setMessageId(message.getMessageId());
//			notification.setEventType("RECEIVED_NOTIFICATION");
//			notification.setEventId("");
//			notification.setReceiverId(message.getSenderId());

            Gson gson = new Gson();

            String input = gson.toJson(notificationToServer);
            System.out.println("JSON TO REMOTE " + input);

            OutputStream os = conn.getOutputStream();
            os.write(input.getBytes());
            os.flush();

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
            }


            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

            String output;
            System.out.println("Output from Server .... \n");
            while ((output = br.readLine()) != null) {
                System.out.println(output);
            }

            conn.disconnect();

        } catch (MalformedURLException e) {

            e.printStackTrace();

        } catch (IOException e) {

            e.printStackTrace();

        }

    }
}
