package chess;

import java.util.Objects;

/**
 * Represents moving a chess piece on a chessboard
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessMove {
    ChessPosition startingPosition = new ChessPosition(-1,-1);
    ChessPosition endingPosition =  new ChessPosition(-1,-1);
    ChessPiece.PieceType promotionPieceVal;

    public ChessMove(ChessPosition startPosition, ChessPosition endPosition,
                     ChessPiece.PieceType promotionPiece) {
        startingPosition = startPosition;
        endingPosition = endPosition;
        promotionPieceVal = promotionPiece;
    }


    /**
     * @return ChessPosition of starting location
     */
    public ChessPosition getStartPosition() {
        return startingPosition;
    }

    /**
     * @return ChessPosition of ending location
     */
    public ChessPosition getEndPosition() {
        return endingPosition;
    }

    /**
     * Gets the type of piece to promote a pawn to if pawn promotion is part of this
     * chess move
     *
     * @return Type of piece to promote a pawn to, or null if no promotion
     */
    public ChessPiece.PieceType getPromotionPiece() {
        return promotionPieceVal;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessMove that = (ChessMove) o;
        return Objects.equals(startingPosition, that.startingPosition) && Objects.equals(endingPosition, that.endingPosition) && promotionPieceVal == that.promotionPieceVal;
    }

    @Override
    public int hashCode() {
        return Objects.hash(startingPosition, endingPosition, promotionPieceVal);
    }
}
