import java.io.IOException;
import java.util.Scanner;

public class Main {
	
	public static Scanner scan;

	public static void main(String[] args) throws IOException {
		scan = new Scanner(System.in);
		runMenu();
	}

	public static int getIntInput() {
		int input = 0;
		boolean valid = false;

		do {
			try {
				input = Integer.parseInt(scan.nextLine());
				valid = true;
			} catch (NumberFormatException e) {
				System.out.println("Input must be an integer!");
			}
		} while (!valid);
		return input;
	}

	private static void runMenu() throws IOException {
		int menuInput = 0;
		do {
			printMenu();
			menuInput = 0;
			// Checks if the input is an integer. If not, informs the user, and
			// try again.
			try {
				menuInput = Integer.parseInt(scan.nextLine());
			} catch (NumberFormatException e) {
				System.out.println("Input must be an integer!");
			}
			// Takes user input for the menu choice
			switch (menuInput) {
			case 1: // Run the default
				runDefaultSim();
				break;
			case 2: // Edit Settlement
				runCustomSim();
				break;
			case 0: // Quit
				break;
			default:
				System.out.println("Invalid menu choice.");
			}
		} while (menuInput != 0); // Will run menu until the menu choice is 8 -
								  // Quit

	}

	private static void printMenu() {
		System.out.println();
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~");
		System.out.println("BONK MURDER SIMULATION");
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~");
		System.out.println("1 - Default Simulation (20x20 board, 20 bonks, 5 zaps)");
		System.out.println("2 - Custom Simulation");
		System.out.println("0 - Quit");
	}

	/**
	 * Runs the default simulation (20 bonks, 5 zaps, 20 board size)
	 */
	private static void runDefaultSim() {
		Board board = new Board(20, 5, 20);
		board.start();
	}
	

	/**
	 * If the user decides on a custom simulation
	 * Asks user for number of bonks, zaps, and the board size
	 */
	private static void runCustomSim() {
		System.out.println("Number of Bonks? (0-500)");
		int bonkNo = getCustomNo(500);
		System.out.println("Number of Zaps? (0-500)");
		int zapNo = getCustomNo(500);
		System.out.println("Board size? (0-100)(Type one number, board will be n by n)");
		int boardSize = getCustomNo(100);
		Board board = new Board(bonkNo, zapNo, boardSize);
		board.start();
	}
	
	/**
	 * Gets an integer input, and checks if it is between 0 and the passed limit
	 * @param limitInput
	 * @return
	 */
	private static int getCustomNo(int limitInput){
		int limit = limitInput;
		int number = getIntInput();
		if (number < 0 || number > limit){
			System.out.println("Invalid number! Must be 0 - " + limit);
			getCustomNo(limit);
		}
		return number;
	}
}
