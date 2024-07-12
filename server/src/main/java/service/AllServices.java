package service;
import dataaccess.*;
import model.*;
import responseRequest.*;
import chess.ChessGame;
import org.mindrot.jbcrypt.BCrypt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

public class AllServices {
    public AllServices(){
        try{
            userDAOObj = new SqlUserDAO();
        }catch(DataAccessException ex){
            System.out.println("Could not create user DAO");
        }
        try{
            authDAOObj = new SqlAuthDAO();
        }catch(DataAccessException ex){
            System.out.println("Could not create Auth DAO");
        }
        try{
            gameDAOObj = new SqlGameDAO();
        }catch(DataAccessException ex){
            System.out.println("Could not create game DAO");
        }
    }
    static UserDAO userDAOObj;
    static AuthDAO authDAOObj;
    static GameDAO gameDAOObj;

    public ErrorResponce clearDatabases(){
        try{
            gameDAOObj.clearGameDataBase();
            userDAOObj.clearUserDataBase();
            authDAOObj.clearAuthDataBase();
        }catch(DataAccessException ex){
            return new ErrorResponce(ex.getMessage());
        }
        return new ErrorResponce(null);
    }

    public ResponseAuth register(UserData user) {
        if(user.getUsername() == null || user.getPassword() == null || user.getEmail() == null){
            return new ResponseAuth(null, null, "Error: bad request");
        }
        try{
            String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
            userDAOObj.createUser(user.getUsername(), hashedPassword, user.getEmail());
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
            if(dataBaseUser == null){
                return new ResponseAuth(null, null, "Error: unauthorized");
            }
            if(!BCrypt.checkpw(user.getPassword(), dataBaseUser.getPassword())){
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
           AuthData returnedAuth = authDAOObj.getAuth(userAuthToken.getAuthToken());
           if(returnedAuth == null){
               return new ErrorResponce("Error: unauthorized");
           }
            authDAOObj.deleteAuth(userAuthToken.getAuthToken());
            return new ErrorResponce(null);
        }catch(DataAccessException e){
            return new ErrorResponce(e.getMessage());
        }
    }

    public GameCreationResponse createGame(AuthData userAuth, String gameName) {
        if(gameName == null){
            return new GameCreationResponse(null, "Error: bad request");
        }
        try{
            AuthData returnedUserAuth = authDAOObj.getAuth(userAuth.getAuthToken());
            if(returnedUserAuth == null){
                throw new DataAccessException("Error: unauthorized");
            }
        }catch(DataAccessException e){
            return new GameCreationResponse(null, e.getMessage());
        }
        try{
            int gameId = gameDAOObj.createGame(gameName);
            return new GameCreationResponse(gameId, null);
        }catch(DataAccessException e){
            return new GameCreationResponse(null, e.getMessage());
        }

    }
    public GameListResponse listGames(AuthData userAuth){
        try{
            AuthData returnedUserAuth = authDAOObj.getAuth(userAuth.getAuthToken());
            if(returnedUserAuth == null){
                throw new DataAccessException("Error: unauthorized");
            }
        }catch(DataAccessException e){
            return new GameListResponse(null, e.getMessage());
        }
        try{
            Collection<GameData> totalGameList = new ArrayList<>(gameDAOObj.listGames());
            for(GameData currentGame:totalGameList){
                currentGame.setChessGame(null);
            }
            return new GameListResponse(totalGameList, null);
        }catch(DataAccessException ex){
            return new GameListResponse(null, ex.getMessage());
        }
    }
    public ErrorResponce joinGame(AuthData userAuth, ChessGame.TeamColor playerColor, Integer gameId) {
        if(playerColor == null || gameId == null){
            return new ErrorResponce("Error: bad request");
        }
        try{
            AuthData returnedUserAuth = authDAOObj.getAuth(userAuth.getAuthToken());
            if(returnedUserAuth == null){
                throw new DataAccessException("Error: unauthorized");
            }
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
