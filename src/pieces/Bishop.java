package pieces;
import game.Board;

import java.util.ArrayList;
import java.util.List;

public class Bishop extends Piece{
    final String symbol = "B";

    public Bishop(PieceColor pieceColor, Position position){
        super(pieceColor, position);
    }

    public String getSymbol(){
        return symbol;
    }

    public List<Position> possibleMoves(Board board){
        List<Position> moveList = new ArrayList<>();
        int r = this.getPosition().getRow();
        int c = this.getPosition().getCol();

        Piece[][] temp = board.getGrid();

        // -------- UP-RIGHT --------
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

            moveList.add(new Position(up, right)); // capture
        }

        // -------- UP-LEFT --------
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

            moveList.add(new Position(up, left)); // capture
        }

        // -------- DOWN-RIGHT --------
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
            moveList.add(new Position(down, right)); // capture
        }

        // -------- DOWN-LEFT --------
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

            moveList.add(new Position(down, left)); // capture
        }
        return moveList;
    }


}
