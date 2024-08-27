package gameengine;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.print.DocFlavor.URL;

import org.apache.commons.lang3.math.NumberUtils;

import Inputs.UserInput;
import renderer.Renderer;
import gameboard.Cell;
import gameboard.GameBoard;
import propertiesloader.PropertiesLoader;

//singleton game engine, using enum type
public enum GameEngine {
	INSTANCE;

	Renderer renderer; // this class is to display the game, currently would be printed into command
						// line
	GameBoard gameBoard = null;
	int numberOfMovesMade = 0;

	PropertiesLoader propertiesLoader=null;
	boolean editorMode = false;
	private void loadProperties() {
		
		propertiesLoader = new PropertiesLoader("game.properties");
		if(propertiesLoader.getProperty("editormode").equals("false"))
		{
			this.editorMode = false;
			
		}
		else {
			this.editorMode = true;
		}
		if(gameBoard!=null) {
			gameBoard.setMaxRows(Integer.parseInt(propertiesLoader.getProperty("maxrows")));
			gameBoard.setMaxCols(Integer.parseInt(propertiesLoader.getProperty("maxcols")));
		}
	}
	// this ensures the singleton nature and INSTANCE can be used to call the
	// functions
	private GameEngine() {
		System.err.println("Game engine created");
		loadProperties();
		renderer = new Renderer();
		gameBoard = new GameBoard();

		this.init();
	}

	private GameStates gameState;

	public void init() {
		
		setGameState(GameStates.SETUP);
		
		renderer.setEditorMode(false);// displays all cells if in editor mode
	}

	public void gameSetup() {
		
		loadProperties();
		renderer.setEditorMode(this.editorMode);

		Cell rowCol = getGridDimensions();
		int numMines = getMinesCount(rowCol);
		
		gameBoard = new GameBoard(rowCol.getRow(), rowCol.getCol(), numMines);
		this.numberOfMovesMade = 0;
		setGameState(GameStates.ONGOING);
	}

	private Cell getGridDimensions() {
		Cell rowColFromInput = new Cell(); // just using it to store the row/col. Game currently takes one dimension and
											// makes board a square, but this design makes its possible in the future
											// for a rectangle board
		boolean gridCountIsValid = false;
		Integer gridSizeInput;
		int gridSize = 0;

		do {
			renderer.renderSetupBoard();
			gridSizeInput = UserInput.getUserGridSizeInput(); // null if not parsable

			if (gridSizeInput != null) {
				gridSize = gridSizeInput.intValue();
				gridCountIsValid = GameBoard.validateGrid(gridSize, gridSize, gameBoard.getMaxRows(),
						gameBoard.getMaxCols());
			}
		} while (!gridCountIsValid);

		rowColFromInput.setRow(gridSize);
		rowColFromInput.setCol(gridSize);

		return rowColFromInput;
	}

	private int getMinesCount(Cell rowCol) {
		int minesCount = 0;
		boolean minesCountIsValid = false;
		Integer minesInput;

		do {
			renderer.renderSetMines();
			minesInput = UserInput.getUserMinesInput(); // null if not parsable

			if (minesInput != null) {
				minesCount = minesInput.intValue();
				minesCountIsValid = GameBoard.validateMineCount(rowCol.getRow(), rowCol.getCol(), minesCount);
			}

		} while (!minesCountIsValid);

		return minesCount;
	}

	public void gameInProgress() {
		System.err.println("Game started.");

		if (gameBoard == null) {
			System.err.println("Gameboard not initialized");
			setGameState(GameStates.TERMINATE);
			return;
		}

		renderer.startGame(gameBoard);
		// steppedOnMine = -1 , Gamewon = 0 , GameContinue = 1 , redundantMove = 2
		int playerMoveResult = 1;

		do {
			// this repeated gets input until its valid and parses into a cell
			Cell cellFromInput = getValidUserCellInput();

			playerMoveResult = playerMove(cellFromInput);
			if (playerMoveResult > 0) {
				renderer.update(gameBoard);
			}

		} while (playerMoveResult > 0);

		if (playerMoveResult == -1)// stepped on mine
		{
			renderer.playerSelectedMine();
			renderer.displayGameBoard(gameBoard, true);
			// check if user wants to restart game
			if (UserInput.userPressedAnyKey()) {
				setGameState(GameStates.SETUP);
			}
		} else if (playerMoveResult == 0)// stepped on mine
		{
			renderer.gameWon();
			renderer.displayGameBoard(gameBoard, false);
			// check if user wants to restart game
			if (UserInput.userPressedAnyKey()) {
				setGameState(GameStates.SETUP);
			}
		}

		// setGameState(GameStates.TERMINATE);
	}

	private Cell getValidUserCellInput() {

		boolean cellInputIsValid = false;
		Cell cellFromInput = null;
		do {
			renderer.requestUserCellInput();

			String userInputtedString = UserInput.getUserCellInput(5); // ZZ999 max input, ZZ denotes 676

			if (userInputtedString != null) {
				cellFromInput = GameBoard.parseStringToCell(userInputtedString);
				if (cellFromInput != null) {

					cellInputIsValid = gameBoard.validateInputtedCell(cellFromInput, gameBoard.getNumRows(),
							gameBoard.getNumCols());

				}
			}
		} while (!cellInputIsValid);

		return cellFromInput;
	}

	public int playerMove(Cell cellCoords) {
		// steppedOnMine = -1 , Gamewon = 0 , GameContinue = 1 , redundantMove = 2
		int playerMoveResult = 1;
		Cell cell = gameBoard.getCellFromGrid(cellCoords); // the player inputed cell only contains x,y coord
		if (gameBoard.isCellRevealed(cell)) // redundant/invalid move but allowed
		{
			renderer.playerSelectedRevealedCell();
			playerMoveResult = 2;
			return playerMoveResult;
		}

		if (this.numberOfMovesMade == 0 && cell.isMine()) { // for first move, game is lenient and swap away the mine
			gameBoard.replaceMine(cell);
			cell.setMine(false);//because this is still the 'old' cell which still set as a mine
		}

		if (cell.isMine()) {
			playerMoveResult = -1; // gameover

		} else {

			// If its a cell with adj mines, simply reveal and end this turn
			if (cell.getAdjacentMines() > 0) {

				gameBoard.revealCell(cell);
				renderer.playerSelectedCellwithAdjMines(cell.getAdjacentMines());

			} else // when selected empty cell, recursively open cells
			{

				ConcurrentHashMap<String, Cell> cellsMap = new ConcurrentHashMap<>();
				ConcurrentHashMap<String, Cell> evaluatedCellsMap = new ConcurrentHashMap<>();

				cellsMap.put(cell.toString(), cell);

				gameBoard.openEmptyCells(cellsMap, evaluatedCellsMap);
				playerMoveResult = 1;
			}
		}

		if (gameBoard.isGameWon()) {
			playerMoveResult = 0;
		}
		this.numberOfMovesMade++;
		return playerMoveResult;
	}

	public void update() {

		while (getGameState() != GameStates.TERMINATE) {
			
			switch (getGameState()) {
			case SETUP:
				//
				gameSetup();
				break;

			case ONGOING:
				gameInProgress();
				// renderer.update(gameBoard);
				break;
			default:
				break;

			}
		}

	}

	public GameStates getGameState() {
		return this.gameState;

	}

	public void setGameState(GameStates state) {
		this.gameState = state;

	}

	public void close() {
		setGameState(GameStates.TERMINATE);
	}

}
