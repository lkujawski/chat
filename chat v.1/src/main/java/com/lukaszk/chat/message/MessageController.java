package com.lukaszk.chat.message;

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
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.kujawski.AbstractRemoteService;
import com.kujawski.notification.SendReadNotificationController;
import com.kujawski.notification.SendReceivedNotificationController;
//import com.kujawski.Settings;

@RestController
@RequestMapping("/api/message")
public class MessageController extends AbstractRemoteService {

    @Autowired
    private MessageService messageService;

    @Autowired
    private MessageDao messageDao;

    @Autowired
    private SendReceivedNotificationController sendReceivedNotificationController;

    @Autowired
    private SendReadNotificationController readNotificationController;

    @Value("${remote.baseUrl}")
    private String remoteBaseUrl;

    // @RequestMapping(method = RequestMethod.GET)
    // public List<Message> getMessage() {
    // // return Arrays.asList("czesc","druga wiadomosc","otworzcie drzwi");
    // return messageService.getAllMessages();
    //
    // }

    private static final String USER_RESOURCE_URLM = "/api/message";

    @RequestMapping(method = RequestMethod.GET)
    public List<Message> getMessageFromRemoteServer() {
        // System.out.println("GET ALL USERNAME");
        HttpHeaders headers = getDefaultHeaders();
        URI url = prepareUrl(USER_RESOURCE_URLM);

        RequestEntity<Void> request = new RequestEntity<>(headers, HttpMethod.GET, url);
        ResponseEntity<List<Message>> response = restTemplate.exchange(request,
                new ParameterizedTypeReference<List<Message>>() {
                });
        // System.out.println("From Remote GETALLMESSAGE "+response.getBody());

        List<Message> listMessageRemoteServer = new ArrayList();
        listMessageRemoteServer.addAll(response.getBody());

        for (Message message : listMessageRemoteServer) {

            if (messageDao.checkIfMessageNotExist(message)) {
                messageService.create(message);
            }

//			System.out.println("Mes cont"+message.getSenderId()+"->"+message.getReceiverId()+"if id exist: "+messageDao.checkIfMessageIdExist(message.getMessageId()));

            if (messageDao.checkIfMessageIdExist(message.getMessageId())) {
//				System.out.println("jestem!!!!");
                sendReceivedNotificationController.sendReceivedNotification(message);
            }

        }

        return messageService.getAllMessages();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{senderId}/{receiverId}/{body}")
    public Message createMessage(@PathVariable String senderId, @PathVariable String receiverId,
                                 @PathVariable String body) {
        Message message = new Message();
        // Date formattedDate = new Date();
        message.setBody(body);
        message.setReceiverId(receiverId);
        message.setSenderId(senderId);
        message.setCreated(new Date());

        messageService.create(message);
        return message;
    }

    // @RequestMapping(method = RequestMethod.POST)
    // public Message createMessage2(@RequestBody Message message){
    // message.setCreated(new Date());
    //
    //// System.out.println("Data: "+message.getCreated().toString());
    // sendMessageToRemoteServer(message);
    // messageService.create(message);
    //
    // return message;
    // }

    private static final String USER_RESOURCE_URL = "/api/message";

    @RequestMapping(method = RequestMethod.POST)
    public Message createMessage2(@RequestBody Message message) {
        message.setCreated(new Date());

        // System.out.println("aaaaa i2.html"+message.getSenderId()+" ->
        // "+message.getReceiverId());
        // Message remoteMes = send2(message);
        // System.out.println("aaaaa remoteMes"+remoteMes.getSenderId()+" ->
        // "+remoteMes.getReceiverId());
        // messageService.create(remoteMes);

        messageService.create(send2(message));
        // send2(message);
        return message;
    }

    public Message send2(Message message) {
        HttpHeaders headers = getDefaultHeaders();
        URI url = prepareUrl(USER_RESOURCE_URL);
        RequestEntity<Message> request = new RequestEntity<>(message, headers, HttpMethod.POST, url);
        ResponseEntity<Message> result = restTemplate.exchange(request, Message.class);
        return result.getBody();

    }

    public void sendMessageToRemoteServer(Message message) {
        try {

            URL url = new URL(remoteBaseUrl + "/api/message");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Authorization", com.kujawski.Authentication.getAuthenticationHeader());

            // String input = "{\"qty\":100,\"name\":\"iPad 4\"}";
            Message2 message2 = new Message2();

            message2.setBody(message.getBody());
            message2.setCreated(message.getCreated());
            message2.setMessageId("" + message.getId());
            message2.setReceiverId(message.getReceiverId());
            message2.setSenderId(message.getSenderId());

            Gson gson = new Gson();
            // String input ="{\"body\": \"string\",\"created\":
            // \"2016-12-18T19:37:19.131Z\",\"messageId\":
            // \"string\",\"receiverId\": \"pkujawski2\",\"senderId\":
            // \"pkujawski\"}";

            String input = gson.toJson(message2);
            System.out.println("JSON TO REMOTE " + input);

            OutputStream os = conn.getOutputStream();
            os.write(input.getBytes());
            os.flush();

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

            String output;
            // System.out.println("Output from Server .... \n");
            // while ((output = br.readLine()) != null) {
            // System.out.println(output);
            // }

            conn.disconnect();

        } catch (MalformedURLException e) {

            e.printStackTrace();

        } catch (IOException e) {

            e.printStackTrace();

        }

    }
}
