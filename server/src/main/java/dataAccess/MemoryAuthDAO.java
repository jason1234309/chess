package dataAccess;

import model.AuthData;
import java.util.ArrayList;
import java.util.Collection;

public class MemoryAuthDAO implements AuthDAO{
    Collection<AuthData> authTokenDataBase = new ArrayList<>();

    @Override
    public void clearUserDataBase() {

    }

    @Override
    public void createAuth(String Username, AuthData authToken) {

    }

    @Override
    public AuthData getAuth(AuthData authToken) {
        return null;
    }

    @Override
    public void deleteAuth(AuthData authToken) {

    }
}
