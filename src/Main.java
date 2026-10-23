import game.Board;

import java.util.Scanner;

/**
 * Runs the chess game and manages player turns, move input,
 * check detection, and checkmate detection.
 */
public class Main{
    /**
     * Starts the chess game, processes player moves, and switches turns
     * after successful moves until checkmate occurs.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args){
        Board board = new Board();
        String turn = "w";

        String BG_RED    = "\u001B[41m";
        String BG_GREEN  = "\u001B[42m";
        String BG_YELLOW = "\u001B[43m";
        String BG_BLUE   = "\u001B[44m";
        String BG_ORANGE = "\u001B[48;5;208m";

        String RESET = "\u001B[0m";

        Scanner sc = new Scanner(System.in);

        while (true) {

            board.printBoard();

            System.out.println(BG_GREEN+(turn.equals("w") ? " White" : " Black") + " to move. "+RESET);

            System.out.print("Enter move (e.g., e2 e4): ");
            String from = sc.next();
            String to   = sc.next();

            boolean moved = board.makeMove(from, to, turn);

            if (!moved) {
                System.out.println(BG_BLUE+" Try again. "+RESET);
                continue;
            }

            String opponent = turn.equals("w") ? "b" : "w";

            if (board.isInCheck(opponent)) {
                if (!board.hasAnyLegalMove(opponent)) {
                    System.out.println(BG_RED+" CHECKMATE! " + (turn.equals("b") ? "black" : "white") + " wins! " + RESET);
                    board.printBoard();
                    break;
                }

                System.out.println(BG_ORANGE + " "+ (opponent=="w"? "White" : "Black") + " is in CHECK! " + RESET);
            }

            turn = opponent;
        }
    }
}