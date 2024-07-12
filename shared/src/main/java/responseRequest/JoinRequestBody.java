package responseRequest;
import chess.ChessGame;

public record JoinRequestBody(ChessGame.TeamColor playerColor, Integer gameID) {
}
