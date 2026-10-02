package pieces;

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

    public List<Position> possibleMoves(){
        List<Position> moveList = new ArrayList<>();


        return moveList;
    }

}
