package gameboard;

import org.junit.jupiter.api.Assertions;

import org.junit.jupiter.api.Test;

public class testgameboard {

	GameBoard gameBoardTest = null;
	@Test
	public void testGameBoardGridValidations()
	{
		System.err.println("testGameBoardGridValidations");
		//negatives
		Assertions.assertEquals(false,GameBoard.validateGrid(-1, 5,26,26));
		Assertions.assertEquals(false,GameBoard.validateGrid(1, -5,26,26));
		Assertions.assertEquals(false,GameBoard.validateGrid(-1, -5,26,26));
		// too small
		Assertions.assertEquals(false,GameBoard.validateGrid(1, 2,26,26)); 
		//acceptable range
		Assertions.assertEquals(true,GameBoard.validateGrid(4, 4,26,26)); 
		Assertions.assertEquals(true,GameBoard.validateGrid(5, 6,26,26)); 
		Assertions.assertEquals(true,GameBoard.validateGrid(26, 26,26,26)); 
		// too big
		Assertions.assertEquals(false,GameBoard.validateGrid(50, 50,26,26)); 
		
		Assertions.assertEquals(false,GameBoard.validateGrid(51, 51,50,50)); 
		
		
	}
	@Test
	public void testGameBoardMinesValidations()
	{
		System.err.println("testGameBoardMinesValidations");
		//negative
		Assertions.assertEquals(false,GameBoard.validateMineCount(5, 5,-5));
		
		//Valid numbers
		Assertions.assertEquals(true,GameBoard.validateMineCount(10, 10,4));
		Assertions.assertEquals(true,GameBoard.validateMineCount(10, 10,8));
		Assertions.assertEquals(true,GameBoard.validateMineCount(10, 10,16));
		
		
		//too many , >35%
		Assertions.assertEquals(false,GameBoard.validateMineCount(10, 10,36)); 
		
		
	}
	@Test
	public void testPlaceMines()
	{
		System.err.println("test Placed Mines");
		GameBoard testBoard = new GameBoard(5,5,4);
		Cell[][] grid = testBoard.getGrid();
	
		int mines = 0;
		
		for (int row = 0; row < testBoard.getNumRows(); row++) {
            for (int col = 0; col < testBoard.getNumCols(); col++) {
                if(grid[row][col].isMine()) {
                	mines++;
                }
            }
        }

		Assertions.assertEquals(mines,4);
		

		
		
	}
	
	
}
