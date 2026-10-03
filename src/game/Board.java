package game;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import pieces.*;

public class Board {
    private Piece[][] grid = new Piece[8][8];

    public Board() {
        initialize();
    }

    public Piece[][] getGrid() {
        return grid;
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




    public boolean isInCheck(String color){
        //Find king position

        Position kingPosition = null;
        boolean foundKing = false;
        for(int i = 0; i< 8; i++){
            for(int j = 0; j < 8; j++){
                if(grid[i][j] != null && grid[i][j].getColor().equals(color) &&
                        grid[i][j].getSymbol().equals("K")){
                     kingPosition = new Position(i, j);
                     foundKing = true;
                }
                if(foundKing){
                    break;
                }
            }
            if(foundKing){
                break;
            }
        }

        //check if it is being attacked
        boolean checkFlag = false;

        if(color.equals("w")){
            // check pawn attacks
            int r = kingPosition.getRow();
            int c = kingPosition.getCol();

            if(r-1 >=0 && c-1 >= 0 && grid[r-1][c-1] != null &&
            !grid[r-1][c-1].getColor().equals(color) &&
            grid[r-1][c-1].getSymbol().equals("p")){
                checkFlag = true;
            }

            if(r-1 >=0 && c+1 <8 && grid[r-1][c+1] != null &&
                    !grid[r-1][c+1].getColor().equals(color) &&
                    grid[r-1][c+1].getSymbol().equals("p")){
                checkFlag = true;
            }

            // check knight attacks
            int[][] knightMoves = {
                    { -2, -1 }, { -2,  1 },
                    { -1, -2 }, { -1,  2 },
                    {  1, -2 }, {  1,  2 },
                    {  2, -1 }, {  2,  1 }
            };

            for (int[] m : knightMoves) {
                int nr = r + m[0];
                int nc = c + m[1];

                if (nr >= 0 && nr < 8 && nc >= 0 && nc < 8 &&
                        grid[nr][nc] != null &&
                        !grid[nr][nc].getColor().equals(color) &&
                        grid[nr][nc].getSymbol().equals("N")) {

                    checkFlag = true;
                }
            }

            // check rook & queen attacks
            // ROOK & QUEEN (straight) ATTACKS

// Up
            for (int i = r - 1; i >= 0; i--) {
                if (grid[i][c] != null) {
                    if (!grid[i][c].getColor().equals(color) &&
                            (grid[i][c].getSymbol().equals("R") || grid[i][c].getSymbol().equals("Q"))) {
                        checkFlag = true;
                    }
                    break;
                }
            }

// Down
            for (int i = r + 1; i < 8; i++) {
                if (grid[i][c] != null) {
                    if (!grid[i][c].getColor().equals(color) &&
                            (grid[i][c].getSymbol().equals("R") || grid[i][c].getSymbol().equals("Q"))) {
                        checkFlag = true;
                    }
                    break;
                }
            }

// Left
            for (int j = c - 1; j >= 0; j--) {
                if (grid[r][j] != null) {
                    if (!grid[r][j].getColor().equals(color) &&
                            (grid[r][j].getSymbol().equals("R") || grid[r][j].getSymbol().equals("Q"))) {
                        checkFlag = true;
                    }
                    break;
                }
            }

// Right
            for (int j = c + 1; j < 8; j++) {
                if (grid[r][j] != null) {
                    if (!grid[r][j].getColor().equals(color) &&
                            (grid[r][j].getSymbol().equals("R") || grid[r][j].getSymbol().equals("Q"))) {
                        checkFlag = true;
                    }
                    break;
                }
            }


            // check bishop and queen

// Up-left
            for (int i = r - 1, j = c - 1; i >= 0 && j >= 0; i--, j--) {
                if (grid[i][j] != null) {
                    if (!grid[i][j].getColor().equals(color) &&
                            (grid[i][j].getSymbol().equals("B") || grid[i][j].getSymbol().equals("Q"))) {
                        checkFlag = true;
                    }
                    break;
                }
            }

// Up-right
            for (int i = r - 1, j = c + 1; i >= 0 && j < 8; i--, j++) {
                if (grid[i][j] != null) {
                    if (!grid[i][j].getColor().equals(color) &&
                            (grid[i][j].getSymbol().equals("B") || grid[i][j].getSymbol().equals("Q"))) {
                        checkFlag = true;
                    }
                    break;
                }
            }

// Down-left
            for (int i = r + 1, j = c - 1; i < 8 && j >= 0; i++, j--) {
                if (grid[i][j] != null) {
                    if (!grid[i][j].getColor().equals(color) &&
                            (grid[i][j].getSymbol().equals("B") || grid[i][j].getSymbol().equals("Q"))) {
                        checkFlag = true;
                    }
                    break;
                }
            }

// Down-right
            for (int i = r + 1, j = c + 1; i < 8 && j < 8; i++, j++) {
                if (grid[i][j] != null) {
                    if (!grid[i][j].getColor().equals(color) &&
                            (grid[i][j].getSymbol().equals("B") || grid[i][j].getSymbol().equals("Q"))) {
                        checkFlag = true;
                    }
                    break;
                }
            }
        }

        return checkFlag;
    }

    public Board copy() {
        Board clone = new Board();
        Piece[][] src = this.grid;
        Piece[][] dst = clone.grid;

        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                if (src[r][c] != null) {
                    Piece p = src[r][c];
                    // assuming each Piece has a copy constructor or clone method
                    Piece cp = p.copy();  // implement copy() in each piece class
                    cp.setPosition(new Position(r, c));
                    dst[r][c] = cp;
                } else {
                    dst[r][c] = null;
                }
            }
        }

        return clone;
    }


    private void applyMoveWithoutCheck(int oldRow, int oldCol, int newRow, int newCol) {
        Piece moving = grid[oldRow][oldCol];

        // capture or move
        grid[newRow][newCol] = moving;
        grid[oldRow][oldCol] = null;

        // update piece position
        if (moving != null) {
            moving.setPosition(new Position(newRow, newCol));
        }
    }



    public boolean makeMove(String from, String to, String turnColor) {
        // maps (you can move these to static fields)
        Map<String, Integer> letterToCol = new HashMap<>();
        letterToCol.put("a", 0);
        letterToCol.put("b", 1);
        letterToCol.put("c", 2);
        letterToCol.put("d", 3);
        letterToCol.put("e", 4);
        letterToCol.put("f", 5);
        letterToCol.put("g", 6);
        letterToCol.put("h", 7);

        Map<Integer, Integer> numToRow = new HashMap<>();
        numToRow.put(8, 0);
        numToRow.put(7, 1);
        numToRow.put(6, 2);
        numToRow.put(5, 3);
        numToRow.put(4, 4);
        numToRow.put(3, 5);
        numToRow.put(2, 6);
        numToRow.put(1, 7);

        // parse from
        int oldCol = letterToCol.get(from.substring(0, 1));
        int oldRow = numToRow.get(Integer.parseInt(from.substring(1)));

        // parse to
        int newCol = letterToCol.get(to.substring(0, 1));
        int newRow = numToRow.get(Integer.parseInt(to.substring(1)));

        // basic checks
        if (oldRow < 0 || oldRow >= 8 || oldCol < 0 || oldCol >= 8 ||
                newRow < 0 || newRow >= 8 || newCol < 0 || newCol >= 8) {
            System.out.println("Out of bounds");
            return false;
        }

        Piece piece = grid[oldRow][oldCol];
        if (piece == null) {
            System.out.println("No piece at " + from);
            return false;
        }

        if (!piece.getColor().equals(turnColor)) {
            System.out.println("Not your piece");
            return false;
        }

        // generate moves
        List<Position> moves = piece.possibleMoves(this);
        Position nextPosition = new Position(newRow, newCol);

        boolean legal = false;
        for (Position p : moves) {
            if (p.getRow() == nextPosition.getRow() &&
                    p.getCol() == nextPosition.getCol()) {
                legal = true;
                break;
            }
        }

        if (!legal) {
            System.out.println("Illegal move (not in move list)");
            return false;
        }

        // simulate on copy to check king safety
        Board sim = this.copy();
        sim.applyMoveWithoutCheck(oldRow, oldCol, newRow, newCol);

        if (sim.isInCheck(turnColor)) {
            System.out.println("Illegal move (king would be in check)");
            return false;
        }

        // apply on real board
        applyMoveWithoutCheck(oldRow, oldCol, newRow, newCol);

        return true;
    }

}
