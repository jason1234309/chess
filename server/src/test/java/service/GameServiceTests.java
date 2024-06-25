package service;
import ResponseRequest.ErrorResponce;
import ResponseRequest.GameCreationResponse;
import ResponseRequest.GameListResponse;
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
    ErrorResponce unauthorizedError;
    ErrorResponce alreadyTakenError;
    @BeforeEach
    public void setUp() {
        testServiceObj = new GameService();
        testServiceObj.clearDatabases();
        player1Data = new UserData("player1_username", "player1_password", "player1_email");
        player2Data = new UserData("player2_username", "player2_password", "player2_email");
        player3Data = new UserData("player3_username", "player3_password", "player3_email");
        ChessGame testGame = new ChessGame();
        unauthorizedError = new ErrorResponce("Error: unauthorized");
        alreadyTakenError = new ErrorResponce("Error: already taken");

    }

    @Test
    @DisplayName("create game Twice")
    public void CreateGameTwice(){
        ResponseAuth registerAuth = testServiceObj.register(player1Data);
        GameCreationResponse game1ID = testServiceObj.CreateGame(new AuthData(registerAuth.username(), registerAuth.authToken()), "firstGame");
        GameCreationResponse duplicateError = testServiceObj.CreateGame(new AuthData(registerAuth.username(), registerAuth.authToken()), "firstGame");
        Assertions.assertEquals(game1ID.gameID(), 1);
        Assertions.assertEquals(duplicateError.message(),unauthorizedError.message());

    }
    @Test
    @DisplayName("create game with invalid auth")
    public void CreateGameInvalidAuth(){
        ResponseAuth registerAuth = testServiceObj.register(player1Data);
        testServiceObj.logout(new AuthData(registerAuth.username(), registerAuth.authToken()));
        GameCreationResponse unauthorizedCreate = testServiceObj.CreateGame(new AuthData(registerAuth.username(), registerAuth.authToken()), "firstGame");
        Assertions.assertEquals(unauthorizedCreate.message(), unauthorizedError.message());

    }
    @Test
    @DisplayName("create multiple games")
    public void CreateMultipleGames(){
        ResponseAuth registerAuth1 = testServiceObj.register(player1Data);
        ResponseAuth registerAuth2 = testServiceObj.register(player2Data);
        GameCreationResponse game1ID = testServiceObj.CreateGame(new AuthData(registerAuth1.username(), registerAuth1.authToken()), "firstGame");
        GameCreationResponse game2ID = testServiceObj.CreateGame(new AuthData(registerAuth1.username(), registerAuth1.authToken()), "secondGame");
        GameCreationResponse game3ID = testServiceObj.CreateGame(new AuthData(registerAuth1.username(), registerAuth1.authToken()), "thirdGame");
        Assertions.assertEquals(game1ID.gameID(), 1);
        Assertions.assertEquals(game2ID.gameID(), 2);
        Assertions.assertEquals(game3ID.gameID(), 3);
        GameCreationResponse otherGame1ID = testServiceObj.CreateGame(new AuthData(registerAuth2.username(), registerAuth2.authToken()), "newGame");
        GameCreationResponse otherGame2ID = testServiceObj.CreateGame(new AuthData(registerAuth2.username(), registerAuth2.authToken()), "coolGame");
        GameCreationResponse otherGame3ID = testServiceObj.CreateGame(new AuthData(registerAuth2.username(), registerAuth2.authToken()), "finalGame");
        Assertions.assertEquals(otherGame1ID.gameID(), 4);
        Assertions.assertEquals(otherGame2ID.gameID(), 5);
        Assertions.assertEquals(otherGame3ID.gameID(), 6);
        GameCreationResponse duplicateGame1 = testServiceObj.CreateGame(new AuthData(registerAuth2.username(), registerAuth2.authToken()), "firstGame");
        GameCreationResponse duplicateGame2 = testServiceObj.CreateGame(new AuthData(registerAuth1.username(), registerAuth1.authToken()), "finalGame");
        Assertions.assertEquals(duplicateGame1.message(), unauthorizedError.message());
        Assertions.assertEquals(duplicateGame2.message(), unauthorizedError.message());
    }
    @Test
    @DisplayName("list 1 game")
    public void List1Game(){
        ResponseAuth registerAuth = testServiceObj.register(player1Data);
        GameCreationResponse game1ID = testServiceObj.CreateGame(new AuthData(registerAuth.username(), registerAuth.authToken()), "firstGame");
        GameListResponse gameList = testServiceObj.ListGames(new AuthData(registerAuth.username(), registerAuth.authToken()));
        Assertions.assertEquals(gameList.games().size(), 1);
        Assertions.assertNull(gameList.message());
    }
    @Test
    @DisplayName("List multiple games")
    public void ListMultipleGames(){
        ResponseAuth registerAuth1 = testServiceObj.register(player1Data);
        ResponseAuth registerAuth2 = testServiceObj.register(player2Data);
        GameCreationResponse game1ID = testServiceObj.CreateGame(new AuthData(registerAuth1.username(), registerAuth1.authToken()), "firstGame");
        GameCreationResponse game2ID = testServiceObj.CreateGame(new AuthData(registerAuth1.username(), registerAuth1.authToken()), "secondGame");
        GameCreationResponse game3ID = testServiceObj.CreateGame(new AuthData(registerAuth1.username(), registerAuth1.authToken()), "thirdGame");
        GameCreationResponse otherGame1ID = testServiceObj.CreateGame(new AuthData(registerAuth2.username(), registerAuth2.authToken()), "newGame");
        GameCreationResponse otherGame2ID = testServiceObj.CreateGame(new AuthData(registerAuth2.username(), registerAuth2.authToken()), "coolGame");
        GameCreationResponse otherGame3ID = testServiceObj.CreateGame(new AuthData(registerAuth2.username(), registerAuth2.authToken()), "finalGame");

        GameListResponse gameList = testServiceObj.ListGames(new AuthData(registerAuth1.username(), registerAuth1.authToken()));
        Assertions.assertEquals(gameList.games().size(), 6);
        Assertions.assertNull(gameList.message());
    }
    @Test
    @DisplayName("UnauthorizedListGame")
    public void UnauthorizedListGame(){
        ResponseAuth registerAuth = testServiceObj.register(player1Data);
        GameCreationResponse game1ID = testServiceObj.CreateGame(new AuthData(registerAuth.username(), registerAuth.authToken()), "firstGame");
        AuthData fakeAuth = new AuthData("", "");
        GameListResponse gameList = testServiceObj.ListGames(fakeAuth);
        Assertions.assertEquals(gameList.message(), unauthorizedError.message());
    }
    @Test
    @DisplayName("white player joins")
    public void WhitePlayerJoins(){
        ResponseAuth registerAuth = testServiceObj.register(player1Data);
        GameCreationResponse game1ID = testServiceObj.CreateGame(new AuthData(registerAuth.username(), registerAuth.authToken()), "firstGame");
        ErrorResponce whiteJoined = testServiceObj.JoinGame(new AuthData(registerAuth.username(), registerAuth.authToken()), ChessGame.TeamColor.WHITE, game1ID.gameID());
        Assertions.assertEquals(new ErrorResponce(null), whiteJoined);


    }
    @Test
    @DisplayName("Black player joins")
    public void BlackPlayerJoins(){
        ResponseAuth registerAuth = testServiceObj.register(player1Data);
        GameCreationResponse game1ID = testServiceObj.CreateGame(new AuthData(registerAuth.username(), registerAuth.authToken()), "firstGame");
        ErrorResponce blackJoined = testServiceObj.JoinGame(new AuthData(registerAuth.username(), registerAuth.authToken()), ChessGame.TeamColor.BLACK, game1ID.gameID());
        Assertions.assertEquals(blackJoined, new ErrorResponce(null));

    }
    @Test
    @DisplayName("both players joins")
    public void BothPlayersJoins(){
        ResponseAuth registerAuth = testServiceObj.register(player1Data);
        GameCreationResponse game1ID = testServiceObj.CreateGame(new AuthData(registerAuth.username(), registerAuth.authToken()), "firstGame");
        ErrorResponce whiteJoined = testServiceObj.JoinGame(new AuthData(registerAuth.username(), registerAuth.authToken()), ChessGame.TeamColor.WHITE, game1ID.gameID());
        ErrorResponce blackJoined = testServiceObj.JoinGame(new AuthData(registerAuth.username(), registerAuth.authToken()), ChessGame.TeamColor.BLACK, game1ID.gameID());
        Assertions.assertEquals(whiteJoined, new ErrorResponce(null));
        Assertions.assertEquals(blackJoined, new ErrorResponce(null));
    }
    @Test
    @DisplayName("white player joins full game")
    public void WhitePlayerJoinsFullGame(){
        ResponseAuth registerAuth = testServiceObj.register(player1Data);
        GameCreationResponse game1ID = testServiceObj.CreateGame(new AuthData(registerAuth.username(), registerAuth.authToken()), "firstGame");
        ErrorResponce whiteJoined = testServiceObj.JoinGame(new AuthData(registerAuth.username(), registerAuth.authToken()), ChessGame.TeamColor.WHITE, game1ID.gameID());
        ErrorResponce whiteJoined2 = testServiceObj.JoinGame(new AuthData(registerAuth.username(), registerAuth.authToken()), ChessGame.TeamColor.WHITE, game1ID.gameID());
        Assertions.assertEquals(whiteJoined, new ErrorResponce(null));
        Assertions.assertEquals(whiteJoined2, alreadyTakenError);

    }
}
