package com.lukaszk.chat.message;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Base64;

public class NetClientGet {
    public static String getAuthenticationHeader(){
        String regularText = "lukaszk" + ":" + "LcHjjZqUAQhwp36Q";
        Base64.Encoder encoder = Base64.getEncoder();
        byte[] encoded = encoder.encode(regularText.getBytes(Charset.forName("UTF-8")));
        return "Basic " + new String(encoded, Charset.forName("UTF-8"));
    }

    // http://localhost:8080/RESTfulExample/json/product/get
    public static void main(String[] args) {


        try {

            URL url = new URL("http://ec2-52-57-234-211.eu-central-1.compute.amazonaws.com:8090/api/message");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("Authorization", getAuthenticationHeader());

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));

            String output;
            System.out.println("Output from Server .... \n");
            while ((output = br.readLine()) != null) {
                System.out.println(output);
            }

            conn.disconnect();

        } catch (MalformedURLException e) {

            e.printStackTrace();

        } catch (IOException e) {

            e.printStackTrace();

        }

    }

}
