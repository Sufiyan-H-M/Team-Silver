package pieces;
import game.Board;
import java.util.ArrayList;
import java.util.List;

public class Rook extends Piece{
    final String symbol = "R";

    public Rook(PieceColor pieceColor, Position position){
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

        // ---------------- UP ----------------
        int u = r - 1;
        while (u >= 0 && temp[u][c] == null) {
            moveList.add(new Position(u, c));
            u--;
        }
        if (u >= 0 && temp[u][c] != null &&
                !temp[u][c].getColor().equals(this.getColor())) {
            moveList.add(new Position(u, c)); // capture
        }

        // ---------------- DOWN ----------------
        int d = r + 1;
        while (d < 8 && temp[d][c] == null) {
            moveList.add(new Position(d, c));
            d++;
        }
        if (d < 8 && temp[d][c] != null &&
                !temp[d][c].getColor().equals(this.getColor())) {
            moveList.add(new Position(d, c)); // capture
        }

        // ---------------- LEFT ----------------
        int l = c - 1;
        while (l >= 0 && temp[r][l] == null) {
            moveList.add(new Position(r, l));
            l--;
        }
        if (l >= 0 && temp[r][l] != null &&
                !temp[r][l].getColor().equals(this.getColor())) {
            moveList.add(new Position(r, l)); // capture
        }

        // ---------------- RIGHT ----------------
        int rr = c + 1;
        while (rr < 8 && temp[r][rr] == null) {
            moveList.add(new Position(r, rr));
            rr++;
        }
        if (rr < 8 && temp[r][rr] != null &&
                !temp[r][rr].getColor().equals(this.getColor())) {
            moveList.add(new Position(r, rr)); // capture
        }

        return moveList;
    }


}
