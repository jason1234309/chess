package chess;

import java.util.Objects;

/**
 * Represents a single square position on a chess board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPosition {
    int row;
    int column;

    public ChessPosition(int row, int col) {
        this.row = row;
        column = col;
    }

    /**
     * @return which row this position is in
     * 1 codes for the bottom row
     */
    public int getRow() {

        return row;
    }
    /**
     * @return which column this position is in
     * 1 codes for the left row
     */
    public int getColumn() {

        return column;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) {return true;}
        if (o == null || getClass() != o.getClass()) {return false;}
        ChessPosition that = (ChessPosition) o;
        return row == that.row && column == that.column;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, column);
    }

    @Override
    public String toString() {
        String positionString;
        String letterIndex;
        switch(column){
            case 1 -> letterIndex = "a";
            case 2 -> letterIndex = "b";
            case 3 -> letterIndex = "c";
            case 4 -> letterIndex = "d";
            case 5 -> letterIndex = "e";
            case 6 -> letterIndex = "f";
            case 7 -> letterIndex = "g";
            case 8 -> letterIndex = "h";
            default -> letterIndex = "z";
        }
        positionString = letterIndex+Integer.toString(row);
        return positionString;
    }
}
