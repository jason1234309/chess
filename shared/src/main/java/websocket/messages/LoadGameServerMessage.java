package websocket.messages;

import chess.ChessGame;

public class LoadGameServerMessage extends ServerMessage{
    ChessGame chessGame;
    public LoadGameServerMessage(ServerMessageType type, ChessGame chessGame) {
        super(type);
        this.chessGame = chessGame;
    }
}
