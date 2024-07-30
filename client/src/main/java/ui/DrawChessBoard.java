package ui;

import chess.*;

import java.io.PrintStream;
import java.util.ArrayList;

public class DrawChessBoard {
    // Board dimensions.
    private static final int BOARD_SIZE_IN_SQUARES = 8;
    // Padded characters.
    private static final String EMPTY = "   ";
    private static final String R = " R ";
    private static final String N = " N ";
    private static final String B = " B ";
    private static final String K = " K ";
    private static final String Q = " Q ";
    private static final String P = " P ";
    // array to hold what pieces are in each position in the row
    ArrayList<String> chessBoardRowArray = new ArrayList<>();

    public static void drawChessBoard(PrintStream out, String playerColor,  ChessBoard chessBoard) {
        boolean spaceIsWhite = true;
        if(playerColor.equals("WHITE")){
            drawWhiteHeader(out);
            for (int boardRow = BOARD_SIZE_IN_SQUARES; boardRow > 0; --boardRow) {
                out.print(EscapeSequences.SET_BG_COLOR_DARK_GREEN);
                out.print(EscapeSequences.SET_TEXT_COLOR_LIGHT_GREY);
                out.print(" " + Integer.toString(boardRow) + " ");
                for(int boardColumn = 1; boardColumn <= BOARD_SIZE_IN_SQUARES; ++boardColumn){
                    drawCorrectPiece(out, spaceIsWhite, new ChessPosition(boardRow, boardColumn), chessBoard);
                    spaceIsWhite = !spaceIsWhite;
                }
                out.print(EscapeSequences.SET_BG_COLOR_DARK_GREEN);
                out.print(EscapeSequences.SET_TEXT_COLOR_LIGHT_GREY);
                out.print(" " + Integer.toString(boardRow) + " ");
                out.print(EscapeSequences.RESET_BG_COLOR);
                out.print("\n");

                if(boardRow % 2 == 0){
                    spaceIsWhite = false;
                }else{
                    spaceIsWhite = true;
                }
            }
            drawWhiteHeader(out);
        }else{
            drawBlackHeader(out);
            for (int boardRow = 1; boardRow <= BOARD_SIZE_IN_SQUARES; ++boardRow) {
                out.print(EscapeSequences.SET_BG_COLOR_DARK_GREEN);
                out.print(EscapeSequences.SET_TEXT_COLOR_LIGHT_GREY);
                out.print(" " + Integer.toString(boardRow) + " ");
                for(int boardColumn = BOARD_SIZE_IN_SQUARES; boardColumn >= 1; --boardColumn){
                    drawCorrectPiece(out, spaceIsWhite, new ChessPosition(boardRow, boardColumn), chessBoard);
                    spaceIsWhite = !spaceIsWhite;
                }
                out.print(EscapeSequences.SET_BG_COLOR_DARK_GREEN);
                out.print(EscapeSequences.SET_TEXT_COLOR_LIGHT_GREY);
                out.print(" " + Integer.toString(boardRow) + " ");
                out.print(EscapeSequences.RESET_BG_COLOR);
                out.print("\n");

                if(boardRow % 2 == 0){
                    spaceIsWhite = true;
                }else{
                    spaceIsWhite = false;
                }
            }
            drawBlackHeader(out);
        }
    }
    private static void drawWhiteHeader(PrintStream out){
        out.print(EscapeSequences.SET_BG_COLOR_DARK_GREEN);
        out.print(EscapeSequences.SET_TEXT_COLOR_LIGHT_GREY);
        out.print(EMPTY);
        out.print(" a  b  c  d  e  f  g  h ");
        out.print(EMPTY);
        out.print(EscapeSequences.RESET_BG_COLOR);
        out.print("\n");


    }
    private static void drawBlackHeader(PrintStream out){
        out.print(EscapeSequences.SET_BG_COLOR_DARK_GREEN);
        out.print(EscapeSequences.SET_TEXT_COLOR_LIGHT_GREY);
        out.print(EMPTY);
        out.print(" h  g  f  e  d  c  b  a ");
        out.print(EMPTY);
        out.print(EscapeSequences.RESET_BG_COLOR);
        out.print("\n");
    }
    private static void drawCorrectPiece(PrintStream out, boolean isWhite, ChessPosition piecePosition, ChessBoard currentChessBoard){
        if(isWhite){
            out.print(EscapeSequences.SET_BG_COLOR_BLUE);

        }else{
            out.print(EscapeSequences.SET_BG_COLOR_RED);
        }
        ChessPiece currentPiece = currentChessBoard.getPiece(piecePosition);
        if(currentPiece == null){
            out.print(EMPTY);
        }else{
            if(currentPiece.getTeamColor() == ChessGame.TeamColor.WHITE){
                out.print(EscapeSequences.SET_TEXT_COLOR_WHITE);
            }else{
                out.print(EscapeSequences.SET_TEXT_COLOR_BLACK);
            }
            switch(currentPiece.getPieceType()){
                case PAWN -> out.print(P);
                case KING -> out.print(K);
                case QUEEN -> out.print(Q);
                case BISHOP -> out.print(B);
                case KNIGHT -> out.print(N);
                case ROOK -> out.print(R);
            }
        }
    }


}


//    private static void drawTicTacToeBoard(PrintStream out) {
//        boolean isWhite = true;
//        for (int boardRow = 0; boardRow < BOARD_SIZE_IN_SQUARES; ++boardRow) {
//            drawRowOfSquares(out,isWhite);
//            isWhite = !isWhite;
//
//            if (boardRow < BOARD_SIZE_IN_SQUARES - 1) {
//                // Draw horizontal row separator.
//                //drawHorizontalLine(out);
//                setBlack(out);
//            }
//        }
//    }

//    private static void drawRowOfSquares(PrintStream out, boolean isWhite) {
//
//        for (int squareRow = 0; squareRow < SQUARE_SIZE_IN_PADDED_CHARS; ++squareRow) {
//            for (int boardCol = 0; boardCol < BOARD_SIZE_IN_SQUARES; ++boardCol) {
//                setWhite(out);
//
//                if (squareRow == SQUARE_SIZE_IN_PADDED_CHARS / 2) {
//                    int prefixLength = SQUARE_SIZE_IN_PADDED_CHARS / 2;
//                    int suffixLength = SQUARE_SIZE_IN_PADDED_CHARS - prefixLength - 1;
//
//                    out.print(EMPTY.repeat(prefixLength));
//                    printPlayer(out, rand.nextBoolean() ? X : O, isWhite);
//                    out.print(EMPTY.repeat(suffixLength));
//                    isWhite = !isWhite;
//                }
//                else {
//                    out.print(EMPTY.repeat(SQUARE_SIZE_IN_PADDED_CHARS));
//                }
//
//                if (boardCol < BOARD_SIZE_IN_SQUARES - 1) {
//                    // Draw vertical column separator.
//                    setRed(out);
//                    out.print(EMPTY.repeat(LINE_WIDTH_IN_PADDED_CHARS));
//                }
//
//                out.print(EscapeSequences.SET_BG_COLOR_LIGHT_GREY);
//            }
//
//            out.println();
//        }
//    }

//    private static void drawHorizontalLine(PrintStream out) {
//
//        int boardSizeInSpaces = BOARD_SIZE_IN_SQUARES * SQUARE_SIZE_IN_PADDED_CHARS +
//                (BOARD_SIZE_IN_SQUARES - 1) * LINE_WIDTH_IN_PADDED_CHARS;
//
//        for (int lineRow = 0; lineRow < LINE_WIDTH_IN_PADDED_CHARS; ++lineRow) {
//            setRed(out);
//            out.print(EMPTY.repeat(boardSizeInSpaces));
//
//            setBlack(out);
//            out.println();
//        }
//    }

//    private static void setWhite(PrintStream out) {
//        out.print(EscapeSequences.SET_BG_COLOR_WHITE);
//        out.print(EscapeSequences.SET_TEXT_COLOR_BLACK);
//    }
//
//    private static void setRed(PrintStream out) {
//        out.print(EscapeSequences.SET_BG_COLOR_RED);
//        out.print(EscapeSequences.SET_TEXT_COLOR_RED);
//    }
//
//    private static void setBlack(PrintStream out) {
//        out.print(EscapeSequences.SET_BG_COLOR_BLACK);
//        out.print(EscapeSequences.SET_TEXT_COLOR_BLACK);
//    }
//
//    private static void printPlayer(PrintStream out, String player, boolean isWhite) {
//        if(isWhite){
//            out.print(EscapeSequences.SET_BG_COLOR_WHITE);
//        }else{
//            out.print(EscapeSequences.SET_BG_COLOR_BLACK);
//        }
//        out.print(EscapeSequences.SET_TEXT_COLOR_BLUE);
//
//        out.print(player);
//
//        setWhite(out);
//    }
