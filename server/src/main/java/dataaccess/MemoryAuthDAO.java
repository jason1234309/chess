package dataaccess;

import model.AuthData;

import java.util.ArrayList;
import java.util.Collection;

public class MemoryAuthDAO implements AuthDAO{
    static Collection<AuthData> authTokenDataBase = new ArrayList<>();

    @Override
    public void clearAuthDataBase() {
        authTokenDataBase.clear();
    }

    @Override
    public void createAuth(String Username, String authToken) throws DataAccessException {
        AuthData currentAuthToken = new AuthData(Username,authToken);
        if(authTokenDataBase.contains(currentAuthToken)){
            throw new DataAccessException("auth Token already exists\n");
        }
        authTokenDataBase.add(currentAuthToken);
    }

    @Override
    public AuthData getAuth(String authToken) throws DataAccessException {
        for(AuthData currentAuthToken: authTokenDataBase){
            if(currentAuthToken.getAuthToken().equals(authToken)){
                return currentAuthToken;
            }
        }
        throw new DataAccessException("Error: unauthorized");
    }

    @Override
    public void deleteAuth(String authToken) throws DataAccessException {
        AuthData tempAuthVar = null;
        for(AuthData currentAuthToken : authTokenDataBase){
            if(currentAuthToken.getAuthToken().equals(authToken)){
                tempAuthVar = currentAuthToken;
                break;
            }
        }
        if(!authTokenDataBase.remove(tempAuthVar)){
            throw new DataAccessException("Error: unauthorized");
        }
    }
}
