package game;

/**
 * Describes the outcome of an attempt to make a move. A move is applied only
 * when the result is {@link #ACCEPTED}; every other value explains why the
 * move was rejected. Each value carries a human-readable message that clients
 * such as the console and the graphical interface can display.
 */
public enum MoveResult {

    /** One or both squares are not valid chess coordinates (a-h, 1-8). */
    INVALID_SQUARE(" Invalid square. Use a-h and 1-8, e.g. e2 e4 "),

    /** The starting square does not contain a piece. */
    NO_PIECE_AT_POSITION(" No piece at this position "),

    /** The piece on the starting square belongs to the other player. */
    NOT_YOUR_PIECE(" This is not your piece "),

    /** The piece cannot move to the destination according to its movement rules. */
    ILLEGAL_MOVE(" This move is not in the move list "),

    /** The player's king is in check and this move does not get it out of check. */
    ESCAPE_CHECK(" Your king is in check, you must first escape check "),

    /** The move would expose the player's own king to check. */
    PUTS_KING_IN_CHECK(" This move puts your king in check "),

    /** The move was valid and has been applied to the board. */
    ACCEPTED(" Valid move "),

    /** The game has already ended in checkmate or stalemate, so no moves are allowed. */
    GAME_OVER(" The game is over ");

    private final String message;

    /**
     * Creates a result with the specified display message.
     *
     * @param message the message describing this result
     */
    MoveResult(String message){
        this.message = message;
    }

    /**
     * Returns the message describing this result, suitable for showing to the player.
     *
     * @return the display message
     */
    public String getMessage(){ return message; }
}