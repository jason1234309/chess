package service;

import dataaccess.*;
import model.AuthData;
import model.UserData;

import javax.xml.crypto.Data;
import java.util.UUID;

public class UserService {
    UserDAO userDAOObj = new MemoryUserDAO();
    AuthDAO authDAOObj = new MemoryAuthDAO();
    public AuthData register(UserData user) {
        try{
            userDAOObj.createUser(user.getUsername(), user.getPassword(), user.getPassword());
        }
        catch(DataAccessException currentException){
            return new AuthData("403 error", "Error: already taken");
        }
        try{
            AuthData brandNewAuthToken = new AuthData(user.getUsername(), UUID.randomUUID().toString());
            authDAOObj.createAuth(brandNewAuthToken.getUsername(), brandNewAuthToken.getAuthToken());
            return brandNewAuthToken;
        }
        catch(DataAccessException currentException){
            return new AuthData("not sure the erorr", "shouldnt get called");
        }
    }
    public AuthData login(UserData user) {
        // needs a try catch block
        try{
            if(userDAOObj.getUser(user.getUsername()) == null){
                throw new DataAccessException("username doesnt exist");
            }
            UserData dataBaseUser = userDAOObj.getUser(user.getUsername());
            if(!dataBaseUser.getPassword().equals(user.getPassword())){
                throw new DataAccessException("Incorrect password");
            }
            try{
                AuthData brandNewAuthToken = new AuthData(user.getUsername(), UUID.randomUUID().toString());
                authDAOObj.createAuth(brandNewAuthToken.getUsername(), brandNewAuthToken.getAuthToken());
                return brandNewAuthToken;
            }
            catch(DataAccessException currentException){
                return new AuthData("not sure the erorr", "shouldnt get called");
            }
        }
        catch(DataAccessException currentException){
            return new AuthData("Auth didnt work", "shouldnt get called");
        }
    }
    public void logout(AuthData userAuthToken) throws DataAccessException {
            if(authDAOObj.getAuth(userAuthToken.getAuthToken()) == null){
                throw new DataAccessException("auth doesnt exist");
            }
            authDAOObj.deleteAuth(userAuthToken.getAuthToken());
    }
}
