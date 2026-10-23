package pieces;
import game.Board;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a king chess piece and calculates its possible moves.
 */
public class King extends Piece{
    final String symbol = "K";

    /**
     * Creates a king with the specified color and position.
     *
     * @param pieceColor the color of the king
     * @param position the initial position of the king
     */
    public King(PieceColor pieceColor, Position position){
        super(pieceColor, position);
    }

    /**
     * Returns the symbol representing the king.
     *
     * @return the king's symbol
     */
    public String getSymbol(){
        return symbol;
    }

    /**
     * Calculates the king's possible moves to adjacent squares, excluding
     * moves that place it next to the opposing king.
     *
     * @param board the current chessboard
     * @return a list of possible destination positions
     */
    public List<Position> possibleMoves(Board board) {
        List<Position> moveList = new ArrayList<>();
        int r = this.getPosition().getRow();
        int c = this.getPosition().getCol();

        Piece[][] temp = board.getGrid();

        if (r - 1 >= 0) {
            Piece p = temp[r - 1][c];
            if (p == null || !p.getColor().equals(this.getColor())) {
                moveList.add(new Position(r - 1, c));
            }
        }

        if (r + 1 < 8) {
            Piece p = temp[r + 1][c];
            if (p == null || !p.getColor().equals(this.getColor())) {
                moveList.add(new Position(r + 1, c));
            }
        }

        if (c + 1 < 8) {
            Piece p = temp[r][c + 1];
            if (p == null || !p.getColor().equals(this.getColor())) {
                moveList.add(new Position(r, c + 1));
            }
        }

        if (c - 1 >= 0) {
            Piece p = temp[r][c - 1];
            if (p == null || !p.getColor().equals(this.getColor())) {
                moveList.add(new Position(r, c - 1));
            }
        }

        if (r - 1 >= 0 && c + 1 < 8) {
            Piece p = temp[r - 1][c + 1];
            if (p == null || !p.getColor().equals(this.getColor())) {
                moveList.add(new Position(r - 1, c + 1));
            }
        }

        if (r - 1 >= 0 && c - 1 >= 0) {
            Piece p = temp[r - 1][c - 1];
            if (p == null || !p.getColor().equals(this.getColor())) {
                moveList.add(new Position(r - 1, c - 1));
            }
        }

        if (r + 1 < 8 && c + 1 < 8) {
            Piece p = temp[r + 1][c + 1];
            if (p == null || !p.getColor().equals(this.getColor())) {
                moveList.add(new Position(r + 1, c + 1));
            }
        }

        if (r + 1 < 8 && c - 1 >= 0) {
            Piece p = temp[r + 1][c - 1];
            if (p == null || !p.getColor().equals(this.getColor())) {
                moveList.add(new Position(r + 1, c - 1));
            }
        }

        List<Position> toRemove = new ArrayList<>();

        for (Position pos : moveList) {
            int nr = pos.getRow();
            int nc = pos.getCol();

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

                        toRemove.add(pos);
                    }
                }
            }
        }

        moveList.removeAll(toRemove);

        return moveList;
    }

    /**
     * Creates an independent copy of this king at the same position.
     *
     * @return a copy of this king
     */
    @Override
    public Piece copy() {
        return new King(getPieceColor(),
                new Position(getPosition().getRow(), getPosition().getCol()));
    }
}