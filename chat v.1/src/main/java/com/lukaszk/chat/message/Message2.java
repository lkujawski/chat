package com.lukaszk.chat.message;

import java.text.SimpleDateFormat;
import java.util.Date;


public class Message2 {

    private String body;

    private String created;

    private String messageId;
    private String receiverId;
    private String senderId;


    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String id) {
        this.messageId = id;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        String format = formatter.format(created);
        this.created = format;
    }

    @Override
    public String toString() {
        return "Message2 [id=" + messageId + ", receiverId=" + receiverId + ", senderId=" + senderId + ", body=" + body
                + ", created=" + created + "]";
    }


}
