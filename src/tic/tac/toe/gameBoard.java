package tic.tac.toe;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * The game board
 * @author Yonatan
 */
public class gameBoard extends JPanel {
    

	private static final long serialVersionUID = 1L;
	
	/** @param background the grid pattern background */
    private BufferedImage background;
    /** @param turn keeps track of the current game turn */
    public int turn;
    
    /** @param gridX the grid X locations array */ 
    public static int[] gridX = {53, 262, 455};
    /** @param GridY the grid Y locations array */
    public static int[] gridY = {215, 420, 620};
    
    /** @param pieces array List to store the pieces */
    ArrayList<Object> pieces;
    
    /** @param grid the grid array that stores the locations and their current pieces */
    public String[] grid = new String[9];
    
    public enemyAI Enemy;
    
    /**
     * New Game Board
     */
    public gameBoard() {
        this.pieces = new ArrayList<>();
        // Reset the grid array
        for (int i = 0; i < grid.length; i++) {
            grid[i] = " ";
        }
        // Initialize the Turns
        turn = 0;
        
        // Add Mouse listener event
        this.addMouseListener(new MouseListener() {
            
            // When mouse is clicked set to new turn and try to run
            @Override
            public void mouseClicked(MouseEvent e) {
                // New turn
                System.out.println("X: " + e.getX() + " Y: " + e.getY());
                newTurn(e.getX(), e.getY());
            }   

            // Unused mouse events
            @Override
            public void mousePressed(MouseEvent e) {}
            @Override
            public void mouseReleased(MouseEvent e) {}
            @Override
            public void mouseEntered(MouseEvent e) {}
            @Override
            public void mouseExited(MouseEvent e) {}
        });
        
        try {
            System.out.println("Setting background");
            this.background = ImageIO.read(new File("textures/background.png"));
        } catch (IOException err) {
                System.out.println(err); 
        }
        
        this.setLayout(null);
       
        
        // Add enemy AI
        Enemy = new enemyAI("O");
        enemyAI.board = this;
    }
    
    // Background
    @Override
    protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(this.background, 40, 200, null);
    }
    
    /**
     * Runs when the game ends. shows end game screen and adds option to restart
     * @param status 
     */
    private void endGame(String status) {
        System.out.println("Ending: " + status);
        
        // Update score
        if ("winX".equals(status)) {
            System.out.println("Adding to X score");
            TicTacToe.scoreX++;
        } else if ("winO".equals(status)) {
            System.out.println("Adding to O score");
            TicTacToe.scoreO++;
        }
        
        // Add a JLabel with the chosen result background  
        JLabel end = new JLabel();
        end.setLayout(null);
        
        // Try to add the icon
        try {
            end.setIcon(new ImageIcon(ImageIO.read(new File("textures/" + status + ".png"))));
        } catch (IOException ex) {
            Logger.getLogger(gameBoard.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //Remove the other pieces
        Timer timer = new Timer(100, e -> pieces.forEach((piece) -> {
            ((Piece) piece).setVisible(false);
        }));
    	timer.setRepeats(false);
    	timer.start();
        
        this.add(end);
        // Growing animation
        int delay = 1;
        int startSize = 1;
        
        // Add a button 
        JButton newGame = null;
        try {
            newGame = new JButton(new ImageIcon(ImageIO.read(new File("textures/newGame.png"))));
        } catch (IOException ex) {
            Logger.getLogger(gameBoard.class.getName()).log(Level.SEVERE, null, ex);
        }
        newGame.setBorderPainted(false);
        newGame.setContentAreaFilled(false);
        newGame.setFocusPainted(false);
        
        // Timer
        Timer Animationtimer = new Timer(delay, null);
		
        class AnimationActionListener implements ActionListener {

            // The size of the label
            private int size = startSize;
            
            
            @Override
            public void actionPerformed(ActionEvent e) {
                // Check if the right size if not then inc the size by 3
                if (size <= 603) {
                    end.setBounds(340 - size/2, 500 - size/2, size, size);
                    size+=5;
                } else {
                    // Stop the timer
                	Animationtimer.stop();
                    
                }
            }
        }  
        // Start the animation timer
        Animationtimer.addActionListener(new AnimationActionListener()); 
        Animationtimer.start();
        
        // Button location and action listener
        newGame.setBounds(150, 400, 300, 100);
        newGame.addActionListener((ActionEvent e) -> {
            TicTacToe.endGame();
        } 
        // When pressed send end game to main:
        // Closes the current window and opens a new one
        );
        end.add(newGame);
        
        
    }
    
    public void checkGameStatus() {
        // Log the check
        System.out.println("Checking game status: ");
        int sumCount = 0,XCount = 0, OCount = 0;
        String win = "draw";
        
        // Check rows
        for (int row = 0; row < 9; row+=3) {
            for (int loc = row; loc < row + 3; loc++) {
                if ("X".equals(grid[loc])) {
                    XCount++;
                    sumCount++;
                }
                if ("O".equals(grid[loc])) {
                    OCount++;
                    sumCount++;
                }
            }
            if (OCount == 3)
                win = "winO";   
            if (XCount == 3)
                win = "winX";
            XCount = 0; 
            OCount = 0;
        }

        // Check Columns
        for (int column = 0; column < 3; column++) {
            for (int loc = column; loc < 9; loc+=3) {
                if ("X".equals(grid[loc]))
                    XCount++;
                if ("O".equals(grid[loc]))
                    OCount++;
            }
            if (OCount == 3)
                win = "winO";   
            if (XCount == 3)
                win = "winX";
            XCount = 0; 
            OCount = 0;
        }
        
        // Check crosses
        for (int cross = 0; cross <= 3; cross+=2) {
            for (int loc = cross; loc < 9 - cross ; loc+= 4-cross) {
                if ("X".equals(grid[loc]))
                    XCount++;
                if ("O".equals(grid[loc]))
                    OCount++;
            }
            if (OCount == 3)
                win = "winO";   
            if (XCount == 3)
                win = "winX";
            XCount = 0; 
            OCount = 0;
        }
        
        // Check if there is a winner
        if (!"draw".equals(win)) {
            System.out.println("Winner: " + win);
            String winner = win;
            Timer timer = new Timer(400, e -> endGame(winner));
        	timer.setRepeats(false);
        	timer.start();
        } else if (sumCount >= 9) {
            // If 9 pieces are on the board and there is no winner claim draw
            System.out.println("Draw");
            endGame("draw");
        }
        
    }
    
    /**
     * Add a new turn
     * @param x the mouse x location
     * @param y the mouse y location
     */
    public void newTurn(int x, int y) {
        // Inc the turn
        turn++;
        
        // If uneven (start from 1)
        if (turn%2 != 0) {
            // for X piece
            Piece piece = new Piece("X", this, x, y);
            pieces.add(piece);
            this.add(piece);
            repaint();
            
        } else {
        	Timer timer = new Timer(2, e -> Enemy.turn());
        	timer.setRepeats(false);
        	timer.start();
        	System.out.println("Running enemy");
        	
        }
        
        // Check the game status for wins / lose / draw
        
    }
}


