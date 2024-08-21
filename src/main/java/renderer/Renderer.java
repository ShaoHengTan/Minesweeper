package renderer;

import gameboard.Cell;
import gameboard.GameBoard;

public class Renderer {

	
	boolean isEditorMode = false; //similar to developer mode, specifically can see the whole board
	

	public void renderSetupBoard()
	{
		System.out.println("Welcome to Minesweeper!");
        System.out.print("Enter the size of the grid (e.g., 4 for a 4x4 grid): ");
	}
	
	public void renderSetMines()
	{
		System.out.print("Enter the number of mines to place on the grid: ");
	}
	
	public void update(GameBoard gameboard)
	{
		displayGameBoard(gameboard);
	}
	//this is a helper function to display the Row alphabets 
    public char getAlphabet(int index) {
        if (index < 0 || index > 25) {
            throw new IllegalArgumentException("Index must be between 0 and 25");
        }
        return (char) ('A' + index);
    }
    
	public void displayGameBoard(GameBoard gameboard)
	{
		int numCols = gameboard.getNumCols();
		int numRows = gameboard.getNumRows();
		
		
		Cell[][] gameGrid = gameboard.getGrid();
		
		//display the column numbers
		//System.out.print("..");
		StringBuilder strBuilder = new StringBuilder("  ");
		for (int col = 0; col < numCols; col++) {
			//System.out.print(col+1+" ");
			strBuilder.append(col+1+" ");
			if(col<9)
			{
				//System.out.print(" ");
				strBuilder.append(" ");
			}
		}
		strBuilder.append(System.getProperty("line.separator"));
		System.out.print(strBuilder.toString());
		strBuilder.length();
		strBuilder.setLength(0);
		for (int row = 0; row < numRows; row++) {
			
			//char rowAlphabet = getAlphabet(row);
			//System.out.print(getAlphabet(row)+" ");
			strBuilder.append(getAlphabet(row)+" ");
			for (int col = 0; col < numCols; col++) {
            	Cell currentCell = gameGrid[row][col];
            	
            	if(isEditorMode)
            	{
            		currentCell.reveal();
            	}
            	
            	if( currentCell.isRevealed() == false)
            	{
            		//System.out.print("_");
            		strBuilder.append("_");
            	}
            	else
            	{
                	if(currentCell.isMine())
                	{
                		//System.out.print("X");
                		strBuilder.append("X");
                	}
                	else
                	{
                		//System.out.print(currentCell.getAdjacentMines());
                		strBuilder.append(currentCell.getAdjacentMines());
                	}
            	}
            	

            	
            	//System.out.print("  ");
            	strBuilder.append("  ");
         
            }
			
			//System.out.print("\n");
			
			strBuilder.append(System.getProperty("line.separator"));
			System.out.print(strBuilder.toString());
			strBuilder.setLength(0);
        }
	}
	public boolean getEditorMode()
	{
		return isEditorMode;
	}
	public void setEditorMode(boolean editorMode)
	{
		this.isEditorMode = editorMode;
	}
}
