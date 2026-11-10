package console;

import game.Game;
import gui.ChessFrame;

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

//        Game game = new Game();
//        System.out.println("turn=" + game.getTurn());
//        System.out.println(game.makeMove("f2", "f3") + " turn=" + game.getTurn());
//        System.out.println(game.makeMove("e7", "e5") + " turn=" + game.getTurn());
//        System.out.println(game.makeMove("g2", "g4") + " turn=" + game.getTurn());
//        System.out.println(game.makeMove("d8", "h4") + " status=" + game.getStatus());
//        System.out.println(game.makeMove("a2", "a3"));

        ChessFrame frame = new ChessFrame();
        frame.setVisible(true);
    }
}