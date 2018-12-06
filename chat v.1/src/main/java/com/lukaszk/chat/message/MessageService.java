package com.lukaszk.chat.message;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class MessageService {

    @Autowired
    private MessageDao messageDao;

    public void create(Message message){
        messageDao.create(message);
    }

    public List<Message> getAllMessages(){
        return messageDao.getAllMessages();
    }

    public List<Message> getMessagesToFromSingleUser(String singleUser){
        return messageDao.getMessagesToFromSingleUser(singleUser);
    }

    public void delete(Message message){
        messageDao.delete(message);
    }

    public void saveSendReadNotification(String messageId){
        messageDao.saveSendReadNotification(messageId);
    }

    public void saveConfirmReceivedNotification(String messageId){
        messageDao.saveConfirmReceivedNotification(messageId);
    }

    public void saveConfirmReadNotification(String messageId){
        messageDao.saveConfirmReadNotification(messageId);
    }
}
