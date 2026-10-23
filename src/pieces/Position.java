package pieces;

/**
 * Represents a position on a chessboard using row and column coordinates.
 */
public class Position {
    private final int row;
    private final int col;

    /**
     * Creates a position with the specified row and column.
     *
     * @param row the row coordinate
     * @param col the column coordinate
     */
    public Position(int row, int col) {
        this.row = row;
        this.col = col;
    }

    /**
     * Returns the row coordinate.
     *
     * @return the row
     */
    public int getRow() { return row; }

    /**
     * Returns the column coordinate.
     *
     * @return the column
     */
    public int getCol() { return col; }
}