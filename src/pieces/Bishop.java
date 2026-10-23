package pieces;
import game.Board;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a bishop chess piece and calculates its possible diagonal moves.
 */
public class Bishop extends Piece{
    final String symbol = "B";

    /**
     * Creates a bishop with the specified color and position.
     *
     * @param pieceColor the color of the bishop
     * @param position the initial position of the bishop
     */
    public Bishop(PieceColor pieceColor, Position position){
        super(pieceColor, position);
    }

    /**
     * Returns the symbol representing the bishop.
     *
     * @return the bishop's symbol
     */
    public String getSymbol(){
        return symbol;
    }

    /**
     * Calculates the bishop's possible diagonal moves, including captures
     * of opposing pieces, while stopping at occupied squares.
     *
     * @param board the current chessboard
     * @return a list of possible destination positions
     */
    public List<Position> possibleMoves(Board board){
        List<Position> moveList = new ArrayList<>();
        int r = this.getPosition().getRow();
        int c = this.getPosition().getCol();

        Piece[][] temp = board.getGrid();

        int up = r - 1;
        int right = c + 1;

        while (up >= 0 && right < 8 && temp[up][right] == null) {
            moveList.add(new Position(up, right));
            up--;
            right++;
        }
        if (up >= 0 && right < 8 &&
                temp[up][right] != null &&
                temp[up][right].getColor() != this.getColor()) {

            moveList.add(new Position(up, right));
        }

        up = r - 1;
        int left = c - 1;

        while (up >= 0 && left >= 0 && temp[up][left] == null) {
            moveList.add(new Position(up, left));
            up--;
            left--;
        }
        if (up >= 0 && left >= 0 &&
                temp[up][left] != null &&
                temp[up][left].getColor() != this.getColor()) {

            moveList.add(new Position(up, left));
        }

        int down = r + 1;
        right = c + 1;

        while (down < 8 && right < 8 && temp[down][right] == null) {
            moveList.add(new Position(down, right));
            down++;
            right++;
        }
        if (down < 8 && right < 8 &&
                temp[down][right] != null &&
                temp[down][right].getColor() != this.getColor()) {
            moveList.add(new Position(down, right));
        }

        down = r + 1;
        left = c - 1;

        while (down < 8 && left >= 0 && temp[down][left] == null) {
            moveList.add(new Position(down, left));
            down++;
            left--;
        }
        if (down < 8 && left >= 0 &&
                temp[down][left] != null &&
                temp[down][left].getColor() != this.getColor()) {

            moveList.add(new Position(down, left));
        }
        return moveList;
    }


    /**
     * Creates an independent copy of this bishop at the same position.
     *
     * @return a copy of this bishop
     */
    @Override
    public Piece copy() {
        return new Bishop(getPieceColor(),
                new Position(getPosition().getRow(), getPosition().getCol()));
    }


}