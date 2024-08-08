package model;

import java.util.Objects;

public class GameData {
    int gameID;
    String whiteUsername;
    String blackUsername;
    String gameName;
    Boolean gameHasEnded;
    chess.ChessGame chessGame;

    public GameData(int gameID, String gameName, String whiteUsername, String blackUsername,
                    chess.ChessGame chessGame) {
        this.gameID = gameID;
        this.whiteUsername = whiteUsername;
        this.blackUsername = blackUsername;
        this.gameName = gameName;
        this.chessGame = chessGame;
        this.gameHasEnded = false;
    }
    public GameData(int gameID, String gameName, String whiteUsername, String blackUsername, Boolean gameHasEnded,
                    chess.ChessGame chessGame) {
        this.gameID = gameID;
        this.whiteUsername = whiteUsername;
        this.blackUsername = blackUsername;
        this.gameName = gameName;
        this.chessGame = chessGame;
        this.gameHasEnded = gameHasEnded;
    }

    public int getGameID() {
        return gameID;
    }

    public String getWhiteUsername() {
        return whiteUsername;
    }

    public void setWhiteUsername(String whiteUsername) {
        this.whiteUsername = whiteUsername;
    }

    public String getBlackUsername() {
        return blackUsername;
    }

    public void setBlackUsername(String blackUsername) {
        this.blackUsername = blackUsername;
    }

    public void setGameHasEnded(Boolean hasEnded){this.gameHasEnded = hasEnded;}

    public Boolean getGameHasEnded(){return gameHasEnded;}

    public String getGameName() {
        return gameName;
    }

    public chess.ChessGame getChessGame() {
        return chessGame;
    }

    public void setChessGame(chess.ChessGame chessGame) {
        this.chessGame = chessGame;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {return true;}
        if (o == null || getClass() != o.getClass()){ return false;}
        GameData gameData = (GameData) o;
        return gameID == gameData.gameID &&
                Objects.equals(whiteUsername, gameData.whiteUsername) &&
                Objects.equals(blackUsername, gameData.blackUsername) &&
                Objects.equals(gameName, gameData.gameName) &&
                Objects.equals(chessGame, gameData.chessGame);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gameID, whiteUsername, blackUsername, gameName, chessGame);
    }
}
