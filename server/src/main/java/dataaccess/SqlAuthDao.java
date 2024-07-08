package dataaccess;

import model.AuthData;

public class SqlAuthDao implements AuthDAO{
    @Override
    public void clearAuthDataBase() {

    }

    @Override
    public void createAuth(String Username, String authToken) throws DataAccessException {

    }

    @Override
    public AuthData getAuth(String authToken) throws DataAccessException {
        return null;
    }

    @Override
    public void deleteAuth(String authToken) throws DataAccessException {

    }
}
