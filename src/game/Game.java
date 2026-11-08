package game;

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
     * Attempts to make a move for the player whose turn it is. If the move is
     * accepted, the status is updated to check, checkmate, stalemate, or in
     * progress, and the turn passes to the opponent unless the game has ended.
     * If the move is rejected, nothing changes.
     *
     * @param from the starting square in chess notation, such as {@code e2}
     * @param to the destination square in chess notation, such as {@code e4}
     * @return {@link MoveResult#ACCEPTED} if the move was made, otherwise the
     *         {@link MoveResult} explaining why it was rejected, including
     *         {@link MoveResult#GAME_OVER} if the game has already ended
     */
    public MoveResult makeMove(String from, String to) {
        if (status == Status.CHECKMATE || status == Status.STALEMATE) {
            return MoveResult.GAME_OVER;
        }

        MoveResult result = board.makeMove(from, to, turn);
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