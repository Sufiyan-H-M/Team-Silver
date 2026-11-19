package pieces;
import game.Board;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a king chess piece and calculates its possible moves.
 */
public class King extends Piece{
    final String symbol = "K";

    /**
     * Creates a king with the specified color and position.
     *
     * @param pieceColor the color of the king
     * @param position the initial position of the king
     */
    public King(PieceColor pieceColor, Position position){
        super(pieceColor, position);
    }

    /**
     * Returns the symbol representing the king.
     *
     * @return the king's symbol
     */
    public String getSymbol(){
        return symbol;
    }

    /**
     * Calculates the king's possible moves to adjacent squares, excluding
     * moves that place it next to the opposing king.
     *
     * @param board the current chessboard
     * @return a list of possible destination positions
     */
    public List<Position> possibleMoves(Board board) {
        List<Position> moveList = new ArrayList<>();
        int r = this.getPosition().getRow();
        int c = this.getPosition().getCol();

        Piece[][] temp = board.getGrid();

        if (r - 1 >= 0) {
            Piece p = temp[r - 1][c];
            if (p == null || !p.getColor().equals(this.getColor())) {
                moveList.add(new Position(r - 1, c));
            }
        }

        if (r + 1 < 8) {
            Piece p = temp[r + 1][c];
            if (p == null || !p.getColor().equals(this.getColor())) {
                moveList.add(new Position(r + 1, c));
            }
        }

        if (c + 1 < 8) {
            Piece p = temp[r][c + 1];
            if (p == null || !p.getColor().equals(this.getColor())) {
                moveList.add(new Position(r, c + 1));
            }
        }

        if (c - 1 >= 0) {
            Piece p = temp[r][c - 1];
            if (p == null || !p.getColor().equals(this.getColor())) {
                moveList.add(new Position(r, c - 1));
            }
        }

        if (r - 1 >= 0 && c + 1 < 8) {
            Piece p = temp[r - 1][c + 1];
            if (p == null || !p.getColor().equals(this.getColor())) {
                moveList.add(new Position(r - 1, c + 1));
            }
        }

        if (r - 1 >= 0 && c - 1 >= 0) {
            Piece p = temp[r - 1][c - 1];
            if (p == null || !p.getColor().equals(this.getColor())) {
                moveList.add(new Position(r - 1, c - 1));
            }
        }

        if (r + 1 < 8 && c + 1 < 8) {
            Piece p = temp[r + 1][c + 1];
            if (p == null || !p.getColor().equals(this.getColor())) {
                moveList.add(new Position(r + 1, c + 1));
            }
        }

        if (r + 1 < 8 && c - 1 >= 0) {
            Piece p = temp[r + 1][c - 1];
            if (p == null || !p.getColor().equals(this.getColor())) {
                moveList.add(new Position(r + 1, c - 1));
            }
        }

        // Castling
        if (!hasMoved()) {
            int home = getColor().equals("w") ? 7 : 0;
            String enemy = getColor().equals("w") ? "b" : "w";

            if (r == home && c == 4 && !board.isSquareAttacked(home, 4, enemy)) {
                if (rookReady(temp, home, 7, new int[]{5, 6})
                        && !board.isSquareAttacked(home, 5, enemy)
                        && !board.isSquareAttacked(home, 6, enemy)) {
                    moveList.add(new Position(home, 6));
                }
                if (rookReady(temp, home, 0, new int[]{1, 2, 3})
                        && !board.isSquareAttacked(home, 3, enemy)
                        && !board.isSquareAttacked(home, 2, enemy)) {
                    moveList.add(new Position(home, 2));
                }
            }
        }

        return moveList;
    }

    /**
     * Checks that the rook in a corner is unmoved and the squares between it
     * and the king are empty.
     *
     * @param grid the board's piece grid
     * @param row the king's home row
     * @param rookCol the column of the corner rook
     * @param between the columns that must be empty
     * @return {@code true} if the castling path is clear
     */
    private boolean rookReady(Piece[][] grid, int row, int rookCol, int[] between) {
        Piece rook = grid[row][rookCol];
        if (!(rook instanceof Rook) || !rook.getColor().equals(getColor()) || rook.hasMoved()) {
            return false;
        }
        for (int col : between) {
            if (grid[row][col] != null) {
                return false;
            }
        }
        return true;
    }

    /**
     * Creates an independent copy of this king at the same position.
     *
     * @return a copy of this king
     */
    @Override
    public Piece copy() {
        return new King(getPieceColor(),
                new Position(getPosition().getRow(), getPosition().getCol()));
    }
}