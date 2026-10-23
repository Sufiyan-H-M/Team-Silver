package pieces;
import game.Board;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a rook chess piece and calculates its possible moves.
 */
public class Rook extends Piece {
    final String symbol = "R";

    /**
     * Creates a rook with the specified color and position.
     *
     * @param pieceColor the color of the rook
     * @param position the initial position of the rook
     */
    public Rook(PieceColor pieceColor, Position position){
        super(pieceColor, position);
    }

    /**
     * Returns the symbol representing the rook.
     *
     * @return the rook's symbol
     */
    public String getSymbol(){
        return symbol;
    }

    /**
     * Calculates the rook's possible moves along horizontal and vertical
     * directions, including captures of opposing pieces.
     *
     * @param board the current chessboard
     * @return a list of possible destination positions
     */
    public List<Position> possibleMoves(Board board){
        List<Position> moveList = new ArrayList<>();
        int r = this.getPosition().getRow();
        int c = this.getPosition().getCol();

        Piece[][] temp = board.getGrid();

        int u = r - 1;
        while (u >= 0 && temp[u][c] == null) {
            moveList.add(new Position(u, c));
            u--;
        }
        if (u >= 0 && temp[u][c] != null &&
                !temp[u][c].getColor().equals(this.getColor())) {
            moveList.add(new Position(u, c));
        }

        int d = r + 1;
        while (d < 8 && temp[d][c] == null) {
            moveList.add(new Position(d, c));
            d++;
        }
        if (d < 8 && temp[d][c] != null &&
                !temp[d][c].getColor().equals(this.getColor())) {
            moveList.add(new Position(d, c));
        }

        int l = c - 1;
        while (l >= 0 && temp[r][l] == null) {
            moveList.add(new Position(r, l));
            l--;
        }
        if (l >= 0 && temp[r][l] != null &&
                !temp[r][l].getColor().equals(this.getColor())) {
            moveList.add(new Position(r, l));
        }

        int rr = c + 1;
        while (rr < 8 && temp[r][rr] == null) {
            moveList.add(new Position(r, rr));
            rr++;
        }
        if (rr < 8 && temp[r][rr] != null &&
                !temp[r][rr].getColor().equals(this.getColor())) {
            moveList.add(new Position(r, rr));
        }

        return moveList;
    }

    /**
     * Creates an independent copy of this rook at the same position.
     *
     * @return a copy of this rook
     */
    @Override
    public Piece copy() {
        return new Rook(getPieceColor(),
                new Position(getPosition().getRow(), getPosition().getCol()));
    }


}