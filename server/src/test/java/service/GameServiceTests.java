package service;
import ResponseRequest.ResponseAuth;
import chess.ChessGame;
import dataaccess.*;
import model.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;

public class GameServiceTests {
    GameService testServiceObj;
    UserData player1Data;
    UserData player2Data;
    UserData player3Data;
    @BeforeEach
    public void setUp() {
        testServiceObj = new GameService();
        testServiceObj.clearUsers();
        testServiceObj.clearGames();
        player1Data = new UserData("player1_username", "player1_password", "player1_email");
        player2Data = new UserData("player2_username", "player2_password", "player2_email");
        player3Data = new UserData("player3_username", "player3_password", "player3_email");
        ChessGame testGame = new ChessGame();
    }

    @Test
    @DisplayName("create game")
    public void CreateGame() throws DataAccessException{
        ResponseAuth registerAuth = testServiceObj.register(player1Data);
        String game1ID = testServiceObj.CreateGame(new AuthData(registerAuth.username(), registerAuth.authToken()), "firstGame");
        Assertions.assertEquals(game1ID, "0");
        Assertions.assertThrows(DataAccessException.class,() ->testServiceObj.CreateGame(new AuthData(registerAuth.username(), registerAuth.authToken()), "firstGame"));

    }
    @Test
    @DisplayName("create game with invalid auth")
    public void CreateGameInvalidAuth() throws DataAccessException{
        ResponseAuth registerAuth = testServiceObj.register(player1Data);
        testServiceObj.logout(new AuthData(registerAuth.username(), registerAuth.authToken()));
        Assertions.assertThrows(DataAccessException.class,() ->testServiceObj.CreateGame(new AuthData(registerAuth.username(), registerAuth.authToken()), "firstGame"));

    }
    @Test
    @DisplayName("create multiple games")
    public void CreateMultipleGames() throws DataAccessException{
        ResponseAuth registerAuth1 = testServiceObj.register(player1Data);
        ResponseAuth registerAuth2 = testServiceObj.register(player2Data);
        String game1ID = testServiceObj.CreateGame(new AuthData(registerAuth1.username(), registerAuth1.authToken()), "firstGame");
        String game2ID = testServiceObj.CreateGame(new AuthData(registerAuth1.username(), registerAuth1.authToken()), "secondGame");
        String game3ID = testServiceObj.CreateGame(new AuthData(registerAuth1.username(), registerAuth1.authToken()), "thirdGame");
        Assertions.assertEquals(game1ID, "0");
        Assertions.assertEquals(game2ID, "1");
        Assertions.assertEquals(game3ID, "2");
        String otherGame1ID = testServiceObj.CreateGame(new AuthData(registerAuth2.username(), registerAuth2.authToken()), "newGame");
        String otherGame2ID = testServiceObj.CreateGame(new AuthData(registerAuth2.username(), registerAuth2.authToken()), "coolGame");
        String otherGame3ID = testServiceObj.CreateGame(new AuthData(registerAuth2.username(), registerAuth2.authToken()), "finalGame");
        Assertions.assertEquals(otherGame1ID, "3");
        Assertions.assertEquals(otherGame2ID, "4");
        Assertions.assertEquals(otherGame3ID, "5");
        Assertions.assertThrows(DataAccessException.class,() ->testServiceObj.CreateGame(new AuthData(registerAuth2.username(), registerAuth2.authToken()), "firstGame"));
        Assertions.assertThrows(DataAccessException.class,() ->testServiceObj.CreateGame(new AuthData(registerAuth1.username(), registerAuth1.authToken()), "finalGame"));
    }
    @Test
    @DisplayName("list 1 game")
    public void List1Game() throws DataAccessException{
        ResponseAuth registerAuth = testServiceObj.register(player1Data);
        String game1ID = testServiceObj.CreateGame(new AuthData(registerAuth.username(), registerAuth.authToken()), "firstGame");
        Collection<GameData> gameList = testServiceObj.ListGames(new AuthData(registerAuth.username(), registerAuth.authToken()));
        Assertions.assertEquals(gameList.size(), 1);
    }
    @Test
    @DisplayName("List multiple games")
    public void ListMultipleGames() throws DataAccessException{
        ResponseAuth registerAuth1 = testServiceObj.register(player1Data);
        ResponseAuth registerAuth2 = testServiceObj.register(player2Data);
        String game1ID = testServiceObj.CreateGame(new AuthData(registerAuth1.username(), registerAuth1.authToken()), "firstGame");
        String game2ID = testServiceObj.CreateGame(new AuthData(registerAuth1.username(), registerAuth1.authToken()), "secondGame");
        String game3ID = testServiceObj.CreateGame(new AuthData(registerAuth1.username(), registerAuth1.authToken()), "thirdGame");
        String otherGame1ID = testServiceObj.CreateGame(new AuthData(registerAuth2.username(), registerAuth2.authToken()), "newGame");
        String otherGame2ID = testServiceObj.CreateGame(new AuthData(registerAuth2.username(), registerAuth2.authToken()), "coolGame");
        String otherGame3ID = testServiceObj.CreateGame(new AuthData(registerAuth2.username(), registerAuth2.authToken()), "finalGame");

        Collection<GameData> gameList = testServiceObj.ListGames(new AuthData(registerAuth1.username(), registerAuth1.authToken()));
        Assertions.assertEquals(gameList.size(), 6);
        AuthData registerAuth3 = new AuthData("","");
        Assertions.assertThrows(DataAccessException.class,() ->testServiceObj.ListGames(registerAuth3));
    }
    @Test
    @DisplayName("white player joins")
    public void WhitePlayerJoins() throws DataAccessException{
        ResponseAuth registerAuth = testServiceObj.register(player1Data);
        String game1ID = testServiceObj.CreateGame(new AuthData(registerAuth.username(), registerAuth.authToken()), "firstGame");
        testServiceObj.JoinGame(new AuthData(registerAuth.username(), registerAuth.authToken()), ChessGame.TeamColor.WHITE, game1ID);
        Collection<GameData> currentGames = testServiceObj.ListGames(new AuthData(registerAuth.username(), registerAuth.authToken()));
        // how to check if correct element, new chess game is different object

    }
}
