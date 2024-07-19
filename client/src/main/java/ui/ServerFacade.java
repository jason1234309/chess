package ui;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import model.AuthData;
import responserequest.ResponseAuth;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.*;
import java.util.Map;
import java.util.Scanner;

public class ServerFacade {
    private final String serverUrl;

    public ServerFacade(String url) {
        serverUrl = url;
    }
    public ResponseAuth registerClient(String username, String password, String email) throws URISyntaxException, IOException {
        URI registerURL = new URI(serverUrl + "/user");
        HttpURLConnection registerConnection = (HttpURLConnection) registerURL.toURL().openConnection();
        registerConnection.setRequestMethod("POST");
        registerConnection.setDoOutput(true);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("username", username);
        jsonObject.addProperty("password", password);
        jsonObject.addProperty("email", email);

        try(OutputStream requestBody = registerConnection.getOutputStream();){
            String jsonObjectString = new Gson().toJson(jsonObject);
            requestBody.write(jsonObjectString.getBytes());
        }
        registerConnection.connect();

        if(registerConnection.getResponseCode() == HttpURLConnection.HTTP_OK){
            InputStream responseBody = registerConnection.getInputStream();
            InputStreamReader responceReader = new InputStreamReader(responseBody);
            ResponseAuth responceObj = new Gson().fromJson(responceReader, ResponseAuth.class);
            System.out.print(responceObj.authToken());
            return responceObj;
        }else{
            InputStream responseBody = registerConnection.getInputStream();
            InputStreamReader responceReader = new InputStreamReader(responseBody);
            ResponseAuth responceObj = new Gson().fromJson(responceReader, ResponseAuth.class);
            System.out.println(responceObj.message());
            return responceObj;
        }
    }
    public ResponseAuth loginClient(String username, String password) throws URISyntaxException, IOException {
        URI registerURL = new URI(serverUrl + "/user");
        HttpURLConnection registerConnection = (HttpURLConnection) registerURL.toURL().openConnection();
        registerConnection.setRequestMethod("POST");
        registerConnection.setDoOutput(true);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("username", username);
        jsonObject.addProperty("password", password);

        try(OutputStream requestBody = registerConnection.getOutputStream();){
            String jsonObjectString = new Gson().toJson(jsonObject);
            requestBody.write(jsonObjectString.getBytes());
        }
        registerConnection.connect();

        if(registerConnection.getResponseCode() == HttpURLConnection.HTTP_OK){
            InputStream responseBody = registerConnection.getInputStream();
            InputStreamReader responseReader = new InputStreamReader(responseBody);
            ResponseAuth responseObj = new Gson().fromJson(responseReader, ResponseAuth.class);
            System.out.print(responseObj.authToken());
            return responseObj;
        }else{
            InputStream responseBody = registerConnection.getInputStream();
            InputStreamReader responseReader = new InputStreamReader(responseBody);
            ResponseAuth responseObj = new Gson().fromJson(responseReader, ResponseAuth.class);
            System.out.println(responseObj.message());
            return responseObj;
        }
    }

}
