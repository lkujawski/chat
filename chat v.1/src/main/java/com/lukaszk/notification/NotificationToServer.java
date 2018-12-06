package com.lukaszk.notification;

import java.util.Date;

public class NotificationToServer {

    private String messageId;


    public NotificationToServer() {

    }



    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    @Override
    public String toString() {
        return "NotificationToSent [messageId=" + messageId + "]";
    }

}
