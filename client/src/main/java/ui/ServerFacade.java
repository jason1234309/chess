package ui;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import model.*;
import responserequest.*;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class ServerFacade {
    private final String serverUrl;
    private ArrayList<GameData> lastReceivedGameList = new ArrayList<>();


    public ServerFacade(String url) {
        serverUrl = url;
    }

    public ErrorResponce clearServerDataBase()throws URISyntaxException, IOException{
        // used mainly for testing purposes
        URI registerURL = new URI(serverUrl + "/db");
        HttpURLConnection registerConnection = (HttpURLConnection) registerURL.toURL().openConnection();
        registerConnection.setRequestMethod("DELETE");
        registerConnection.connect();

        if(registerConnection.getResponseCode() == HttpURLConnection.HTTP_OK){
            InputStream responseBody = registerConnection.getInputStream();
            InputStreamReader responseReader = new InputStreamReader(responseBody);
            return new Gson().fromJson(responseReader, ErrorResponce.class);
        }else{
            InputStream responseBody = registerConnection.getErrorStream();
            InputStreamReader responseReader = new InputStreamReader(responseBody);
            return new Gson().fromJson(responseReader, ErrorResponce.class);
        }
    }

    public ResponseAuth registerClient(String username, String password, String email)
            throws URISyntaxException, IOException {
        URI registerURL = new URI(serverUrl + "/user");
        HttpURLConnection registerConnection = (HttpURLConnection) registerURL.toURL().openConnection();
        registerConnection.setRequestMethod("POST");
        registerConnection.setDoOutput(true);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("username", username);
        jsonObject.addProperty("password", password);
        jsonObject.addProperty("email", email);
        try(OutputStream requestBody = registerConnection.getOutputStream()){
            String jsonObjectString = new Gson().toJson(jsonObject);
            requestBody.write(jsonObjectString.getBytes());
        }
        registerConnection.connect();

        if(registerConnection.getResponseCode() == HttpURLConnection.HTTP_OK){
            InputStream responseBody = registerConnection.getInputStream();
            InputStreamReader responseReader = new InputStreamReader(responseBody);
            return new Gson().fromJson(responseReader, ResponseAuth.class);
        }else{
            InputStream responseBody = registerConnection.getErrorStream();
            InputStreamReader responseReader = new InputStreamReader(responseBody);
            return new Gson().fromJson(responseReader, ResponseAuth.class);
        }
    }
    public ResponseAuth loginClient(String username, String password)
            throws URISyntaxException, IOException {
        URI registerURL = new URI(serverUrl + "/session");
        HttpURLConnection registerConnection = (HttpURLConnection) registerURL.toURL().openConnection();
        registerConnection.setRequestMethod("POST");
        registerConnection.setDoOutput(true);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("username", username);
        jsonObject.addProperty("password", password);
        try(OutputStream requestBody = registerConnection.getOutputStream()){
            String jsonObjectString = new Gson().toJson(jsonObject);
            requestBody.write(jsonObjectString.getBytes());
        }
        registerConnection.connect();

        if(registerConnection.getResponseCode() == HttpURLConnection.HTTP_OK){
            InputStream responseBody = registerConnection.getInputStream();
            InputStreamReader responseReader = new InputStreamReader(responseBody);
            return new Gson().fromJson(responseReader, ResponseAuth.class);
        }else{
            InputStream responseBody = registerConnection.getErrorStream();
            InputStreamReader responseReader = new InputStreamReader(responseBody);
            return new Gson().fromJson(responseReader, ResponseAuth.class);
        }
    }
    public GameCreationResponse createClientGame(String clientAuth, String gameName)
            throws IOException, URISyntaxException {
        URI registerURL = new URI(serverUrl + "/game");
        HttpURLConnection registerConnection = (HttpURLConnection) registerURL.toURL().openConnection();
        registerConnection.setRequestMethod("POST");
        registerConnection.setDoOutput(true);
        registerConnection.addRequestProperty("authorization", clientAuth);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("gameName", gameName);
        try(OutputStream requestBody = registerConnection.getOutputStream()){
            String jsonObjectString = new Gson().toJson(jsonObject);
            requestBody.write(jsonObjectString.getBytes());
        }
        registerConnection.connect();

        if(registerConnection.getResponseCode() == HttpURLConnection.HTTP_OK){
            InputStream responseBody = registerConnection.getInputStream();
            InputStreamReader responseReader = new InputStreamReader(responseBody);
            return new Gson().fromJson(responseReader, GameCreationResponse.class);
        }else{
            InputStream responseBody = registerConnection.getErrorStream();
            InputStreamReader responseReader = new InputStreamReader(responseBody);
            return new Gson().fromJson(responseReader, GameCreationResponse.class);
        }
    }

    public GameListResponse listServerGames(String clientAuth)
            throws IOException, URISyntaxException {
        URI registerURL = new URI(serverUrl + "/game");
        HttpURLConnection registerConnection = (HttpURLConnection) registerURL.toURL().openConnection();
        registerConnection.setRequestMethod("GET");
        registerConnection.setDoOutput(true);
        registerConnection.addRequestProperty("authorization", clientAuth);
        registerConnection.connect();
        if(registerConnection.getResponseCode() == HttpURLConnection.HTTP_OK){
            InputStream responseBody = registerConnection.getInputStream();
            InputStreamReader responseReader = new InputStreamReader(responseBody);
            Gson serializer = new GsonBuilder().enableComplexMapKeySerialization().create();
            GameListResponse gameListResponse = serializer.fromJson(responseReader, GameListResponse.class);
            lastReceivedGameList.clear();
            lastReceivedGameList.addAll(gameListResponse.games());
            return gameListResponse;
        }else{
            InputStream responseBody = registerConnection.getErrorStream();
            InputStreamReader responseReader = new InputStreamReader(responseBody);
            return new Gson().fromJson(responseReader, GameListResponse.class);
        }
    }
    public ErrorResponce joinClientToServerGame(String clientAuth, int gameNumber, String playerColor)
            throws IOException, URISyntaxException {
        if(lastReceivedGameList.size() <= gameNumber){
            return new ErrorResponce("Invalid gameNumber");
        }
        int gameID = lastReceivedGameList.get(gameNumber).getGameID();
        URI registerURL = new URI(serverUrl + "/game");
        HttpURLConnection registerConnection = (HttpURLConnection) registerURL.toURL().openConnection();
        registerConnection.setRequestMethod("PUT");
        registerConnection.setDoOutput(true);
        registerConnection.addRequestProperty("authorization", clientAuth);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("playerColor", playerColor.toUpperCase());
        jsonObject.addProperty("gameID", gameID);
        try(OutputStream requestBody = registerConnection.getOutputStream()){
            String jsonObjectString = new Gson().toJson(jsonObject);
            requestBody.write(jsonObjectString.getBytes());
        }
        registerConnection.connect();

        if(registerConnection.getResponseCode() == HttpURLConnection.HTTP_OK){
            InputStream responseBody = registerConnection.getInputStream();
            InputStreamReader responseReader = new InputStreamReader(responseBody);
            return new Gson().fromJson(responseReader, ErrorResponce.class);
        }else{
            InputStream responseBody = registerConnection.getErrorStream();
            InputStreamReader responseReader = new InputStreamReader(responseBody);
            return new Gson().fromJson(responseReader, ErrorResponce.class);
        }
    }
    public GameData observeServerGame(int gameNumber){
        if(lastReceivedGameList.size() <= gameNumber){
            return null;
        }
        int gameID = lastReceivedGameList.get(gameNumber).getGameID();
        for(GameData currentGame: lastReceivedGameList){
            if(currentGame.getGameID() == gameID){
                return currentGame;
            }
        }
        return null;
    }
    public ErrorResponce logoutClient(String clientAuth)
            throws IOException, URISyntaxException {
        URI registerURL = new URI(serverUrl + "/session");
        HttpURLConnection registerConnection = (HttpURLConnection) registerURL.toURL().openConnection();
        registerConnection.setRequestMethod("DELETE");
        registerConnection.setDoOutput(true);
        registerConnection.addRequestProperty("authorization", clientAuth);
        registerConnection.connect();

        if(registerConnection.getResponseCode() == HttpURLConnection.HTTP_OK){
            InputStream responseBody = registerConnection.getInputStream();
            InputStreamReader responseReader = new InputStreamReader(responseBody);
            return new Gson().fromJson(responseReader, ErrorResponce.class);
        }else{
            InputStream responseBody = registerConnection.getErrorStream();
            InputStreamReader responseReader = new InputStreamReader(responseBody);
            return new Gson().fromJson(responseReader, ErrorResponce.class);
        }
    }
}
