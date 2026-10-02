package pieces;

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

    public List<Position> possibleMoves(){
        List<Position> moveList = new ArrayList<>();


        return moveList;
    }

}
