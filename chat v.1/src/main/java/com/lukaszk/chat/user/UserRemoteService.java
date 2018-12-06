package com.lukaszk.chat.user;

import java.net.URI;
import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.kujawski.AbstractRemoteService;

@Service
public class UserRemoteService extends AbstractRemoteService{

    private static final String USER_RESOURCE_URL="/api/user";
//	private RestTemplate restTemplate =  new RestTemplate();


    public List<String> getAllUsernames(){
//		System.out.println("GET ALL USERNAME");
        HttpHeaders headers = getDefaultHeaders();
        URI url =prepareUrl(USER_RESOURCE_URL);
//		System.out.println("URL toRS "+url);
        RequestEntity<Void> request = new RequestEntity<>(headers, HttpMethod.GET,url);
        ResponseEntity<List<String>> response =
                restTemplate.exchange(request, new ParameterizedTypeReference<List<String>>(){});
//		System.out.println("From Remote "+response.getBody());
        return response.getBody();
    }
}