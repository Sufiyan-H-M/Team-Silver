package game;

import pieces.*;

/**
 * Handles chess moves by validating input, checking piece movement rules,
 * recording captures and moves, promoting pawns, and ensuring the king
 * remains safe.
 * Instead of printing messages, it reports the outcome of every attempt as a
 * {@link MoveResult}, so that any client can decide how to display it.
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
     * Creates the piece a pawn is promoted to.
     *
     * @param type {@code "R"}, {@code "B"} or {@code "N"}; anything else gives a queen
     * @param color the color of the new piece
     * @param pos the position of the new piece
     * @return the new piece
     */
    private Piece createPiece(String type, PieceColor color, Position pos) {
        if ("R".equals(type)) return new Rook(color, pos);
        if ("B".equals(type)) return new Bishop(color, pos);
        if ("N".equals(type)) return new Knight(color, pos);
        return new Queen(color, pos);
    }

    /**
     * Attempts to move a piece from one square to another. A pawn that
     * reaches the last rank is promoted to a queen.
     *
     * @param from the starting square in chess notation
     * @param to the destination square in chess notation
     * @param turnColor the color of the player making the move
     * @return {@link MoveResult#ACCEPTED} if the move was applied, otherwise
     *         the {@link MoveResult} explaining why it was rejected
     */
    public MoveResult makeMove(String from, String to, String turnColor) {
        return makeMove(from, to, turnColor, null);
    }

    /**
     * Attempts to move a piece from one square to another. The move is
     * checked for valid squares, piece ownership, the piece's movement rules,
     * and king safety. If every check passes, the capture and the move are
     * recorded and the move is applied to the board, and a pawn that reaches
     * the last rank is replaced by the requested piece. If any check fails,
     * the board and its history are left unchanged.
     *
     * @param from the starting square in chess notation
     * @param to the destination square in chess notation
     * @param turnColor the color of the player making the move
     * @param promotion the promotion piece ({@code "Q"}, {@code "R"},
     *                  {@code "B"} or {@code "N"}), or {@code null} to
     *                  default to a queen
     * @return {@link MoveResult#ACCEPTED} if the move was applied, otherwise
     *         the {@link MoveResult} explaining why it was rejected
     */
    public MoveResult makeMove(String from, String to, String turnColor, String promotion) {
        Position src = parseSquare(from);
        Position dst = parseSquare(to);
        if (src == null || dst == null) {
            return MoveResult.INVALID_SQUARE;
        }
        int oldRow = src.getRow(), oldCol = src.getCol();
        int newRow = dst.getRow(), newCol = dst.getCol();

        Piece[][] grid = board.getGrid();
        Piece piece = grid[oldRow][oldCol];
        if (piece == null) {
            return MoveResult.NO_PIECE_AT_POSITION;
        }
        if (!piece.getColor().equals(turnColor)) {
            return MoveResult.NOT_YOUR_PIECE;
        }

        boolean reachable = false;
        String captureText = "";
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
                    captureText = "captures " +
                            (grid[dst.getRow()][dst.getCol()].getColor().equals("b") ? "Black " : "White ") + sym;
                }
                break;
            }
        }
        if (!reachable) {
            return MoveResult.ILLEGAL_MOVE;
        }

        if (!board.leavesKingSafe(oldRow, oldCol, newRow, newCol, turnColor)) {
            if (board.isInCheck(turnColor)) {
                return MoveResult.ESCAPE_CHECK;
            } else {
                return MoveResult.PUTS_KING_IN_CHECK;
            }
        }

        board.getCaptures().add(captureText);
        board.getMoveList().add(new Position[]{src, dst});
        board.applyMoveWithoutCheck(oldRow, oldCol, newRow, newCol);

        // Promote a pawn that reached the last rank
        Piece moved = grid[newRow][newCol];
        if (moved instanceof Pawn && (newRow == 0 || newRow == 7)) {
            PieceColor color = moved.getColor().equals("w") ? PieceColor.WHITE : PieceColor.BLACK;
            grid[newRow][newCol] = createPiece(promotion, color, new Position(newRow, newCol));
        }

        return MoveResult.ACCEPTED;
    }
}