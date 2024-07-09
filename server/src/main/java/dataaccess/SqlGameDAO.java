package dataaccess;

import chess.ChessGame;
import com.google.gson.GsonBuilder;
import com.google.gson.Gson;
import model.AuthData;
import model.GameData;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class SqlGameDAO implements GameDAO{
    public SqlGameDAO() throws DataAccessException{
        DatabaseManager.createDatabase();
        final String[] createTableStatements = {
                """
            CREATE TABLE IF NOT EXISTS  game (
            `id` int not Null AUTO_INCREMENT,
            `gameName` varchar(256) NOT NULL,
            `whiteUserName` varchar(256) NOT NULL,
            `blackUserName` varchar(256) NOT NULL,                
            PRIMARY KEY (`id`)
            )
            """
        };
        try (var conn = DatabaseManager.getConnection()) {
            for (var statement : createTableStatements) {
                try (var preparedStatement = conn.prepareStatement(statement)) {
                    preparedStatement.executeUpdate();
                }
            }
        } catch (SQLException ex) {
            throw new DataAccessException(String.format("Unable to configure database: %s", ex.getMessage()));
        }
    }
    @Override
    public void clearGameDataBase() throws DataAccessException{
        var statement = "DELETE from game";
        try(var conn = DatabaseManager.getConnection()){
            try(var ps = conn.prepareStatement(statement)){
                ps.executeUpdate();
            }
        }catch(DataAccessException | SQLException ex){
            throw new DataAccessException(String.format("Unable to clear data: %s", ex.getMessage()));
        }
    }

    public String gameToJson(ChessGame currentGame){
//        Gson gson = new GsonBuilder().enableComplexMapKeySerialization().registerTypeAdapter(ChessGame.class,new typeAdapter()).create();
//        String json = gson.toJson(currentGame);

        return "placeholder";
    }

    @Override
    public int createGame(String gameName) throws DataAccessException {
        try (var conn = DatabaseManager.getConnection()) {
            String queryStatement = "SELECT * FROM game WHERE gameName=?";
            try (var ps = conn.prepareStatement(queryStatement)) {
                ps.setString(1,gameName);
                try (var rs = ps.executeQuery()) {
                    if (rs.next()) {
                        throw new DataAccessException("Error: unauthorized");
                    }
                }
            }
        } catch (DataAccessException | SQLException ex) {
            throw new DataAccessException(String.format("Unable to read data: %s", ex.getMessage()));
        }
        ChessGame chessGameObj = new ChessGame();
        var insertStatement = "INSERT INTO game (gameName, whiteUserName, BlackUserName, chessGameJson) VALUES (?,?,?,?)";
        try(var conn = DatabaseManager.getConnection()){
            try(var ps = conn.prepareStatement(insertStatement)){
                ps.setString(1, gameName);
                ps.setString(2, null);
                ps.setString(3, null);
                ps.setString(4, gameToJson(chessGameObj));  // not implemented
                ps.executeUpdate();
            }
        }catch(DataAccessException | SQLException ex){
            throw new DataAccessException(String.format("Unable to create game: %s", ex.getMessage()));
        }
            // query for newly make game id
        return -1;
    }

    @Override
    public GameData getGame(Integer gameID) throws DataAccessException {
        try (var conn = DatabaseManager.getConnection()) {
            String statement = "SELECT * FROM game WHERE id=?";
            try (var ps = conn.prepareStatement(statement)) {
                ps.setInt(1,gameID);
                try (var rs = ps.executeQuery()) {
                    if (rs.next()) {
                        int returnedGameId = rs.getInt("id");
                        String returnedGameName = rs.getString("gameName");
                        String returnedWhiteUserName = rs.getString("whiteUserName");
                        String returnedBlackUserName = rs.getString("blackUserName");
                        ChessGame returnedChessGame = new ChessGame(); // not implmented
                        return new GameData(returnedGameId, returnedGameName,
                                returnedWhiteUserName, returnedBlackUserName, returnedChessGame);
                    }
                }
            }
        } catch (DataAccessException | SQLException ex) {
            throw new DataAccessException(String.format("Unable to read data: %s", ex.getMessage()));
        }
        return null;
    }

    @Override
    public Collection<GameData> listGames() throws DataAccessException{
        Collection<GameData> gameList = new ArrayList<>();
        try (var conn = DatabaseManager.getConnection()) {
            String statement = "SELECT * FROM game";
            try (var ps = conn.prepareStatement(statement)) {
                try (var rs = ps.executeQuery()) {
                    while (rs.next()) {
                        int returnedGameId = rs.getInt("id");
                        String returnedGameName = rs.getString("gameName");
                        String returnedWhiteUserName = rs.getString("whiteUserName");
                        String returnedBlackUserName = rs.getString("blackUserName");
                        ChessGame returnedChessGame = new ChessGame(); // not implmented
                        gameList.add(new GameData(returnedGameId, returnedGameName,
                                returnedWhiteUserName, returnedBlackUserName, returnedChessGame));
                    }
                }
            }
        } catch (DataAccessException | SQLException ex) {
            throw new DataAccessException(String.format("Unable to read data: %s", ex.getMessage()));
        }
        return gameList;
    }

    @Override
    public void updateGame(Integer gameID, GameData updatedGameObject) throws DataAccessException {

    }
}
