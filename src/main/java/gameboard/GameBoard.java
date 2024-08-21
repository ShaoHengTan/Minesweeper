package gameboard;

import java.util.Random;

public class GameBoard {
    private Cell[][] grid;
    private int numRows;
    private int numCols;
    private int numMines;
    private boolean gameOver;
    private boolean win;
    private double allowedMineCountRatio = 0.35; //default is 35% or 0.35 of the total number of cells, to be read from file
    private int maxRows = 26; //to be dynamically read from file
    private int maxCols = 26;
    
    
    public class minesCountException extends Exception { 
        public minesCountException(String errorMessage) {
            super(errorMessage);
        }
    }
    
    
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
    //public GameBoardSquare()
    //{
    	
    //}
    
    public static boolean validateMineCount(int numRows, int numCols, int numMines)// throws minesCountException
    {
    	boolean result = true; 
    	int totalCells = numRows* numCols;
    	int allowedMines = (int)((float)totalCells *0.35);
    	System.err.println("Validating mines="+numMines+".Total cells="+totalCells+". AllowedMines="+allowedMines);
    	//try
    	if(numMines > totalCells *0.35)
    	{
    		result = false; //kind of doesnt matter
    		//throw new minesCountException("Mines("+numMines+") more than 35% of total cells:"+totalCells);
    		System.out.println("Mines("+numMines+") more than 35% of total cells:"+totalCells);
    	}
    	else if( numMines<0 ) //negative amount
    	{
    		result = false; //kind of doesnt matter
    		//throw new minesCountException("Negative number of Mines("+numMines+") inputted.");
    		System.out.println("Negative number of Mines("+numMines+") inputted.");
    	}
    	return result;
    }
    private void initializeGrid() {
        grid = new Cell[numRows][numCols];
        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {
                grid[row][col] = new Cell();
            }
        }
    }

    private void placeMines() {
        Random random = new Random();
        int minesPlaced = 0;
        while (minesPlaced < numMines) {
            int row = random.nextInt(numRows);
            int col = random.nextInt(numCols);
            if (!grid[row][col].isMine()) {
                grid[row][col].setMine();
                minesPlaced++;
            }
        }
    }

    private void calculateAdjacentMines() {
        // Implement this method to calculate and set adjacent mine counts for each cell
    }

    public void displayGrid() {
        
    }

    public void handleUserInput(String input) {
       
    }

    public boolean isGameOver() {
      
        return gameOver;
    }

    public boolean isWin() {
    
        return win;
    }
    
    public int getNumRows()
    {
    	return numRows;
    }
    
    public int getNumCols()
    {
    	return numCols;
    }
    public Cell[][] getGrid() {
    	return grid;
    }
}