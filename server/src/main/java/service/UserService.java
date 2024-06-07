package service;

import dataaccess.*;
import model.AuthData;
import model.UserData;

import javax.xml.crypto.Data;
import java.util.Objects;
import java.util.UUID;

public class UserService {
    static UserDAO userDAOObj = new MemoryUserDAO();
    static AuthDAO authDAOObj = new MemoryAuthDAO();

    public AuthData register(UserData user) throws DataAccessException {
            userDAOObj.createUser(user.getUsername(), user.getPassword(), user.getPassword());
            AuthData brandNewAuthToken = new AuthData(UUID.randomUUID().toString(),user.getUsername());
            authDAOObj.createAuth(brandNewAuthToken.getAuthToken(), brandNewAuthToken.getUsername());
            return brandNewAuthToken;
    }
    public AuthData login(UserData user) throws DataAccessException {
            if(userDAOObj.getUser(user.getUsername()) == null){
                throw new DataAccessException("username doesnt exist");
            }
            UserData dataBaseUser = userDAOObj.getUser(user.getUsername());
            if(!dataBaseUser.getPassword().equals(user.getPassword())){
                throw new DataAccessException("Incorrect password");
            }
            AuthData brandNewAuthToken = new AuthData(user.getUsername(), UUID.randomUUID().toString());
            authDAOObj.createAuth(brandNewAuthToken.getAuthToken(), brandNewAuthToken.getUsername());
            return brandNewAuthToken;
    }
    public void logout(AuthData userAuthToken) throws DataAccessException {
            if(authDAOObj.getAuth(userAuthToken.getAuthToken()) == null){
                throw new DataAccessException("auth doesnt exist");
            }
            authDAOObj.deleteAuth(userAuthToken.getAuthToken());
    }
    public void clearUsers(){
        userDAOObj.clearUserDataBase();
        authDAOObj.clearAuthDataBase();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserService that = (UserService) o;
        return Objects.equals(userDAOObj, that.userDAOObj) && Objects.equals(authDAOObj, that.authDAOObj);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userDAOObj, authDAOObj);
    }
}
