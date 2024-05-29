package dataaccess;

import model.UserData;

import java.util.ArrayList;
import java.util.Collection;

public class MemoryUserDAO implements UserDAO{
    static Collection<UserData> userDataBase = new ArrayList<>();
    @Override
    public void clearUserDataBase() {
        userDataBase = null;
    }

    @Override
    public void createUser(String username, String password, String email) throws DataAccessException {
        UserData currentUser = new UserData(username,password,email);
        if(!userDataBase.contains(currentUser)){
            userDataBase.add(currentUser);
        }
        else{
            throw new DataAccessException("user already exists");
        }

    }

    @Override
    public UserData getUser(String username) throws DataAccessException {
        for(UserData currentUser: userDataBase){
            if(currentUser.getUsername().equals(username)){
                return currentUser;
            }
        }
        throw new DataAccessException("user does not exist");
    }
}
