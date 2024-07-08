package SqlDAOTests;
import ResponseRequest.ErrorResponce;
import ResponseRequest.ResponseAuth;
import dataaccess.DataAccessException;
import dataaccess.MemoryUserDAO;
import dataaccess.SqlUserDAO;
import dataaccess.UserDAO;
import model.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import service.GameService;

import java.sql.SQLException;

public class sqlUserDAOTests {
     try{
        try {
            UserDAO userDAOObj = new SqlUserDAO();
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
    }catch(DataAccessException ex) {
        int placeholder = 1;
    }

    @BeforeEach
    public void setUp() {
        testServiceObj = new GameService();
        testServiceObj.clearDatabases();
        player1Data = new UserData("player1_username", "player1_password", "player1_email");
        player2Data = new UserData("player2_username", "player2_password", "player2_email");
        player3Data = new UserData("player3_username", "player3_password", "player3_email");
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
}