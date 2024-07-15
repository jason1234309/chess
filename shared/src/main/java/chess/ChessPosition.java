package chess;

import java.util.Objects;

/**
 * Represents a single square position on a chess board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPosition {
    int moveRow;
    int moveCol;

    public ChessPosition(int row, int col) {
        moveRow = row;
        moveCol = col;
    }

    /**
     * @return which row this position is in
     * 1 codes for the bottom row
     */
    public int getRow() {

        return moveRow;
    }
    /**
     * @return which column this position is in
     * 1 codes for the left row
     */
    public int getColumn() {

        return moveCol;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) {return true;}
        if (o == null || getClass() != o.getClass()) {return false;}
        ChessPosition that = (ChessPosition) o;
        return moveRow == that.moveRow && moveCol == that.moveCol;
    }

    @Override
    public int hashCode() {
        return Objects.hash(moveRow, moveCol);
    }


}
