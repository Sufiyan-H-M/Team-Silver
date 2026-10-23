package pieces;
import game.Board;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a queen chess piece and calculates its possible moves.
 */
public class Queen extends Piece{
    final String symbol = "Q";

    /**
     * Creates a queen with the specified color and position.
     *
     * @param pieceColor the color of the queen
     * @param position the initial position of the queen
     */
    public Queen(PieceColor pieceColor, Position position){
        super(pieceColor, position);
    }

    /**
     * Returns the symbol representing the queen.
     *
     * @return the queen's symbol
     */
    public String getSymbol(){
        return symbol;
    }

    /**
     * Calculates the queen's possible moves in all horizontal, vertical,
     * and diagonal directions on the specified board.
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
        while (up >= 0 && temp[up][c] == null) {
            moveList.add(new Position(up, c));
            up--;
        }
        if (up >= 0 && temp[up][c] != null &&
                temp[up][c].getColor() != this.getColor()) {
            moveList.add(new Position(up, c));
        }

        int down = r + 1;
        while (down < 8 && temp[down][c] == null) {
            moveList.add(new Position(down, c));
            down++;
        }
        if (down < 8 && temp[down][c] != null &&
                temp[down][c].getColor() != this.getColor()) {
            moveList.add(new Position(down, c));
        }

        int left = c - 1;
        while (left >= 0 && temp[r][left] == null) {
            moveList.add(new Position(r, left));
            left--;
        }
        if (left >= 0 && temp[r][left] != null &&
                temp[r][left].getColor() != this.getColor()) {
            moveList.add(new Position(r, left));
        }

        int right = c + 1;
        while (right < 8 && temp[r][right] == null) {
            moveList.add(new Position(r, right));
            right++;
        }
        if (right < 8 && temp[r][right] != null &&
                temp[r][right].getColor() != this.getColor()) {
            moveList.add(new Position(r, right));
        }


        up = r - 1;
        right = c + 1;
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
        left = c - 1;
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

        down = r + 1;
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
     * Creates an independent copy of this queen at the same position.
     *
     * @return a copy of this queen
     */
    @Override
    public Piece copy() {
        return new Queen(getPieceColor(),
                new Position(getPosition().getRow(), getPosition().getCol()));
    }

}