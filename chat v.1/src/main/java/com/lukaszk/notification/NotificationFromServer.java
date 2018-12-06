package com.lukaszk.notification;

import java.util.Date;


public class NotificationFromServer {

    private String eventType;
    private MessageElementsFromNotificationFromServer eventBody;
    private String eventId;
    private Date created;
    private String receiverId;
    public String getEventType() {
        return eventType;
    }
    public void setEventType(String eventType) {
        this.eventType = eventType;
    }
    public MessageElementsFromNotificationFromServer getEventBody() {
        return eventBody;
    }
    public void setEventBody(MessageElementsFromNotificationFromServer eventBody) {
        this.eventBody = eventBody;
    }
    public String getEventId() {
        return eventId;
    }
    public void setEventId(String eventId) {
        this.eventId = eventId;
    }
    public Date getCreated() {
        return created;
    }
    public void setCreated(Date created) {
        this.created = created;
    }
    public String getReceiverId() {
        return receiverId;
    }
    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }
    @Override
    public String toString() {
        return "NotificationFromServer [eventType=" + eventType + ", eventBody.getMessageId=" + eventBody.getMessageId() +", eventBody.getCreated=" + eventBody.getCreated() + ", eventId=" + eventId
                + ", created=" + created + ", receiverId=" + receiverId + "]";
    }
}
