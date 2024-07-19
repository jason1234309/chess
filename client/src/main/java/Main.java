import chess.ChessGame;
import chess.ChessPiece;
import ui.Client;

import java.io.IOException;
import java.io.PrintStream;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.Random;
import java.util.Scanner;

import static ui.EscapeSequences.*;

public class Main {
    public static void main(String[] args) throws IOException, URISyntaxException {
        Client clientObj = new Client("http://localhost:8080");
        clientObj.run();
    }
}