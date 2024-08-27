package gameboard;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


//The individual cell objects in the grid on the gameboard, could have used record type too
@Getter
@Setter
@AllArgsConstructor
//@NoArgsConstructor
//@EqualsAndHashCode
public class Cell {

		private int row,col;
	    private boolean isMine = false;
	    private boolean isRevealed = false;
	    private int adjacentMines = 0;
	    	

	    
	    public Cell() {
	        this.isMine = false;
	        this.isRevealed = false;
	        this.adjacentMines = 0;
	    }
	    public Cell(Cell cell) {
	    	this.row = cell.getRow();
	    	this.col = cell.getCol();
	    	this.isMine = cell.isMine();
	        this.isRevealed = cell.isRevealed();
	        this.adjacentMines = cell.getAdjacentMines();
	      
	    }
	    @Override
	    public int hashCode() {
	    	final int PRIME = 31;
	        return this.row * 31 + this.col;
	    }
	    
	    @Override
	    public String toString() {
	    	
	        return "Cell(Row=" + this.row + ", Col=" + this.col+")";
	    }
	  
}
