package uk.ac.aber.dcs.haa14;

import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * 
 * @author Harry Adams
 *
 */

public class Main {

	public static Scanner scan;

	/**
	 * Main function - starts the game
	 * 
	 * @param args
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws IOException, InterruptedException {
		scan = new Scanner(System.in);
		printASCIIArt();
		runMenu();
	}

	/**
	 * Gets an integer input. If anything not an integer is input, catch the
	 * exception and try again
	 * 
	 * @return
	 */
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

	/**
	 * Run the menu, until the "Quit" option is selected Program always returns
	 * to this at the end of its run Run a custom/default simulation, get the
	 * about page, and read the log of the previous program. Or quit
	 * 
	 * @throws IOException
	 * @throws InterruptedException
	 */
	private static void runMenu() throws IOException, InterruptedException {
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
			case 3:
				about();
				break;
			case 4:
				readFile();
				break;
			case 0: // Quit
				break;
			default:
				System.out.println("Invalid menu choice.");
			}
		} while (menuInput != 0); // Will run menu until the menu choice is 8 -
									// Quit

	}

	/**
	 * Prints the menu
	 */
	private static void printMenu() {
		System.out.println();
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~");
		System.out.println("BONK MURDER SIMULATION");
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~");
		System.out.println("1 - Default Simulation (20x20 board, 20 bonks, 5 zaps)");
		System.out.println("2 - Custom Simulation");
		System.out.println("3 - About this game");
		System.out.println("4 - Print the log of the previous game");
		System.out.println("0 - Quit");
	}

	/**
	 * Prints a beautiful ASCII art banner. Why not? 
	 */
	private static void printASCIIArt() {
		System.out.println(
				" ____   ____  _   _ _  __   __  __ _    _ _____  _____  ______ _____      _____ _____ __  __ _    _ _            _______ _____ ____  _   _ ");
		System.out.println(
				"|  _ \\ / __ \\| \\ | | |/ /  |  \\/  | |  | |  __ \\|  __ \\|  ____|  __ \\    / ____|_   _|  \\/  | |  | | |        /\\|__   __|_   _/ __ \\| \\ | |");
		System.out.println(
				"| |_) | |  | |  \\| | ' /   | \\  / | |  | | |__) | |  | | |__  | |__) |  | (___   | | | \\  / | |  | | |       /  \\  | |    | || |  | |  \\| |");
		System.out.println(
				"|  _ <| |  | | . ` |  <    | |\\/| | |  | |  _  /| |  | |  __| |  _  /    \\___ \\  | | | |\\/| | |  | | |      / /\\ \\ | |    | || |  | | . ` |");
		System.out.println(
				"| |_) | |__| | |\\  | . \\   | |  | | |__| | | \\ \\| |__| | |____| | \\ \\    ____) |_| |_| |  | | |__| | |____ / ____ \\| |   _| || |__| | |\\  |");
		System.out.println(
				"|____/ \\____/|_| \\_|_|\\_\\  |_|  |_|\\____/|_|  \\_\\_____/|______|_|  \\_\\  |_____/|_____|_|  |_|\\____/|______/_/    \\_\\_|  |_____\\____/|_| \\_|");
	}

	/**
	 * Runs the default simulation (20 bonks, 5 zaps, 20 board size)
	 * 
	 * @throws InterruptedException
	 * @throws IOException
	 */
	private static void runDefaultSim() throws InterruptedException, IOException {
		Board board = new Board(20, 5, 20, 30);
		board.start();
	}

	/**
	 * If the user decides on a custom simulation Asks user for number of bonks,
	 * zaps, and the board size
	 * 
	 * @throws InterruptedException
	 * @throws IOException
	 */
	private static void runCustomSim() throws InterruptedException, IOException {
		System.out.println("Number of Bonks? (0-500)");
		int bonkNo = getCustomNo(500);
		System.out.println("Number of Zaps? (0-500)");
		int zapNo = getCustomNo(500);
		System.out.println("Board size? (0-100)(Type one number, board will be n by n)");
		int boardSize = getCustomNo(100);
		System.out.println("Number of cycles/turns? (0-200)");
		int cycles = getCustomNo(200);
		Board board = new Board(bonkNo, zapNo, boardSize, cycles);
		board.start();
	}

	/**
	 * Gets an integer input, and checks if it is between 0 and the passed limit
	 * 
	 * @param limitInput
	 * @return
	 */
	private static int getCustomNo(int limitInput) {
		int limit = limitInput;
		int number = getIntInput();
		if (number < 0 || number > limit) {
			System.out.println("Invalid number! Must be 0 - " + limit);
			getCustomNo(limit);
		}
		return number;
	}

	/**
	 * Prints an "about the game" section, explaining bonks, zaps, and how it works
	 */
	private static void about() {
		System.out.println();
		System.out.println("ABOUT THIS GAME");
		System.out.println("~~~~~~~~~~~~~~~");
		System.out.println("Zaps - Immortal. Kill all bonks in the room");
		System.out.println("Bonks - Mortal. Breed with as many other bonks as possible");
		System.out.println();
		System.out.println("The game consists of 20 bonks, 5 zaps, 30 cycles (turns), and a 20 x 20 board*");
		System.out.println(
				"Every room is connected to adjacent rooms, including diagonally, and to the other side of the board");
		System.out.println("Every turn, each being moves to a random room (or stays in place), and then acts as above");
		System.out.println("Zaps will kill all the bonks, and bonks will breed");
		System.out.println();
		System.out.println("*These numbers are all modifiable, this is just the base simulation");
	}

	/**
	 * Reads the log file of the previous game. Also 
	 * gives the user the option to open the .txt file
	 * @throws IOException
	 * @throws InterruptedException
	 */
	private static void readFile() throws IOException, InterruptedException {
		Scanner file = null;

		try {
			file = new Scanner(new FileReader("log.txt"));
		} catch (FileNotFoundException e) {
			System.out.println("File does not exist - have you played a game yet?");
			runMenu();
		}

		file.useDelimiter(":|\r?\n|\r");
		String inputLine = null;

		do {
			try {
				inputLine = file.nextLine();
				System.out.println(inputLine);
			} catch (NoSuchElementException e) {
				System.out.println("Corrupted log file!");
				System.out.println("Aborting...");
				inputLine = "End of log";
			}
		} while (!inputLine.equals("End of log"));
		file.close();
		System.out.println();
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.println("log.txt IS AVAILABLE IN SOURCE CODE FOLDER");
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		openLogFileChoice();
	}
	
	/**
	 * Gives the user the choice to open log.txt
	 * @throws IOException
	 */
	public static void openLogFileChoice() throws IOException{
		System.out.println("Press 1 to open, or 2 to continue");
		int choice = getIntInput();
		boolean valid = false;
		do {
			switch (choice) {
			case 1:
				File filePath = new File("log.txt");
				Desktop desktop = Desktop.getDesktop();
				desktop.open(filePath);
				valid = true;
				break;
			case 2:
				valid = true;
				break;
			default:
				System.out.println("Invalid choice! Press 1 to open, or 2 to continue");
				break;
			}
		} while (!valid);
	}
}
