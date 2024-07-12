package dataaccess;

import chess.ChessGame;
import com.google.gson.GsonBuilder;
import com.google.gson.Gson;
import model.GameData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

public class SqlGameDAO implements GameDAO{
    public SqlGameDAO() throws DataAccessException{
        DatabaseManager.createDatabase();
        final String createTableStatement = "CREATE TABLE IF NOT EXISTS  game (" +
                "`id` int not Null AUTO_INCREMENT," +
                "`gameName` varchar(256) NOT NULL," +
                "`whiteUserName` varchar(256) NULL," +
                "`blackUserName` varchar(256) NULL," +
                "`chessGame` varchar(4096) not NULL, " +
                "PRIMARY KEY (`id`)) ";
        try (var conn = DatabaseManager.getConnection()) {
                try (var preparedStatement = conn.prepareStatement(createTableStatement)) {
                    preparedStatement.executeUpdate();
                }
        } catch (SQLException ex) {
            throw new DataAccessException(String.format("Unable to configure database: %s", ex.getMessage()));
        }
    }
    @Override
    public void clearGameDataBase() throws DataAccessException{
        var statement = "DELETE from game";
        try(var conn = DatabaseManager.getConnection()){
            try(var preparedStatement = conn.prepareStatement(statement)){
                preparedStatement.executeUpdate();
            }
        }catch(DataAccessException | SQLException ex){
            throw new DataAccessException(String.format("Unable to clear data: %s", ex.getMessage()));
        }
        var resetAutoVarsStatement = "ALTER TABLE game AUTO_INCREMENT = 1";
        try(var conn = DatabaseManager.getConnection()){
            try(var preparedStatement = conn.prepareStatement(resetAutoVarsStatement)){
                preparedStatement.executeUpdate();
            }
        }catch(DataAccessException | SQLException ex){
            throw new DataAccessException(String.format("Unable to resent auto increment vars data: %s", ex.getMessage()));
        }
    }

    public String gameToJson(ChessGame currentGame){   // may not work
        Gson gson = new GsonBuilder().enableComplexMapKeySerialization().create();
        return gson.toJson(currentGame);
    }
    public ChessGame jsonToGame(String json){    // may not work
        Gson serializer = new Gson();
        return serializer.fromJson(json, ChessGame.class);
    }

    @Override
    public int createGame(String gameName) throws DataAccessException {
        try (var conn = DatabaseManager.getConnection()) {
            String queryStatement = "SELECT * FROM game WHERE gameName=?";
            try (var preparedStatement = conn.prepareStatement(queryStatement)) {
                preparedStatement.setString(1,gameName);
                try (var resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        throw new DataAccessException("Error: unauthorized");
                    }
                }
            }
        } catch (DataAccessException | SQLException ex) {
            if(ex.getMessage().equals("Error: unauthorized")){
                throw new DataAccessException("Error: unauthorized");
            }else{
                throw new DataAccessException(String.format("Unable to read data: %s", ex.getMessage()));
            }
        }
        ChessGame chessGameObj = new ChessGame();
        var insertStatement = "INSERT INTO game (gameName, whiteUserName, BlackUserName, chessGame) VALUES (?,?,?,?)";
        try(var conn = DatabaseManager.getConnection()){
            try(var preparedStatement = conn.prepareStatement(insertStatement, Statement.RETURN_GENERATED_KEYS)){
                preparedStatement.setString(1, gameName);
                preparedStatement.setString(2, null);
                preparedStatement.setString(3, null);
                preparedStatement.setString(4, gameToJson(chessGameObj));  // not implemented
                preparedStatement.executeUpdate();
                var resultSet = preparedStatement.getGeneratedKeys();
                if(resultSet.next()){
                    return resultSet.getInt(1);
                }
            }
        }catch(DataAccessException | SQLException ex){
            throw new DataAccessException(String.format("Unable to create game: %s", ex.getMessage()));
        }
        // this return is in case something goes wrong, should not get this far
        return -1;
    }

    @Override
    public GameData getGame(Integer gameID) throws DataAccessException {
        try (var conn = DatabaseManager.getConnection()) {
            String queryStatement = "SELECT * FROM game WHERE id=?";
            try (var preparedStatement = conn.prepareStatement(queryStatement)) {
                preparedStatement.setInt(1,gameID);
                try (var resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        int returnedGameId = resultSet.getInt("id");
                        String returnedGameName = resultSet.getString("gameName");
                        String returnedWhiteUserName = resultSet.getString("whiteUserName");
                        String returnedBlackUserName = resultSet.getString("blackUserName");
                        ChessGame returnedChessGame = jsonToGame(resultSet.getString("chessGame")); // may not work
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
            String queryStatement = "SELECT * FROM game";
            try (var preparedStatement = conn.prepareStatement(queryStatement)) {
                try (var resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        int returnedGameId = resultSet.getInt("id");
                        String returnedGameName = resultSet.getString("gameName");
                        String returnedWhiteUserName = resultSet.getString("whiteUserName");
                        String returnedBlackUserName = resultSet.getString("blackUserName");
                        ChessGame returnedChessGame = jsonToGame(resultSet.getString("chessGame")); // may not work
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
        GameData desiredGame = this.getGame(gameID);
        if(desiredGame == null){
            throw new DataAccessException("Error: unauthorized" );
        }
        var updateStatement = "UPDATE game SET gameName=?, whiteUserName=?, blackUserName=?, chessGame=? where id=?";  //
        try(var conn = DatabaseManager.getConnection()){
            try(var preparedStatement = conn.prepareStatement(updateStatement)){
                preparedStatement.setString(1, updatedGameObject.getGameName());
                preparedStatement.setString(2, updatedGameObject.getWhiteUsername());
                preparedStatement.setString(3, updatedGameObject.getBlackUsername());
                preparedStatement.setString(4, gameToJson(updatedGameObject.getChessGame()));  // not implemented
                preparedStatement.setInt(5, gameID);
                preparedStatement.executeUpdate();
            }
        }catch(DataAccessException | SQLException ex){
            throw new DataAccessException(String.format("Unable to update game: %s", ex.getMessage()));
        }
    }
}
