package dataaccess;

import model.AuthData;

public interface AuthDAO {
    //A method for clearing all data from the database. This is used during testing.
    void clearAuthDataBase()throws DataAccessException;
    // Create a new authorization.
    void createAuth(String Username, String authToken) throws DataAccessException;
    // Retrieve an authorization given an authToken.
    AuthData getAuth(String authToken) throws DataAccessException;
    // Delete an authorization so that it is no longer valid.
    void deleteAuth(String authToken) throws DataAccessException;
}
