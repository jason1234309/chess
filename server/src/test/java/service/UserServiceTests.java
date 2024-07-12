package service;

import responseRequest.ErrorResponce;
import responseRequest.ResponseAuth;
import model.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class UserServiceTests {
    AllServices testServiceObj;
    UserData player1Data;
    UserData player2Data;
    UserData player3Data;
    ResponseAuth badRequestAuthError;
    ResponseAuth alreadyTakenAuthError;
    ResponseAuth unauthorizedAuthError;
    ErrorResponce unauthorizedResError;
    @BeforeEach
    public void setUp() {
        testServiceObj = new AllServices();
        testServiceObj.clearDatabases();
        player1Data = new UserData("player1_username", "player1_password", "player1_email");
        player2Data = new UserData("player2_username", "player2_password", "player2_email");
        player3Data = new UserData("player3_username", "player3_password", "player3_email");
        badRequestAuthError = new ResponseAuth(null, null, "Error: bad request");
        alreadyTakenAuthError = new ResponseAuth(null, null, "Error: already taken");
        unauthorizedAuthError = new ResponseAuth(null, null, "Error: unauthorized");
        unauthorizedResError = new ErrorResponce("Error: unauthorized");
    }

    @Test
    @DisplayName("register player 1")
    public void RegisterPlayer1(){
        ResponseAuth registerAuth = testServiceObj.register(player1Data);
        ResponseAuth loginAuth = testServiceObj.login(player1Data);
        Assertions.assertNull(registerAuth.message());
        Assertions.assertNull(loginAuth.message());
        ErrorResponce logoutRegisterRes = testServiceObj.logout(new AuthData(registerAuth.username(), registerAuth.authToken()));
        ErrorResponce logoutLoginRes = testServiceObj.logout(new AuthData(loginAuth.username(), loginAuth.authToken()));
        Assertions.assertNull(logoutRegisterRes.message());
        Assertions.assertNull(logoutLoginRes.message());
    }


    @Test
    @DisplayName("register multiple players")
    public void RegisterMultiplePlayers(){
       ResponseAuth player1RegisteredAuth = testServiceObj.register(player1Data);
       ResponseAuth player2RegisteredAuth = testServiceObj.register(player2Data);
       ResponseAuth player3RegisteredAuth = testServiceObj.register(player3Data);

        Assertions.assertNull(player1RegisteredAuth.message());
        Assertions.assertNull(player2RegisteredAuth.message());
        Assertions.assertNull(player3RegisteredAuth.message());

        ErrorResponce logoutPlayer1 = testServiceObj.logout(new AuthData(player1RegisteredAuth.username(), player1RegisteredAuth.authToken()));
        ErrorResponce logoutPlayer2 = testServiceObj.logout(new AuthData(player2RegisteredAuth.username(), player2RegisteredAuth.authToken()));
        ErrorResponce logoutPlayer3 = testServiceObj.logout(new AuthData(player3RegisteredAuth.username(), player3RegisteredAuth.authToken()));

        Assertions.assertNull(logoutPlayer1.message());
        Assertions.assertNull(logoutPlayer2.message());
        Assertions.assertNull(logoutPlayer3.message());
    }
    @Test
    @DisplayName("register same player twice")
    public void RegisterSamePlayerTwice(){
        ResponseAuth player1RegisteredAuth = testServiceObj.register(player1Data);
        ResponseAuth dubplicateRegistrationAuth = testServiceObj.register(player1Data);
        Assertions.assertNull(player1RegisteredAuth.message());
        Assertions.assertEquals(dubplicateRegistrationAuth, alreadyTakenAuthError);
    }
    @Test
    @DisplayName("bad register request")
    public void RegisterBadRequest(){
        UserData badUser = new UserData("badperson", null, "fake.atcom");
        ResponseAuth badUserAuth = testServiceObj.register(badUser);
        Assertions.assertEquals(badRequestAuthError, badUserAuth);
    }
    @Test
    @DisplayName("reg/logout/login player 1")
    public void LoginPlayer1(){
        ResponseAuth player1RegisteredAuth = testServiceObj.register(player1Data);
        Assertions.assertNull(player1RegisteredAuth.message());
        testServiceObj.logout(new AuthData(player1RegisteredAuth.username(), player1RegisteredAuth.authToken()));
        ResponseAuth player1LoginAuth = testServiceObj.login(player1Data);
        testServiceObj.logout(new AuthData(player1RegisteredAuth.username(), player1RegisteredAuth.authToken()));
        Assertions.assertNull(player1LoginAuth.message());
    }
    @Test
    @DisplayName("login unknown player")
    public void LoginUnknownPlayer(){
        ResponseAuth player1RegisteredAuth = testServiceObj.register(player1Data);
        ResponseAuth player2Login = testServiceObj.login(player2Data);
        Assertions.assertEquals(player2Login, unauthorizedAuthError);
    }
    @Test
    @DisplayName("login wrong player")
    public void LoginWrongPlayer(){
        ResponseAuth player1RegisteredAuth = testServiceObj.register(player1Data);
        ResponseAuth player2RegisteredAuth = testServiceObj.register(player2Data);
        ErrorResponce player1LogoutReg = testServiceObj.logout(new AuthData(player1RegisteredAuth.username(), player1RegisteredAuth.authToken()));
        ErrorResponce player2LogoutReg = testServiceObj.logout(new AuthData(player2RegisteredAuth.username(), player2RegisteredAuth.authToken()));
        ResponseAuth player1Login = testServiceObj.login(player1Data);
        ResponseAuth fakeLogin = new ResponseAuth(player2Data.getUsername(),"notRealAuth", null);
        ErrorResponce fakeLogout = testServiceObj.logout(new AuthData(fakeLogin.username(), fakeLogin.authToken()));
        Assertions.assertEquals(fakeLogout, unauthorizedResError);
    }
    @Test
    @DisplayName("logout player twice")
    public void LogoutPlayerTwice(){
        ResponseAuth player1RegisteredAuth = testServiceObj.register(player1Data);
        ErrorResponce firstLogoutRes = testServiceObj.logout(new AuthData(player1RegisteredAuth.username(), player1RegisteredAuth.authToken()));
        ErrorResponce secondLogoutRes = testServiceObj.logout(new AuthData(player1RegisteredAuth.username(), player1RegisteredAuth.authToken()));
        Assertions.assertNull(firstLogoutRes.message());
        Assertions.assertEquals(secondLogoutRes, new ErrorResponce("Error: unauthorized"));
    }
    @Test
    @DisplayName("login wrong password")
    public void LoginWrongPassword(){
        ResponseAuth player1RegisteredAuth = testServiceObj.register(player1Data);
        UserData mixedUser = new UserData(player1Data.getUsername(), player2Data.getPassword(), player3Data.getEmail());
        ResponseAuth mixedRegisteredAuth = testServiceObj.login(mixedUser);
        Assertions.assertEquals(mixedRegisteredAuth, unauthorizedAuthError);


    }
    @Test
    @DisplayName("bad login request")
    public void LoginBadRequest(){
        ResponseAuth player1RegisteredAuth = testServiceObj.register(player1Data);
        UserData noPasswordUser = new UserData(player1Data.getUsername(), null, null);
        UserData noUsernameUser = new UserData(null, player1Data.getPassword(), null);
        ResponseAuth noPasswordAuth = testServiceObj.login(noPasswordUser);
        ResponseAuth noUsernameAuth = testServiceObj.login(noUsernameUser);
        Assertions.assertEquals(unauthorizedAuthError, noPasswordAuth);
        Assertions.assertEquals(unauthorizedAuthError, noUsernameAuth);
    }
    @Test
    @DisplayName("Login_LogoutMultiplePlayers")
    public void Login_LogoutMultiplePlayers(){
        ResponseAuth player1RegisteredAuth = testServiceObj.register(player1Data);
        ResponseAuth player2RegisteredAuth = testServiceObj.register(player2Data);
        ResponseAuth player3RegisteredAuth = testServiceObj.register(player3Data);
        ResponseAuth player1LoginAuth = testServiceObj.login(player1Data);
        ResponseAuth player2LoginAuth = testServiceObj.login(player2Data);
        ResponseAuth player3LoginAuth = testServiceObj.login(player3Data);
        ErrorResponce player1LogoutAuth = testServiceObj.logout(new AuthData(player1LoginAuth.username(), player1LoginAuth.authToken()));
        ErrorResponce player2LogoutAuth = testServiceObj.logout(new AuthData(player2LoginAuth.username(), player2LoginAuth.authToken()));
        ErrorResponce player3LogoutAuth = testServiceObj.logout(new AuthData(player3LoginAuth.username(), player3LoginAuth.authToken()));
        Assertions.assertNull(player1LoginAuth.message());
        Assertions.assertNull(player2LoginAuth.message());
        Assertions.assertNull(player3LoginAuth.message());
        Assertions.assertNull(player1LogoutAuth.message());
        Assertions.assertNull(player2LogoutAuth.message());
        Assertions.assertNull(player3LogoutAuth.message());
    }
    @Test
    @DisplayName("Login_LogoutPlayer1ThreeTimes")
    public void Login_LogoutPlayer1ThreeTimes(){
        ResponseAuth player1RegisteredAuth = testServiceObj.register(player1Data);
        testServiceObj.logout(new AuthData(player1RegisteredAuth.username(), player1RegisteredAuth.authToken()));
        ResponseAuth player1LoginAuth1 = testServiceObj.login(player1Data);
        ResponseAuth player1LoginAuth2 = testServiceObj.login(player1Data);
        ResponseAuth player1LoginAuth3 = testServiceObj.login(player1Data);
        ErrorResponce playerLogoutAuth1 = testServiceObj.logout(new AuthData(player1LoginAuth1.username(), player1LoginAuth1.authToken()));
        ErrorResponce playerLogoutAuth2 = testServiceObj.logout(new AuthData(player1LoginAuth2.username(), player1LoginAuth2.authToken()));
        ErrorResponce playerLogoutAuth3 = testServiceObj.logout(new AuthData(player1LoginAuth3.username(), player1LoginAuth3.authToken()));
        Assertions.assertNull(player1LoginAuth1.message());
        Assertions.assertNull(player1LoginAuth2.message());
        Assertions.assertNull(player1LoginAuth3.message());
        Assertions.assertNull(playerLogoutAuth1.message());
        Assertions.assertNull(playerLogoutAuth2.message());
        Assertions.assertNull(playerLogoutAuth3.message());
    }

}
