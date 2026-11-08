package game;

/**
 * Represents the status of a chess game after the most recent move.
 * {@link #CHECKMATE} and {@link #STALEMATE} end the game, so no further
 * moves are accepted once either is reached.
 */
public enum Status {

    /** The game is ongoing and the player to move is not in check. */
    IN_PROGRESS,

    /** The player to move is in check but has at least one legal move. */
    CHECK,

    /** The player to move is in check and has no legal move, so the opponent wins. */
    CHECKMATE,

    /** The player to move is not in check but has no legal move, so the game is a draw. */
    STALEMATE;
}