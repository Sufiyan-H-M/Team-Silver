package pieces;
import game.Board;


import java.util.ArrayList;
import java.util.List;

public class Knight extends Piece{
    final String symbol = "N";

    public Knight(PieceColor pieceColor, Position position){
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

        // r-2, c-1
        if (r - 2 >= 0 && c - 1 >= 0 &&
                (temp[r - 2][c - 1] == null ||
                        !temp[r - 2][c - 1].getColor().equals(this.getColor()))) {

            moveList.add(new Position(r - 2, c - 1));
        }
        // r-2, c+1
        if (r - 2 >= 0 && c + 1 < 8 &&
                (temp[r - 2][c + 1] == null ||
                        !temp[r - 2][c + 1].getColor().equals(this.getColor()))) {

            moveList.add(new Position(r - 2, c + 1));
        }
        // r-1, c-2
        if (r - 1 >= 0 && c - 2 >= 0 &&
                (temp[r - 1][c - 2] == null ||
                        !temp[r - 1][c - 2].getColor().equals(this.getColor()))) {

            moveList.add(new Position(r - 1, c - 2));
        }
        // r-1, c+2
        if (r - 1 >= 0 && c + 2 < 8 &&
                (temp[r - 1][c + 2] == null ||
                        !temp[r - 1][c + 2].getColor().equals(this.getColor()))) {

            moveList.add(new Position(r - 1, c + 2));
        }
        // r+1, c-2
        if (r + 1 < 8 && c - 2 >= 0 &&
                (temp[r + 1][c - 2] == null ||
                        !temp[r + 1][c - 2].getColor().equals(this.getColor()))) {

            moveList.add(new Position(r + 1, c - 2));
        }
        // r+1, c+2
        if (r + 1 < 8 && c + 2 < 8 &&
                (temp[r + 1][c + 2] == null ||
                        !temp[r + 1][c + 2].getColor().equals(this.getColor()))) {

            moveList.add(new Position(r + 1, c + 2));
        }
        // r+2, c-1
        if (r + 2 < 8 && c - 1 >= 0 &&
                (temp[r + 2][c - 1] == null ||
                        !temp[r + 2][c - 1].getColor().equals(this.getColor()))) {

            moveList.add(new Position(r + 2, c - 1));
        }
        // r+2, c+1
        if (r + 2 < 8 && c + 1 < 8 &&
                (temp[r + 2][c + 1] == null ||
                        !temp[r + 2][c + 1].getColor().equals(this.getColor()))) {

            moveList.add(new Position(r + 2, c + 1));
        }

        return moveList;
    }



}
