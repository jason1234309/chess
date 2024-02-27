package dataAccess;

import model.AuthData;
import model.UserData;

import java.util.ArrayList;
import java.util.Collection;

public class MemoryUserDAO implements UserDAO{
    Collection<AuthData> userDataBase = new ArrayList<>();
    @Override
    public void clearUserDataBase() {

    }

    @Override
    public void createUser(String username, String password, String email) {

    }

    @Override
    public UserData getUser(String username) {
        return null;
    }
}
