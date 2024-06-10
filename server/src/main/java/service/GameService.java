package service;
import dataaccess.*;
import model.AuthData;
import model.GameData;
import model.UserData;
import chess.ChessGame;

import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.UUID;

public class GameService {
    static UserDAO userDAOObj = new MemoryUserDAO();
    static AuthDAO authDAOObj = new MemoryAuthDAO();
    static GameDAO gameDAOObj = new MemoryGameDAO();

    int gameIdNumOffset = 0;

    public AuthData register(UserData user) throws DataAccessException {
        userDAOObj.createUser(user.getUsername(), user.getPassword(), user.getPassword());
        AuthData brandNewAuthToken = new AuthData(UUID.randomUUID().toString(),user.getUsername());
        authDAOObj.createAuth(brandNewAuthToken.getAuthToken(), brandNewAuthToken.getUsername());
        return brandNewAuthToken;
    }
    public AuthData login(UserData user) throws DataAccessException {
        if(userDAOObj.getUser(user.getUsername()) == null){
            throw new DataAccessException("username doesnt exist");
        }
        UserData dataBaseUser = userDAOObj.getUser(user.getUsername());
        if(!dataBaseUser.getPassword().equals(user.getPassword())){
            throw new DataAccessException("Incorrect password");
        }
        AuthData brandNewAuthToken = new AuthData(user.getUsername(), UUID.randomUUID().toString());
        authDAOObj.createAuth(brandNewAuthToken.getAuthToken(), brandNewAuthToken.getUsername());
        return brandNewAuthToken;
    }
    public void logout(AuthData userAuthToken) throws DataAccessException {
        if(authDAOObj.getAuth(userAuthToken.getAuthToken()) == null){
            throw new DataAccessException("auth doesnt exist");
        }
        authDAOObj.deleteAuth(userAuthToken.getAuthToken());
    }
    public void clearUsers(){
        userDAOObj.clearUserDataBase();
        authDAOObj.clearAuthDataBase();
    }
    public String CreateGame(AuthData userAuth, String gameName) throws DataAccessException {
        if(authDAOObj.getAuth(userAuth.getAuthToken()) == null){
            throw new DataAccessException("auth doesnt exist");
        }
        gameDAOObj.createGame(gameIdNumOffset, gameName);
        gameIdNumOffset += 1;
        return Integer.toString(gameIdNumOffset-1);
    }
    public Collection<GameData> ListGames(AuthData userAuth) throws DataAccessException {
        if(authDAOObj.getAuth(userAuth.getAuthToken()) == null){
            throw new DataAccessException("auth doesnt exist");
        }
        Collection<GameData> totalGameList = new ArrayList<>();
        for(GameData currentGame : gameDAOObj.listGames()){
            totalGameList.add(currentGame);
        }
        return totalGameList;
    }
    public void JoinGame(AuthData userAuth, ChessGame.TeamColor playerColor, String gameId) throws DataAccessException {
        if(authDAOObj.getAuth(userAuth.getAuthToken()) == null){
            throw new DataAccessException("auth doesnt exist");
        }
        GameData currentGame = gameDAOObj.getGame(gameId);
        if(playerColor == ChessGame.TeamColor.BLACK){
            currentGame.setBlackUsername(userAuth.getUsername());
        }
        else{
            currentGame.setWhiteUsername(userAuth.getUsername());
        }
        gameDAOObj.updateGame(gameId, currentGame);
    }
    public void clearGames(){
        gameDAOObj.clearGameDataBase();

    }
}
