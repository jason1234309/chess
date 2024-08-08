package websocket.commands;

import chess.ChessMove;

public class MakeMoveCommand extends UserGameCommand{
    ChessMove chessMove;
    CommandType commandType;
    public MakeMoveCommand(CommandType commandType, String authToken, Integer gameID, ChessMove chessMove) {
        super(commandType, authToken, gameID);
        this.chessMove = chessMove;
        this.commandType = CommandType.MAKE_MOVE;
    }
    public ChessMove getChessMove(){
        return chessMove;
    }
}
