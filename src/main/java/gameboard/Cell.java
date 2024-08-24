package gameboard;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


//The individual cell objects in the grid on the gameboard, could have used record type too
@Getter
@Setter
@NoArgsConstructor
public class Cell {

		private int row,col;
	    private boolean isMine;
	    private boolean revealed;
	    private int adjacentMines;
	    	

	    /*
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

	    

	    public boolean isRevealed() {
	        return revealed;
	    }

	    public void setAdjacentMines(int count) {
	        this.adjacentMines = count;
	    }

	    public int getAdjacentMines() {
	        return adjacentMines;
	    }
	*/
}
