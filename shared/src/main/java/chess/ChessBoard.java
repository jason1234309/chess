package chess;

import java.util.Arrays;

/**
 * A chessboard that can hold and rearrange chess pieces.
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessBoard {
    ChessPiece[][] chessBoardArray = new ChessPiece[8][8];

    static ChessPiece blackKing = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KING);
    static ChessPiece blackQueen = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.QUEEN);
    static ChessPiece blackBishopLeft = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.BISHOP);
    static ChessPiece blackBishopRight = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.BISHOP);
    static ChessPiece blackKnightLeft = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KNIGHT);
    static ChessPiece blackKnightRight = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KNIGHT);
    static ChessPiece blackRookLeft = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.ROOK);
   static ChessPiece blackRookRight = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.ROOK);
   static ChessPiece blackPawn1 = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.PAWN);
   static ChessPiece blackPawn2 = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.PAWN);
   static ChessPiece blackPawn3 = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.PAWN);
   static ChessPiece blackPawn4 = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.PAWN);
   static ChessPiece blackPawn5 = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.PAWN);
   static ChessPiece blackPawn6 = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.PAWN);
   static ChessPiece blackPawn7 = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.PAWN);
   static ChessPiece blackPawn8 = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.PAWN);

   static ChessPiece whiteKing = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KING);
   static ChessPiece whiteQueen = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.QUEEN);
   static ChessPiece whiteBishopLeft = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.BISHOP);
   static ChessPiece whiteBishopRight = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.BISHOP);
   static ChessPiece whiteKnightLeft = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KNIGHT);
   static ChessPiece whiteKnightRight = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KNIGHT);
   static ChessPiece whiteRookLeft = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.ROOK);
   static ChessPiece whiteRookRight = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.ROOK);
   static ChessPiece whitePawn1 = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
   static ChessPiece whitePawn2 = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
   static ChessPiece whitePawn3 = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
   static ChessPiece whitePawn4 = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
   static ChessPiece whitePawn5 = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
   static ChessPiece whitePawn6 = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
   static ChessPiece whitePawn7 = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
   static ChessPiece whitePawn8 = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);

   static ChessPosition position1_1 = new ChessPosition(1, 1);
   static ChessPosition position1_2 = new ChessPosition(1, 2);
   static ChessPosition position1_3 = new ChessPosition(1, 3);
   static ChessPosition position1_4 = new ChessPosition(1, 4);
   static ChessPosition position1_5 = new ChessPosition(1, 5);
   static ChessPosition position1_6 = new ChessPosition(1, 6);
   static ChessPosition position1_7 = new ChessPosition(1, 7);
   static ChessPosition position1_8 = new ChessPosition(1, 8);

   static ChessPosition position2_1 = new ChessPosition(2, 1);
   static ChessPosition position2_2 = new ChessPosition(2, 2);
   static ChessPosition position2_3 = new ChessPosition(2, 3);
   static ChessPosition position2_4 = new ChessPosition(2, 4);
   static ChessPosition position2_5 = new ChessPosition(2, 5);
   static ChessPosition position2_6 = new ChessPosition(2, 6);
   static ChessPosition position2_7 = new ChessPosition(2, 7);
   static ChessPosition position2_8 = new ChessPosition(2, 8);

   static ChessPosition position7_1 = new ChessPosition(7, 1);
   static ChessPosition position7_2 = new ChessPosition(7, 2);
   static ChessPosition position7_3 = new ChessPosition(7, 3);
   static ChessPosition position7_4 = new ChessPosition(7, 4);
   static ChessPosition position7_5 = new ChessPosition(7, 5);
   static ChessPosition position7_6 = new ChessPosition(7, 6);
   static ChessPosition position7_7 = new ChessPosition(7, 7);
   static ChessPosition position7_8 = new ChessPosition(7, 8);

   static ChessPosition position8_1 = new ChessPosition(8, 1);
   static ChessPosition position8_2 = new ChessPosition(8, 2);
   static ChessPosition position8_3 = new ChessPosition(8, 3);
   static ChessPosition position8_4 = new ChessPosition(8, 4);
   static ChessPosition position8_5 = new ChessPosition(8, 5);
   static ChessPosition position8_6 = new ChessPosition(8, 6);
   static ChessPosition position8_7 = new ChessPosition(8, 7);
   static ChessPosition position8_8 = new ChessPosition(8, 8);


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessBoard that = (ChessBoard) o;
        return Arrays.deepEquals(chessBoardArray, that.chessBoardArray);
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(chessBoardArray);
    }

    public ChessBoard() {

    }

    /**
     * Adds a chess piece to the chessboard
     *
     * @param position where to add the piece to
     * @param piece    the piece to add
     */
    public void addPiece(ChessPosition position, ChessPiece piece) {

        chessBoardArray[position.getRow()-1][position.getColumn()-1] = piece;
    }
     /**
     * Gets a chess piece on the chessboard
     *
     * @param position The position to get the piece from
     * @return Either the piece at the position, or null if no piece is at that
     * position
     */
    public ChessPiece getPiece(ChessPosition position) {

        return chessBoardArray[position.getRow()-1][position.getColumn()-1];
    }
    /**
     * Sets the board to the default starting board
     * (How the game of chess normally starts)
     */
    public void resetBoard() {
        chessBoardArray = new ChessPiece[8][8];
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
