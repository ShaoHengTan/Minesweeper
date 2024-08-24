package Inputs;

import java.util.Scanner;

import gameboard.Cell;

public class UserInput {

	public static int getUserGridsizeInput() {
		
		int input = 4;
		
		return input;
	
	}
	
	public static Cell getUserCellInput() {
		Cell cell = new Cell();
		
		Scanner scanner = new Scanner(System.in);
		String input = scanner.nextLine();
		parseStringToCell(input,cell);
		return cell;
	}
	
	public static Cell parseStringToCell(String input,Cell cell) {
        // Extract the first character and convert it to uppercase
        char firstChar = Character.toUpperCase(input.charAt(0));
        
        // Calculate the integer value of the first character ('A' -> 1, 'B' -> 2, ..., 'Z' -> 26)
        int firstInt = firstChar - 'A' + 1;
        
        // Extract the remainder of the string and convert it to an integer
        int secondInt = Integer.parseInt(input.substring(1));
        
        // Print the results
        System.err.println("First integer: " + firstInt);
        System.err.println("Second integer: " + secondInt);
        
        cell.setRow(firstInt);
        cell.setCol(secondInt);
		
		return cell;
		
	}

}

