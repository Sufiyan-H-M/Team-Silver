package game;

import java.util.List;
import pieces.*;

/**
 * Prints the chessboard and the history of moves and captures.
 */
public class BoardPrinter {

    /**
     * Prints the board to standard output as an 8x8 grid labeled with files
     * (A-H) and ranks (8-1). Each occupied square shows the piece's color
     * letter followed by its symbol, for example {@code wp} or {@code bK}.
     * After the grid, prints one line for every move in the board's move
     * history, including the captured piece when there was one.
     *
     * @param board the board to print
     */
    public void print(Board board) {
        Piece[][] grid = board.getGrid();

        System.out.println();
        System.out.println("   A  B  C  D  E  F  G  H  ");
        for (int i = 0; i < 8; i++) {
            System.out.print(" ");
            System.out.print(8 - i + " ");


            for (int j = 0; j < 8; j++) {
                if (grid[i][j] == null) {
                    System.out.print("   ");
                } else {
                    System.out.print(
                           grid[i][j].getColor() +
                                    grid[i][j].getSymbol() + " "
                    );
                }
            }
            System.out.println();
        }

        System.out.println();

        List<Position[]> trackedMoveList = board.getMoveList();
        List<String> trackCaptures = board.getCaptures();

        for (int i = 0; i < trackedMoveList.size(); i++) {
            Position[] move = trackedMoveList.get(i);
            String capture = trackCaptures.get(i);

            System.out.println(
                    "From: " + board.convertToChessNotation(move[0]) +
                            " To: " + board.convertToChessNotation(move[1]) +
                            (capture.equals("") ? "" : ("| Capture: " + capture))
            );
        }

        System.out.println();
    }
}