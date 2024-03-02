package service;

import dataAccess.DataAccessException;
import dataAccess.UserDAO;
import model.AuthData;
import model.UserData;

public class UserService {
    public AuthData register(UserData user) {
        try{
            UserDAO.createUser(user.getUsername(), user.getPassword(), user.getPassword());
        }
        catch(DataAccessException currentException){
            return new AuthData("403 error", "Error: already taken");
        }
        return null;
    }
    public AuthData login(UserData user) throws DataAccessException {
        return null;
    }
    public void logout(UserData user) throws DataAccessException {}
}
