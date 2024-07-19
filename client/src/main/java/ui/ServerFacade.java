package ui;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import model.*;
import responserequest.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.*;
import java.util.ArrayList;
import java.util.Collection;

public class ServerFacade {
    private final String serverUrl;
    private Collection<GameData> lastRecievedGameList;

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
            return new Gson().fromJson(responceReader, ResponseAuth.class);
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
            return new Gson().fromJson(responseReader, ResponseAuth.class);
        }else{
            InputStream responseBody = registerConnection.getInputStream();
            InputStreamReader responseReader = new InputStreamReader(responseBody);
            ResponseAuth responseObj = new Gson().fromJson(responseReader, ResponseAuth.class);
            System.out.println(responseObj.message());
            return responseObj;
        }
    }
    public GameCreationResponse createClientGame(AuthData clientAuth, String gameName){
        return new GameCreationResponse(5,null);
    }
    public GameListResponse listServerGames(AuthData clientAuth){

        // need to initilize lastRecievedGameList
        return new GameListResponse(new ArrayList<GameData>(),null);
    }
    public ErrorResponce joinClientToServerGame(AuthData clientAuth, int gameID, String playerColor){
        // can replace player color with the correct variable or not
        return new ErrorResponce(null);
    }
    public ErrorResponce observeServerGame(int gameID){
        return new ErrorResponce(null);
    }
    public ErrorResponce logoutClient(AuthData clientAuth){
        return new ErrorResponce(null);
    }

}
