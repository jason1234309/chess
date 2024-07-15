package chess;

import java.util.Objects;

/**
 * Represents moving a chess piece on a chessboard
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessMove {
    ChessPosition startingPoint;
    ChessPosition endingPoint;
    ChessPiece.PieceType promotionPieceType;

    public ChessMove(ChessPosition startPosition, ChessPosition endPosition,
                     ChessPiece.PieceType promotionPiece) {
        startingPoint = startPosition;
        endingPoint = endPosition;
        promotionPieceType = promotionPiece;
    }

    /**
     * @return ChessPosition of starting location
     */
    public ChessPosition getStartPosition() {

        return startingPoint;
    }

    /**
     * @return ChessPosition of ending location
     */
    public ChessPosition getEndPosition() {

        return endingPoint;
    }

    /**
     * Gets the type of piece to promote a pawn to if pawn promotion is part of this
     * chess move
     *
     * @return Type of piece to promote a pawn to, or null if no promotion
     */
    public ChessPiece.PieceType getPromotionPiece() {

        return promotionPieceType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {return true;}
        if (o == null || getClass() != o.getClass()) {return false;}
        ChessMove chessMove = (ChessMove) o;
        return Objects.equals(startingPoint, chessMove.startingPoint)
                && Objects.equals(endingPoint, chessMove.endingPoint)
                && promotionPieceType == chessMove.promotionPieceType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(startingPoint, endingPoint, promotionPieceType);
    }
}
