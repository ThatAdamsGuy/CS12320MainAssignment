package uk.ac.aber.dcs.haa14;

import java.util.ArrayList;
import java.util.Random;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 
 * @author Harry Adams
 *
 */
public class Board {

	private PrintWriter file;
	private int boardSize;
	Random rand = new Random();
	public ArrayList<Being>[][] world;
	private int numberOfBonks;
	private int numberOfZaps;
	private int numberOfCycles;
	private int bonksBorn = 0;
	private int bonkDeaths = 0;

	/**
	 * Board initializer - takes the number of bonks, zaps, board size, 
	 * and number of cycles
	 * 
	 * @param bonks
	 * @param zaps
	 * @param board
	 */
	public Board(int bonks, int zaps, int board, int cycles) {
		numberOfBonks = bonks;
		numberOfZaps = zaps;
		boardSize = board;
		numberOfCycles = cycles;
	}

	/**
	 * Overarching function for displaying the board every turn
	 */
	public void displayBoard() {

		for (int x = 0; x < boardSize; x++) {
			int beingCount = 0;
			for (int y = 0; y < boardSize; y++) {
				if (world[x][y].size() > beingCount) {
					beingCount = world[x][y].size();
				}
			}
			if (beingCount == 0) {
				printEmptyRow();
			} else if (beingCount == 1) {
				printRowWithOneBeing(x);
			} else if (beingCount >= 2) {
				printRowWithManyBeings(beingCount, x);
			}

		}

		printRowOfHash();
		System.out.println();

	}

	/**
	 * Prints when there are no beings in the row
	 */
	private void printEmptyRow() {
		printRowOfHash();
		printEmptySingleRow();
		printEmptySingleRow();
	}

	/**
	 * Prints when there is a max of one being in a single cell in the row
	 * 
	 * @param inX
	 */
	private void printRowWithOneBeing(int inX) {
		int x = inX;
		printRowOfHash();

		printBeings(x);

		printEmptySingleRow();
	}

	/**
	 * Prints when any cell in a row has >=2 beings
	 * 
	 * @param being
	 * @param inX
	 */
	private void printRowWithManyBeings(int being, int inX) {
		int beingCount = being;
		int x = inX;

		printRowOfHash();
		for (int k = 0; k < beingCount; k++) {
			for (int y = 0; y < boardSize; y++) {
				try {
					String cellWithBeing = "# " + world[x][y].get(k).getName();
					while (cellWithBeing.length() < 6) {
						cellWithBeing += " ";
					}
					System.out.print(cellWithBeing);
				} catch (Exception e) {
					if (y != boardSize - 1) {
						System.out.print("#     ");
					} else {
						System.out.print("#    #");
					}
				}

			}
			System.out.println();
		}

	}

	/**
	 * Runs when there is a maximum of one being in any cell in a row
	 * 
	 * @param inLimit
	 * @param inX
	 * @param inY
	 */
	private void printCellWithOneBeing(int inLimit, int inX, int inY) {
		int limit = inLimit;
		int x = inX;
		int y = inY;

		String cellWithBeing = "# " + world[x][y].get(0).getName();
		while (cellWithBeing.length() < limit) {
			cellWithBeing += " ";
		}
		System.out.print(cellWithBeing);
	}

	/**
	 * Runs when there are no beings anywhere in the row
	 */
	private void printEmptySingleRow() {
		for (int i = 0; i < boardSize; i++) {
			if (i == boardSize - 1) {
				System.out.print("#    #");
			} else {
				System.out.print("#     ");
			}
		}
		System.out.println();
	}

	/**
	 * Print the beings for a particular mini-row
	 * 
	 * @param inX
	 */
	private void printBeings(int inX) {
		int x = inX;
		for (int y = 0; y < boardSize; y++) {
			if ((world[x][y].size() > 0) && (y != boardSize - 1)) {
				printCellWithOneBeing(6, x, y);
			} else if ((world[x][y].size() > 0) && (y == boardSize - 1)) {
				printCellWithOneBeing(5, x, y);
			} else if (y == boardSize - 1) {
				System.out.print("#    #");
			} else {
				System.out.print("#     ");
			}
		}
		System.out.println();
	}

	/**
	 * Prints a full row of hash Used for row breaks
	 */
	private void printRowOfHash() {
		for (int i = 0; i < boardSize; i++) {
			System.out.print("######");
		}
		System.out.println();
	}

	/**
	 * Creates the board, the scanner for fileWrite, and then created the bonks
	 * and zaps + randomly places them Then moves on to runGame for... well...
	 * running the game
	 * 
	 * @throws InterruptedException
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public void start() throws InterruptedException, IOException {
		world = new ArrayList[boardSize][boardSize];
		initialize();
		setUpScanner();
		createBonksAndZaps();
		runGame();
	}

	/**
	 * Main function for running the game Shows each step of a turn
	 * 
	 * @throws InterruptedException
	 * @throws IOException 
	 */
	public void runGame() throws InterruptedException, IOException {
		for (int i = 0; i < numberOfCycles; i++) { // For number of cycles
			if (i != 0) {
				resetAll();
			}
			writeTurnNumberToFile(i);
			moveAllOnBoard();
			actAllOnBoard();
			displayBoard();
			Thread.sleep(1000);
		}
		finalWriteUp();
		writeToFile("End of log");
		closeScanner();
		System.out.println("GAME OVER");
		System.out.println("~~~~~~~~~");
		System.out.println("Log file created");
		Main.openLogFileChoice();
	}

	/**
	 * Creates a new ArrayList of beings in each room of the world
	 */
	private void initialize() {
		for (int x = 0; x < boardSize; x++) {
			for (int y = 0; y < boardSize; y++) {
				world[x][y] = new ArrayList<Being>();
			}
		}
	}

	/**
	 * Resets all beings back to previous state
	 */
	private void resetAll() {
		for (int x = 0; x < boardSize; x++) {
			for (int y = 0; y < boardSize; y++) {
				if (world[x][y].size() > 0) {
					for (int i = 0; i < world[x][y].size(); i++) {
						Being being = world[x][y].get(i);
						being.hasMoved(false);
						being.setCanAct(true);
					}
				}
			}
		}
	}

	/**
	 * Goes along all cells on board If there are beings, attempt to move them
	 */
	private void moveAllOnBoard() {
		for (int x = 0; x < boardSize; x++) { // For each row
			for (int y = 0; y < boardSize; y++) { // For each column
				if (world[x][y].size() > 0) {
					moveBeing(x, y);
				}
			}
		}
	}

	/**
	 * Checks if being can move If so, moves it randomly in any direction
	 * 
	 * @param inX
	 * @param inY
	 */
	private void moveBeing(int inX, int inY) {

		int x = inX;
		int y = inY;

		for (int i = 0; i < world[x][y].size(); i++) {

			int newX = 0;
			int newY = 0;

			Being being = world[x][y].get(i);

			if (being.checkHasMoved() == false) {
			
			 if (being.isDead() == false) {
				world[x][y].remove(i);

				int tempX = rand.nextInt(3);
				int tempY = rand.nextInt(3);

				switch (tempX) {
				case 0:
					newX = x - 1;
				case 1:
					newX = x;
					break;
				case 2:
					newX = x + 1;
				default:
					break;
				}

				switch (tempY) {
				case 0:
					newY = y - 1;
					break;
				case 1:
					newY = y;
					break;
				case 2:
					newY = y + 1;
					break;
				}

				/**
				 * The board wraps now If the location would be outside of the
				 * array, make it the other side of the map
				 */
				if (newX < 0) {
					newX = boardSize - 1;
				} else if (newX >= boardSize) {
					newX = 0;
				}
				if (newY < 0) {
					newY = boardSize - 1;
				} else if (newY >= boardSize) {
					newY = 0;
				}

				Position pos = new Position(newX, newY);
				Position oldPos = being.getLocation();
				being.setLocation(pos);
				String output;

				if (pos.equals(oldPos)) {
					output = (being.getName() + " stayed at " + oldPos.toString());
				} else {
					output = (being.getName() + " has moved from " + oldPos.toString() + " to "
							+ being.getLocation().toString());
				}
				outputToConsoleAndFile(output);
				world[newX][newY].add(being);
				being.hasMoved(true);
			} 

		} 
			
		}

	}

	/**
	 * Goes along all cells on board If there are beings, make them act
	 * based on their type
	 *
	 */
	private void actAllOnBoard() {
		for (int x = 0; x < boardSize; x++) { // For each row
			for (int y = 0; y < boardSize; y++) { // For each column
				if (world[x][y].size() > 0) {
					actBeing(x, y);
				}
			}
		}
	}

	/**
	 * Makes being act INCOMPLETE
	 * 
	 * @param inX
	 * @param inY
	 */
	private void actBeing(int inX, int inY) {
		int x = inX;
		int y = inY;
		boolean zapPresent = false;

		for (int i = 0; i < world[x][y].size(); i++) {
			Being being = world[x][y].get(i);
			if (being.getName().charAt(0) == 'Z') {
				zapPresent = true;
			}
		}
		if (zapPresent) {
			killAllBonks(x, y);
		} else if (!zapPresent && world[x][y].size() > 1) {
			breedAllBonks(x, y);
		}
	}

	/**
	 * Kills all bonks in room. Is only called if there is
	 * a Zap present
	 * @param inX
	 * @param inY
	 */
	private void killAllBonks(int inX, int inY) {
		int x = inX;
		int y = inY;
		String output;

		for (int i = 0; i < world[x][y].size(); i++) {
			Being being = world[x][y].get(i);
			if (being.getName().charAt(0) == 'B' && !being.isDead()) {
				being.setDead(true);
				output = (being.getName() + " died!");
				outputToConsoleAndFile(output);
				bonkDeaths++;
			}
		}

	}

	/**
	 * Function only called when there is a cell with two or more bonks, and no zaps. 
	 * For a cell, adds all the genders to a separate arraylist.
	 * Check along the arraylist for matching genders who can breed.
	 * If a suitable pair is found, call a function to breed them
	 * @param inX
	 * @param inY
	 */
	private void breedAllBonks(int inX, int inY) {
		int x = inX;
		int y = inY;

		int numOfMales = 0;
		int numOfFemales = 0;

		ArrayList<Character> genders = new ArrayList<Character>();
		for (int i = 0; i < world[x][y].size(); i++) {
			Being being = world[x][y].get(i);
			char gender = being.getGender();
			genders.add(gender);
			if (gender == 'M') {
				numOfMales++;
			} else {
				numOfFemales++;
			}
		}
		if (numOfMales == 0 || numOfFemales == 0) {

		} else {
			for (int k = 0; k < genders.size(); k++) {
				if (genders.get(k) == 'M') {
					for (int i = 0; i < genders.size(); i++) {
						if (genders.get(i) == 'F' && world[x][y].get(i).getCanAct()) {
							breedTwoBonks(world[x][y].get(k), world[x][y].get(i), x, y);
							bonksBorn++;
							world[x][y].get(0).setCanAct(false);
							world[x][y].get(i).setCanAct(false);
						}
					}
				} else {
					for (int i = (k + 1); i < genders.size(); i++) {
						if (genders.get(i) == 'F' && world[x][y].get(i).getCanAct()) {
							breedTwoBonks(world[x][y].get(i), world[x][y].get(k), x, y);
							bonksBorn++;
							world[x][y].get(0).setCanAct(false);
							world[x][y].get(i).setCanAct(false);
						}
					}
				}
			}
		}
	}

	/**
	 * Breeds two bonks, creating a new bonk, and placing it in the same room.
	 * @param father
	 * @param mother
	 * @param x
	 * @param y
	 */
	private void breedTwoBonks(Being father, Being mother, int x, int y) {
		int nextBonk = numberOfBonks + bonksBorn + 1;
		String name = "B" + nextBonk;
		char gender = randomGender();
		Position pos = new Position(x, y);
		Bonk bonk = new Bonk(name, gender, pos);
		world[x][y].add(bonk);
		String output = bonk.getName() + " was born! It was assigned to room " + bonk.getLocation().toString();
		outputToConsoleAndFile(output);
	}

	/**
	 * Creates all the bonks and zaps needed for the game, and places them in
	 * random rooms Number is set by the user at the start of the game
	 */
	private void createBonksAndZaps() {
		for (int i = 1; i < numberOfBonks + 1; i++) {
			String name = "B" + i;
			char gender = randomGender();
			Position pos = createRandomPos();
			Bonk bonk = new Bonk(name, gender, pos);
			assignToRoom(bonk);
			System.out.println(bonk.getName() + " assigned to room " + bonk.getLocation().toString());
		}
		for (int j = 1; j < numberOfZaps + 1; j++) {
			String name = "Z" + j;
			Position pos = createRandomPos();
			Zap zap = new Zap(name, pos);
			assignToRoom(zap);
			System.out.println(zap.getName() + " assigned to room " + zap.getLocation().toString());
		}
	}

	/**
	 * Uses a random number generator (0 or 1) to determine a Bonk's gender (0
	 * for male, 1 for female) This is then returned as a char
	 * 
	 * @return
	 */
	private char randomGender() {
		char gender;

		int gendNo = rand.nextInt(2);
		if (gendNo == 0) {
			gender = 'M';
		} else {
			gender = 'F';
		}
		return gender;
	}

	/**
	 * Uses a random number generator (0 to boardSize) twice to create random X
	 * and Y coordinates Makes this into a Position, and returns it
	 * 
	 * @return
	 */
	private Position createRandomPos() {
		int newX = rand.nextInt(boardSize);
		int newY = rand.nextInt(boardSize);
		Position newPos = new Position(newX, newY);
		return newPos;
	}

	/**
	 * Assigns the being to its room
	 * 
	 * @param inBeing
	 */
	private void assignToRoom(Being inBeing) {
		Being being = inBeing;
		int x = being.getLocation().getX();
		int y = being.getLocation().getY();
		world[x][y].add(being);
	}

	/**
	 * Prints a final output of the results
	 */
	private void finalWriteUp() {
		String output = "";
		outputToConsoleAndFile(output);
		output = "STARTING BONKS:  " + numberOfBonks;
		outputToConsoleAndFile(output);
		int totalBonks = numberOfBonks + bonksBorn - bonkDeaths;
		output = "FINISHING BONKS: " + totalBonks;
		outputToConsoleAndFile(output);
		output = "BONKS BORN:      " + bonksBorn;
		outputToConsoleAndFile(output);
		output = "BONKS MURDERED:  " + bonkDeaths;
		outputToConsoleAndFile(output);
	}

	/**
	 * For any given string input, both outputs it to the console during run, and adds to the log file
	 * @param input
	 */
	private void outputToConsoleAndFile(String input) {
		System.out.println(input);
		writeToFile(input);
	}

	/**
	 * Creates the fileWriter (for the log) Also writes the log header
	 * 
	 * @throws IOException
	 */
	private void setUpScanner() throws IOException {
		file = new PrintWriter("log.txt");
		file.println("THE PREVIOUS GAME");
		file.println("~~~~~~~~~~~~~~~~~");
		file.println();
	}

	/**
	 * Write a string input to the file
	 * 
	 * @param inputString
	 */
	private void writeToFile(String inputString) {
		file.println(inputString);
	}

	/**
	 * Writes each turn number to the file
	 * 
	 * @param i
	 */
	private void writeTurnNumberToFile(int i) {
		writeToFile("");
		writeToFile("TURN " + (i + 1));
		writeToFile("~~~~~~~~");
	}

	/**
	 * Closes the scanner at the end
	 */
	private void closeScanner() {
		file.close();
	}
}
