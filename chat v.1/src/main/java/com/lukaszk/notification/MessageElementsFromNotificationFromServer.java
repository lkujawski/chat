package com.lukaszk.notification;

import java.util.Date;


public class MessageElementsFromNotificationFromServer {


    private Date created;
    private String messageId;

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }


    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }


}