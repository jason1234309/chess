package dataaccess;

import model.UserData;

public interface UserDAO {
    //A method for clearing all data from the database. This is used during testing.
    void clearUserDataBase() throws DataAccessException;
    //Create a new user.
    void createUser(String username, String password, String email) throws DataAccessException;
    //Retrieve a user with the given username.
    UserData getUser(String username) throws DataAccessException;
}
