package gui;
import game.Board;
import game.Game;
import game.MoveResult;
import pieces.Piece;
import pieces.Position;


import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class ChessFrame extends JFrame {

    private final Game game = new Game();
    JButton[][] squares = new JButton[8][8];
    private final Map<String, ImageIcon> icons = new HashMap<>();
    private final JLabel statusLabel = new JLabel();
    private final JPanel moveList = new JPanel();

    private Position from;


    public ChessFrame(){
        this.setTitle("Java Chess");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setSize(750,600);

        loadIcons();

        JPanel board = new JPanel(new GridLayout(8, 8));

        for(int i = 0; i<8; i++){
            for(int j = 0; j<8; j++){
                squares[i][j] = new JButton();
                squares[i][j].setMargin(new java.awt.Insets(0, 0, 0, 0));
                final int row = i;
                final int col = j;
                squares[i][j].addActionListener(e -> handleClick(row, col));
                if((i + j) % 2 == 0){
                    squares[i][j].setBackground(new Color(255, 233, 172));
                } else{
                    squares[i][j].setBackground(new Color(121, 35, 35));
                }
                board.add(squares[i][j]);
            }
        }
        refresh();
        board.setPreferredSize(new Dimension(450, 450));

        JPanel centerWrapper = new JPanel(new GridBagLayout());
        centerWrapper.add(board);
        add(centerWrapper, BorderLayout.CENTER);

        statusLabel.setText("White to move");
        statusLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(statusLabel, BorderLayout.SOUTH);

        moveList.setBackground(Color.WHITE);
        moveList.setPreferredSize(new Dimension(200, 0));
        add(moveList, BorderLayout.EAST);

        setResizable(false);
        setVisible(true);
    }

    private void handleClick(int row, int col){
        Board board = game.getBoard();
        Piece clicked = board.getGrid()[row][col];

        if(from == null){
            if(clicked != null && clicked.getColor().equals(game.getTurn())){
                from = new Position(row,col);
            }
            return;
        }
        if (from.getRow() == row && from.getCol() == col) {
            from = null;
            return;
        }

        // Clicking another of your own pieces switches the selection
        if (clicked != null && clicked.getColor().equals(game.getTurn())) {
            from = new Position(row, col);
            return;
        }

        // Second click: try the move
        String fromSquare = board.convertToChessNotation(from);
        String toSquare = board.convertToChessNotation(new Position(row, col));
        from = null;

        MoveResult result = game.makeMove(fromSquare, toSquare);
        if (result == MoveResult.ACCEPTED) {
            refresh();
            updateStatus();
        } else {
            JOptionPane.showMessageDialog(this, result.getMessage());
        }
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
                if(p != null){
                    String code = grid[r][c].getColor() + grid[r][c].getSymbol();
                    squares[r][c].setIcon(icons.get(code));
                } else{
                    squares[r][c].setIcon(null);
                }
            }
        }
    }

    private void updateStatus() {
        String player = game.getTurn().equals("w") ? "White" : "Black";

        switch (game.getStatus()) {
            case IN_PROGRESS:
                statusLabel.setText(player + " to move");
                break;
            case CHECK:
                // TODO: say that 'player' is in check
                break;
            case CHECKMATE:
                // TODO: remember that after checkmate the turn does NOT switch,
                //       so 'player' is the winner. Say "Checkmate! ___ wins"
                break;
            case STALEMATE:
                // TODO: announce a draw
                break;
        }
    }
}
