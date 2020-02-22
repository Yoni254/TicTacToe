/*
 * A simple Tic Tac Toe game
 */
package tic.tac.toe;

// Imports
import java.awt.Font;
import javax.swing.*;



/**
 * Main class
 * @author Yonatan
 */
public class TicTacToe {
    
    /** @param window the current running JFrame window */
    private static JFrame window;
    
    /** Keeping track of team score */
    public static int scoreX = 0;
    public static int scoreO = 0;
    
    /**
     * End the game and open new window
     */
    public static void endGame() {
        window.dispose();
        main(null);
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        // Setting up the board
        JFrame frame = new JFrame("Tic Tac Toe");
        gameBoard board = new gameBoard();
        
        // Keeping track of the score
        JLabel ScoreX = new JLabel("X Score: " + String.valueOf(scoreX));
        ScoreX.setFont(new Font("Serif", Font.BOLD, 50));
        JLabel ScoreO = new JLabel("O Score: " + String.valueOf(scoreO));
        ScoreO.setFont(new Font("Serif", Font.BOLD, 50));
        ScoreX.setBounds(50, 20, 300, 70);
        ScoreO.setBounds(400, 20, 300, 70);
        board.add(ScoreX);
        board.add(ScoreO);
        
        
        // Add the board to the frame
        frame.add(board);
        window = frame;
        
        // Frame settings
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 900);
        frame.setResizable(false);
        frame.setVisible(true);
    }  
}
