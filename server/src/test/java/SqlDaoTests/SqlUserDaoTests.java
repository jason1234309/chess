package SqlDaoTests;
import responserequest.ErrorResponce;
import responserequest.ResponseAuth;
import model.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import service.AllServices;

public class SqlUserDaoTests {
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
    public void registerPlayer1(){
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
    public void registerMultiplePlayers(){
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
}
