package pieces;

import java.util.ArrayList;
import java.util.List;

public class Queen extends Piece{
    final String symbol = "Q";

    public Queen(PieceColor pieceColor, Position position){
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
