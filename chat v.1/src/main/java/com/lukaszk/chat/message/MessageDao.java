package com.lukaszk.chat.message;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

//import com.kujawski.Settings;

@Repository
public class MessageDao {

    @PersistenceContext
    EntityManager em;

//	@Autowired
//	private Settings settings;

    @Value("${auth.username}")
    private String username;

    public void create(Message message){
        em.persist(message);
    }

    public void delete(Message message){
        em.remove(message);
    }

    public List<Message> getAllMessages(){


//		return em.createNativeQuery("SELECT * FROM MESSAGE",Message.class).getResultList();
        String sql = "SELECT * FROM MESSAGE WHERE receiver_id='"+username+"' or sender_id='"+username+"'";
//		System.out.println(sql);
        return em.createNativeQuery(sql,Message.class).getResultList();
    }

    public List<Message> getMessagesToFromSingleUser(String singleUser){
//		return em.createNativeQuery("SELECT * FROM MESSAGE",Message.class).getResultList();
        String sql = "SELECT * FROM MESSAGE WHERE receiver_id='"+singleUser+"' or sender_id='"+singleUser+"'";
//		System.out.println("aaa"+sql);
        return em.createNativeQuery(sql,Message.class).getResultList();
//		return null;
    }

    public boolean checkIfMessageNotExist(Message message){
        String mesid=message.getMessageId();
        String sql = "SELECT * FROM MESSAGE WHERE message_id='"+mesid+"'";
        List<Message> checkList = new ArrayList();
        checkList.addAll(em.createNativeQuery(sql,Message.class).getResultList());

        return checkList.isEmpty();
    }

    public boolean checkIfMessageIdExist(String messageId){
        String sql = "SELECT * FROM MESSAGE WHERE message_id='"+messageId+"'";
        List<Message> checkList = new ArrayList();
        checkList.addAll(em.createNativeQuery(sql,Message.class).getResultList());
        return !checkList.isEmpty();
    }

    public void saveSendReadNotification(String messageId){
        String sql = "UPDATE message SET read_notification='SENT' where message_id='"+messageId+"'";
//		System.out.println("UPDATE SQL: "+sql);
        em.createNativeQuery(sql,Message.class).executeUpdate();
    }

    public void saveConfirmReceivedNotification(String messageId){
        String sql = "UPDATE message SET confirmation_of_received='YES' where message_id='"+messageId+"'";
//		System.out.println("UPDATE SQL: "+sql);
        em.createNativeQuery(sql,Message.class).executeUpdate();
    }

    public void saveConfirmReadNotification(String messageId){
        String sql = "UPDATE message SET confirmation_of_read='YES' where message_id='"+messageId+"'";
//		System.out.println("UPDATE SQL: "+sql);
        em.createNativeQuery(sql,Message.class).executeUpdate();
    }
}
