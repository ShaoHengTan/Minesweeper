package gameboard;

import java.util.Random;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GameBoard {
	private Cell[][] grid;
	private int numRows;
	private int numCols;
	private int numMines;
	private boolean gameOver;
	private boolean win;
	private double allowedMineCountRatio = 0.35; // default is 35% or 0.35 of the total number of cells, to be read from
													// file
	private int maxRows = 26; // to be dynamically read from file
	private int maxCols = 26;

	/*
	public class minesCountException extends Exception {
		public minesCountException(String errorMessage) {
			super(errorMessage);
		}
	}
	*/
	public GameBoard(int numRows, int numCols, int numMines) {

		this.numRows = numRows;
		this.numCols = numCols;
		this.numMines = numMines;
		this.gameOver = false;
		this.win = false;

		initializeGrid();
		placeMines();
		calculateAdjacentMines();

	}

	// total number should not be less than 4
	public static boolean validateGrid(int numRows, int numCols, int maxRows, int maxCols) {
		boolean result = true;
		int totalCells = numRows * numCols;
		System.err.println("Validating Grid. Total cells=" + totalCells + ".");

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
			System.err.println(" Grid is Valid!");

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

	public static boolean validateInputtedCell(Cell cell)// throws minesCountException
	{
		boolean isValid = true;

		System.out.println("Invalid input!");
		return isValid;

	}

	private void initializeGrid() {
		grid = new Cell[numRows][numCols];
		for (int row = 0; row < numRows; row++) {
			for (int col = 0; col < numCols; col++) {
				grid[row][col] = new Cell();
			}
		}
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

				countAdjacentMinesOfCell(grid, row, col);
			}
		}
	}

	// this method sets and also returns the number of adjacent mine of cell
	public int countAdjacentMinesOfCell(Cell[][] grid, int rowIndex, int colIndex) {
		int mineCount = 0;
		// check the 8 surrounding cells for mines
		int[] dx = { -1, -1, -1, 0, 0, 1, 1, 1 };
		int[] dy = { -1, 0, 1, -1, 1, -1, 0, 1 };
		/*
		 * (-1,-1) ( 0,-1) ( 1,-1) (-1, 0) ( 1, 0) (-1, 1) ( 0, 1) ( 1, 1)
		 */
		for (int i = 0; i < 8; i++) {

			int newX = rowIndex + dx[i];
			int newY = colIndex + dy[i];

			// Check if the new coordinates are within the bounds of the minefield
			if (newX >= 0 && newX < grid.length && newY >= 0 && newY < grid[0].length) {
				if (grid[newX][newY].isMine()) {
					mineCount++;
				}
			}
		}

		grid[rowIndex][colIndex].setAdjacentMines(mineCount);
		return mineCount;
	}

	public void displayGrid() {

	}

	public void handleUserInput(String input) {

	}

	/*
	 * public boolean isGameOver() {
	 * 
	 * return gameOver; }
	 * 
	 * public boolean isWin() {
	 * 
	 * return win; }
	 * 
	 * public int getNumRows() { return numRows; }
	 * 
	 * public int getNumCols() { return numCols; } public Cell[][] getGrid() {
	 * return grid; }
	 */
}