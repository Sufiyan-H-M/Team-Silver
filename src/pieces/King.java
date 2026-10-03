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

        // Remove moves that place king next to enemy king
        List<Position> toRemove = new ArrayList<>();

        for (Position pos : moveList) {
            int nr = pos.getRow();
            int nc = pos.getCol();

            // Check all 8 surrounding squares for an enemy king
            int[][] kingAdj = {
                    { -1, -1 }, { -1, 0 }, { -1, 1 },
                    {  0, -1 },           {  0, 1 },
                    {  1, -1 }, {  1, 0 }, {  1, 1 }
            };

            for (int[] m : kingAdj) {
                int ar = nr + m[0];
                int ac = nc + m[1];

                if (ar >= 0 && ar < 8 && ac >= 0 && ac < 8) {
                    Piece p = temp[ar][ac];

                    if (p != null &&
                            !p.getColor().equals(this.getColor()) &&
                            p.getSymbol().equals("k")) {

                        // This move puts king next to enemy king → illegal
                        toRemove.add(pos);
                    }
                }
            }
        }

// Remove illegal king-adjacent moves
        moveList.removeAll(toRemove);

        return moveList;
    }







}
