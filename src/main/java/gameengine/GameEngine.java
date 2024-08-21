package gameengine;

import java.util.Scanner;

import renderer.Renderer;
import gameboard.GameBoard;
import gameboard.GameBoard.minesCountException;


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
	
	public void gameSetup() {
		System.err.println("Gameboard Setup.");
		
		renderer.renderSetupBoard();
		var gridSize = scanner.nextInt();
		
		boolean minesCountInValid = true;
		int numMines = -1;
		
		do
		{
			renderer.renderSetMines();
			numMines = scanner.nextInt();
			
			//validateParameters returns true when its valid 
			minesCountInValid = !GameBoard.validateMineCount(gridSize, gridSize, numMines);
			

		}
		while(minesCountInValid);
	

		
		gameBoard = new GameBoard(gridSize,gridSize,numMines);
		setGameState(GameStates.ONGOING);
	}
	
	public void startGame() {
		System.err.println("Game started.");
		
		if(gameBoard==null)
		{
			System.err.println("Gameboard not initialized");
			setGameState(GameStates.TERMINATE);
			return;
		}
		
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
					startGame();
					renderer.update(gameBoard);
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

