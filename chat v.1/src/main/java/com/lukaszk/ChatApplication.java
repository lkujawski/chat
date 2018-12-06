package com.lukaszk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ChatApplication {

//	public static void readSettings(String[] args){
//		Settings.getInstance().setUser(args[0]);
//		Settings.getInstance().setServer(args[1]);
//		Settings.getInstance().setPassword(args[2]);
//	}

    public static void main(String[] args) {
        SpringApplication.run(ChatApplication.class, args);
//		readSettings(args);

//		UserController eu = new UserController();
////		eu.getUserList();
//		for (int i=0;i<eu.getUserList().size();i++){
//			System.out.println(eu.getUserList().get(i).getId());
//		}

    }
}
