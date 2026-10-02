package pieces;

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

    public List<Position> possibleMoves(){
        List<Position> moveList = new ArrayList<>();


        return moveList;
    }

}
