package dataaccess;

import chess.ChessGame;
import model.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collection;

public class SqlGameDaoTests {
    SqlGameDaoTests(){
        try{
            gameDAOObj = new SqlGameDAO();
            gameDAOObj.clearGameDataBase();
        }catch(DataAccessException ex){
            System.out.println("Could not create Auth DAO");
        }
    }
    static GameDAO gameDAOObj;
    UserData player1User;
    UserData player2User;
    UserData player3User;
    @BeforeEach
    public void setUp() {
        player1User = new UserData("player1_username",
                "player1 password", "player1_email");
        player2User = new UserData("player2_username",
                "player2 password", "player2_email");
        player3User = new UserData("player3_username",
                "player3 password", "player3_email");
    }

    @Test
    @DisplayName("create game")
    public void createGame() throws DataAccessException{
        int firstGameId = gameDAOObj.createGame("firstGame");
        GameData returnedGameData = gameDAOObj.getGame(firstGameId);
        Assertions.assertEquals(firstGameId, returnedGameData.getGameID());
        Assertions.assertEquals("firstGame", returnedGameData.getGameName());
        Assertions.assertNull(returnedGameData.getBlackUsername());
        Assertions.assertNull(returnedGameData.getWhiteUsername());
        Assertions.assertEquals(new ChessGame(), returnedGameData.getChessGame());
    }
    @Test
    @DisplayName("create 2 games")
    public void create2Games() throws DataAccessException{
        int firstGameId = gameDAOObj.createGame("firstGame");
        GameData returnedGameData1 = gameDAOObj.getGame(firstGameId);
        Assertions.assertEquals(firstGameId, returnedGameData1.getGameID());
        Assertions.assertEquals("firstGame", returnedGameData1.getGameName());
        Assertions.assertNull(returnedGameData1.getBlackUsername());
        Assertions.assertNull(returnedGameData1.getWhiteUsername());
        Assertions.assertEquals(new ChessGame(), returnedGameData1.getChessGame());

        int secondGameId = gameDAOObj.createGame("secondGame");
        GameData returnedGameData2 = gameDAOObj.getGame(secondGameId);
        Assertions.assertEquals(secondGameId, returnedGameData2.getGameID());
        Assertions.assertEquals("secondGame", returnedGameData2.getGameName());
        Assertions.assertNull(returnedGameData2.getBlackUsername());
        Assertions.assertNull(returnedGameData2.getWhiteUsername());
        Assertions.assertEquals(new ChessGame(), returnedGameData2.getChessGame());

    }
    @Test
    @DisplayName("create duplicate game")
    public void createDuplicateGame() throws DataAccessException{
        int firstGameId = gameDAOObj.createGame("firstGame");
        GameData returnedGameData1 = gameDAOObj.getGame(firstGameId);
        Assertions.assertEquals(firstGameId, returnedGameData1.getGameID());
        Assertions.assertEquals("firstGame", returnedGameData1.getGameName());
        Assertions.assertNull(returnedGameData1.getBlackUsername());
        Assertions.assertNull(returnedGameData1.getWhiteUsername());
        Assertions.assertEquals(new ChessGame(), returnedGameData1.getChessGame());
        Assertions.assertThrows(DataAccessException.class, ()->
                gameDAOObj.createGame("firstGame"));
    }

    @Test
    @DisplayName("get invalid game")
    public void getInvalidGame() throws DataAccessException{
        int firstGameId = gameDAOObj.createGame("firstGame");
        GameData returnedGameData1 = gameDAOObj.getGame(200);
        Assertions.assertNull(returnedGameData1);
    }
    @Test
    @DisplayName("list games")
    public void listGames() throws DataAccessException{
        int gameListSize = 2;
        int firstGameId = gameDAOObj.createGame("firstGame");
        int secondGameId = gameDAOObj.createGame("secondGame");
        Collection<GameData> returnedGameList = gameDAOObj.listGames();
        Assertions.assertEquals(gameListSize, returnedGameList.size());
    }
    @Test
    @DisplayName("update game white username")
    public void updateGameWhiteUsername() throws DataAccessException{
        int firstGameId = gameDAOObj.createGame("firstGame");
        GameData returnedGameDataFirst = gameDAOObj.getGame(firstGameId);
        GameData updatedGame1 = new GameData(returnedGameDataFirst.getGameID(),
                returnedGameDataFirst.getGameName(),
                player1User.getUsername(), null, returnedGameDataFirst.getChessGame());
        gameDAOObj.updateGame(updatedGame1.getGameID(), updatedGame1);
        GameData returnedGameDataSecond = gameDAOObj.getGame(firstGameId);
        Assertions.assertEquals(firstGameId, returnedGameDataSecond.getGameID());
        Assertions.assertEquals("firstGame", returnedGameDataSecond.getGameName());
        Assertions.assertEquals(player1User.getUsername(), returnedGameDataSecond.getWhiteUsername());
        Assertions.assertNull(returnedGameDataSecond.getBlackUsername());
        Assertions.assertEquals(new ChessGame(), returnedGameDataSecond.getChessGame());
    }
    @Test
    @DisplayName("update game with both player usernames")
    public void updateGameBothUsername() throws DataAccessException{
        int firstGameId = gameDAOObj.createGame("firstGame");
        GameData returnedGameDataFirst = gameDAOObj.getGame(firstGameId);
        GameData updatedGamePlayer1 = new GameData(returnedGameDataFirst.getGameID(),
                returnedGameDataFirst.getGameName(), player1User.getUsername(),
                null, returnedGameDataFirst.getChessGame());
        gameDAOObj.updateGame(updatedGamePlayer1.getGameID(), updatedGamePlayer1);
        GameData returnedGameDataSecond = gameDAOObj.getGame(firstGameId);
        GameData updatedGamePlayer2 = new GameData(returnedGameDataSecond.getGameID(),
                returnedGameDataSecond.getGameName(),
                returnedGameDataSecond.getWhiteUsername(), player2User.getUsername(),
                returnedGameDataSecond.getChessGame());
        gameDAOObj.updateGame(updatedGamePlayer2.getGameID(), updatedGamePlayer2);
        GameData returnedGameDataThird = gameDAOObj.getGame(firstGameId);

        Assertions.assertEquals(firstGameId, returnedGameDataThird.getGameID());
        Assertions.assertEquals("firstGame", returnedGameDataThird.getGameName());
        Assertions.assertEquals(player1User.getUsername(), returnedGameDataThird.getWhiteUsername());
        Assertions.assertEquals(player2User.getUsername(), returnedGameDataThird.getBlackUsername());
        Assertions.assertEquals(new ChessGame(), returnedGameDataThird.getChessGame());
    }
    @Test
    @DisplayName("update game white username twice")
    public void updateGameWhiteUsernameTwice() throws DataAccessException{
        int firstGameId = gameDAOObj.createGame("firstGame");
        GameData returnedGameDataFirst = gameDAOObj.getGame(firstGameId);
        GameData updatedGame1 = new GameData(returnedGameDataFirst.getGameID(),
                returnedGameDataFirst.getGameName(),
                player1User.getUsername(), null, returnedGameDataFirst.getChessGame());
        gameDAOObj.updateGame(updatedGame1.getGameID(), updatedGame1);
        GameData returnedGameDataSecond = gameDAOObj.getGame(updatedGame1.getGameID());
        Assertions.assertEquals(player1User.getUsername(), returnedGameDataSecond.getWhiteUsername());

        GameData updatedGame2 = new GameData(returnedGameDataFirst.getGameID(),
                returnedGameDataFirst.getGameName(),
                player2User.getUsername(), null, returnedGameDataFirst.getChessGame());
        gameDAOObj.updateGame(updatedGame2.getGameID(), updatedGame2);
        GameData returnedGameDataThird = gameDAOObj.getGame(updatedGame2.getGameID());
        Assertions.assertEquals(player2User.getUsername(), returnedGameDataThird.getWhiteUsername());

    }
    @Test
    @DisplayName("update invalid game")
    public void updateInvalidGame() throws DataAccessException{
        int firstGameId = gameDAOObj.createGame("firstGame");
        GameData returnedGameDataFirst = gameDAOObj.getGame(firstGameId);
        GameData updatedGame1 = new GameData(200, returnedGameDataFirst.getGameName(),
                player1User.getUsername(), null, returnedGameDataFirst.getChessGame());
        Assertions.assertThrows(DataAccessException.class, () ->
                gameDAOObj.updateGame(updatedGame1.getGameID(), updatedGame1));

    }
}
