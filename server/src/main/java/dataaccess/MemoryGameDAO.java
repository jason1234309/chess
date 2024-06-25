package dataaccess;

import chess.ChessGame;
import model.GameData;

import java.util.ArrayList;
import java.util.Collection;

public class MemoryGameDAO implements GameDAO{
    static Collection<GameData> gameDataBase = new ArrayList<>();

    @Override
    public void clearGameDataBase() {
        gameDataBase.clear();
    }

    @Override
    public void createGame(int gameID, String gameName) throws DataAccessException {
        ChessGame chessGameObj = new ChessGame();
        GameData brandNewGame = new GameData(gameID,null,null, gameName,chessGameObj);
        for(GameData currentGame: gameDataBase){
            if(currentGame.getGameID() == brandNewGame.getGameID() ||
            currentGame.getGameName().equals(brandNewGame.getGameName())){
                throw new DataAccessException("Error: unauthorized");
            }
        }
        gameDataBase.add(brandNewGame);
    }

    @Override
    public GameData getGame(Integer gameID) throws DataAccessException {
        for(GameData currentGame: gameDataBase){
            if(currentGame.getGameID() == gameID){
                return currentGame;
            }
        }
        throw new DataAccessException("Error: unauthorized");
    }

    @Override
    public Collection<GameData> listGames() {
        return gameDataBase;
    }

    @Override
    public void updateGame(Integer gameID, GameData updatedGameObject) throws DataAccessException {
        boolean foundGame = false;
        GameData desiredGame;
        for(GameData currentGame: gameDataBase){
            if(currentGame.getGameID() == gameID){
                foundGame = true;
                ArrayList <GameData> tempGameList = (ArrayList<GameData>) gameDataBase;
                int currentGameIndex = tempGameList.indexOf(currentGame);
                tempGameList.set(currentGameIndex, updatedGameObject);
                gameDataBase = tempGameList;
                break;
            }
        }
        if(!foundGame){
            throw new DataAccessException("Error: unauthorized" );
        }
    }
}
