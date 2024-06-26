package service;
import dataaccess.*;
import model.*;
import ResponseRequest.*;
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

    int gameIdNumOffset = 1;

    public ErrorResponce clearDatabases(){
        gameDAOObj.clearGameDataBase();
        userDAOObj.clearUserDataBase();
        authDAOObj.clearAuthDataBase();
        return new ErrorResponce(null);
    }

    public ResponseAuth register(UserData user) {
        if(user.getUsername() == null || user.getPassword() == null || user.getEmail() == null){
            return new ResponseAuth(null, null, "Error: bad request");
        }
        try{
            userDAOObj.createUser(user.getUsername(), user.getPassword(), user.getPassword());
        }catch(DataAccessException e){
            return new ResponseAuth(null, null, e.getMessage());
        }
        try{
            AuthData brandNewAuthToken = new AuthData(user.getUsername(), UUID.randomUUID().toString());
            authDAOObj.createAuth(brandNewAuthToken.getUsername(), brandNewAuthToken.getAuthToken());
            return new ResponseAuth(brandNewAuthToken.getUsername(), brandNewAuthToken.getAuthToken(), null);
        }catch(DataAccessException e){
            return new ResponseAuth(null, null, e.getMessage());
        }
    }
    public ResponseAuth login(UserData user) {
        try{
            UserData dataBaseUser = userDAOObj.getUser(user.getUsername());
            if(!dataBaseUser.getPassword().equals(user.getPassword())){
                return new ResponseAuth(null, null, "Error: unauthorized");
            }
        }catch(DataAccessException e){
            return new ResponseAuth(null, null, e.getMessage());
        }
        try{
            AuthData brandNewAuthToken = new AuthData(user.getUsername(), UUID.randomUUID().toString());
            authDAOObj.createAuth(brandNewAuthToken.getUsername(), brandNewAuthToken.getAuthToken());
            return new ResponseAuth(brandNewAuthToken.getUsername(), brandNewAuthToken.getAuthToken(), null);
        }catch(DataAccessException e){
            return new ResponseAuth(null, null, e.getMessage());
        }
    }
    public ErrorResponce logout(AuthData userAuthToken){
        try{
            authDAOObj.getAuth(userAuthToken.getAuthToken());
            authDAOObj.deleteAuth(userAuthToken.getAuthToken());
            return new ErrorResponce(null);
        }catch(DataAccessException e){
            return new ErrorResponce(e.getMessage());
        }
    }

    public GameCreationResponse CreateGame(AuthData userAuth, String gameName) {
        if(gameName == null){
            return new GameCreationResponse(null, "Error: bad request");
        }
        try{
            authDAOObj.getAuth(userAuth.getAuthToken());
        }catch(DataAccessException e){
            return new GameCreationResponse(null, e.getMessage());
        }
        try{
            gameDAOObj.createGame(gameIdNumOffset, gameName);
            gameIdNumOffset += 1;
            return new GameCreationResponse(gameIdNumOffset-1, null);
        }catch(DataAccessException e){
            return new GameCreationResponse(null, e.getMessage());
        }

    }
    public GameListResponse ListGames(AuthData userAuth){
        try{
            authDAOObj.getAuth(userAuth.getAuthToken());
        }catch(DataAccessException e){
            return new GameListResponse(null, e.getMessage());
        }
        Collection<GameData> totalGameList = new ArrayList<>();
        totalGameList.addAll(gameDAOObj.listGames());
        for(GameData currentGame:totalGameList){
            currentGame.setChessGame(null);
        }
        return new GameListResponse(totalGameList, null);
    }
    public ErrorResponce JoinGame(AuthData userAuth, ChessGame.TeamColor playerColor, Integer gameId) {
        if(playerColor == null || gameId == null){
            return new ErrorResponce("Error: bad request");
        }
        try{
            AuthData fullUserAuth = authDAOObj.getAuth(userAuth.getAuthToken());
        }catch(DataAccessException e){
            return new ErrorResponce(e.getMessage());
        }
        try{
            AuthData fullUserAuth = authDAOObj.getAuth(userAuth.getAuthToken());
            GameData currentGame = gameDAOObj.getGame(gameId);
            if(playerColor == ChessGame.TeamColor.BLACK){
                if(currentGame.getBlackUsername() == null){
                    currentGame.setBlackUsername(fullUserAuth.getUsername());
                }else{
                    return new ErrorResponce("Error: already taken");
                }

            }
            else{
                if(currentGame.getWhiteUsername() == null){
                    currentGame.setWhiteUsername(fullUserAuth.getUsername());
                }else{
                    return new ErrorResponce("Error: already taken");
                }
            }
            gameDAOObj.updateGame(gameId, currentGame);
            return new ErrorResponce(null);

        }catch(DataAccessException e){
            return new ErrorResponce(e.getMessage());
        }
    }
}
