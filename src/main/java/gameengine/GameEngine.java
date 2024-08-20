package gameengine;


//singleton game engine, using enum type
public enum GameEngine {
	INSTANCE;
	//this ensures the singleton nature and INSTANCE can be used to call the functions
	private GameEngine() {
		System.err.println("Game engine created");
		this.init();
		
	}
	
	private GameStates gameState;
	

	public void init() {
		System.err.println("Init ");
		setGameState(GameStates.SETUP);
		System.err.println("Gamestate:"+getGameState().toString());
	}
	public void update() {
		System.err.println("Start game loop");
		
		while(getGameState() == GameStates.ONGOING)
		{
			System.err.println("Game ONGOING");
			
			
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

