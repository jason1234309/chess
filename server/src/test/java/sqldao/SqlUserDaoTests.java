package sqldao;

import dataaccess.UserDAO;
import dataaccess.DataAccessException;
import dataaccess.SqlUserDAO;
import model.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SqlUserDaoTests {
    public SqlUserDaoTests(){
        try{
            userDAOObj = new SqlUserDAO();
            userDAOObj.clearUserDataBase();
        }catch(DataAccessException ex){
            System.out.println("Could not create Auth DAO");
        }
    }
    static UserDAO userDAOObj;
    UserData player1User;
    UserData player2User;
    @BeforeEach
    public void setUp() {
        player1User = new UserData("player1_username",
                "player1 password", "player1_email");
        player2User = new UserData("player2_username",
                "player2 password", "player2_email");
    }

    @Test
    @DisplayName("create player 1 user")
    public void createPlayer1User() throws DataAccessException{
        userDAOObj.createUser(player1User.getUsername(), player1User.getPassword(),
                player1User.getEmail());
        UserData returnedUserData = userDAOObj.getUser(player1User.getUsername());
        Assertions.assertEquals(player1User, returnedUserData);
    }
    @Test
    @DisplayName("create 2 users")
    public void create2Users() throws DataAccessException{
        userDAOObj.createUser(player1User.getUsername(), player1User.getPassword(),
                player1User.getEmail());
        UserData returnedUserData1 = userDAOObj.getUser(player1User.getUsername());
        Assertions.assertEquals(player1User, returnedUserData1);
        userDAOObj.createUser(player2User.getUsername(), player2User.getPassword(),
                player2User.getEmail());
        UserData returnedUserData2 = userDAOObj.getUser(player2User.getUsername());
        Assertions.assertEquals(player2User, returnedUserData2);
    }
    @Test
    @DisplayName("create duplicate user")
    public void createDuplicateUser() throws DataAccessException{
        userDAOObj.createUser(player1User.getUsername(), player1User.getPassword(),
                player1User.getEmail());
        UserData returnedUserData1 = userDAOObj.getUser(player1User.getUsername());
        Assertions.assertEquals(player1User, returnedUserData1);
        Assertions.assertThrows(DataAccessException.class, ()->
                userDAOObj.createUser(player1User.getUsername(), player1User.getPassword(),
                        player1User.getEmail()));
        Assertions.assertThrows(DataAccessException.class, ()->
                userDAOObj.createUser(player1User.getUsername(), player2User.getPassword(),
                        player2User.getEmail()));

    }

    @Test
    @DisplayName("get invalid user")
    public void getInvalidUser() throws DataAccessException{
        userDAOObj.createUser(player1User.getUsername(), player1User.getPassword(),
                player1User.getEmail());
        UserData returnedUserData1 = userDAOObj.getUser(player2User.getUsername());
        Assertions.assertNull(returnedUserData1);

    }
}
