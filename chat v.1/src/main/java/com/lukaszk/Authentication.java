package com.lukaszk;

import java.nio.charset.Charset;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Value;

public class Authentication {

    @Value("${auth.username}")
    private static String username;
    @Value("${auth.password}")
    private static String password;

    public static String getAuthenticationHeader(){
//        String regularText = Settings.getInstance().getUser() + ":" + Settings.getInstance().getPassword();
        String regularText = "lukaszku" + ":" + "LcHjjZqUAQhwp36Q";
        Base64.Encoder encoder = Base64.getEncoder();
        byte[] encoded = encoder.encode(regularText.getBytes(Charset.forName("UTF-8")));
        return "Basic " + new String(encoded, Charset.forName("UTF-8"));
    }



}
