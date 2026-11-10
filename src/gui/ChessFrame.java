package gui;
import game.Game;
import pieces.Piece;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class ChessFrame extends JFrame {

    private final Game game = new Game();
    JButton[][] squares = new JButton[8][8];
    private final Map<String, ImageIcon> icons = new HashMap<>();

    public ChessFrame(){
        this.setTitle("Java Chess");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        loadIcons();

        JPanel board = new JPanel(new GridLayout(8, 8));


        for(int i = 0; i<8; i++){
            for(int j = 0; j<8; j++){
                squares[i][j] = new JButton();
                squares[i][j].setMargin(new java.awt.Insets(0, 0, 0, 0));
                if((i + j) % 2 == 0){
                    squares[i][j].setBackground(new Color(255, 233, 172));
                } else{
                    squares[i][j].setBackground(new Color(121, 35, 35));
                }
                board.add(squares[i][j]);
            }
        }

        refresh();

        setSize(500, 500);
        setResizable(false);
        add(board);


        setVisible(true);
    }

    private void loadIcons(){
        String[] colors = {"w", "b"};
        String[] types = {"K", "Q", "R", "B", "N", "p"};

        for (String color : colors) {
            for (String type : types) {
                String key = color + type;
                java.net.URL url = getClass().getResource("/chess_pieces_imgs/" + key + ".png");
                if(url == null){
                    throw new IllegalStateException("/chess_pieces_imgs/" + key + ".png");
                } else{
                    ImageIcon icon = new ImageIcon(url);
                    Image scaled = icon.getImage().getScaledInstance(64,64,Image.SCALE_SMOOTH);
                    icons.put(key, new ImageIcon(scaled));
                }
            }
        }
    }

    private void refresh(){
        Piece[][] grid = game.getBoard().getGrid();

        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                Piece p = grid[r][c];
                // TODO: if p is null, clear the icon on squares[r][c]
                // TODO: otherwise build the key (color + symbol) and set the icon from the icons map
                if(p != null){
                    String code = grid[r][c].getColor() + grid[r][c].getSymbol();
                    squares[r][c].setIcon(icons.get(code));
                } else{
                    squares[r][c].setIcon(null);
                }
            }
        }
    }
}
