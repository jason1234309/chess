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

        InputStream responseBody = registerConnection.getInputStream();
        InputStreamReader responseReader = new InputStreamReader(responseBody);
        return new Gson().fromJson(responseReader, ResponseAuth.class);
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
        InputStream responseBody = registerConnection.getInputStream();
        InputStreamReader responseReader = new InputStreamReader(responseBody);
        return new Gson().fromJson(responseReader, ResponseAuth.class);
    }
    public GameCreationResponse createClientGame(AuthData clientAuth, String gameName) throws IOException, URISyntaxException {
        URI registerURL = new URI(serverUrl + "/game");
        HttpURLConnection registerConnection = (HttpURLConnection) registerURL.toURL().openConnection();
        registerConnection.setRequestMethod("POST");
        registerConnection.setDoOutput(true);
        registerConnection.addRequestProperty("authToken", clientAuth.getAuthToken());
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("gameName", gameName);
        try(OutputStream requestBody = registerConnection.getOutputStream();){
            String jsonObjectString = new Gson().toJson(jsonObject);
            requestBody.write(jsonObjectString.getBytes());
        }
        registerConnection.connect();

        InputStream responseBody = registerConnection.getInputStream();
        InputStreamReader responseReader = new InputStreamReader(responseBody);
        return new Gson().fromJson(responseReader, GameCreationResponse.class);
    }
    public GameListResponse listServerGames(AuthData clientAuth) throws IOException, URISyntaxException {
        URI registerURL = new URI(serverUrl + "/game");
        HttpURLConnection registerConnection = (HttpURLConnection) registerURL.toURL().openConnection();
        registerConnection.setRequestMethod("POST");
        registerConnection.setDoOutput(true);
        registerConnection.addRequestProperty("authToken", clientAuth.getAuthToken());
        registerConnection.connect();

        InputStream responseBody = registerConnection.getInputStream();
        InputStreamReader responseReader = new InputStreamReader(responseBody);
        GameListResponse gameListResponse = new Gson().fromJson(responseReader, GameListResponse.class);
        lastRecievedGameList = gameListResponse.games();
        return gameListResponse;
    }
    public ErrorResponce joinClientToServerGame(AuthData clientAuth, int gameID, String playerColor) throws IOException, URISyntaxException {
        URI registerURL = new URI(serverUrl + "/game");
        HttpURLConnection registerConnection = (HttpURLConnection) registerURL.toURL().openConnection();
        registerConnection.setRequestMethod("PUT");
        registerConnection.setDoOutput(true);
        registerConnection.addRequestProperty("authToken", clientAuth.getAuthToken());
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("playerColor", playerColor.toUpperCase());
        jsonObject.addProperty("gameID", gameID);
        try(OutputStream requestBody = registerConnection.getOutputStream();){
            String jsonObjectString = new Gson().toJson(jsonObject);
            requestBody.write(jsonObjectString.getBytes());
        }
        registerConnection.connect();

        InputStream responseBody = registerConnection.getInputStream();
        InputStreamReader responseReader = new InputStreamReader(responseBody);
        return new Gson().fromJson(responseReader, ErrorResponce.class);
    }
    public GameData observeServerGame(int gameID){
        for(GameData currentGame:lastRecievedGameList){
            if(currentGame.getGameID() == gameID);
            return currentGame;
        }
        return null;
    }
    public ErrorResponce logoutClient(AuthData clientAuth) throws IOException, URISyntaxException {
        URI registerURL = new URI(serverUrl + "/game");
        HttpURLConnection registerConnection = (HttpURLConnection) registerURL.toURL().openConnection();
        registerConnection.setRequestMethod("PUT");
        registerConnection.setDoOutput(true);
        registerConnection.addRequestProperty("authToken", clientAuth.getAuthToken());
        registerConnection.connect();

        InputStream responseBody = registerConnection.getInputStream();
        InputStreamReader responseReader = new InputStreamReader(responseBody);
        return new Gson().fromJson(responseReader, ErrorResponce.class);
    }

}
