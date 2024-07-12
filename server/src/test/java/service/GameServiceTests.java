package service;

import responseRequest.ErrorResponce;
import responseRequest.GameCreationResponse;
import responseRequest.GameListResponse;
import responseRequest.ResponseAuth;
import chess.ChessGame;
import model.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class GameServiceTests {
    AllServices testServiceObj;
    UserData player1Data;
    UserData player2Data;
    UserData player3Data;
    ErrorResponce unauthorizedError;
    ErrorResponce alreadyTakenError;
    GameCreationResponse unauthorizedCreation;
    @BeforeEach
    public void setUp() {
        testServiceObj = new AllServices();
        testServiceObj.clearDatabases();
        player1Data = new UserData("player1_username", "player1_password", "player1_email");
        player2Data = new UserData("player2_username", "player2_password", "player2_email");
        player3Data = new UserData("player3_username", "player3_password", "player3_email");
        ChessGame testGame = new ChessGame();
        unauthorizedError = new ErrorResponce("Error: unauthorized");
        alreadyTakenError = new ErrorResponce("Error: already taken");
        unauthorizedCreation = new GameCreationResponse(null, "Error: unauthorized");

    }

    @Test
    @DisplayName("create game Twice")
    public void createGameTwice(){
        ResponseAuth registerAuth = testServiceObj.register(player1Data);
        GameCreationResponse game1ID = testServiceObj.createGame(new AuthData(registerAuth.username(), registerAuth.authToken()), "firstGame");
        GameCreationResponse duplicateError = testServiceObj.createGame(new AuthData(registerAuth.username(), registerAuth.authToken()), "firstGame");
        Assertions.assertEquals(game1ID.gameID(), 1);
        Assertions.assertEquals(duplicateError.message(),unauthorizedError.message());

    }
    @Test
    @DisplayName("create game with invalid auth")
    public void createGameInvalidAuth(){
        ResponseAuth registerAuth = testServiceObj.register(player1Data);
        testServiceObj.logout(new AuthData(registerAuth.username(), registerAuth.authToken()));
        GameCreationResponse unauthorizedCreate = testServiceObj.createGame(new AuthData(registerAuth.username(), registerAuth.authToken()), "firstGame");
        Assertions.assertEquals(unauthorizedCreation, unauthorizedCreate);

    }
    @Test
    @DisplayName("create multiple games")
    public void createMultipleGames(){
        ResponseAuth registerAuth1 = testServiceObj.register(player1Data);
        ResponseAuth registerAuth2 = testServiceObj.register(player2Data);
        GameCreationResponse game1ID = testServiceObj.createGame(new AuthData(registerAuth1.username(), registerAuth1.authToken()), "firstGame");
        GameCreationResponse game2ID = testServiceObj.createGame(new AuthData(registerAuth1.username(), registerAuth1.authToken()), "secondGame");
        GameCreationResponse game3ID = testServiceObj.createGame(new AuthData(registerAuth1.username(), registerAuth1.authToken()), "thirdGame");
        Assertions.assertEquals(game1ID.gameID(), 1);
        Assertions.assertEquals(game2ID.gameID(), 2);
        Assertions.assertEquals(game3ID.gameID(), 3);
        GameCreationResponse otherGame1ID = testServiceObj.createGame(new AuthData(registerAuth2.username(), registerAuth2.authToken()), "newGame");
        GameCreationResponse otherGame2ID = testServiceObj.createGame(new AuthData(registerAuth2.username(), registerAuth2.authToken()), "coolGame");
        GameCreationResponse otherGame3ID = testServiceObj.createGame(new AuthData(registerAuth2.username(), registerAuth2.authToken()), "finalGame");
        Assertions.assertEquals(otherGame1ID.gameID(), 4);
        Assertions.assertEquals(otherGame2ID.gameID(), 5);
        Assertions.assertEquals(otherGame3ID.gameID(), 6);
        GameCreationResponse duplicateGame1 = testServiceObj.createGame(new AuthData(registerAuth2.username(), registerAuth2.authToken()), "firstGame");
        GameCreationResponse duplicateGame2 = testServiceObj.createGame(new AuthData(registerAuth1.username(), registerAuth1.authToken()), "finalGame");
        Assertions.assertEquals(duplicateGame1.message(), unauthorizedError.message());
        Assertions.assertEquals(duplicateGame2.message(), unauthorizedError.message());
    }
    @Test
    @DisplayName("list 1 game")
    public void list1Game(){
        ResponseAuth registerAuth = testServiceObj.register(player1Data);
        GameCreationResponse game1ID = testServiceObj.createGame(new AuthData(registerAuth.username(), registerAuth.authToken()), "firstGame");
        GameListResponse gameList = testServiceObj.listGames(new AuthData(registerAuth.username(), registerAuth.authToken()));
        Assertions.assertEquals(gameList.games().size(), 1);
        Assertions.assertNull(gameList.message());
    }
    @Test
    @DisplayName("List multiple games")
    public void listMultipleGames(){
        ResponseAuth registerAuth1 = testServiceObj.register(player1Data);
        ResponseAuth registerAuth2 = testServiceObj.register(player2Data);
        GameCreationResponse game1ID = testServiceObj.createGame(new AuthData(registerAuth1.username(), registerAuth1.authToken()), "firstGame");
        GameCreationResponse game2ID = testServiceObj.createGame(new AuthData(registerAuth1.username(), registerAuth1.authToken()), "secondGame");
        GameCreationResponse game3ID = testServiceObj.createGame(new AuthData(registerAuth1.username(), registerAuth1.authToken()), "thirdGame");
        GameCreationResponse otherGame1ID = testServiceObj.createGame(new AuthData(registerAuth2.username(), registerAuth2.authToken()), "newGame");
        GameCreationResponse otherGame2ID = testServiceObj.createGame(new AuthData(registerAuth2.username(), registerAuth2.authToken()), "coolGame");
        GameCreationResponse otherGame3ID = testServiceObj.createGame(new AuthData(registerAuth2.username(), registerAuth2.authToken()), "finalGame");

        GameListResponse gameList = testServiceObj.listGames(new AuthData(registerAuth1.username(), registerAuth1.authToken()));
        Assertions.assertEquals(gameList.games().size(), 6);
        Assertions.assertNull(gameList.message());
    }
    @Test
    @DisplayName("UnauthorizedListGame")
    public void unauthorizedListGame(){
        ResponseAuth registerAuth = testServiceObj.register(player1Data);
        GameCreationResponse game1ID = testServiceObj.createGame(new AuthData(registerAuth.username(), registerAuth.authToken()), "firstGame");
        AuthData fakeAuth = new AuthData("", "");
        GameListResponse gameList = testServiceObj.listGames(fakeAuth);
        Assertions.assertEquals(gameList.message(), unauthorizedError.message());
    }
    @Test
    @DisplayName("white player joins")
    public void whitePlayerJoins(){
        ResponseAuth registerAuth = testServiceObj.register(player1Data);
        GameCreationResponse game1ID = testServiceObj.createGame(new AuthData(registerAuth.username(), registerAuth.authToken()), "firstGame");
        ErrorResponce whiteJoined = testServiceObj.joinGame(new AuthData(registerAuth.username(), registerAuth.authToken()), ChessGame.TeamColor.WHITE, game1ID.gameID());
        Assertions.assertEquals(new ErrorResponce(null), whiteJoined);


    }
    @Test
    @DisplayName("Black player joins")
    public void blackPlayerJoins(){
        ResponseAuth registerAuth = testServiceObj.register(player1Data);
        GameCreationResponse game1ID = testServiceObj.createGame(new AuthData(registerAuth.username(), registerAuth.authToken()), "firstGame");
        ErrorResponce blackJoined = testServiceObj.joinGame(new AuthData(registerAuth.username(), registerAuth.authToken()), ChessGame.TeamColor.BLACK, game1ID.gameID());
        Assertions.assertEquals(blackJoined, new ErrorResponce(null));

    }
    @Test
    @DisplayName("both players joins")
    public void bothPlayersJoins(){
        ResponseAuth registerAuth = testServiceObj.register(player1Data);
        GameCreationResponse game1ID = testServiceObj.createGame(new AuthData(registerAuth.username(), registerAuth.authToken()), "firstGame");
        ErrorResponce whiteJoined = testServiceObj.joinGame(new AuthData(registerAuth.username(), registerAuth.authToken()), ChessGame.TeamColor.WHITE, game1ID.gameID());
        ErrorResponce blackJoined = testServiceObj.joinGame(new AuthData(registerAuth.username(), registerAuth.authToken()), ChessGame.TeamColor.BLACK, game1ID.gameID());
        Assertions.assertEquals(whiteJoined, new ErrorResponce(null));
        Assertions.assertEquals(blackJoined, new ErrorResponce(null));
    }
    @Test
    @DisplayName("white player joins full game")
    public void whitePlayerJoinsFullGame(){
        ResponseAuth registerAuth = testServiceObj.register(player1Data);
        GameCreationResponse game1ID = testServiceObj.createGame(new AuthData(registerAuth.username(), registerAuth.authToken()), "firstGame");
        ErrorResponce whiteJoined = testServiceObj.joinGame(new AuthData(registerAuth.username(), registerAuth.authToken()), ChessGame.TeamColor.WHITE, game1ID.gameID());
        ErrorResponce whiteJoined2 = testServiceObj.joinGame(new AuthData(registerAuth.username(), registerAuth.authToken()), ChessGame.TeamColor.WHITE, game1ID.gameID());
        Assertions.assertEquals(whiteJoined, new ErrorResponce(null));
        Assertions.assertEquals(whiteJoined2, alreadyTakenError);

    }
}
