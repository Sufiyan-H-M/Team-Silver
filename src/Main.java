import game.Board;

import java.util.Scanner;

public class Main{
    public static void main(String[] args){
        Board board = new Board();   // your board with pieces placed
        String turn = "w";           // white starts

        Scanner sc = new Scanner(System.in);

        while (true) {

            board.printBoard();

            System.out.println((turn.equals("w") ? "White" : "Black") + " to move.");

            System.out.print("Enter move (e.g., e2 e4): ");
            String from = sc.next();
            String to   = sc.next();

            boolean moved = board.makeMove(from, to, turn);

            if (!moved) {
                System.out.println("Try again.");
                continue;   // same player tries again
            }

            // After a successful move, check if opponent is in check
            String opponent = turn.equals("w") ? "b" : "w";

            if (board.isInCheck(opponent)) {

                if (!board.hasAnyLegalMove(opponent)) {
                    System.out.println("CHECKMATE! " + (turn.equals("b") ? "black" : "white") + " wins!");
                    board.printBoard();
                    break;
                }

                System.out.println(opponent + " is in CHECK!");
            }


            // Switch turn
            turn = opponent;
        }
    }
}
