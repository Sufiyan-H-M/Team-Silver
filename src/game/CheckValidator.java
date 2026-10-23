package game;

import java.util.List;
import pieces.*;

/**
 * Validates check conditions and determines whether moves leave a king safe.
 */
public class CheckValidator {
    private final Board board;

    /**
     * Creates a validator for the specified board.
     *
     * @param board the board to validate
     */
    public CheckValidator(Board board) {
        this.board = board;
    }

    /**
     * Determines whether the king of the specified color is currently in check.
     *
     * @param color the color of the king to check
     * @return {@code true} if the king is in check; {@code false} otherwise
     */
    public boolean isInCheck(String color) {
        Piece[][] grid = board.getGrid();
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                Piece p = grid[r][c];
                if (p instanceof King && p.getColor().equals(color)) {
                    String enemy = color.equals("w") ? "b" : "w";
                    return isSquareAttacked(r, c, enemy);
                }
            }
        }
        return false;
    }

    /**
     * Determines whether a square is attacked by any piece of the specified color.
     *
     * @param r the target row
     * @param c the target column
     * @param byColor the color of the attacking pieces
     * @return {@code true} if the square is attacked; {@code false} otherwise
     */
    private boolean isSquareAttacked(int r, int c, String byColor) {

        int pawnRow = byColor.equals("b") ? r - 1 : r + 1;
        for (int dc = -1; dc <= 1; dc += 2) {
            if (isEnemy(pawnRow, c + dc, byColor, Pawn.class)) return true;
        }

        int[][] knightMoves = {
                {-2, -1}, {-2, 1}, {-1, -2}, {-1, 2},
                {1, -2}, {1, 2}, {2, -1}, {2, 1}
        };
        for (int[] m : knightMoves) {
            if (isEnemy(r + m[0], c + m[1], byColor, Knight.class)) return true;
        }

        for (int dr = -1; dr <= 1; dr++) {
            for (int dc = -1; dc <= 1; dc++) {
                if (dr == 0 && dc == 0) continue;
                if (isEnemy(r + dr, c + dc, byColor, King.class)) return true;
            }
        }

        int[][] straight = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        for (int[] d : straight) {
            Piece first = firstPieceInDirection(r, c, d[0], d[1]);
            if (first != null && first.getColor().equals(byColor)
                    && (first instanceof Rook || first instanceof Queen)) return true;
        }

        int[][] diagonal = {{-1, -1}, {-1, 1}, {1, -1}, {1, 1}};
        for (int[] d : diagonal) {
            Piece first = firstPieceInDirection(r, c, d[0], d[1]);
            if (first != null && first.getColor().equals(byColor)
                    && (first instanceof Bishop || first instanceof Queen)) return true;
        }

        return false;
    }

    /**
     * Checks whether a row and column are within the board boundaries.
     *
     * @param r the row to check
     * @param c the column to check
     * @return {@code true} if the coordinates are within bounds; {@code false} otherwise
     */
    private boolean inBounds(int r, int c) {
        return r >= 0 && r < 8 && c >= 0 && c < 8;
    }

    /**
     * Checks whether a square contains a piece of the specified color and type.
     *
     * @param r the row to check
     * @param c the column to check
     * @param color the color of the piece
     * @param type the required piece class
     * @return {@code true} if the square contains a matching piece; {@code false} otherwise
     */
    private boolean isEnemy(int r, int c, String color, Class<? extends Piece> type) {
        Piece[][] grid = board.getGrid();
        return inBounds(r, c)
                && grid[r][c] != null
                && grid[r][c].getColor().equals(color)
                && type.isInstance(grid[r][c]);
    }

    /**
     * Finds the first piece encountered when moving from a square in a specified direction.
     *
     * @param r the starting row
     * @param c the starting column
     * @param dr the row direction
     * @param dc the column direction
     * @return the first piece encountered, or {@code null} if no piece is found
     */
    private Piece firstPieceInDirection(int r, int c, int dr, int dc) {
        Piece[][] grid = board.getGrid();
        int nr = r + dr, nc = c + dc;
        while (inBounds(nr, nc)) {
            if (grid[nr][nc] != null) return grid[nr][nc];
            nr += dr;
            nc += dc;
        }
        return null;
    }

    /**
     * Determines whether a move leaves the moving player's king safe by simulating
     * the move on a copy of the board.
     *
     * @param oldRow the starting row
     * @param oldCol the starting column
     * @param newRow the destination row
     * @param newCol the destination column
     * @param color the color of the moving player
     * @return {@code true} if the king remains safe; {@code false} otherwise
     */
    boolean leavesKingSafe(int oldRow, int oldCol, int newRow, int newCol, String color) {
        Board sim = board.copy();
        sim.applyMoveWithoutCheck(oldRow, oldCol, newRow, newCol);
        return !sim.isInCheck(color);
    }

    /**
     * Determines whether the specified color has at least one legal move.
     *
     * @param color the color of the player
     * @return {@code true} if at least one legal move exists; {@code false} otherwise
     */
    public boolean hasAnyLegalMove(String color) {
        Piece[][] grid = board.getGrid();
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                Piece p = grid[r][c];
                if (p == null || !p.getColor().equals(color)) continue;

                List<Position> moves = p.possibleMoves(board);
                for (Position move : moves) {
                    if (leavesKingSafe(r, c, move.getRow(), move.getCol(), color)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}