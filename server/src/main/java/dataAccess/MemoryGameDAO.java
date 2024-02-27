package dataAccess;

import model.AuthData;
import model.GameData;

import java.util.ArrayList;
import java.util.Collection;

public class MemoryGameDAO implements GameDAO{
    Collection<AuthData> gameDataBase = new ArrayList<>();

    @Override
    public void clearUserDataBase() {

    }

    @Override
    public void createGame(String gameID, String gameName) throws DataAccessException {

    }

    @Override
    public GameData getGame(String gameID) throws DataAccessException {
        return null;
    }

    @Override
    public Collection<GameData> listGames() {
        return null;
    }

    @Override
    public void updateGame(String gameID) throws DataAccessException {

    }
}
