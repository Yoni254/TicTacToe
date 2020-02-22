package tic.tac.toe;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Enemy AI class for Tic Tac Toe game.
 * runs as the enemy.
 * @author Yonatan
 *
 */
public class enemyAI {

	/** @param Kind stores the AI kind */
	public static String kind;
	public static gameBoard board;
	static Random rand = new Random();
 	
	/**
	 * the Enemy AI for tic tac toe
	 * @param givenKind String with the kind of piece - "X" | "O"
	 */
	public enemyAI(String givenKind) {
		kind = givenKind;
		
	}
	
	/**
	 * Run.
	 */
	public void turn() {
		
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	
		int sumCount = 0,XCount = 0, OCount = 0;
		int locX = rand.nextInt(3);
		int locY = rand.nextInt(3);
		int emptyLoc = 0;
		boolean stop = false;
		
		// Check rows
		for (int row = 0; row < 9; row+=3) {
            for (int loc = row; loc < row + 3; loc++) {
                if ("X".equals(board.grid[loc])) {
                	XCount++;
                    sumCount++;
                } else if ("O".equals(board.grid[loc])) {
                    OCount++;
                    sumCount++;
                } else {
                	emptyLoc = loc;
                }
                
            }
            if (OCount == 2 && XCount == 0|| XCount == 2 && OCount == 0) {
            	locY = row/3;   
            	locX = emptyLoc - row;
            	System.out.println("Picking Row in: X-" + locX + " y-" + locY);
            }
            if (XCount == 3) {
            	stop = true;
            }
            XCount = 0; 
            OCount = 0;
        }

        // Check Columns
        for (int column = 0; column < 3; column++) {
            for (int loc = column; loc < 9; loc+=3) {
                if ("X".equals(board.grid[loc])) {
                    XCount++;
                } else if ("O".equals(board.grid[loc])) {
                    OCount++; 
                } else {
                	emptyLoc = loc/3;
                }
                
            }
            if (OCount == 2 && XCount == 0 || XCount == 2 && OCount == 0) {
            	locX = column;   
            	locY = emptyLoc;
            	System.out.println("Picking Column in: X-" + locX + " y-" + locY);
            }
            if (XCount == 3) {
            	stop = true;
            }
            XCount = 0; 
            OCount = 0;
        }
        
        // Check crosses
        int localX = 0, localY = 0;
        for (int cross = 0; cross <= 3; cross+=2) {
            for (int loc = cross; loc < 9 - cross ; loc+= 4-cross) {
                if ("X".equals(board.grid[loc])) {
                    XCount++;
                } else if ("O".equals(board.grid[loc])) {
                    OCount++; 
                } else {
                	emptyLoc = loc;
                	localX = loc%3;
                	localY = loc/3;
                }
            }
            if (OCount == 2 && XCount == 0|| XCount == 2 && OCount == 0) {
            	locX = localX;   
            	locY = localY;
            	System.out.println("Picking cross in: X-" + locX + " y-" + locY);
            }
            if (XCount == 3) {
            	stop = true;
            }
            XCount = 0; 
            OCount = 0;
        }
        
        /* By the end if there's an empty location he NEEDS to fill like X, ,X or O, ,O he will go there
         * Else a random location
         */
        
        int finalX = gameBoard.gridX[locX] + 5;
        int finalY = gameBoard.gridY[locY] + 5;
        
        if (sumCount != 9 && !stop) {
        	Piece piece = new Piece(kind, board, finalX, finalY);
            board.add(piece);
            board.pieces.add(piece);
            board.repaint();
        }
        
		
	}
	
}
