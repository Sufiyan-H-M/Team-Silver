import pieces.*;

public class Board {
    private Piece[][] grid = new Piece[8][8];

    public Board() {
        initialize();
    }

    public void initialize() {
        // Black back rank (row 0 = rank 8) and pawns (row 1)
        placeBackRank(0, PieceColor.BLACK);
        for (int c = 0; c < 8; c++) {
            grid[1][c] = new Pawn(PieceColor.BLACK, new Position(1, c));
        }
        // White pawns (row 6) and back rank (row 7 = rank 1)
        for (int c = 0; c < 8; c++) {
            grid[6][c] = new Pawn(PieceColor.WHITE, new Position(6, c));
        }
        placeBackRank(7, PieceColor.WHITE);
    }

    private void placeBackRank(int row, PieceColor color) {
        grid[row][0] = new Rook(color, new Position(row, 0));
        grid[row][1] = new Knight(color, new Position(row, 1));
        grid[row][2] = new Bishop(color, new Position(row, 2));
        grid[row][3] = new Queen(color, new Position(row, 3));
        grid[row][4] = new King(color, new Position(row, 4));
        grid[row][5] = new Bishop(color, new Position(row, 5));
        grid[row][6] = new Knight(color, new Position(row, 6));
        grid[row][7] = new Rook(color, new Position(row, 7));
    }

    public void printBoard(){
        System.out.println("  A  B  C  D  E  F  G  H ");
        for(int i = 0 ; i< 8; i++){
            System.out.print(8 - i + " ");
            for(int j = 0; j < 8; j++){

                if(grid[i][j] == null){
                    System.out.print("## ");
                } else {
                    System.out.print( grid[i][j].getColor() + grid[i][j].getSymbol() + " ");
                }
            }
            System.out.println();
        }
    }
}
