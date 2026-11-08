package gui;

import javax.swing.SwingUtilities;

/**
 * Starts the graphical chess application.
 */
public class ChessApp {

    /**
     * Creates and shows the chess window on the Swing event dispatch thread.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ChessFrame frame = new ChessFrame();
            frame.setVisible(true);
        });
    }
}