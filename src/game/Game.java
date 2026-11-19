package game;

import pieces.*;

/**
 * Represents a single chess game and is the entry point used by clients such
 * as the console and the graphical interface. It owns the {@link Board}, tracks
 * whose turn it is, and tracks the current {@link Status} of the game.
 * <p>
 * Clients submit moves through {@link #makeMove(String, String)} and read the
 * resulting state through the getters. A rejected move never changes the
 * board, the turn, or the status.
 */
public class Game {
    private Board board;
    private String turn;
    private Status status;

    /**
     * Creates a new game with the pieces in their starting positions,
     * white to move, and the status set to {@link Status#IN_PROGRESS}.
     */
    public Game(){
        this.board = new Board();
        turn = "w";
        status = Status.IN_PROGRESS;
    }

    /**
     * Returns the color of the player whose turn it is. After checkmate the
     * turn does not switch, so this is the color of the winner.
     *
     * @return {@code "w"} for white or {@code "b"} for black
     */
    public String getTurn(){return turn;}

    /**
     * Returns the current status of the game.
     *
     * @return the game status
     */
    public Status getStatus(){return status;}

    /**
     * Returns the board for this game.
     *
     * @return the current board
     */
    public Board getBoard(){return board;}

    /**
     * Restarts the game with a new board, white to move, and the status set
     * to {@link Status#IN_PROGRESS}. The move and capture history is cleared.
     */
    public void restart(){
        board = new Board();
        turn = "w";
        status = Status.IN_PROGRESS;
    }

    /**
     * Determines whether moving the piece on one square to another would
     * promote a pawn, so a client knows when to ask the player which piece
     * to choose.
     *
     * @param from the starting position
     * @param to the destination position
     * @return {@code true} if the move is a legal pawn move to the last rank
     */
    public boolean isPromotionMove(Position from, Position to) {
        Piece piece = board.getGrid()[from.getRow()][from.getCol()];
        if (!(piece instanceof Pawn) || !piece.getColor().equals(turn)) {
            return false;
        }
        if (to.getRow() != 0 && to.getRow() != 7) {
            return false;
        }
        for (Position p : piece.possibleMoves(board)) {
            if (p.getRow() == to.getRow() && p.getCol() == to.getCol()) {
                return board.leavesKingSafe(from.getRow(), from.getCol(),
                        to.getRow(), to.getCol(), turn);
            }
        }
        return false;
    }

    /**
     * Attempts to make a move for the player whose turn it is. A pawn that
     * reaches the last rank is promoted to a queen. If the move is accepted,
     * the status is updated to check, checkmate, stalemate, or in progress,
     * and the turn passes to the opponent unless the game has ended.
     * If the move is rejected, nothing changes.
     *
     * @param from the starting square in chess notation, such as {@code e2}
     * @param to the destination square in chess notation, such as {@code e4}
     * @return {@link MoveResult#ACCEPTED} if the move was made, otherwise the
     *         {@link MoveResult} explaining why it was rejected, including
     *         {@link MoveResult#GAME_OVER} if the game has already ended
     */
    public MoveResult makeMove(String from, String to) {
        return makeMove(from, to, null);
    }

    /**
     * Attempts to make a move for the player whose turn it is, promoting a
     * pawn that reaches the last rank to the given piece. If the move is
     * accepted, the status is updated to check, checkmate, stalemate, or in
     * progress, and the turn passes to the opponent unless the game has
     * ended. If the move is rejected, nothing changes.
     *
     * @param from the starting square in chess notation, such as {@code e2}
     * @param to the destination square in chess notation, such as {@code e4}
     * @param promotion {@code "Q"}, {@code "R"}, {@code "B"} or {@code "N"},
     *                  or {@code null} for a queen
     * @return {@link MoveResult#ACCEPTED} if the move was made, otherwise the
     *         {@link MoveResult} explaining why it was rejected, including
     *         {@link MoveResult#GAME_OVER} if the game has already ended
     */
    public MoveResult makeMove(String from, String to, String promotion) {
        if (status == Status.CHECKMATE || status == Status.STALEMATE) {
            return MoveResult.GAME_OVER;
        }

        MoveResult result = board.makeMove(from, to, turn, promotion);
        if (result != MoveResult.ACCEPTED) {
            return result;   // pass the reason up unchanged
        }

        String opponent = turn.equals("w") ? "b" : "w";
        boolean inCheck = board.isInCheck(opponent);
        boolean hasLegalMove = board.hasAnyLegalMove(opponent);

        if (inCheck && !hasLegalMove) {
            status = Status.CHECKMATE;
        } else if (inCheck) {
            status = Status.CHECK;
        } else if (!hasLegalMove) {
            status = Status.STALEMATE;
        } else {
            status = Status.IN_PROGRESS;
        }

        if (status != Status.CHECKMATE && status != Status.STALEMATE) {
            turn = opponent;
        }

        return MoveResult.ACCEPTED;
    }
}