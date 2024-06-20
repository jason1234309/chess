package ResponseRequest;
import chess.ChessGame;

public record JoinRequestBody(ChessGame.TeamColor playerColor, String gameName) {
}
