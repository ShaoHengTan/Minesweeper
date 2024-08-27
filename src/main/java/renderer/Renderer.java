package renderer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import gameboard.Cell;
import gameboard.GameBoard;
import lombok.Getter;
import lombok.Setter;
import propertiesloader.PropertiesLoader;

@Getter
@Setter
public class Renderer {

	boolean isEditorMode = false; // similar to developer mode, specifically can see the whole board

	PropertiesLoader propertiesLoader = null;

	public Renderer() {
		propertiesLoader = new PropertiesLoader("renderer.properties");
	}

	public void renderSetupBoard() {

		renderText(propertiesLoader.getProperty("SetupBoard1"), true);
		renderText(propertiesLoader.getProperty("SetupBoard2"), false);

	}

	public void renderSetMines() {

		renderText(propertiesLoader.getProperty("SetMines"), true);
	}

	public void startGame(GameBoard gameboard) {

		renderText(propertiesLoader.getProperty("startGame"), true);
		displayGameBoard(gameboard, false);

		if (this.isEditorMode) {
			renderText("\n", true);
			renderText(propertiesLoader.getProperty("revealedGrid"), true);
			
			displayGameBoard(gameboard, true);
		}

	}

	public void requestUserCellInput() {
		renderText(propertiesLoader.getProperty("requestUserInput"), true);

	}

	public void renderText(String text, boolean lineBreak) {
		if (lineBreak) {
			// text.concat("\n");
			System.out.println(text);
		} else {
			System.out.print(text);
		}

	}

	public void update(GameBoard gameboard) {
		renderText("\n", true);
		renderText(propertiesLoader.getProperty("updateGame1"), true);
		renderText("\n", true);

		displayGameBoard(gameboard, false);

		if (this.isEditorMode) {
			renderText(propertiesLoader.getProperty("revealedGrid"), true);
			renderText("\n", true);
			displayGameBoard(gameboard, true);
		}

	}

	// this is a helper function to display the Row alphabets
	public String getAlphabet(int index) {
		if (index < 0 || index > 25) {
			throw new IllegalArgumentException("Index must be between 0 and 25");
		}
		char alphabet = (char) ('A' + index);
		return String.valueOf(alphabet);
	}

	// this is a helper function to display the Row alphabets
	public String getAlphabetFrom2Char(int index) {
		// 675 is 26*26 , maximum denotable by 2 Letters
		if (index < 0 || index > 675) {
			throw new IllegalArgumentException("Index must be between 0 and 675");
		}
		String rowAlphabet = "";
		if (index <= 25) {
			rowAlphabet = String.valueOf(getAlphabet(index) + " ");

		} else //
		{
			rowAlphabet = getAlphabet((index + 1) / 26); // if index:26 27/26=1 ,alphabet:B
			rowAlphabet += getAlphabet(((index + 1) % 26) - 1);// 27%26=1 , 1-1=0, alphabet:A

		}

		return rowAlphabet;
	}

	public void displayGameBoard(GameBoard gameboard, boolean revealBoard) {
		int numCols = gameboard.getNumCols();
		int numRows = gameboard.getNumRows();

		Cell[][] gameGrid = gameboard.getGrid();

		// display the column numbers
		// System.out.print("..");
		StringBuilder strBuilder = new StringBuilder("   _");
		StringBuilder strBuilder2ndline = new StringBuilder("   _");
		StringBuilder bottomLine = new StringBuilder("██████");
		for (int row = 0; row < numRows; row++) {
			// System.out.print(col+1+" ");
			strBuilder.append(row + 1 + " ");
			strBuilder2ndline.append("___");
			bottomLine.append("███");
			if (row < 9) {
				// System.out.print(" ");
				strBuilder.append(" ");

			} else {
				// strBuilder2ndline.append("_");
			}
		}
		strBuilder.append(System.getProperty("line.separator"));
		System.out.print(strBuilder.toString());
		// System.out.println(strBuilder2ndline.toString());
		System.out.println(bottomLine.toString());

		strBuilder.length();
		strBuilder.setLength(0);
		for (int row = 0; row < numRows; row++) {

			// char rowAlphabet = getAlphabet(row);
			// System.out.print(getAlphabet(row)+" ");
			strBuilder.append(getAlphabetFrom2Char(row) + "| ");
			for (int col = 0; col < numCols; col++) {
				Cell currentCell = gameGrid[row][col];

				if (currentCell.isRevealed() == false && !revealBoard) {
					// System.out.print("_");
					strBuilder.append("_");
				} else {
					if (currentCell.isMine()) {
						// System.out.print("X");
						strBuilder.append("X");
					} else {
						// System.out.print(currentCell.getAdjacentMines());
						strBuilder.append(currentCell.getAdjacentMines());
					}
				}

				strBuilder.append("  ");

			}

			strBuilder.append("|" + getAlphabetFrom2Char(row));
			strBuilder.append(System.getProperty("line.separator"));
			System.out.print(strBuilder.toString());
			strBuilder.setLength(0);
		}

		System.out.println(bottomLine.toString());
	}

	public void playerSelectedRevealedCell() {
		renderText(propertiesLoader.getProperty("playerSelectedRevealedCell"), true);
	}

	public void playerSelectedCellwithAdjMines(int adjMines) {
		renderText(propertiesLoader.getProperty("playerSelectedCellwithAdjMines") + adjMines, true);

	}

	public void playerSelectedMine() {
		renderText(propertiesLoader.getProperty("playerSelectedMine"), true);
		renderText(propertiesLoader.getProperty("pressToRestart"), true);

	}

	public void gameWon() {

		renderText(propertiesLoader.getProperty("gameWon"), true);
		renderText(propertiesLoader.getProperty("pressToRestart"), true);
	}
}
