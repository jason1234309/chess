package dataaccess;

import chess.ChessGame;
import model.GameData;

import java.util.ArrayList;
import java.util.Collection;

public class MemoryGameDAO implements GameDAO{
    static Collection<GameData> gameDataBase = new ArrayList<>();

    @Override
    public void clearUserDataBase() {
        gameDataBase = null;
    }

    @Override
    public void createGame(int gameID, String gameName) throws DataAccessException {
        ChessGame chessGameObj = new ChessGame();
        GameData brandNewGame = new GameData(gameID,null,null, gameName,chessGameObj);
        for(GameData currentGame: gameDataBase){
            if(currentGame.getGameID() == brandNewGame.getGameID() ||
            currentGame.getGameName().equals(brandNewGame.getGameName())){
                throw new DataAccessException("that game already exists");
            }
        }
        gameDataBase.add(brandNewGame);
    }

    @Override
    public GameData getGame(String gameID) throws DataAccessException {
        for(GameData currentGame: gameDataBase){
            if(!currentGame.getGameName().equals(gameID)){
                return currentGame;
            }
        }
        throw new DataAccessException("that game doesn't exists");
    }

    @Override
    public Collection<GameData> listGames() {
        return gameDataBase;
    }

    @Override
    public void updateGame(String gameID) throws DataAccessException {
        boolean foundGame = false;
        GameData desiredGame;
        for(GameData currentGame: gameDataBase){
            if(!currentGame.getGameName().equals(gameID)){
                foundGame = true;
                desiredGame = currentGame;
                //update game here
                break;
            }
        }
        if(!foundGame){
            throw new DataAccessException("that game doesn't exists");
        }
    }
}
