package pieces;

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

    public List<Position> possibleMoves(){
        List<Position> moveList = new ArrayList<>();
        moveList.add(new Position(this.getPosition().getRow() - 1,
                                       this.getPosition().getCol())); // Move forward for white
        moveList.add(new Position(this.getPosition().getRow() + 1,
                                       this.getPosition().getCol())); // Move back for white
        moveList.add(new Position(this.getPosition().getRow(),
                                   this.getPosition().getCol() + 1)); // Move right for white
        moveList.add(new Position(this.getPosition().getRow(),
                                   this.getPosition().getCol() - 1)); // Move left for white
        moveList.add(new Position(this.getPosition().getRow() - 1,
                                   this.getPosition().getCol() + 1)); // Move up right diagonal for white
        moveList.add(new Position(this.getPosition().getRow() - 1,
                                   this.getPosition().getCol() - 1)); // Move up left diagonal for white
        moveList.add(new Position(this.getPosition().getRow() + 1,
                                   this.getPosition().getCol() + 1)); // Move down right diagonal for white
        moveList.add(new Position(this.getPosition().getRow() + 1,
                                   this.getPosition().getCol() - 1)); // Move down left diagonal for white

        return moveList;
    }

}
