package dataaccess;

import model.GameData;

import java.util.Collection;

public class SqlGameDAO implements GameDAO{
    @Override
    public void clearGameDataBase() {

    }

    @Override
    public void createGame(int gameID, String gameName) throws DataAccessException {

    }

    @Override
    public GameData getGame(Integer gameID) throws DataAccessException {
        return null;
    }

    @Override
    public Collection<GameData> listGames() {
        return null;
    }

    @Override
    public void updateGame(Integer gameID, GameData updatedGameObject) throws DataAccessException {

    }
}
