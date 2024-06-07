package service;

import service.UserService;
import dataaccess.*;
import model.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.xml.crypto.Data;

public class UserServiceTests {
    UserService testServiceObj;
    UserData player1Data;
    UserData player2Data;
    UserData player3Data;
    @BeforeEach
    public void setUp() {
        testServiceObj = new UserService();
        testServiceObj.clearUsers();
        player1Data = new UserData("player1_username", "player1_password", "player1_email");
        player2Data = new UserData("player2_username", "player2_password", "player2_email");
        player3Data = new UserData("player3_username", "player3_password", "player3_email");
    }

    @Test
    @DisplayName("register player 1")
    public void RegisterPlayer1() throws DataAccessException{
        AuthData registerAuth = testServiceObj.register(player1Data);
        AuthData loginAuth = testServiceObj.login(player1Data);
        testServiceObj.logout(registerAuth);
        testServiceObj.logout(loginAuth);

    }


    @Test
    @DisplayName("register multiple players")
    public void RegisterMultiplePlayers()throws DataAccessException{
       AuthData player1RegisteredAuth = testServiceObj.register(player1Data);
       AuthData player2RegisteredAuth = testServiceObj.register(player2Data);
       AuthData player3RegisteredAuth = testServiceObj.register(player3Data);

       testServiceObj.logout(player1RegisteredAuth);
       testServiceObj.logout(player2RegisteredAuth);
       testServiceObj.logout(player3RegisteredAuth);
    }
    @Test
    @DisplayName("register same player twice")
    public void RegisterSamePlayerTwice()throws DataAccessException{
        AuthData player1RegisteredAuth = testServiceObj.register(player1Data);
        Assertions.assertThrows(DataAccessException.class,() ->testServiceObj.register(player1Data));
    }
    @Test
    @DisplayName("reg/logout/login player 1")
    public void LoginPlayer1()throws DataAccessException{
        AuthData player1RegisteredAuth = testServiceObj.register(player1Data);
        testServiceObj.logout(player1RegisteredAuth);
        AuthData player1LoginAuth = testServiceObj.login(player1Data);
        testServiceObj.logout(player1LoginAuth);
    }
    @Test
    @DisplayName("login wrong player")
    public void LoginWrongPlayer()throws DataAccessException{
        AuthData player1RegisteredAuth = testServiceObj.register(player1Data);
        testServiceObj.logout(player1RegisteredAuth);
        Assertions.assertThrows(DataAccessException.class, ()-> testServiceObj.login(player2Data));
    }
    @Test
    @DisplayName("login wrong password")
    public void LoginWrongPassword()throws DataAccessException{
        AuthData player1RegisteredAuth = testServiceObj.register(player1Data);
        testServiceObj.logout(player1RegisteredAuth);
        UserData mixedUser = new UserData(player1Data.getUsername(), player2Data.getPassword(), player3Data.getEmail());
        Assertions.assertThrows(DataAccessException.class, ()-> testServiceObj.login(mixedUser));
    }
    @Test
    @DisplayName("reg/logout/login multiple players")
    public void LoginMulitplePlayers()throws DataAccessException{
        AuthData player1RegisteredAuth = testServiceObj.register(player1Data);
        AuthData player2RegisteredAuth = testServiceObj.register(player2Data);
        AuthData player3RegisteredAuth = testServiceObj.register(player3Data);
        AuthData player1LoginAuth = testServiceObj.login(player1Data);
        AuthData player2LoginAuth = testServiceObj.login(player2Data);
        AuthData player3LoginAuth = testServiceObj.login(player3Data);
        testServiceObj.logout(player1LoginAuth);
        testServiceObj.logout(player2LoginAuth);
        testServiceObj.logout(player3LoginAuth);
    }
    @Test
    @DisplayName("reg/logout/login player 1")
    public void LoginPlayer1ThreeTimes()throws DataAccessException{
        AuthData player1RegisteredAuth = testServiceObj.register(player1Data);
        testServiceObj.logout(player1RegisteredAuth);
        AuthData player1LoginAuth1 = testServiceObj.login(player1Data);
        AuthData player1LoginAuth2 = testServiceObj.login(player1Data);
        AuthData player1LoginAuth3 = testServiceObj.login(player1Data);
        testServiceObj.logout(player1LoginAuth3);
        testServiceObj.logout(player1LoginAuth2);
        testServiceObj.logout(player1LoginAuth1);
    }
    @Test
    @DisplayName("logout player 1 twice")
    public void LogoutPlayer1Twice()throws DataAccessException{
        AuthData player1RegisteredAuth = testServiceObj.register(player1Data);
        testServiceObj.logout(player1RegisteredAuth);
        Assertions.assertThrows(DataAccessException.class, () -> testServiceObj.logout(player1RegisteredAuth));
        AuthData player1LoginAuth = testServiceObj.login(player1Data);
        testServiceObj.logout(player1LoginAuth);
        Assertions.assertThrows(DataAccessException.class, () -> testServiceObj.logout(player1LoginAuth));
    }

}
