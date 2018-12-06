package com.lukaszk.notification;

import com.kujawski.AbstractRemoteService;
import com.kujawski.chat.message.MessageDao;
import com.kujawski.chat.message.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/event")
public class GetNotificationFromRemoteServetController extends AbstractRemoteService {

    @Autowired
    private MessageService messageService;

    @Autowired
    private MessageDao messageDao;

    @Value("${remote.baseUrl}")
    private String remoteBaseUrl;

    private static final String USER_RESOURCE_URLM = "/api/event";

    @RequestMapping(method = RequestMethod.GET)
    public void getNotificationFromRemoteServer() {

        HttpHeaders headers = getDefaultHeaders();
        URI url = prepareUrl(USER_RESOURCE_URLM);

        RequestEntity<Void> request = new RequestEntity<>(headers, HttpMethod.GET, url);
        ResponseEntity<List<com.kujawski.notification.NotificationFromServer>> response = restTemplate.exchange(request,
                new ParameterizedTypeReference<List<com.kujawski.notification.NotificationFromServer>>() {
                });
        System.out.println("From Remote GETALLEVENT " + response.getBody());

        List<com.kujawski.notification.NotificationFromServer> listeventsRemoteServer = new ArrayList();
        listeventsRemoteServer.addAll(response.getBody());
        System.out.println("GET ALL EVENT amounts of event: "+listeventsRemoteServer.size());
        for (com.kujawski.notification.NotificationFromServer notificationFromServer : listeventsRemoteServer) {

            if (messageDao.checkIfMessageIdExist(notificationFromServer.getEventBody().getMessageId())) {

                if (notificationFromServer.getEventType().equals("RECEIVED_NOTIFICATION")) {
                    System.out.println("Save Received Notification to messageId: "
                            + notificationFromServer.getEventBody().getMessageId());
                    messageService
                            .saveConfirmReceivedNotification(notificationFromServer.getEventBody().getMessageId());
                } else if (notificationFromServer.getEventType().equals("READ_NOTIFICATION")) {
                    System.out.println("Save Read Notification to messageId: "
                            + notificationFromServer.getEventBody().getMessageId());
                    messageService.saveConfirmReadNotification(notificationFromServer.getEventBody().getMessageId());
                }

            }
        }
    }

}
