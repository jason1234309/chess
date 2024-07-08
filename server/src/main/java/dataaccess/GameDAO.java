package dataaccess;

import model.GameData;

import java.util.Collection;

public interface GameDAO {
    //A method for clearing all data from the database. This is used during testing.
    void clearGameDataBase() throws DataAccessException;
    //  Create a new game.
    void createGame(int gameID, String gameName) throws DataAccessException;
    // Retrieve a specified game with the given game ID.
    GameData getGame(Integer gameID) throws DataAccessException;
    // Retrieve all games.
    Collection<GameData> listGames()throws DataAccessException;
    //  Updates a chess game. It should replace the chess game string corresponding to a given gameID.
    //  This is used when players join a game or when a move is made.
    void updateGame(Integer gameID, GameData updatedGameObject)throws DataAccessException;
}
