package gameboard;

public class Cell {

	    private boolean isMine;
	    private boolean revealed;
	    private int adjacentMines;

	    public Cell() {
	        this.isMine = false;
	        this.revealed = false;
	        this.adjacentMines = 0;
	    }

	    public void setMine() {
	        this.isMine = true;
	    }

	    public boolean isMine() {
	        return isMine;
	    }

	    public void reveal() {
	        this.revealed = true;
	    }

	    public boolean isRevealed() {
	        return revealed;
	    }

	    public void setAdjacentMines(int count) {
	        this.adjacentMines = count;
	    }

	    public int getAdjacentMines() {
	        return adjacentMines;
	    }
	
}
