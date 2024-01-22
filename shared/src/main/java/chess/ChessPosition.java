package chess;

import java.util.Objects;

/**
 * Represents a single square position on a chess board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPosition {
    private int rowInt = 0;
    private int columnInt = 0;

    public ChessPosition(int row, int col) {
        rowInt = row;
        columnInt = col;
    }

    /**
     * @return which row this position is in
     * 1 codes for the bottom row
     */
    public int getRow() {
        return rowInt;
    }

    /**
     * @return which column this position is in
     * 1 codes for the left row
     */
    public int getColumn() {
        return columnInt;
    }
    @Override
    public boolean equals(Object o) {
        if(o == null){
            return false;
        }
        else if(o.getClass() != this.getClass()){
            return false;
        }
        else if(((ChessPosition) o).getRow() != this.getRow()){
            return false;
        }
        else if(((ChessPosition) o).getColumn() != this.getColumn()){
            return false;
        }
        else{
            return true;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(rowInt, columnInt);
    }
}
