package gameboard;

import org.junit.jupiter.api.Assertions;

import org.junit.jupiter.api.Test;

import Inputs.UserInput;

public class testgameboard {

	GameBoard gameBoardTest = null;
	
	@Test
	public void testGameBoardGridValidations()
	{
		System.out.println("testGameBoardGridValidations");
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
		
		System.out.println(" ");
	}
	@Test
	public void testGameBoardMinesValidations()
	{
		System.out.println("testGameBoardMinesValidations");
		//negative
		Assertions.assertEquals(false,GameBoard.validateMineCount(5, 5,-5));
		
		//Valid numbers
		Assertions.assertEquals(true,GameBoard.validateMineCount(10, 10,4));
		Assertions.assertEquals(true,GameBoard.validateMineCount(10, 10,8));
		Assertions.assertEquals(true,GameBoard.validateMineCount(10, 10,16));
		
		
		//too many , >35%
		Assertions.assertEquals(false,GameBoard.validateMineCount(10, 10,36)); 
		
		System.out.println(" ");
	}
	@Test
	public void testPlaceMines()
	{
		System.out.println("test Placed Mines");
		GameBoard testBoard = new GameBoard(5,5,4);
		Cell[][] grid = testBoard.getGrid();
	
		int mines = 0;
		int revealed = 0;
		for (int row = 0; row < testBoard.getNumRows(); row++) {
            for (int col = 0; col < testBoard.getNumCols(); col++) {
                if(grid[row][col].isMine()) {
                	mines++;
                }
                if(grid[row][col].isRevealed()) {
                	revealed++;
                }
                
            }
        }

		Assertions.assertEquals(mines,4);
		Assertions.assertEquals(revealed,0);
		
		System.out.println(" ");
	}
	@Test
	public void testParseStringToCell()
	{
		System.out.println("testParseStringToCell");
		Cell cell = GameBoard.parseStringToCell("D4");
		Assertions.assertEquals(cell.getRow(),3);
		Assertions.assertEquals(cell.getCol(),3);

		cell = GameBoard.parseStringToCell("X22");
		Assertions.assertEquals(cell.getRow(),23);
		Assertions.assertEquals(cell.getCol(),21);
		
		
		System.out.println(" ");
	}
	
	
	@Test
	public void testValidateInputtedCell()
	{
		System.out.println("testValidateInputtedCell");

		Cell outOfGrid = new Cell();
		outOfGrid.setRow(-1);
		outOfGrid.setCol(1);
		
		//check
		Assertions.assertEquals(GameBoard.validateInputtedCell(outOfGrid, 3, 3),false); 
		
		outOfGrid.setRow(5);
		outOfGrid.setCol(1);
		Assertions.assertEquals(GameBoard.validateInputtedCell(outOfGrid, 3, 3),false); 
		
		Cell withinGrid = new Cell();
		withinGrid.setRow(1);
		withinGrid.setCol(1);
		Assertions.assertEquals(GameBoard.validateInputtedCell(withinGrid, 3,3),true); 
		
		System.out.println(" ");
	}
	
	@Test
	public void testWithinGrid()
	{
		System.out.println("testWithinGrid");
		//Cell[][] grid = new Cell[3][3];
		//createGrid(grid);
		GameBoard gameboard = new GameBoard(3,3,1);
		
		
		Cell outOfGrid = new Cell();
		outOfGrid.setRow(-1);
		outOfGrid.setCol(1);
		
		System.out.println("   Assertions ");
		Assertions.assertEquals(gameboard.isWithinGrid(outOfGrid),false); 
		
		outOfGrid.setRow(5);
		outOfGrid.setCol(1);
		System.out.println("   Assertions ");
		Assertions.assertEquals(gameboard.isWithinGrid( outOfGrid),false); 
		
		Cell withinGrid = new Cell();
		withinGrid.setRow(1);
		withinGrid.setCol(1);
		System.out.println("   Assertions ");
		Assertions.assertEquals(gameboard.isWithinGrid(withinGrid),true); 
		
		System.out.println(" ");
	}
	
	private void createGrid(Cell[][] grid )
	{
		
		setAdj(grid[1][0]);  setAdj(grid[1][1]);
		setMine(grid[2][0]); setAdj(grid[2][1]);
		
	}
	private void setAdj(Cell cell) {
		if(cell == null)
		{
			cell = new Cell();
		}
		cell.setAdjacentMines(1);
	}
	private void setMine(Cell cell) {
		if(cell == null)
		{
			cell = new Cell();
		}
		cell.setMine(true);
	}
}
