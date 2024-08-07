package dataaccess;

import model.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SqlAuthDaoTests {
    SqlAuthDaoTests(){
        try{
            authDAOObj = new SqlAuthDAO();
            authDAOObj.clearAuthDataBase();
        }catch(DataAccessException ex){
            System.out.println("Could not create Auth DAO");
        }
    }
    static AuthDAO authDAOObj;
    AuthData player1Auth;
    AuthData player2Auth;
    @BeforeEach
    public void setUp() {
        player1Auth = new AuthData("player1_username", "AuthToken1");
        player2Auth = new AuthData("player2_username", "Authtoken2");
    }

    @Test
    @DisplayName("create player 1 auth")
    public void createPlayer1Auth() throws DataAccessException{
        authDAOObj.createAuth(player1Auth.getUsername(), player1Auth.getAuthToken());
        AuthData returnedAuthData = authDAOObj.getAuth(player1Auth.getAuthToken());
        Assertions.assertEquals(player1Auth, returnedAuthData);
    }
    @Test
    @DisplayName("create 2 auth")
    public void create2Auth() throws DataAccessException{
        authDAOObj.createAuth(player1Auth.getUsername(), player1Auth.getAuthToken());
        AuthData returnedAuthData1 = authDAOObj.getAuth(player1Auth.getAuthToken());
        Assertions.assertEquals(player1Auth, returnedAuthData1);
        authDAOObj.createAuth(player2Auth.getUsername(), player2Auth.getAuthToken());
        AuthData returnedAuthData2 = authDAOObj.getAuth(player2Auth.getAuthToken());
        Assertions.assertEquals(player2Auth, returnedAuthData2);
    }
    //
    @Test
    @DisplayName("delete auth")
    public void deleteAuth() throws DataAccessException{
        authDAOObj.createAuth(player1Auth.getUsername(), player1Auth.getAuthToken());
        AuthData returnedAuthData1 = authDAOObj.getAuth(player1Auth.getAuthToken());
        Assertions.assertEquals(player1Auth, returnedAuthData1);
        authDAOObj.deleteAuth(returnedAuthData1.getAuthToken());
        Assertions.assertThrows(DataAccessException.class, ()->
                authDAOObj.deleteAuth(returnedAuthData1.getAuthToken()));
    }
    @Test
    @DisplayName("get invalid auth")
    public void getInvalidAuth() throws DataAccessException{
        authDAOObj.createAuth(player1Auth.getUsername(), player1Auth.getAuthToken());
        AuthData returnedAuthData1 = authDAOObj.getAuth("invalid auth");
        Assertions.assertNull(returnedAuthData1);

    }
    @Test
    @DisplayName("delete invalid auth")
    public void deleteInvalidAuth() throws DataAccessException{
        authDAOObj.createAuth(player1Auth.getUsername(), player1Auth.getAuthToken());
        AuthData returnedAuthData1 = authDAOObj.getAuth(player1Auth.getAuthToken());
        Assertions.assertEquals(player1Auth, returnedAuthData1);
        Assertions.assertThrows(DataAccessException.class, ()->
                authDAOObj.deleteAuth("invalid auth"));
    }
}
