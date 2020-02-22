package tic.tac.toe;

import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 * X / O pieces
 * @author Yonatan
 * @param pieceKind what kind of piece - String 
 * @param theBoard What game board the pieces is linked to
 * @param x The x cords of the mouse click
 * @param y The Y cords of the mouse click
 */
public class Piece extends JLabel {
    

	private static final long serialVersionUID = 1L;
	
	/** @param kind the kind of piece */
    public String kind;
    private gameBoard board;
    
    /**
     * X or O pieces
     * @param pieceKind what kind of piece - String 
     * @param theBoard What game board the pieces is linked to
     * @param x The x cords of the mouse click
     * @param y The Y cords of the mouse click
     */
    public Piece(String pieceKind, gameBoard theBoard, int x, int y) {
        // Initialize the vars
        kind = pieceKind;
        board = theBoard;
        
        // New Random from java.util
        Random rand = new Random();
        
        // Pick random number for the texture
        int num = rand.nextInt(6) + 1;
        
        // Add texture to the label
        try {
            System.out.println("Setting background: " + "textures/" + kind + String.valueOf(num) + ".png");
            this.setIcon(new ImageIcon(ImageIO.read(new File("textures/" + kind + String.valueOf(num) + ".png"))));
        } catch (IOException err) {
            System.out.println(err); 
        }
        
        // Try to place the piece
        
        // Compare the X cords
        int count = 0;
        for (int locX : gameBoard.gridX) {
            // System.out.println("Starting from X: " + x);
            if (x < locX) {
                // System.out.println("Found X: " + count);
                x = count - 1;
                break;
            }
            count++;
        }
        // For X == 3
        if (count == 3) 
            x = count - 1;
        count = 0;
        
        // Compare the Y cords
        for (int locY : gameBoard.gridY) {
            // System.out.println("Starting from Y: " + y + " LocY: " + locY);
            if (y < locY) {
                // System.out.println("Found Y: " + count);
                y = count - 1;
                break;
            }
            count++;
        }
        
        // For y == 3
        if (count == 3) 
            y = count - 1;
        

        // If the location is clear place the piece
        try {
            if (" ".equals(board.grid[y * 3 + x])) {
                this.setBounds(gameBoard.gridX[x], gameBoard.gridY[y], 180, 180);
                board.grid[y * 3 + x] = kind;
                board.checkGameStatus();
                if (board.turn%2 != 0) {
                	board.newTurn(0, 0);
                }
            } else {
            	board.turn--;
        		if (board.turn%2 != 0) {
        			board.turn++;
        			board.Enemy.turn();
        			
                }
        		
            }
            
            
        }
        catch (Exception err) {
            System.out.println(err);
            board.turn--;
        }
    }
}


