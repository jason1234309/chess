package service;

import dataaccess.*;
import model.*;
import responserequest.*;
import chess.ChessGame;
import org.mindrot.jbcrypt.BCrypt;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

public class AllServices {
    public AllServices(){
        // creates all of the sql DAO objects
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
        // clears all the data from the database
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
        // checks if the user object has null fields and throws DataAccessException if null field is found
        if(user.getUsername() == null || user.getPassword() == null || user.getEmail() == null){
            return new ResponseAuth(null, null, "Error: bad request");
        }
        // creates a hashed password for the database entry and
        // calls the create user DAO method to add the new user to the database
        try{
            String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
            userDAOObj.createUser(user.getUsername(), hashedPassword, user.getEmail());
        }catch(DataAccessException e){
            return new ResponseAuth(null, null, e.getMessage());
        }
        try{
            // creates a new AuthToken and
            // calls the create auth DAO method to add the new auth to the database
            AuthData brandNewAuthToken = new AuthData(user.getUsername(), UUID.randomUUID().toString());
            authDAOObj.createAuth(brandNewAuthToken.getUsername(), brandNewAuthToken.getAuthToken());
            return new ResponseAuth(brandNewAuthToken.getUsername(), brandNewAuthToken.getAuthToken(), null);
        }catch(DataAccessException e){
            return new ResponseAuth(null, null, e.getMessage());
        }
    }
    public ResponseAuth login(UserData user) {
        // calls the get user DAO method to verify that the provided user is in the database
        try{
            UserData dataBaseUser = userDAOObj.getUser(user.getUsername());
            if(dataBaseUser == null){
                return new ResponseAuth(null, null, "Error: unauthorized");
            }
            // verifies that the found user has the same password as the provided user password
            if(!BCrypt.checkpw(user.getPassword(), dataBaseUser.getPassword())){
                return new ResponseAuth(null, null, "Error: unauthorized");
            }
        }catch(DataAccessException e){
            return new ResponseAuth(null, null, e.getMessage());
        }
        // creates a new authToken and
        // calls the create auth DAO method to add the new auth to the database
        try{
            AuthData brandNewAuthToken = new AuthData(user.getUsername(), UUID.randomUUID().toString());
            authDAOObj.createAuth(brandNewAuthToken.getUsername(), brandNewAuthToken.getAuthToken());
            return new ResponseAuth(brandNewAuthToken.getUsername(), brandNewAuthToken.getAuthToken(), null);
        }catch(DataAccessException e){
            return new ResponseAuth(null, null, e.getMessage());
        }
    }
    public ErrorResponce logout(AuthData userAuthToken){
        // calls the get auth DAO method to verify the authToken is in the database
        try{
           AuthData returnedAuth = authDAOObj.getAuth(userAuthToken.getAuthToken());
           if(returnedAuth == null){
               return new ErrorResponce("Error: unauthorized");
           }
           // deletes the authToken provided from the database
            authDAOObj.deleteAuth(userAuthToken.getAuthToken());
            return new ErrorResponce(null);
        }catch(DataAccessException e){
            return new ErrorResponce(e.getMessage());
        }
    }

    public GameCreationResponse createGame(AuthData userAuth, String gameName) {
        // checks to see if the game name is null and will throw DataAccessException if null is found
        if(gameName == null){
            return new GameCreationResponse(null, "Error: bad request");
        }
        // calls get auth DAO method to verify provided authToken is in the dataBase
        try{
            AuthData returnedUserAuth = authDAOObj.getAuth(userAuth.getAuthToken());
            if(returnedUserAuth == null){
                throw new DataAccessException("Error: unauthorized");
            }
        }catch(DataAccessException e){
            return new GameCreationResponse(null, e.getMessage());
        }
        // calls the create game DAO method to add a game to the database and returns the game ID
        try{
            int gameId = gameDAOObj.createGame(gameName);
            return new GameCreationResponse(gameId, null);
        }catch(DataAccessException e){
            return new GameCreationResponse(null, e.getMessage());
        }

    }
    public GameListResponse listGames(AuthData userAuth){
        // calls get auth DAO method to verify provided authToken is in the dataBase
        try{
            AuthData returnedUserAuth = authDAOObj.getAuth(userAuth.getAuthToken());
            if(returnedUserAuth == null){
                throw new DataAccessException("Error: unauthorized");
            }
        }catch(DataAccessException e){
            return new GameListResponse(null, e.getMessage());
        }
        // calls the list games DAO method to get all games in the database
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
        // checks to see if the game name is null and if the gameID is null and
        // will throw DataAccessException if null is found
        if(playerColor == null || gameId == null){
            return new ErrorResponce("Error: bad request");
        }
        // calls get auth DAO method to verify provided authToken is in the dataBase
        try{
            AuthData returnedUserAuth = authDAOObj.getAuth(userAuth.getAuthToken());
            if(returnedUserAuth == null){
                throw new DataAccessException("Error: unauthorized");
            }
        }catch(DataAccessException e){
            return new ErrorResponce(e.getMessage());
        }
        // calls get game DAO method to verify that the game is in the database and to get the game object

        try{
            AuthData fullUserAuth = authDAOObj.getAuth(userAuth.getAuthToken());
            GameData currentGame = gameDAOObj.getGame(gameId);
            // if the game object exists, then the current game is checked to see if the provided player color
            // is null and can be added to the current game. if the current teamcolor is taken throws
            // a DataAccessException
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
            // calls the update game DAO method to update the game data in the database
            gameDAOObj.updateGame(gameId, currentGame);
            return new ErrorResponce(null);

        }catch(DataAccessException e){
            return new ErrorResponce(e.getMessage());
        }
    }
}
