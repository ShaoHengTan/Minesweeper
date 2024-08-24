package gameengine;

import java.util.Scanner;

import org.apache.commons.lang3.math.NumberUtils;

import Inputs.UserInput;
import renderer.Renderer;
import gameboard.Cell;
import gameboard.GameBoard;



//singleton game engine, using enum type
public enum GameEngine {
	INSTANCE;
	
	Renderer renderer; //this class is to display the game, currently would be printed into command line
	GameBoard gameBoard = null;
	
	Scanner scanner = new Scanner(System.in);
	
	//this ensures the singleton nature and INSTANCE can be used to call the functions
	private GameEngine() {
		System.err.println("Game engine created");
		renderer = new Renderer();
		gameBoard = null;
		this.init();
		
		
	}
	
	private GameStates gameState;
	

	public void init() {
		System.err.println("Init.");
		setGameState(GameStates.SETUP);
		System.err.println("Gamestate:"+getGameState().toString());
		
		renderer.setEditorMode(true);//displays all cells
	}
	public boolean void checkUserGridInput(String gridInput) {
			renderer.renderSetupBoard();
			gridSizeInput = scanner.nextLine().replaceAll("\\s", "");
			
			if(NumberUtils.isParsable(gridSizeInput))
			{
				System.err.println("gridSizeInput is parsable:"+gridSizeInput+".");
				gridSize = Integer.parseInt(gridSizeInput);
				gridCountIsValid = GameBoard.validateGrid(gridSize, gridSize,gameBoard.getMaxRows(),gameBoard.getMaxCols());
			}
			
			else
			{
				System.err.println("gridSize is not parsable");
			}
			
		return 
	}
	public void gameSetup() {
		System.err.println("Gameboard Setup.");
		
		boolean gridCountIsValid = false;
		String gridSizeInput;
		int gridSize = 0;
		do
		{
			renderer.renderSetupBoard();
			gridSizeInput = scanner.nextLine().replaceAll("\\s", ""); //deletes any spaces in the input, be it in front , behind, or inbetween
			
			if(NumberUtils.isParsable(gridSizeInput))
			{
				System.err.println("gridSizeInput is parsable:"+gridSizeInput+".");
				gridSize = Integer.parseInt(gridSizeInput);
				gridCountIsValid = GameBoard.validateGrid(gridSize, gridSize,gameBoard.getMaxRows(),gameBoard.getMaxCols());
			}
			else
			{
				System.err.println("gridSize is not parsable");
			}
			
			
			
		}
		while(!gridCountIsValid);
	
		
		//gridSize.
		boolean minesCountIsValid = false;
		int numMines = -1;
		
		do
		{
			renderer.renderSetMines();
			numMines = scanner.nextInt();
			
			//validateParameters returns true when its valid 
			minesCountIsValid = GameBoard.validateMineCount(gridSize, gridSize, numMines);
			

		}
		while(!minesCountIsValid);
	

		
		gameBoard = new GameBoard(gridSize,gridSize,numMines);
		setGameState(GameStates.ONGOING);
	}
	
	public void gameInProgress() {
		System.err.println("Game started.");
		
		if(gameBoard==null)
		{
			System.err.println("Gameboard not initialized");
			setGameState(GameStates.TERMINATE);
			return;
		}
		
		
		
		renderer.startGame(gameBoard);

		
		boolean cellInputIsValid = false;
		
		do
		{
			Cell cell = UserInput.getUserCellInput();
			
	
			//validateParameters returns true when its valid 
			cellInputIsValid = GameBoard.validateInputtedCell(cell);
			
			renderer.update(gameBoard);
		}
		while(!cellInputIsValid);
		
		
		boolean steppedOnMine = false;
		boolean allCellsOpened = false;
		
		int numMines = -1;
		
		do
		{
			Cell cell = UserInput.getUserCellInput();
			
			
			
			//validateParameters returns true when its valid 
			//minesCountIsValid = GameBoard.validateMineCount(gridSize, gridSize, numMines);
			
			renderer.update(gameBoard);
		}
		while(steppedOnMine == false && allCellsOpened == false);
		
		
		
		setGameState(GameStates.TERMINATE);
	}
	
	public void update() {
		
		System.err.println("Start game loop");
		
		while(getGameState() != GameStates.TERMINATE)
		{
			System.err.println("Game loop");
			switch(getGameState())
			{
				case SETUP:
					//
					gameSetup();
                    break;
                    
				case ONGOING:
					gameInProgress();
					//renderer.update(gameBoard);
					break;
				default:
					break;
					
				

			}
			

			
			
		}
		
		
		System.err.println("Gamestate:"+getGameState().toString());
		
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

