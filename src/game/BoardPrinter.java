package game;

import java.util.List;
import pieces.*;

/**
 * Prints the chessboard and the history of moves and captures.
 */
public class BoardPrinter {

    /**
     * Prints the current board, its coordinates, and the tracked moves and captures.
     *
     * @param board the board to print
     */
    public void print(Board board) {
        Piece[][] grid = board.getGrid();

        System.out.println(board.WHITE_BACKGROUND + board.BLACK_TEXT + "   A  B  C  D  E  F  G  H  " + board.RESET);
        for (int i = 0; i < 8; i++) {
            System.out.print(board.WHITE_BACKGROUND + board.BLACK_TEXT + " ");
            System.out.print(8 - i + " ");
            System.out.print(board.RESET);

            for (int j = 0; j < 8; j++) {
                String bg = ((i + j) % 2 == 0)
                        ? board.BG_TUSK + board.FG_BLACK
                        : board.BG_MAROON + board.FG_WHITE ;

                if (grid[i][j] == null) {
                    System.out.print(bg + "   " + board.RESET);
                } else {
                    System.out.print(
                            bg  + grid[i][j].getColor() +
                                    grid[i][j].getSymbol() + " " + board.RESET
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
                            (capture == "" ? "" : ("| Capture: " + capture))
            );
        }

        System.out.println();
    }
}