package game;

import java.util.List;
import pieces.*;

public class Board {
    private Piece[][] grid = new Piece[8][8];

    public Board() {
        initialize();
    }

    // Used by copy(): creates an empty board without setting up the pieces
    private Board(boolean empty) {
    }

    public Piece[][] getGrid() {
        return grid;
    }

    public void initialize() {
        // Black back rank (row 0 = rank 8) and pawns (row 1)
        placeBackRank(0, PieceColor.BLACK);
        for (int c = 0; c < 8; c++) {
            grid[1][c] = new Pawn(PieceColor.BLACK, new Position(1, c));
        }
        // White pawns (row 6) and back rank (row 7 = rank 1)
        for (int c = 0; c < 8; c++) {
            grid[6][c] = new Pawn(PieceColor.WHITE, new Position(6, c));
        }
        placeBackRank(7, PieceColor.WHITE);
    }

    private void placeBackRank(int row, PieceColor color) {
        grid[row][0] = new Rook(color, new Position(row, 0));
        grid[row][1] = new Knight(color, new Position(row, 1));
        grid[row][2] = new Bishop(color, new Position(row, 2));
        grid[row][3] = new Queen(color, new Position(row, 3));
        grid[row][4] = new King(color, new Position(row, 4));
        grid[row][5] = new Bishop(color, new Position(row, 5));
        grid[row][6] = new Knight(color, new Position(row, 6));
        grid[row][7] = new Rook(color, new Position(row, 7));
    }

    public void printBoard() {
        System.out.println("  A  B  C  D  E  F  G  H ");
        for (int i = 0; i < 8; i++) {
            System.out.print(8 - i + " ");
            for (int j = 0; j < 8; j++) {
                if (grid[i][j] == null) {
                    System.out.print("## ");
                } else {
                    System.out.print(grid[i][j].getColor() + grid[i][j].getSymbol() + " ");
                }
            }
            System.out.println();
        }
    }

    // ------------------------------------------------------------------
    // CHECK DETECTION
    // ------------------------------------------------------------------

    /** Is the king of the given color currently attacked? */
    public boolean isInCheck(String color) {
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                Piece p = grid[r][c];
                if (p instanceof King && p.getColor().equals(color)) {
                    String enemy = color.equals("w") ? "b" : "w";
                    return isSquareAttacked(r, c, enemy);
                }
            }
        }
        return false; // no king found
    }

    /** Is square (r, c) attacked by any piece of color byColor? */
    private boolean isSquareAttacked(int r, int c, String byColor) {

        // 1. Pawns.
        // Black pawns move DOWN the array (row+1) so they attack from the row ABOVE the target.
        // White pawns move UP the array (row-1) so they attack from the row BELOW the target.
        int pawnRow = byColor.equals("b") ? r - 1 : r + 1;
        for (int dc = -1; dc <= 1; dc += 2) {
            if (isEnemy(pawnRow, c + dc, byColor, Pawn.class)) return true;
        }

        // 2. Knights
        int[][] knightMoves = {
                {-2, -1}, {-2, 1}, {-1, -2}, {-1, 2},
                {1, -2}, {1, 2}, {2, -1}, {2, 1}
        };
        for (int[] m : knightMoves) {
            if (isEnemy(r + m[0], c + m[1], byColor, Knight.class)) return true;
        }

        // 3. Enemy king next to the square (kings can never stand adjacent)
        for (int dr = -1; dr <= 1; dr++) {
            for (int dc = -1; dc <= 1; dc++) {
                if (dr == 0 && dc == 0) continue;
                if (isEnemy(r + dr, c + dc, byColor, King.class)) return true;
            }
        }

        // 4. Rooks / queens along ranks and files
        int[][] straight = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        for (int[] d : straight) {
            Piece first = firstPieceInDirection(r, c, d[0], d[1]);
            if (first != null && first.getColor().equals(byColor)
                    && (first instanceof Rook || first instanceof Queen)) return true;
        }

        // 5. Bishops / queens along diagonals
        int[][] diagonal = {{-1, -1}, {-1, 1}, {1, -1}, {1, 1}};
        for (int[] d : diagonal) {
            Piece first = firstPieceInDirection(r, c, d[0], d[1]);
            if (first != null && first.getColor().equals(byColor)
                    && (first instanceof Bishop || first instanceof Queen)) return true;
        }

        return false;
    }

    private boolean inBounds(int r, int c) {
        return r >= 0 && r < 8 && c >= 0 && c < 8;
    }

    private boolean isEnemy(int r, int c, String color, Class<? extends Piece> type) {
        return inBounds(r, c)
                && grid[r][c] != null
                && grid[r][c].getColor().equals(color)
                && type.isInstance(grid[r][c]);
    }

    /** Walk from (r, c) in direction (dr, dc); return the first piece hit, or null. */
    private Piece firstPieceInDirection(int r, int c, int dr, int dc) {
        int nr = r + dr, nc = c + dc;
        while (inBounds(nr, nc)) {
            if (grid[nr][nc] != null) return grid[nr][nc];
            nr += dr;
            nc += dc;
        }
        return null;
    }

    // ------------------------------------------------------------------
    // LEGALITY
    // ------------------------------------------------------------------

    /**
     * A move is fully legal if the piece can make it AND it doesn't leave
     * the mover's own king in check. Simulating on a copy handles every
     * case at once: stepping into check, ignoring a check, and moving a pinned piece.
     */
    private boolean leavesKingSafe(int oldRow, int oldCol, int newRow, int newCol, String color) {
        Board sim = this.copy();
        sim.applyMoveWithoutCheck(oldRow, oldCol, newRow, newCol);
        return !sim.isInCheck(color);
    }

    public boolean hasAnyLegalMove(String color) {
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                Piece p = grid[r][c];
                if (p == null || !p.getColor().equals(color)) continue;

                List<Position> moves = p.possibleMoves(this);
                for (Position move : moves) {
                    if (leavesKingSafe(r, c, move.getRow(), move.getCol(), color)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public Board copy() {
        Board clone = new Board(true);
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                Piece p = this.grid[r][c];
                if (p != null) {
                    Piece cp = p.copy();
                    cp.setPosition(new Position(r, c));
                    clone.grid[r][c] = cp;
                }
            }
        }
        return clone;
    }

    private void applyMoveWithoutCheck(int oldRow, int oldCol, int newRow, int newCol) {
        Piece moving = grid[oldRow][oldCol];
        grid[newRow][newCol] = moving;
        grid[oldRow][oldCol] = null;
        if (moving != null) {
            moving.setPosition(new Position(newRow, newCol));
        }
    }

    // ------------------------------------------------------------------
    // MAKING A MOVE
    // ------------------------------------------------------------------

    /** Converts "e2" to {row, col}; returns null if the text isn't a valid square. */
    private Position parseSquare(String sq) {
        if (sq == null || sq.length() != 2) return null;
        sq = sq.toLowerCase();
        int col = sq.charAt(0) - 'a';
        int rank = sq.charAt(1) - '0';
        if (col < 0 || col > 7 || rank < 1 || rank > 8) return null;
        return new Position(8 - rank, col);
    }

    public boolean makeMove(String from, String to, String turnColor) {
        Position src = parseSquare(from);
        Position dst = parseSquare(to);
        if (src == null || dst == null) {
            System.out.println("Invalid square. Use a-h and 1-8, e.g. e2 e4");
            return false;
        }
        int oldRow = src.getRow(), oldCol = src.getCol();
        int newRow = dst.getRow(), newCol = dst.getCol();

        Piece piece = grid[oldRow][oldCol];
        if (piece == null) {
            System.out.println("No piece at " + from);
            return false;
        }
        if (!piece.getColor().equals(turnColor)) {
            System.out.println("Not your piece");
            return false;
        }

        // Is the destination in the piece's own move list?
        boolean reachable = false;
        for (Position p : piece.possibleMoves(this)) {
            if (p.getRow() == newRow && p.getCol() == newCol) {
                reachable = true;
                break;
            }
        }
        if (!reachable) {
            System.out.println("Illegal move (not in move list)");
            return false;
        }

        // Would our king be safe afterwards?
        if (!leavesKingSafe(oldRow, oldCol, newRow, newCol, turnColor)) {
            if (isInCheck(turnColor)) {
                System.out.println("Illegal move: you must escape check");
            } else {
                System.out.println("Illegal move: your king would be in check");
            }
            return false;
        }

        applyMoveWithoutCheck(oldRow, oldCol, newRow, newCol);
        return true;
    }
}