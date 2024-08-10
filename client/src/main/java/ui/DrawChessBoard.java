package ui;

import chess.*;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Set;

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

    public static void drawChessBoard(PrintStream out, String playerColor, ChessPosition highLightStartPos ,
                                      Set<ChessPosition> validEndPosSet, ChessBoard chessBoard) {
        boolean spaceIsWhite = true;
        if(playerColor.equals("WHITE")){
            drawWhiteHeader(out);
            // prints the chess board to the console 1 square at a time the border is handled here and the
            // pieces are printed by a helper function
            for (int boardRow = BOARD_SIZE_IN_SQUARES; boardRow > 0; --boardRow) {
                out.print(EscapeSequences.SET_BG_COLOR_DARK_GREEN);
                out.print(EscapeSequences.SET_TEXT_COLOR_LIGHT_GREY);
                out.print(" " + Integer.toString(boardRow) + " ");
                for(int boardColumn = 1; boardColumn <= BOARD_SIZE_IN_SQUARES; ++boardColumn){
                    drawCorrectPiece(out, spaceIsWhite, new ChessPosition(boardRow, boardColumn), highLightStartPos,
                            validEndPosSet, chessBoard);
                    spaceIsWhite = !spaceIsWhite;
                }
                out.print(EscapeSequences.SET_BG_COLOR_DARK_GREEN);
                out.print(EscapeSequences.SET_TEXT_COLOR_LIGHT_GREY);
                out.print(" " + Integer.toString(boardRow) + " ");
                out.print(EscapeSequences.RESET_BG_COLOR);
                out.print("\n");

                // ensures that the spaceIsWhite boolean is correct for starting another row
                if(boardRow % 2 == 0){
                    spaceIsWhite = false;
                }else{
                    spaceIsWhite = true;
                }
            }
            drawWhiteHeader(out);
        }else{
            drawBlackHeader(out);
            // prints the chess board to the console 1 square at a time the border is handled here and the
            // pieces are printed by a helper function
            for (int boardRow = 1; boardRow <= BOARD_SIZE_IN_SQUARES; ++boardRow) {
                out.print(EscapeSequences.SET_BG_COLOR_DARK_GREEN);
                out.print(EscapeSequences.SET_TEXT_COLOR_LIGHT_GREY);
                out.print(" " + Integer.toString(boardRow) + " ");
                for(int boardColumn = BOARD_SIZE_IN_SQUARES; boardColumn >= 1; --boardColumn){
                    drawCorrectPiece(out, spaceIsWhite, new ChessPosition(boardRow, boardColumn), highLightStartPos,
                            validEndPosSet, chessBoard);
                    spaceIsWhite = !spaceIsWhite;
                }
                out.print(EscapeSequences.SET_BG_COLOR_DARK_GREEN);
                out.print(EscapeSequences.SET_TEXT_COLOR_LIGHT_GREY);
                out.print(" " + Integer.toString(boardRow) + " ");
                out.print(EscapeSequences.RESET_BG_COLOR);
                out.print("\n");

                // ensures that the spaceIsWhite boolean is correct for starting another row
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
    private static void drawCorrectPiece(PrintStream out, boolean isWhite,  ChessPosition piecePosition,
                                         ChessPosition highLightStartPos , Set<ChessPosition> validEndPosSet,
                                         ChessBoard currentChessBoard){
        if(highLightStartPos !=null && validEndPosSet != null){
            if(piecePosition.equals(highLightStartPos)){
                out.print(EscapeSequences.SET_BG_COLOR_YELLOW);
            }else if(validEndPosSet.contains(piecePosition)){
                if(isWhite){
                    out.print(EscapeSequences.SET_BG_COLOR_GREEN);

                }else{
                    out.print(EscapeSequences.SET_BG_COLOR_DARK_GREEN);
                }
            }else{
                if(isWhite){
                    out.print(EscapeSequences.SET_BG_COLOR_BLUE);

                }else{
                    out.print(EscapeSequences.SET_BG_COLOR_RED);
                }
            }
        }else{
            if(isWhite){
                out.print(EscapeSequences.SET_BG_COLOR_BLUE);

            }else{
                out.print(EscapeSequences.SET_BG_COLOR_RED);
            }
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
