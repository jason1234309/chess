package service;

import dataAccess.DataAccessException;
import dataAccess.UserDAO;
import dataAccess.AuthDAO;
import model.AuthData;
import model.UserData;

import java.util.UUID;

public class UserService {
    public AuthData register(UserData user) {
        try{
            UserDAO.createUser(user.getUsername(), user.getPassword(), user.getPassword());
        }
        catch(DataAccessException currentException){
            return new AuthData("403 error", "Error: already taken");
        }
        try{
            AuthData brandNewAuthToken = new AuthData(user.getUsername(), UUID.randomUUID().toString();
            AuthDAO.createAuth(brandNewAuthToken);
            return brandNewAuthToken;
        }
        catch(DataAccessException currentException){
            return new AuthData("not sure the erorr", "shouldnt get called");
        }
        return null;
    }
    public AuthData login(UserData user) {
        // needs a try catch block
        return null;
    }
    public void logout(UserData user) throws DataAccessException {
        // needs a try catch block
    }
}
