import chess.ChessGame;
import chess.ChessPiece;
import server.Server;

public class Main {
    public static void main(String[] args) {
        Server serverObj = new Server();
        serverObj.run(8080);
    }
}