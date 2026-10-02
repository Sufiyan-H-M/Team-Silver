package pieces;
import game.Board;


import java.util.ArrayList;
import java.util.List;

public class Pawn extends Piece{
    final String symbol = "p";

    public Pawn(PieceColor pieceColor, Position position){
        super(pieceColor, position);
    }

    public String getSymbol(){
        return symbol;
    }

    public List<Position> possibleMoves(Board board) {
        int r = this.getPosition().getRow();
        int c = this.getPosition().getCol();
        List<Position> moveList = new ArrayList<>();

        Piece[][] temp = board.getGrid();

        if (super.getColor().equals("w")) {
            // one step forward
            if (r - 1 >= 0 && temp[r - 1][c] == null) {
                moveList.add(new Position(r - 1, c));
            }
            // two steps forward (starting rank = 6)
            if (r == 6 && temp[r - 1][c] == null && temp[r - 2][c] == null) {
                moveList.add(new Position(r - 2, c));
            }
            // capture right
            if (r - 1 >= 0 && c + 1 < 8 &&
                    temp[r - 1][c + 1] != null &&
                    temp[r - 1][c + 1].getColor().equals("b")) {

                moveList.add(new Position(r - 1, c + 1));
            }
            // capture left
            if (r - 1 >= 0 && c - 1 >= 0 &&
                    temp[r - 1][c - 1] != null &&
                    temp[r - 1][c - 1].getColor().equals("b")) {

                moveList.add(new Position(r - 1, c - 1));
            }
        } else{
            // one step forward
            if (r + 1 < 8 && temp[r + 1][c] == null) {
                moveList.add(new Position(r + 1, c));
            }

            // two steps forward (starting rank = 1)
            if (r == 1 && temp[r + 1][c] == null && temp[r + 2][c] == null) {
                moveList.add(new Position(r + 2, c));
            }

            // capture right (down-right)
            if (r + 1 < 8 && c + 1 < 8 &&
                    temp[r + 1][c + 1] != null &&
                    temp[r + 1][c + 1].getColor().equals("w")) {

                moveList.add(new Position(r + 1, c + 1));
            }

            // capture left (down-left)
            if (r + 1 < 8 && c - 1 >= 0 &&
                    temp[r + 1][c - 1] != null &&
                    temp[r + 1][c - 1].getColor().equals("w")) {

                moveList.add(new Position(r + 1, c - 1));
            }
        }
        return moveList;
    }

}
