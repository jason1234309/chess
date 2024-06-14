package service;

import ResponseRequest.ResponseAuth;
import dataaccess.*;
import model.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.xml.crypto.Data;

public class UserServiceTests {
    GameService testServiceObj;
    UserData player1Data;
    UserData player2Data;
    UserData player3Data;
    @BeforeEach
    public void setUp() {
        testServiceObj = new GameService();
        testServiceObj.clearUsers();
        player1Data = new UserData("player1_username", "player1_password", "player1_email");
        player2Data = new UserData("player2_username", "player2_password", "player2_email");
        player3Data = new UserData("player3_username", "player3_password", "player3_email");
    }

    @Test
    @DisplayName("register player 1")
    public void RegisterPlayer1() throws DataAccessException{
        ResponseAuth registerAuth = testServiceObj.register(player1Data);
        AuthData loginAuth = testServiceObj.login(player1Data);
        testServiceObj.logout(new AuthData(registerAuth.username(), registerAuth.authToken()));
        testServiceObj.logout(loginAuth);

    }


    @Test
    @DisplayName("register multiple players")
    public void RegisterMultiplePlayers()throws DataAccessException{
       ResponseAuth player1RegisteredAuth = testServiceObj.register(player1Data);
       ResponseAuth player2RegisteredAuth = testServiceObj.register(player2Data);
       ResponseAuth player3RegisteredAuth = testServiceObj.register(player3Data);

       testServiceObj.logout(new AuthData(player1RegisteredAuth.username(), player1RegisteredAuth.authToken()));
       testServiceObj.logout(new AuthData(player2RegisteredAuth.username(), player1RegisteredAuth.authToken()));
       testServiceObj.logout(new AuthData(player3RegisteredAuth.username(), player1RegisteredAuth.authToken()));
    }
    @Test
    @DisplayName("register same player twice")
    public void RegisterSamePlayerTwice()throws DataAccessException{
        ResponseAuth player1RegisteredAuth = testServiceObj.register(player1Data);
        Assertions.assertThrows(DataAccessException.class,() ->testServiceObj.register(player1Data));
    }
    @Test
    @DisplayName("reg/logout/login player 1")
    public void LoginPlayer1()throws DataAccessException{
        ResponseAuth player1RegisteredAuth = testServiceObj.register(player1Data);
        testServiceObj.logout(new AuthData(player1RegisteredAuth.username(), player1RegisteredAuth.authToken()));
        AuthData player1LoginAuth = testServiceObj.login(player1Data);
        testServiceObj.logout(player1LoginAuth);
    }
    @Test
    @DisplayName("login wrong player")
    public void LoginWrongPlayer()throws DataAccessException{
        ResponseAuth player1RegisteredAuth = testServiceObj.register(player1Data);
        testServiceObj.logout(new AuthData(player1RegisteredAuth.username(), player1RegisteredAuth.authToken()));
        Assertions.assertThrows(DataAccessException.class, ()-> testServiceObj.login(player2Data));
    }
    @Test
    @DisplayName("login wrong password")
    public void LoginWrongPassword()throws DataAccessException{
        ResponseAuth player1RegisteredAuth = testServiceObj.register(player1Data);
        testServiceObj.logout(new AuthData(player1RegisteredAuth.username(), player1RegisteredAuth.authToken()));
        UserData mixedUser = new UserData(player1Data.getUsername(), player2Data.getPassword(), player3Data.getEmail());
        Assertions.assertThrows(DataAccessException.class, ()-> testServiceObj.login(mixedUser));
    }
    @Test
    @DisplayName("reg/logout/login multiple players")
    public void LoginMulitplePlayers()throws DataAccessException{
        ResponseAuth player1RegisteredAuth = testServiceObj.register(player1Data);
        ResponseAuth player2RegisteredAuth = testServiceObj.register(player2Data);
        ResponseAuth player3RegisteredAuth = testServiceObj.register(player3Data);
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
        ResponseAuth player1RegisteredAuth = testServiceObj.register(player1Data);
        testServiceObj.logout(new AuthData(player1RegisteredAuth.username(), player1RegisteredAuth.authToken()));
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
        ResponseAuth player1RegisteredAuth = testServiceObj.register(player1Data);
        testServiceObj.logout(new AuthData(player1RegisteredAuth.username(), player1RegisteredAuth.authToken()));
        Assertions.assertThrows(DataAccessException.class, () -> testServiceObj.logout(new AuthData(player1RegisteredAuth.username(), player1RegisteredAuth.authToken())));
        AuthData player1LoginAuth = testServiceObj.login(player1Data);
        testServiceObj.logout(player1LoginAuth);
        Assertions.assertThrows(DataAccessException.class, () -> testServiceObj.logout(player1LoginAuth));
    }

}
