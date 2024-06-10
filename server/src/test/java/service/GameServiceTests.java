package service;
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
        AuthData registerAuth = testServiceObj.register(player1Data);
        String game1ID = testServiceObj.CreateGame(registerAuth, "firstGame");
        Assertions.assertEquals(game1ID, "0");
        Assertions.assertThrows(DataAccessException.class,() ->testServiceObj.CreateGame(registerAuth, "firstGame"));

    }
    @Test
    @DisplayName("create game with invalid auth")
    public void CreateGameInvalidAuth() throws DataAccessException{
        AuthData registerAuth = testServiceObj.register(player1Data);
        testServiceObj.logout(registerAuth);
        Assertions.assertThrows(DataAccessException.class,() ->testServiceObj.CreateGame(registerAuth, "firstGame"));

    }
    @Test
    @DisplayName("create multiple games")
    public void CreateMultipleGames() throws DataAccessException{
        AuthData registerAuth1 = testServiceObj.register(player1Data);
        AuthData registerAuth2 = testServiceObj.register(player2Data);
        String game1ID = testServiceObj.CreateGame(registerAuth1, "firstGame");
        String game2ID = testServiceObj.CreateGame(registerAuth1, "secondGame");
        String game3ID = testServiceObj.CreateGame(registerAuth1, "thirdGame");
        Assertions.assertEquals(game1ID, "0");
        Assertions.assertEquals(game2ID, "1");
        Assertions.assertEquals(game3ID, "2");
        String otherGame1ID = testServiceObj.CreateGame(registerAuth2, "newGame");
        String otherGame2ID = testServiceObj.CreateGame(registerAuth2, "coolGame");
        String otherGame3ID = testServiceObj.CreateGame(registerAuth2, "finalGame");
        Assertions.assertEquals(otherGame1ID, "3");
        Assertions.assertEquals(otherGame2ID, "4");
        Assertions.assertEquals(otherGame3ID, "5");
        Assertions.assertThrows(DataAccessException.class,() ->testServiceObj.CreateGame(registerAuth2, "firstGame"));
        Assertions.assertThrows(DataAccessException.class,() ->testServiceObj.CreateGame(registerAuth1, "finalGame"));
    }
    @Test
    @DisplayName("list 1 game")
    public void List1Game() throws DataAccessException{
        AuthData registerAuth = testServiceObj.register(player1Data);
        String game1ID = testServiceObj.CreateGame(registerAuth, "firstGame");
        Collection<GameData> gameList = testServiceObj.ListGames(registerAuth);
        Assertions.assertEquals(gameList.size(), 1);
    }
    @Test
    @DisplayName("List multiple games")
    public void ListMultipleGames() throws DataAccessException{
        AuthData registerAuth1 = testServiceObj.register(player1Data);
        AuthData registerAuth2 = testServiceObj.register(player2Data);
        String game1ID = testServiceObj.CreateGame(registerAuth1, "firstGame");
        String game2ID = testServiceObj.CreateGame(registerAuth1, "secondGame");
        String game3ID = testServiceObj.CreateGame(registerAuth1, "thirdGame");
        String otherGame1ID = testServiceObj.CreateGame(registerAuth2, "newGame");
        String otherGame2ID = testServiceObj.CreateGame(registerAuth2, "coolGame");
        String otherGame3ID = testServiceObj.CreateGame(registerAuth2, "finalGame");

        Collection<GameData> gameList = testServiceObj.ListGames(registerAuth1);
        Assertions.assertEquals(gameList.size(), 6);
        AuthData registerAuth3 = new AuthData("","");
        Assertions.assertThrows(DataAccessException.class,() ->testServiceObj.ListGames(registerAuth3));
    }
    @Test
    @DisplayName("white player joins")
    public void WhitePlayerJoins() throws DataAccessException{
        AuthData registerAuth = testServiceObj.register(player1Data);
        String game1ID = testServiceObj.CreateGame(registerAuth, "firstGame");
        testServiceObj.JoinGame(registerAuth, ChessGame.TeamColor.WHITE, game1ID);
        Collection<GameData> currentGames = testServiceObj.ListGames(registerAuth);
        // how to check if correct element, new chess game is different object

    }
}
