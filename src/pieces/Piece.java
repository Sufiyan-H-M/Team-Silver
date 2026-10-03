package pieces;
import game.Board;

import java.util.List;

public abstract class Piece {

    private Position position;
    private PieceColor color;

    public Piece(PieceColor color, Position position) {
        this.color = color;
        this.position = position;
    }

    public Position getPosition(){return position;}
    public String getColor(){
        if(color == PieceColor.BLACK){
            return "b";
        } else {
            return "w";
        }
    }

    public abstract String getSymbol();

    public void setPosition(Position p){
        position = p;
    }
    public void setColor(PieceColor c){
        color = c;
    }


    public Piece copy() {
        PieceColor enumColor = this.getColor().equals("w")
                ? PieceColor.WHITE
                : PieceColor.BLACK;

        return new King(enumColor, new Position(
                this.getPosition().getRow(),
                this.getPosition().getCol()
        ));
    }


    public abstract List<Position> possibleMoves(Board board);

}
