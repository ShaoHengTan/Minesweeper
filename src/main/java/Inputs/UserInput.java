package Inputs;

import java.io.IOException;
import java.util.Scanner;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

public class UserInput {

	private static Scanner scanner = null;

	public static String getUserNonNullStringInput() {
		if (scanner == null)
			scanner = new Scanner(System.in);

		// deletes any spaces in the input, be it in front , behind, or inbetween//
		// scanner.nextLine().replaceAll("\\s", "");
		String input = StringUtils.deleteWhitespace(scanner.nextLine());

		boolean inputIsNull = (input == null || input.length() == 0);

		while (inputIsNull) {
			System.out.println("Empty input.");
			input = StringUtils.deleteWhitespace(scanner.nextLine());
			if (input != null && input.length() != 0) {
				inputIsNull = false;
			}
		}

		return input;
	}

	public static Integer getUserGridSizeInput() {
		Integer input = null;
		String gridSizeInput = UserInput.getUserNonNullStringInput();

		if (NumberUtils.isParsable(gridSizeInput)) {
			System.err.println("gridSizeInput is parsable:" + gridSizeInput + ".");

			input = Integer.parseInt(gridSizeInput);

		} else {
			System.err.println("GridSize intput is not parsable.");
			input = null;
		}

		return input;
	}

	public static Integer getUserMinesInput() {
		Integer input = null;
		String minesInput = UserInput.getUserNonNullStringInput();

		if (NumberUtils.isParsable(minesInput)) {
			System.err.println("Mines input is parsable:" + minesInput + ".");
			input = Integer.parseInt(minesInput);
		} else {
			System.err.println("Mines input is not parsable.");
			input = null;
		}

		return input;
	}

	// gets alphanumerical uppercase input that is between 1 to maxAllowedInput
	public static String getUserCellInput(int maxAllowedInput) {

		// Regex to check string is alphanumeric or not.
		// String regex = "^(?=.*[a-zA-Z])(?=.*[0-9])[A-Za-z0-9]+$";
		// Pattern p = Pattern.compile(regex);

		String cellInput = UserInput.getUserNonNullStringInput().toUpperCase();

		// if user input is alphanumerical if(p.matcher(cellInput).matches())
		if (!StringUtils.isAlphanumeric(cellInput) || cellInput.length() > maxAllowedInput || cellInput.length() == 1) {

			System.out.println("Input is invalid.");
			return null;
		}
		return cellInput;
	}

	public static boolean userPressedAnyKey() {

		if (scanner == null)
			scanner = new Scanner(System.in);
		boolean anyKeyPressed = false;
		int inChar = -1;

		try {
			inChar = System.in.read();
		} catch (IOException e) {
			
			e.printStackTrace();
			System.err.println("Error reading from user");
		}

		if (inChar != -1) {
			anyKeyPressed = true;
		}

		return anyKeyPressed;
	}

	public void finalize() {
		System.err.println("Destruct UserInput");
		if (scanner != null)
			scanner.close();
	}
}
