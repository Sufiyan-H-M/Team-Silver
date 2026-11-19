package gui;

import game.Board;
import game.Game;
import game.MoveResult;
import game.Status;
import pieces.Piece;
import pieces.Position;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * The main window of the chess application. It draws the board, translates
 * mouse clicks into moves on the {@link Game}, and keeps the display in sync
 * with the game state.
 */
public class ChessFrame extends JFrame {

    private final Game game = new Game();

    JButton[][] squares = new JButton[8][8];

    private final Map<String, ImageIcon> icons = new HashMap<>();

    private ImageIcon circle;

    private final JLabel statusLabel = new JLabel();

    private final JPanel moveList = new JPanel();

    private final JMenuBar menu = new JMenuBar();
    private final JMenu gameMenu = new JMenu("Game");
    private final JMenuItem restartItem = new JMenuItem("Restart");
    private final JMenuItem exitItem = new JMenuItem("Exit");

    private Position from;

    /**
     * Creates the window, builds the board of buttons, and shows it.
     */
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
                final int row = i;
                final int col = j;
                squares[i][j].addActionListener(e -> handleClick(row, col));
                squares[i][j].setBorderPainted(false);
                squares[i][j].setFocusPainted(false);
                squares[i][j].setRolloverEnabled(false);
                squares[i][j].setOpaque(true);

                squares[i][j].setBackground(squareColor(i,j));

                board.add(squares[i][j]);
            }
        }
        refresh();
        board.setPreferredSize(new Dimension(500, 500));

        JPanel centerWrapper = new JPanel(new GridBagLayout());

        initMenu();
        add(menu, BorderLayout.NORTH);

        centerWrapper.add(board);
        add(centerWrapper, BorderLayout.CENTER);

        statusLabel.setText("White to move");
        statusLabel.setFont(new Font("SansSerif", Font.PLAIN, 25));
        statusLabel.setBorder(new EmptyBorder(15, 25, 15, 25));
        statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
        statusLabel.setVerticalAlignment(SwingConstants.CENTER);
        add(statusLabel, BorderLayout.SOUTH);

        moveList.setBackground(Color.WHITE);
        moveList.setPreferredSize(new Dimension(200, 0));
        add(moveList, BorderLayout.EAST);

        setResizable(false);
        pack();
        setVisible(true);
    }

    /**
     * Builds the Game menu with Restart and Exit items.
     */
    public void initMenu() {
        restartItem.addActionListener(e -> {
            game.restart();
            from = null;
            updateSquareColors();
            refresh();
            updateStatus();
        });

        exitItem.addActionListener(e -> System.exit(0));

        gameMenu.add(restartItem);
        gameMenu.addSeparator();
        gameMenu.add(exitItem);
        menu.add(gameMenu);

        setJMenuBar(menu);
    }

    /**
     * Marks the squares the piece on the given square can move to: a circle
     * on empty squares and a red background on captures.
     *
     * @param r the row of the selected piece
     * @param c the column of the selected piece
     */
    private void updatePossibleMoves(int r, int c){
        refresh();// clears the old possible moves

        java.util.List<Position> moves = new ArrayList<>();
        moves = game.getBoard().getGrid()[r][c].possibleMoves(game.getBoard());

        if(moves == null){
            return;
        }

        for(Position p: moves){
            if(game.getBoard().getGrid()[p.getRow()][p.getCol()] != null){
                squares[p.getRow()][p.getCol()].setBackground(new Color(241, 8, 8));
            } else{
                squares[p.getRow()][p.getCol()].setIcon(circle);
            }
        }

    }

    /**
     * Resets every square to its normal color, highlighting the selected one.
     */
    private void updateSquareColors() {
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                squares[r][c].setBackground(squareColor(r, c));
            }
        }
    }

    /**
     * Returns the background color of a square, using a lighter shade when
     * it is the selected square.
     *
     * @param r the row of the square
     * @param c the column of the square
     * @return the color to use
     */
    private Color squareColor(int r, int c){
        Color color;
        if((r + c) % 2 == 0){
            if(from!=null && from.getRow() == r && from.getCol() == c){
                color = new Color(236, 233, 223); // make it lighter
            }else{
                color = new Color(255, 233, 172);
            }
        } else {
            if(from!=null && from.getRow() == r && from.getCol() == c){
                color = new Color(207, 149, 149);
            } else{
                color = new Color(121, 35, 35);
            }
        }

        return color;
    }

    /**
     * Handles a click on a square. The first click selects a piece and the
     * second click tries to move it, asking for a promotion piece when a
     * pawn reaches the last rank.
     *
     * @param row the row of the clicked square
     * @param col the column of the clicked square
     */
    private void handleClick(int row, int col){
        if(game.getStatus() == Status.CHECKMATE || game.getStatus() == Status.STALEMATE){
            return;
        }

        Board board = game.getBoard();
        Piece clicked = board.getGrid()[row][col];

        if(from == null){
            if(clicked != null && clicked.getColor().equals(game.getTurn())){
                from = new Position(row,col);
                updateSquareColors();
                updatePossibleMoves(row,col);
            }
            return;
        }
        if (from.getRow() == row && from.getCol() == col) {
            from = null;
            updateSquareColors();
            refresh();   // removes the move hints
            return;
        }

        // Clicking another of your own pieces switches the selection
        if (clicked != null && clicked.getColor().equals(game.getTurn())) {
            from = new Position(row, col);
            updateSquareColors();
            updatePossibleMoves(row,col);
            return;
        }

        // Second click: try the move
        Position start = from;
        Position to = new Position(row, col);
        String fromSquare = board.convertToChessNotation(start);
        String toSquare = board.convertToChessNotation(to);
        from = null;
        updateSquareColors();

        String promotion = null;
        if (game.isPromotionMove(start, to)) {
            promotion = askPromotion();
            if (promotion == null) {      // dialog closed: cancel the move
                refresh();
                return;
            }
        }

        MoveResult result = game.makeMove(fromSquare, toSquare, promotion);
        refresh();   // always redraw, which also removes the move hints
        if (result == MoveResult.ACCEPTED) {
            updateStatus();
        } else {
            JOptionPane.showMessageDialog(this, result.getMessage());
        }
    }

    /**
     * Asks the player which piece a promoted pawn should become.
     *
     * @return {@code "Q"}, {@code "R"}, {@code "B"} or {@code "N"}, or
     *         {@code null} if the dialog was closed
     */
    private String askPromotion() {
        String[] names = {"Queen", "Rook", "Bishop", "Knight"};
        String[] codes = {"Q", "R", "B", "N"};
        int choice = JOptionPane.showOptionDialog(this,
                "Promote pawn to:", "Pawn Promotion",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
                null, names, names[0]);
        return choice < 0 ? null : codes[choice];
    }

    /**
     * Loads and scales the piece images and the move-hint circle once.
     */
    private void loadIcons(){
        String[] colors = {"w", "b"};
        String[] types = {"K", "Q", "R", "B", "N", "p"};

        java.net.URL urlCircle = getClass().getResource("/chess_pieces_imgs/circle.png");
        if(urlCircle == null){
            throw new IllegalStateException("/chess_pieces_imgs/circle.png");
        } else{
            ImageIcon icon = new ImageIcon(urlCircle);
            Image scaled = icon.getImage().getScaledInstance(30,30,Image.SCALE_SMOOTH);
            circle = new ImageIcon(scaled);
        }

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

    /**
     * Redraws every square from the current game state.
     */
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

    /**
     * Updates the status label from the game's turn and status.
     */
    private void updateStatus() {
        String player = game.getTurn().equals("w") ? "White" : "Black";

        switch (game.getStatus()) {
            case IN_PROGRESS:
                statusLabel.setText(player + " to move");
                break;
            case CHECK:
                statusLabel.setText(player + " is in check!");
                break;
            case CHECKMATE:
                statusLabel.setText("Checkmate! " + player+" wins");
                break;
            case STALEMATE:
                statusLabel.setText("The game ended in a draw!");
                break;
        }
    }
}