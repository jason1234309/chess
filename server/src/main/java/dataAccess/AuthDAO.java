package dataAccess;

import model.AuthData;

public interface AuthDAO {
    //A method for clearing all data from the database. This is used during testing.
    void clearUserDataBase();
    // Create a new authorization.
    void createAuth(String Username, AuthData authToken);
    // Retrieve an authorization given an authToken.
    AuthData getAuth(AuthData authToken);
    // Delete an authorization so that it is no longer valid.
    void deleteAuth(AuthData authToken);
}
