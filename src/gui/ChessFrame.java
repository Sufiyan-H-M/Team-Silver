package gui;
import game.Game;
import javax.swing.*;
import java.awt.*;

public class ChessFrame extends JFrame {

    private final Game game = new Game();

    public ChessFrame(){
        this.setTitle("Java Chess");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel board = new JPanel(new GridLayout(8, 8));
        JButton[][] squares = new JButton[8][8];

        for(int i = 0; i<8; i++){
            for(int j = 0; j<8; j++){
                squares[i][j] = new JButton();
                if((i + j) % 2 == 0){
                    squares[i][j].setBackground(new Color(255, 233, 172));
                } else{
                    squares[i][j].setBackground(new Color(121, 35, 35));
                }
                board.add(squares[i][j]);
            }
        }



        setSize(450, 450);
        setResizable(false);
        add(board);


        setVisible(true);
    }
}
