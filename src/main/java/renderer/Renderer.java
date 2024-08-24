package renderer;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import gameboard.Cell;
import gameboard.GameBoard;

public class Renderer {

	
	boolean isEditorMode = false; //similar to developer mode, specifically can see the whole board
	
	Properties prop =null;
	
	public Renderer()
	{
		 try (InputStream input = new FileInputStream("src/main/resources/Renderer.properties")) {
			 
		 } catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void renderSetupBoard()
	{
		renderText("Welcome to Minesweeper!",true);
		renderText("Enter the size of the grid (e.g., 4 for a 4x4 grid): ",false);
	}
	
	public void renderSetMines()
	{
		renderText("Enter the number of mines to place on the grid: ",false);
	}
	public void startGame(GameBoard gameboard)
	{
		renderText("Here is your minefield:",true);
		displayGameBoard(gameboard);
	}
	public void requestUserInput()
	{
		renderText("Select a square to reveal (e.g. A1): ",true);
		
	}
	public void renderText(String text,boolean lineBreak)
	{
		if(lineBreak) {
			text.concat("\n");
		}
			
		System.out.print(text);
		
	}
	
	public void update(GameBoard gameboard)
	{
		renderText("Here is your Updated minefield:",true);
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
		StringBuilder strBuilder = new StringBuilder("   ");
		StringBuilder strBuilder2ndline = new StringBuilder("   _");
		for (int col = 0; col < numCols; col++) {
			//System.out.print(col+1+" ");
			strBuilder.append(col+1+" ");
			strBuilder2ndline.append("___");
			if(col<9)
			{
				//System.out.print(" ");
				strBuilder.append(" ");
				
			}
			else
			{
				//strBuilder2ndline.append("_");
			}
		}
		strBuilder.append(System.getProperty("line.separator"));
		System.out.print(strBuilder.toString());
		System.out.println(strBuilder2ndline.toString());
		strBuilder.length();
		strBuilder.setLength(0);
		for (int row = 0; row < numRows; row++) {
			
			//char rowAlphabet = getAlphabet(row);
			//System.out.print(getAlphabet(row)+" ");
			strBuilder.append(getAlphabet(row)+" | ");
			for (int col = 0; col < numCols; col++) {
            	Cell currentCell = gameGrid[row][col];
            	
            	if(isEditorMode)
            	{
            		currentCell.setRevealed(true);
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
			strBuilder.append("| "+getAlphabet(row));
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
