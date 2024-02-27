package dataAccess;

import model.AuthData;
import model.UserData;

import java.util.ArrayList;
import java.util.Collection;

public class MemoryUserDAO implements UserDAO{
    Collection<UserData> userDataBase = new ArrayList<>();
    @Override
    public void clearUserDataBase() {
        userDataBase = null;
    }

    @Override
    public void createUser(String username, String password, String email) throws DataAccessException {
        UserData currentUser = new UserData(username,password,email);
        userDataBase.add(currentUser);
    }

    @Override
    public UserData getUser(String username) throws DataAccessException {
        for(UserData currentUser: userDataBase){
            if(currentUser.getUsername().equals(username)){
                return currentUser;
            }
        }
        return null;
    }
}
