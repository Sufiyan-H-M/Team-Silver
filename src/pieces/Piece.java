package pieces;
import game.Board;

import java.util.List;

/**
 * Represents an abstract chess piece with a position and color.
 * Provides common methods for accessing and modifying piece properties.
 */
public abstract class Piece {

    private Position position;
    private PieceColor color;

    /**
     * Creates a piece with the specified color and position.
     *
     * @param color the color of the piece
     * @param position the position of the piece on the board
     */
    public Piece(PieceColor color, Position position) {
        this.color = color;
        this.position = position;
    }

    /**
     * Returns the current position of the piece.
     *
     * @return the piece's position
     */
    public Position getPosition(){
        return position;
    }

    /**
     * Returns the piece's color as a single-character string.
     *
     * @return "b" for black or "w" for white
     */
    public String getColor(){
        if(color == PieceColor.BLACK){
            return "b";
        } else {
            return "w";
        }
    }

    /**
     * Updates the position of the piece.
     *
     * @param p the new position
     */
    public void setPosition(Position p){
        position = p;
    }

    /**
     * Updates the color of the piece.
     *
     * @param c the new color
     */
    public void setColor(PieceColor c){
        color = c;
    }

    /**
     * Returns the symbol used to represent this piece.
     *
     * @return the piece's symbol
     */
    public abstract String getSymbol();

    /**
     * Creates an independent copy of this piece.
     *
     * @return a copy of this piece
     */
    public abstract Piece copy();

    /**
     * Returns the piece's color as a {@link PieceColor} value.
     *
     * @return the piece's color
     */
    protected PieceColor getPieceColor() {
        return color;
    }

    /**
     * Calculates the possible moves for this piece on the specified board.
     *
     * @param board the current chessboard
     * @return a list of possible destination positions
     */
    public abstract List<Position> possibleMoves(Board board);
}