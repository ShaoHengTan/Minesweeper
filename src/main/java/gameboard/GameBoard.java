package gameboard;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GameBoard {
	private Cell[][] grid = null;
	private int numRows;
	private int numCols;
	private int numMines;
	private boolean gameOver;
	private boolean win;
	private double allowedMineCountRatio = 0.35; // default is 35% or 0.35 of the total number of cells, to be read from
													// file
	private int revealedCount = 0;
	private int maxRows = 676; // to be dynamically read from file
	private int maxCols = 676;
	private int totalCells ; 
	private boolean debugMode = true;
	
	//private int numberOfMovesMade = 0;
	
	/*
	public class minesCountException extends Exception {
		public minesCountException(String errorMessage) {
			super(errorMessage);
		}
	}
	*/
	public GameBoard() {
		LoadGameBoard();
	}
	public void LoadGameBoard() {
		
	}
	public GameBoard(int numRows, int numCols, int numMines) {

		initializeGameBoard(numRows, numCols, numMines);

	}
	public void initializeGameBoard(int numRows, int numCols, int numMines)
	{
		this.numRows = numRows;
		this.numCols = numCols;
		this.numMines = numMines;
		this.gameOver = false;
		this.win = false;
		this.revealedCount = 0;
		this.totalCells = this.numRows *this.numCols;
		//this.numberOfMovesMade = 0;
		initializeGrid();
		placeMines();
		calculateAdjacentMines();
		
		
	}
	
	// total number should not be less than 4
	public static boolean validateGrid(int numRows, int numCols, int maxRows, int maxCols) {
		boolean result = true;
		int totalCells = numRows * numCols;

		if (totalCells < 4 && totalCells > 0) {
			result = false;
			System.out.println("Grid is InValid! Grid inputted is too small(only " + totalCells + "cells).");
		}

		else if (totalCells < 0 || numRows < 0 || numCols < 0) // negative amount
		{
			result = false;
			// throw new minesCountException("Negative number of Mines("+numMines+")
			// inputted.");
			System.out.println(
					"Grid is InValid! Negative number of cells(" + totalCells + ") or Negative rows/columns inputted.");
		} else if (numRows > maxRows || numCols > maxCols) // exceeding max
		{
			result = false;
			// throw new minesCountException("Negative number of Mines("+numMines+")
			// inputted.");
			System.out.println("Grid is InValid! Too many rows/columns inputted.");
		}

		if (result)
			System.err.println("Grid is Valid!");

		return result;

	}

	public static boolean validateMineCount(int numRows, int numCols, int numMines)// throws minesCountException
	{
		boolean result = true;
		int totalCells = numRows * numCols;

		int allowedMines = (int) ((float) totalCells * 0.35);
		System.err.println(
				"Validating mines=" + numMines + ".Total cells=" + totalCells + ". AllowedMines=" + allowedMines);
		// try
		if (numMines > totalCells * 0.35) {
			result = false;
			// throw new minesCountException("Mines("+numMines+") more than 35% of total
			// cells:"+totalCells);
			System.out.println("MineCount InValid! Mines(" + numMines + ") more than 35% of total cells:" + totalCells);
		} else if (numMines < 0) // negative amount
		{
			result = false;
			// throw new minesCountException("Negative number of Mines("+numMines+")
			// inputted.");
			System.out.println("MineCount InValid! Negative number of Mines(" + numMines + ") inputted.");
		} else if (numMines == 0) // no mines
		{
			result = false;
			// throw new minesCountException("Negative number of Mines("+numMines+")
			// inputted.");
			System.out.println("MineCount InValid! No Mines inputted.");
		}

		if (result)
			System.err.println("MineCount Valid!");

		return result;
	}

	private void initializeGrid() {
		if(grid==null)
			grid = new Cell[numRows][numCols];
		
		for (int row = 0; row < numRows; row++) {
			for (int col = 0; col < numCols; col++) {
				
				grid[row][col] = initializeCell(grid[row][col]);
			}
		}
	}
	private Cell initializeCell(Cell cell)
	{
		if(cell == null) {
			cell = new Cell();
		}
		else
		{
			cell.setAdjacentMines(0);
			cell.setMine(false);
			cell.setRevealed(false);
		}
		return cell;
	}
	// method that places mines
	private void placeMines() {
		Random random = new Random();
		int minesPlaced = 0;
		while (minesPlaced < numMines) {
			int row = random.nextInt(numRows);
			int col = random.nextInt(numCols);
			if (!grid[row][col].isMine()) {
				grid[row][col].setMine(true);
				minesPlaced++;
			}
		}
	}

	private void calculateAdjacentMines() {
		for (int row = 0; row < numRows; row++) {
			for (int col = 0; col < numCols; col++) {

				countAdjacentMinesOfCell( row, col);
			}
		}
	}

	// this method sets and also returns the number of adjacent mine of cell
	public int countAdjacentMinesOfCell( int rowIndex, int colIndex) {
		int mineCount = 0;
		 
		Cell[] adjacentCells = new Cell[8];
		getAdjacentCells(adjacentCells,rowIndex,colIndex);
		
		for (int i = 0; i < 8; i++) {

			int newX = adjacentCells[i].getRow();
			int newY = adjacentCells[i].getCol();

			if(isWithinGrid(newX,newY))
			{
				if (this.grid[newX][newY].isMine()) {
					mineCount++;
				}
			}
		}
		this.grid[rowIndex][colIndex].setAdjacentMines(mineCount);
		return mineCount;
	}
	public boolean isWithinGrid(Cell cell) {
	
		int row = cell.getRow();
		int col = cell.getCol();
		
	
		return isWithinGrid(row,col);
	}
	private boolean isWithinGrid(int row,int col) {
		boolean result = false;

		if (row >= 0 && row < this.numRows && col >= 0 && col < this.numCols) {
			result = true;
		}
		
		return result;
	}
	//gets overloaded function
	private static void getAdjacentCells(Cell[] adjacentCells,Cell cell) {
		
		getAdjacentCells(adjacentCells,cell.getRow(),cell.getCol());
	}
	//gets the 8 adjacent cells
	private static void getAdjacentCells(Cell[] adjacentCells,int rowIndex, int colIndex) {
		
		//Cell[] adjacentCells = new Cell[8];
		int[] dx = { -1, -1, -1,  0, 0, 1, 1, 1 };
		int[] dy = { -1, 0,   1, -1, 1, -1, 0, 1 };
		/* (-1,-1) ( 0,-1) ( 1,-1)     
		 * (-1, 0)		   ( 1, 0)
		 * (-1, 1) ( 0, 1) ( 1, 1)
		 */
		for (int i = 0; i < 8; i++) {
			int newX = rowIndex + dx[i];
			int newY = colIndex + dy[i];
			if(adjacentCells[i]==null)
			{
				adjacentCells[i] = new Cell();
			}
			adjacentCells[i].setRow(newX);
			adjacentCells[i].setCol(newY);
		}
		
	}
	
	public Cell getCell(int row, int col)
	{
		Cell result = null;
		
		if(isWithinGrid(row,col)) {
			
			result = grid[row][col];
		}
		
		return result; 
	}
	
	//helper function in parseStringToCell
	//takes a user inputted cell coordinate eg, D22 , and returns D,  returns null if invalid
	private static String validateInputAndReturnRow(String inputCell){
		String result = null;
		
		boolean parsingAlphabet = true;
		int numberOfLetters = 0;
		int numberOfNumbers = 0;
		
		if( !Character.isLetter(inputCell.charAt(0)))
		{
			System.out.println("Input needs to start with a letter.");
			return null;
		}
		
		for(int i =0;i< inputCell .length();i++) {
			if(parsingAlphabet) {
				//CharSequence char = null;
				if( Character.isLetter(inputCell.charAt(i))) {
					numberOfLetters++;
				}
				if(Character.isDigit(inputCell.charAt(i+1)))
				{
					parsingAlphabet = false;
				}
				
			}
			else {
				
				if( Character.isDigit(inputCell.charAt(i))) {
					numberOfNumbers++;
				}
			}
		}
		
		
		if(!StringUtils.isNumeric(inputCell.substring(numberOfLetters)) || numberOfLetters == 0||numberOfNumbers ==0)
		{
			System.out.println("Input is invalid. Key letter(s) followed by Number.");
			return null;
		}
		else
		{
			result = inputCell.substring(0,numberOfLetters);
		}

		return result;
	}
	//currently parses first letter as row, then next 1-2 characters as column
	public static Cell parseStringToCell(String inputCell) {
		
		String rowString = null;
		rowString = validateInputAndReturnRow(inputCell);
		if(rowString == null)
		{
			System.out.println("Letter invalid.");
			return null;
		}
		if(rowString.length()>2)
		{
			System.out.println("Too Many Letters inputted.");
			return null;
		}
	
		Cell cell = new Cell();
        // Extract the first character and convert it to uppercase
        char rowChar = Character.toUpperCase(rowString.charAt(0));
        
        // Calculate the integer value of the first character ('A' -> 1, 'B' -> 2, ..., 'Z' -> 26)
        // made it index form so A -> 0 , X,Y,Z->23,24,25
        int row = rowChar - 'A';
        if(rowString.length()==2) //for if input was AA(1) or BA(27) or ZZ(26*26) 
        {
        	rowChar = Character.toUpperCase(rowString.charAt(1));
        
        	row = (row*26)+ (rowChar - 'A' );
        }
        
        // Extract the remainder of the string and convert it to an integer
        int col = (Integer.parseInt(inputCell.substring(rowString.length()))) -1 ;
        
        cell.setRow(row);
        cell.setCol(col);
		
		return cell;
		
	}

	public static boolean validateInputtedCell(Cell cell, int gameBoardRows, int gameBoardCols)// throws minesCountException
	{
		boolean isValid = true;
		
		
		if(cell.getRow() < 0 || cell.getCol() < 0)
		{
			isValid = false;
			System.out.println("Inputted row or column is negative");
		}
		else if (cell.getRow() >= gameBoardRows )
		{
			isValid = false;
			System.out.println("Inputted row is outside the grid.");
			
		}
		else if (cell.getCol() >= gameBoardCols)
		{
			isValid = false;
			System.out.println("Inputted column is outside the grid.");
		}
		
		/*
		if(isWithinGrid(cell))
		{
			isValid = true;
		}
		else
		{
			isValid = false;
		}*/
		return isValid;

	}
	
	//by default called when player selects a mine on the first move
	public void replaceMine(Cell cell)
	{
		//sanitycheck
		if(cell.isMine() == false)
		{
			return;
		}
		this.grid[cell.getRow()][cell.getCol()].setMine(false);
		//adjust/minus surronding cell's adjacent mine count
		Cell[] adjacentCells = new Cell[8];
		getAdjacentCells(adjacentCells,cell);
		
		for (int i = 0; i < 8; i++) {

			int newX = adjacentCells[i].getRow();
			int newY = adjacentCells[i].getCol();

			if(isWithinGrid(adjacentCells[i]))
			{
				if (!grid[newX][newY].isMine()) {
					grid[newX][newY].setAdjacentMines(grid[newX][newY].getAdjacentMines()-1);
				}
			}
		}
		
		autoSetNewMine() ;
	}

	// sets first available cell from top left as a mine
	private void autoSetNewMine(){//Cell[][] grid) {

		Cell newMine = null;
		int numRows = this.numRows;//grid.length;
		int numCols = this.numCols;//grid[0].length;

		outerloop: for (int row = 0; row < numRows; row++) {
			for (int col = 0; col < numCols; col++) {
				if (!grid[row][col].isMine()) {
					grid[row][col].setMine(true);
					newMine = grid[row][col];
					break outerloop;
				}
			}
		}
		
		//adjust/incre surronding cell's adjacent mine count
		Cell[] adjacentCells = new Cell[8];
		getAdjacentCells(adjacentCells,newMine);
		
		for (int i = 0; i < 8; i++) {
			int newX = adjacentCells[i].getRow();
			int newY = adjacentCells[i].getCol();
			if (isWithinGrid(adjacentCells[i])) {
				if (!grid[newX][newY].isMine()) {
					grid[newX][newY].setAdjacentMines(grid[newX][newY].getAdjacentMines() + 1);
				}
			}
		}
		
	}
	
	public Cell getCellFromGrid(int row,int col) {
		Cell cellCoords = new Cell();
		cellCoords.setRow(row);
		cellCoords.setCol(col);
		return getCellFromGrid(cellCoords);
	}
	public Cell getCellFromGrid(Cell cellCoord) {
		
		
		if(isWithinGrid(cellCoord))
		{
			
			cellCoord.setMine(this.grid[cellCoord.getRow()][cellCoord.getCol()].isMine());
			cellCoord.setAdjacentMines(this.grid[cellCoord.getRow()][cellCoord.getCol()].getAdjacentMines());
			cellCoord.setRevealed(this.grid[cellCoord.getRow()][cellCoord.getCol()].isRevealed());
			//return this.grid[cell.getRow()][cell.getCol()];				
		}
		
		return cellCoord;
	}
	
	public boolean isCellRevealed(Cell cell) {
		boolean result = false;
		
		if(isWithinGrid(cell))
		{
			if(this.grid[cell.getRow()][cell.getCol()].isRevealed())			
				result=true;
		}
		
		return result;
	}
	public void revealCell(Cell cell) {
		
		//if(isWithinGrid(cell))
		//{
			if(this.grid[cell.getRow()][cell.getCol()].isRevealed())
				return;
			
			this.grid[cell.getRow()][cell.getCol()].setRevealed(true);
			this.revealedCount++;
		//}
	}
	public void revealCell(int row,int col) {
		
		//if(isWithinGrid(cell))
		//{
			if(this.grid[row][col].isRevealed())
				return;
			
			this.grid[row][col].setRevealed(true);
			this.revealedCount++;
		//}
	}
	public void openEmptyCells(Map<String,Cell> emptyCellsMap, Map<String,Cell> evaluatedCellsMap) { 
		
		if(emptyCellsMap==null || emptyCellsMap.isEmpty()) {
			return;
		}
		if(evaluatedCellsMap.size() >= this.totalCells )
		{
			
			return;
		}


		int count = 0;

		for (Map.Entry<String,Cell> entry : emptyCellsMap.entrySet()) {
				  
			Cell cell = entry.getValue(); 
			
			if(!isCellRevealed(cell)) {
				
				revealCell(cell.getRow(),cell.getCol());
			}
			evaluatedCellsMap.put(cell.toString(), cell);
			//check surrounding cells of this cell
			Cell[] adjacentCells = new Cell[8];
			getAdjacentCells(adjacentCells,cell);
			//traversedCount++;
			for (int y = 0; y < 8; y++) {
				
				
				int newX = adjacentCells[y].getRow();
				int newY = adjacentCells[y].getCol();
				
				if(isWithinGrid(newX,newY))
				{
					Cell adjCellFromGrid = getCellFromGrid(newX,newY);
					if(evaluatedCellsMap.containsKey(adjCellFromGrid.toString()))
					{
						continue;
					}
						
					if(adjCellFromGrid.isRevealed() || adjCellFromGrid.isMine()) {	
						
						evaluatedCellsMap.put(adjCellFromGrid.toString(), adjCellFromGrid);
						
						continue;
					}
					
					if(adjCellFromGrid.getAdjacentMines() == 0) {

						//if(!emptyCellsMap.containsKey(this.grid[newX][newY]))
						if(!emptyCellsMap.containsKey(adjCellFromGrid))
						{
					
							//Cell newEmptyCell = new Cell(this.grid[newX][newY]);
							Cell newEmptyCell = new Cell(adjCellFromGrid);
							emptyCellsMap.put(newEmptyCell.toString(),newEmptyCell);
							
						}
						
					}
					else { 
						revealCell(newX,newY);	
						evaluatedCellsMap.put(adjCellFromGrid.toString(), adjCellFromGrid);
					}
				}
			}
			
			emptyCellsMap.remove(cell.toString());//once this cell is evaluated, pop it from the list
			count++;
		}
		
		openEmptyCells(emptyCellsMap,evaluatedCellsMap);
	}
	public void incrementRevealedCellCount(int incre) {
		this.revealedCount += incre;
	}
	public boolean isGameWon() {

		int totalCells = this.numRows * this.numCols;
		int totalToReveal = totalCells-this.numMines;
		
		return (totalToReveal==this.revealedCount);
	}
	

}