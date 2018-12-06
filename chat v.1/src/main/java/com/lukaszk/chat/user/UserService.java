package com.lukaszk.chat.user;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Value("${auth.username}")
    private String authenticatedUsername;

    public String getAuthenticatedUsername(){
        return authenticatedUsername;
    }
}
