package com.lukaszk.chat.user;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

//import com.kujawski.Settings;


//@RestController
//@RequestMapping("/api/user")
public class MyUserController {

    @Value("${remote.baseUrl}")
    private String remoteBaseUrl;


    @RequestMapping(method=RequestMethod.GET)
    public List<User> getUserList(){
        List<User> userList = new ArrayList();
        try {

            URL url = new URL(remoteBaseUrl+"/api/user");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("Authorization", com.kujawski.Authentication.getAuthenticationHeader());

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));

            String output1=br.readLine();
            String output2=output1.substring(1, output1.length()-1);
            String [] tabUsers=output2.split(",");


            for(int i=0;i<tabUsers.length;i++){
                userList.add(new User(tabUsers[i].substring(1, tabUsers[i].length()-1)));
            }


            conn.disconnect();

        } catch (MalformedURLException e) {

            e.printStackTrace();

        } catch (IOException e) {

            e.printStackTrace();

        }
        return userList;

    }

}
