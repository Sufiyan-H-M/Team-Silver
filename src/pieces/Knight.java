package pieces;
import game.Board;


import java.util.ArrayList;
import java.util.List;

/**
 * Represents a knight chess piece and calculates its possible moves.
 */
public class Knight extends Piece{
    final String symbol = "N";

    /**
     * Creates a knight with the specified color and position.
     *
     * @param pieceColor the color of the knight
     * @param position the initial position of the knight
     */
    public Knight(PieceColor pieceColor, Position position){
        super(pieceColor, position);
    }

    /**
     * Returns the symbol representing the knight.
     *
     * @return the knight's symbol
     */
    public String getSymbol(){
        return symbol;
    }

    /**
     * Calculates the knight's possible L-shaped moves, excluding destinations
     * occupied by pieces of the same color.
     *
     * @param board the current chessboard
     * @return a list of possible destination positions
     */
    public List<Position> possibleMoves(Board board) {
        int r = this.getPosition().getRow();
        int c = this.getPosition().getCol();

        List<Position> moveList = new ArrayList<>();
        Piece[][] temp = board.getGrid();

        if (r - 2 >= 0 && c - 1 >= 0 &&
                (temp[r - 2][c - 1] == null ||
                        !temp[r - 2][c - 1].getColor().equals(this.getColor()))) {

            moveList.add(new Position(r - 2, c - 1));
        }
        if (r - 2 >= 0 && c + 1 < 8 &&
                (temp[r - 2][c + 1] == null ||
                        !temp[r - 2][c + 1].getColor().equals(this.getColor()))) {

            moveList.add(new Position(r - 2, c + 1));
        }
        if (r - 1 >= 0 && c - 2 >= 0 &&
                (temp[r - 1][c - 2] == null ||
                        !temp[r - 1][c - 2].getColor().equals(this.getColor()))) {

            moveList.add(new Position(r - 1, c - 2));
        }
        if (r - 1 >= 0 && c + 2 < 8 &&
                (temp[r - 1][c + 2] == null ||
                        !temp[r - 1][c + 2].getColor().equals(this.getColor()))) {

            moveList.add(new Position(r - 1, c + 2));
        }
        if (r + 1 < 8 && c - 2 >= 0 &&
                (temp[r + 1][c - 2] == null ||
                        !temp[r + 1][c - 2].getColor().equals(this.getColor()))) {

            moveList.add(new Position(r + 1, c - 2));
        }
        if (r + 1 < 8 && c + 2 < 8 &&
                (temp[r + 1][c + 2] == null ||
                        !temp[r + 1][c + 2].getColor().equals(this.getColor()))) {

            moveList.add(new Position(r + 1, c + 2));
        }
        if (r + 2 < 8 && c - 1 >= 0 &&
                (temp[r + 2][c - 1] == null ||
                        !temp[r + 2][c - 1].getColor().equals(this.getColor()))) {

            moveList.add(new Position(r + 2, c - 1));
        }
        if (r + 2 < 8 && c + 1 < 8 &&
                (temp[r + 2][c + 1] == null ||
                        !temp[r + 2][c + 1].getColor().equals(this.getColor()))) {

            moveList.add(new Position(r + 2, c + 1));
        }

        return moveList;
    }

    /**
     * Creates an independent copy of this knight at the same position.
     *
     * @return a copy of this knight
     */
    @Override
    public Piece copy() {
        return new Knight(getPieceColor(),
                new Position(getPosition().getRow(), getPosition().getCol()));
    }



}