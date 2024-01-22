package chess;

import java.util.HashMap;
import java.util.Map;

/**
 * A chessboard that can hold and rearrange chess pieces.
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessBoard {
    Map<ChessPosition, ChessPiece> chessBoardMap = new HashMap<>();
    KingPiece blackKing = new KingPiece(ChessGame.TeamColor.BLACK);
    QueenPiece blackQueen = new QueenPiece(ChessGame.TeamColor.BLACK);
    BishopPiece blackBishopLeft = new BishopPiece(ChessGame.TeamColor.BLACK);
    BishopPiece blackBishopRight = new BishopPiece(ChessGame.TeamColor.BLACK);
    KnightPiece blackKnightLeft = new KnightPiece(ChessGame.TeamColor.BLACK);
    KnightPiece blackKnightRight = new KnightPiece(ChessGame.TeamColor.BLACK);
    RookPiece blackRookLeft = new RookPiece(ChessGame.TeamColor.BLACK);
    RookPiece blackRookRight = new RookPiece(ChessGame.TeamColor.BLACK);
    PawnPiece blackPawn1 = new PawnPiece(ChessGame.TeamColor.BLACK);
    PawnPiece blackPawn2 = new PawnPiece(ChessGame.TeamColor.BLACK);
    PawnPiece blackPawn3 = new PawnPiece(ChessGame.TeamColor.BLACK);
    PawnPiece blackPawn4 = new PawnPiece(ChessGame.TeamColor.BLACK);
    PawnPiece blackPawn5 = new PawnPiece(ChessGame.TeamColor.BLACK);
    PawnPiece blackPawn6 = new PawnPiece(ChessGame.TeamColor.BLACK);
    PawnPiece blackPawn7 = new PawnPiece(ChessGame.TeamColor.BLACK);
    PawnPiece blackPawn8 = new PawnPiece(ChessGame.TeamColor.BLACK);

    KingPiece whiteKing = new KingPiece(ChessGame.TeamColor.WHITE);
    QueenPiece whiteQueen = new QueenPiece(ChessGame.TeamColor.WHITE);
    BishopPiece whiteBishopLeft = new BishopPiece(ChessGame.TeamColor.WHITE);
    BishopPiece whiteBishopRight = new BishopPiece(ChessGame.TeamColor.WHITE);
    KnightPiece whiteKnightLeft = new KnightPiece(ChessGame.TeamColor.WHITE);
    KnightPiece whiteKnightRight = new KnightPiece(ChessGame.TeamColor.WHITE);
    RookPiece whiteRookLeft = new RookPiece(ChessGame.TeamColor.WHITE);
    RookPiece whiteRookRight = new RookPiece(ChessGame.TeamColor.WHITE);
    PawnPiece whitePawn1 = new PawnPiece(ChessGame.TeamColor.WHITE);
    PawnPiece whitePawn2 = new PawnPiece(ChessGame.TeamColor.WHITE);
    PawnPiece whitePawn3 = new PawnPiece(ChessGame.TeamColor.WHITE);
    PawnPiece whitePawn4 = new PawnPiece(ChessGame.TeamColor.WHITE);
    PawnPiece whitePawn5 = new PawnPiece(ChessGame.TeamColor.WHITE);
    PawnPiece whitePawn6 = new PawnPiece(ChessGame.TeamColor.WHITE);
    PawnPiece whitePawn7 = new PawnPiece(ChessGame.TeamColor.WHITE);
    PawnPiece whitePawn8 = new PawnPiece(ChessGame.TeamColor.WHITE);

    ChessPosition position1_1 = new ChessPosition(1, 1);
    ChessPosition position1_2 = new ChessPosition(1, 2);
    ChessPosition position1_3 = new ChessPosition(1, 3);
    ChessPosition position1_4 = new ChessPosition(1, 4);
    ChessPosition position1_5 = new ChessPosition(1, 5);
    ChessPosition position1_6 = new ChessPosition(1, 6);
    ChessPosition position1_7 = new ChessPosition(1, 7);
    ChessPosition position1_8 = new ChessPosition(1, 8);

    ChessPosition position2_1 = new ChessPosition(2, 1);
    ChessPosition position2_2 = new ChessPosition(2, 2);
    ChessPosition position2_3 = new ChessPosition(2, 3);
    ChessPosition position2_4 = new ChessPosition(2, 4);
    ChessPosition position2_5 = new ChessPosition(2, 5);
    ChessPosition position2_6 = new ChessPosition(2, 6);
    ChessPosition position2_7 = new ChessPosition(2, 7);
    ChessPosition position2_8 = new ChessPosition(2, 8);

    ChessPosition position3_1 = new ChessPosition(3, 1);
    ChessPosition position3_2 = new ChessPosition(3, 2);
    ChessPosition position3_3 = new ChessPosition(3, 3);
    ChessPosition position3_4 = new ChessPosition(3, 4);
    ChessPosition position3_5 = new ChessPosition(3, 5);
    ChessPosition position3_6 = new ChessPosition(3, 6);
    ChessPosition position3_7 = new ChessPosition(3, 7);
    ChessPosition position3_8 = new ChessPosition(3, 8);

    ChessPosition position4_1 = new ChessPosition(4, 1);
    ChessPosition position4_2 = new ChessPosition(4, 2);
    ChessPosition position4_3 = new ChessPosition(4, 3);
    ChessPosition position4_4 = new ChessPosition(4, 4);
    ChessPosition position4_5 = new ChessPosition(4, 5);
    ChessPosition position4_6 = new ChessPosition(4, 6);
    ChessPosition position4_7 = new ChessPosition(4, 7);
    ChessPosition position4_8 = new ChessPosition(4, 8);

    ChessPosition position5_1 = new ChessPosition(5, 1);
    ChessPosition position5_2 = new ChessPosition(5, 2);
    ChessPosition position5_3 = new ChessPosition(5, 3);
    ChessPosition position5_4 = new ChessPosition(5, 4);
    ChessPosition position5_5 = new ChessPosition(5, 5);
    ChessPosition position5_6 = new ChessPosition(5, 6);
    ChessPosition position5_7 = new ChessPosition(5, 7);
    ChessPosition position5_8 = new ChessPosition(5, 8);

    ChessPosition position6_1 = new ChessPosition(6, 1);
    ChessPosition position6_2 = new ChessPosition(6, 2);
    ChessPosition position6_3 = new ChessPosition(6, 3);
    ChessPosition position6_4 = new ChessPosition(6, 4);
    ChessPosition position6_5 = new ChessPosition(6, 5);
    ChessPosition position6_6 = new ChessPosition(6, 6);
    ChessPosition position6_7 = new ChessPosition(6, 7);
    ChessPosition position6_8 = new ChessPosition(6, 8);

    ChessPosition position7_1 = new ChessPosition(7, 1);
    ChessPosition position7_2 = new ChessPosition(7, 2);
    ChessPosition position7_3 = new ChessPosition(7, 3);
    ChessPosition position7_4 = new ChessPosition(7, 4);
    ChessPosition position7_5 = new ChessPosition(7, 5);
    ChessPosition position7_6 = new ChessPosition(7, 6);
    ChessPosition position7_7 = new ChessPosition(7, 7);
    ChessPosition position7_8 = new ChessPosition(7, 8);

    ChessPosition position8_1 = new ChessPosition(8, 1);
    ChessPosition position8_2 = new ChessPosition(8, 2);
    ChessPosition position8_3 = new ChessPosition(8, 3);
    ChessPosition position8_4 = new ChessPosition(8, 4);
    ChessPosition position8_5 = new ChessPosition(8, 5);
    ChessPosition position8_6 = new ChessPosition(8, 6);
    ChessPosition position8_7 = new ChessPosition(8, 7);
    ChessPosition position8_8 = new ChessPosition(8, 8);


    public ChessBoard() {

    }

    /**
     * Adds a chess piece to the chessboard
     *
     * @param position where to add the piece to
     * @param piece    the piece to add
     */
    public void addPiece(ChessPosition position, ChessPiece piece) {
        chessBoardMap.put(position,piece);
    }
     /**
     * Gets a chess piece on the chessboard
     *
     * @param position The position to get the piece from
     * @return Either the piece at the position, or null if no piece is at that
     * position
     */
    public ChessPiece getPiece(ChessPosition position) {
        return chessBoardMap.get(position);
    }
    /**
     * Sets the board to the default starting board
     * (How the game of chess normally starts)
     */
    public void resetBoard() {
        chessBoardMap.clear();
        addPiece(position1_1, whiteRookLeft);
        addPiece(position1_2, whiteKnightLeft);
        addPiece(position1_3, whiteBishopLeft);
        addPiece(position1_4, whiteQueen);
        addPiece(position1_5, whiteKing);
        addPiece(position1_6, whiteBishopRight);
        addPiece(position1_7, whiteKnightRight);
        addPiece(position1_8, whiteRookRight);

        addPiece(position2_1, whitePawn1);
        addPiece(position2_2, whitePawn2);
        addPiece(position2_3, whitePawn3);
        addPiece(position2_4, whitePawn4);
        addPiece(position2_5, whitePawn5);
        addPiece(position2_6, whitePawn6);
        addPiece(position2_7, whitePawn7);
        addPiece(position2_8, whitePawn8);

        addPiece(position8_1, blackRookLeft);
        addPiece(position8_2, blackKnightLeft);
        addPiece(position8_3, blackBishopLeft);
        addPiece(position8_4, blackQueen);
        addPiece(position8_5, blackKing);
        addPiece(position8_6, blackBishopRight);
        addPiece(position8_7, blackKnightRight);
        addPiece(position8_8, blackRookRight);

        addPiece(position7_1, blackPawn1);
        addPiece(position7_2, blackPawn2);
        addPiece(position7_3, blackPawn3);
        addPiece(position7_4, blackPawn4);
        addPiece(position7_5, blackPawn5);
        addPiece(position7_6, blackPawn6);
        addPiece(position7_7, blackPawn7);
        addPiece(position7_8, blackPawn8);
    }
}
