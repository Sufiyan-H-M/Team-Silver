import game.Board;

import java.util.Scanner;

public class Main{
    public static void main(String[] args){
        Board board = new Board();   // your board with pieces placed
        String turn = "w";           // white starts

        Scanner sc = new Scanner(System.in);

        while (true) {

            board.printBoard();      // if you have this method

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
                System.out.println((opponent.equals("w") ? "White" : "Black") + " is in CHECK!");
            }

            // Switch turn
            turn = opponent;
        }
    }
}
