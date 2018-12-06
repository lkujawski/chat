package com.lukaszk.removemessage;
package com.kujawski.removemessage;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.kujawski.AbstractRemoteService;
//import com.kujawski.Settings;
import com.kujawski.chat.message.Message;
import com.kujawski.chat.message.MessageDao;
import com.kujawski.chat.message.MessageService;

@RestController
@RequestMapping("/api/remove")
public class RemoveMessageController extends AbstractRemoteService {

    @Autowired
    private MessageService messageService;

    @Autowired
    private MessageDao messageDao;

    @RequestMapping(method = RequestMethod.POST)
    public void removeMessage(@RequestBody String id) {
        System.out.println(id);

        Message MessageToDelete = null;
        List<Message> tmpList = messageDao.getAllMessages();
        for (Message mes : tmpList) {
            if (mes.getMessageId().equals(id)) {
                MessageToDelete = mes;
            }
        }
        messageService.delete(MessageToDelete);

    }

}
