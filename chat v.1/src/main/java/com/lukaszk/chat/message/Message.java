package com.lukaszk.chat.message;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Message {

    private String body;
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;
    @Column(unique = true)
    private String messageId;
    @Id
    @GeneratedValue
    private int id;
    private String receiverId;
    private String senderId;
    private String readNotification;
    private String confirmationOfReceived;
    private String confirmationOfRead;

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public String getReadNotification() {
        return readNotification;
    }

    public void setReadNotification(String readNotification) {
        this.readNotification = readNotification;
    }

    public String getConfirmationOfReceived() {
        return confirmationOfReceived;
    }

    public void setConfirmationOfReceived(String confirmationOfReceived) {
        this.confirmationOfReceived = confirmationOfReceived;
    }

    public String getConfirmationOfRead() {
        return confirmationOfRead;
    }

    public void setConfirmationOfRead(String confirmationOfRead) {
        this.confirmationOfRead = confirmationOfRead;
    }

    @Override
    public String toString() {
        return "Message [id=" + id + ", receiverId=" + receiverId + ", senderId=" + senderId + ", body=" + body
                + ", created=" + created + "]";
    }


}
