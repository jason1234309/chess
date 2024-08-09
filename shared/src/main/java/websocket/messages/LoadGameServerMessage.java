package websocket.messages;

import chess.ChessGame;

public class LoadGameServerMessage extends ServerMessage{
    ChessGame game;
    public LoadGameServerMessage(ServerMessageType type, ChessGame chessGame) {
        super(type);
        this.game = chessGame;
    }
}
