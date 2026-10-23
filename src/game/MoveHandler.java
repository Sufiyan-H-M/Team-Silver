package game;

import pieces.*;

/**
 * Handles chess moves by validating input, checking piece movement rules,
 * recording captures and moves, and ensuring the king remains safe.
 */
public class MoveHandler {
    private final Board board;

    /**
     * Creates a move handler for the specified board.
     *
     * @param board the board on which moves are handled
     */
    public MoveHandler(Board board) {
        this.board = board;
    }

    /**
     * Converts a chess square such as {@code e2} into a board position.
     *
     * @param sq the square in chess notation
     * @return the corresponding position, or {@code null} if the square is invalid
     */
    private Position parseSquare(String sq) {
        if (sq == null || sq.length() != 2) return null;
        sq = sq.toLowerCase();
        int col = sq.charAt(0) - 'a';
        int rank = sq.charAt(1) - '0';
        if (col < 0 || col > 7 || rank < 1 || rank > 8) return null;
        return new Position(8 - rank, col);
    }

    /**
     * Attempts to move a piece from one square to another.
     *
     * @param from the starting square in chess notation
     * @param to the destination square in chess notation
     * @param turnColor the color of the player making the move
     * @return {@code true} if the move is successful; {@code false} otherwise
     */
    public boolean makeMove(String from, String to, String turnColor) {
        Position src = parseSquare(from);
        Position dst = parseSquare(to);
        if (src == null || dst == null) {
            System.out.println(board.BG_YELLOW + " Invalid square. Use a-h and 1-8, e.g. e2 e4 " + board.RESET);
            return false;
        }
        int oldRow = src.getRow(), oldCol = src.getCol();
        int newRow = dst.getRow(), newCol = dst.getCol();

        Piece[][] grid = board.getGrid();
        Piece piece = grid[oldRow][oldCol];
        if (piece == null) {
            System.out.println(board.BG_YELLOW + " No piece at " + from +" "+ board.RESET);
            return false;
        }
        if (!piece.getColor().equals(turnColor)) {
            System.out.println(board.BG_YELLOW + " Not your piece " + board.RESET);
            return false;
        }

        boolean reachable = false;
        for (Position p : piece.possibleMoves(board)) {
            if (p.getRow() == newRow && p.getCol() == newCol) {
                reachable = true;
                if (grid[dst.getRow()][dst.getCol()] != null) {
                    String sym = grid[dst.getRow()][dst.getCol()].getSymbol();
                    switch(sym){
                        case ("p"):
                            sym = "Pawn";
                            break;
                        case("K"):
                            sym = "King";
                            break;
                        case ("Q"):
                            sym = "Queen";
                            break;
                        case("N"):
                            sym = "Knight";
                            break;
                        case("B"):
                            sym = "Bishop";
                            break;
                        case ("R"):
                            sym = "Rook";
                            break;
                    }
                    board.getCaptures().add("captures " +
                            (grid[dst.getRow()][dst.getCol()].getColor()=="b" ? "Black " : "White ") + sym);
                } else{
                    board.getCaptures().add("");
                }
                board.getMoveList().add(new Position[]{src, dst});
                break;
            }
        }
        if (!reachable) {
            System.out.println(board.BG_ORANGE + " Illegal move (not in move list) " + board.RESET);
            return false;
        }

        if (!board.leavesKingSafe(oldRow, oldCol, newRow, newCol, turnColor)) {
            if (board.isInCheck(turnColor)) {
                System.out.println(board.BG_RED + " Illegal move: you must escape check " + board.RESET);
            } else {
                System.out.println(board.BG_RED + " Illegal move: your king would be in check " + board.RESET);
            }
            return false;
        }

        board.applyMoveWithoutCheck(oldRow, oldCol, newRow, newCol);
        return true;
    }
}