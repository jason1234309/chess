package ui;

import chess.ChessBoard;
import chess.ChessGame;

import java.io.PrintStream;
import java.util.Random;

public class DrawChessBoard {
    // Board dimensions.
    private static final int BOARD_SIZE_IN_SQUARES = 8;
    private static final int SQUARE_SIZE_IN_PADDED_CHARS = 1;
    private static final int LINE_WIDTH_IN_PADDED_CHARS = 1;

    // Padded characters.
    private static final String EMPTY = "   ";
    private static final String X = " X ";
    private static final String O = " O ";

    public static void drawChessBoard(PrintStream out, ChessBoard chessBoard) {
        boolean isWhite = true;
        for (int boardRow = 0; boardRow < BOARD_SIZE_IN_SQUARES; ++boardRow) {
            //     drawRowOfSquares(out,isWhite);
            isWhite = !isWhite;
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
