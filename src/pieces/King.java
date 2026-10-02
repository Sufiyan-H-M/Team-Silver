package pieces;
import game.Board;

import java.util.ArrayList;
import java.util.List;

public class King extends Piece{
    final String symbol = "K";

    public King(PieceColor pieceColor, Position position){
        super(pieceColor, position);
    }

    public String getSymbol(){
        return symbol;
    }

    public List<Position> possibleMoves(Board board) {
        List<Position> moveList = new ArrayList<>();
        int r = this.getPosition().getRow();
        int c = this.getPosition().getCol();

        Piece[][] temp = board.getGrid();

        // UP
        if (r - 1 >= 0) {
            Piece p = temp[r - 1][c];
            if (p == null || !p.getColor().equals(this.getColor())) {
                moveList.add(new Position(r - 1, c));
            }
        }

        // DOWN
        if (r + 1 < 8) {
            Piece p = temp[r + 1][c];
            if (p == null || !p.getColor().equals(this.getColor())) {
                moveList.add(new Position(r + 1, c));
            }
        }

        // RIGHT
        if (c + 1 < 8) {
            Piece p = temp[r][c + 1];
            if (p == null || !p.getColor().equals(this.getColor())) {
                moveList.add(new Position(r, c + 1));
            }
        }

        // LEFT
        if (c - 1 >= 0) {
            Piece p = temp[r][c - 1];
            if (p == null || !p.getColor().equals(this.getColor())) {
                moveList.add(new Position(r, c - 1));
            }
        }

        // UP-RIGHT
        if (r - 1 >= 0 && c + 1 < 8) {
            Piece p = temp[r - 1][c + 1];
            if (p == null || !p.getColor().equals(this.getColor())) {
                moveList.add(new Position(r - 1, c + 1));
            }
        }

        // UP-LEFT
        if (r - 1 >= 0 && c - 1 >= 0) {
            Piece p = temp[r - 1][c - 1];
            if (p == null || !p.getColor().equals(this.getColor())) {
                moveList.add(new Position(r - 1, c - 1));
            }
        }

        // DOWN-RIGHT
        if (r + 1 < 8 && c + 1 < 8) {
            Piece p = temp[r + 1][c + 1];
            if (p == null || !p.getColor().equals(this.getColor())) {
                moveList.add(new Position(r + 1, c + 1));
            }
        }

        // DOWN-LEFT
        if (r + 1 < 8 && c - 1 >= 0) {
            Piece p = temp[r + 1][c - 1];
            if (p == null || !p.getColor().equals(this.getColor())) {
                moveList.add(new Position(r + 1, c - 1));
            }
        }

        return moveList;
    }



}
