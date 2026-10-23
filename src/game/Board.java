package game;

import java.util.ArrayList;
import java.util.List;
import pieces.*;

/**
 * Represents a chessboard and manages its pieces, moves, captures, and
 * board state.
 */
public class Board {
    private Piece[][] grid = new Piece[8][8];
    private List<Position[]> trackedMoveList = new ArrayList<>();
    private List<String> trackCaptures = new ArrayList<>();

    String BG_RED    = "\u001B[41m";
    String BG_YELLOW = "\u001B[43m";
    String BG_ORANGE = "\u001B[48;5;208m";

    String BLACK_TEXT = "\u001B[30m";
    String WHITE_BACKGROUND = "\u001B[107m";

    String FG_BLACK = "\u001B[30m";
    String FG_WHITE = "\u001B[97m";

    String BG_MAROON = "\u001B[48;5;88m";
    String BG_TUSK = "\u001B[48;5;230m";

    String RESET = "\u001B[0m";

    /**
     * Creates a board and initializes the pieces in their starting positions.
     */
    public Board() {
        initialize();
    }

    /**
     * Creates an empty board without initializing the pieces.
     *
     * @param empty indicates that the board should remain empty
     */
    private Board(boolean empty) {}

    /**
     * Returns the board's piece grid.
     *
     * @return the two-dimensional array containing the board's pieces
     */
    public Piece[][] getGrid() {
        return grid;
    }

    /**
     * Returns the list of tracked moves.
     *
     * @return the list of move position pairs
     */
    public List<Position[]> getMoveList(){
        return trackedMoveList;
    }

    /**
     * Returns the list of captured pieces.
     *
     * @return the list of captured piece descriptions
     */
    List<String> getCaptures() {
        return trackCaptures;
    }

    /**
     * Initializes the board with all pieces in their standard starting positions.
     */
    public void initialize() {
        placeBackRank(0, PieceColor.BLACK);
        for (int c = 0; c < 8; c++) {
            grid[1][c] = new Pawn(PieceColor.BLACK, new Position(1, c));
        }
        for (int c = 0; c < 8; c++) {
            grid[6][c] = new Pawn(PieceColor.WHITE, new Position(6, c));
        }
        placeBackRank(7, PieceColor.WHITE);
    }

    /**
     * Places the back-rank pieces for the specified color.
     *
     * @param row the row in which to place the pieces
     * @param color the color of the pieces
     */
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

    /**
     * Prints the current board to the console.
     */
    public void printBoard() {
        new BoardPrinter().print(this);
    }

    /**
     * Converts a board position into standard chess notation.
     *
     * @param pos the position to convert
     * @return the position in chess notation, such as {@code e4}
     */
    public String convertToChessNotation(Position pos) {
        char file = (char) ('a' + pos.getCol());
        int rank = 8 - pos.getRow();

        return "" + file + rank;
    }

    /**
     * Determines whether the king of the specified color is currently in check.
     *
     * @param color the color of the king to check
     * @return {@code true} if the king is in check; {@code false} otherwise
     */
    public boolean isInCheck(String color) {
        return new CheckValidator(this).isInCheck(color);
    }

    /**
     * Determines whether a move leaves the moving player's king safe.
     *
     * @param oldRow the starting row
     * @param oldCol the starting column
     * @param newRow the destination row
     * @param newCol the destination column
     * @param color the color of the moving player
     * @return {@code true} if the move leaves the king safe; {@code false} otherwise
     */
    boolean leavesKingSafe(int oldRow, int oldCol, int newRow, int newCol, String color) {
        return new CheckValidator(this).leavesKingSafe(oldRow, oldCol, newRow, newCol, color);
    }

    /**
     * Determines whether the specified color has at least one legal move.
     *
     * @param color the color of the player
     * @return {@code true} if at least one legal move exists; {@code false} otherwise
     */
    public boolean hasAnyLegalMove(String color) {
        return new CheckValidator(this).hasAnyLegalMove(color);
    }

    /**
     * Creates a copy of the board with independent copies of its pieces.
     *
     * @return a copy of the current board
     */
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

    /**
     * Moves a piece to a destination without checking whether the move is legal.
     *
     * @param oldRow the starting row
     * @param oldCol the starting column
     * @param newRow the destination row
     * @param newCol the destination column
     */
    void applyMoveWithoutCheck(int oldRow, int oldCol, int newRow, int newCol) {
        Piece moving = grid[oldRow][oldCol];
        grid[newRow][newCol] = moving;
        grid[oldRow][oldCol] = null;
        if (moving != null) {
            moving.setPosition(new Position(newRow, newCol));
        }
    }

    /**
     * Attempts to make a move from one chess position to another.
     *
     * @param from the starting position in chess notation
     * @param to the destination position in chess notation
     * @param turnColor the color of the player making the move
     * @return {@code true} if the move succeeds; {@code false} otherwise
     */
    public boolean makeMove(String from, String to, String turnColor) {
        return new MoveHandler(this).makeMove(from, to, turnColor);
    }
}