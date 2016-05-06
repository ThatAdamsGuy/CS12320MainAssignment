import java.util.ArrayList;
import java.util.Random;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Board {

	private PrintWriter file;
	private int boardSize;
	Random rand = new Random();
	public ArrayList<Being>[][] world;
	private int numberOfBonks;
	private int numberOfZaps;
	private int numberOfCycles;
	private int currentBonk = numberOfBonks + 1;
	private int bonksBorn;
	private int bonksDied;

	/**
	 * Board initializer - takes the number of bonks,
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
	 * Function for displaying the board. Haven't figured this one out yet
	 */
	public void displayBoard() {
		/**
		 * For 0 to boardsize, write "######" for each cell per row, check
		 * number of beings in cell if 0,1,2 - ## else - number of beings
		 */

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
	 * Creates the board, and is the main function for running the game
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

	public void runGame() throws InterruptedException {
		for (int i = 0; i < numberOfCycles; i++) { // For number of cycles
			writeTurnNumberToFile(i);
			moveAllOnBoard();
			actAllOnBoard();
			displayBoard();
			Thread.sleep(1000);
		}
		writeToFile("End of log");
		closeScanner();
	}
	
	private void resetAll(){
		for (int x = 0; x < boardSize; x++){
			for (int y = 0; y < boardSize; y++){
				if (world[x][y].size() < 0){
					for (int i = 0; i < world[x][y].size(); i++){
						Being being = world[x][y].get(i);
						being.hasMoved(false);
						System.out.println(being.toString());
					}
				}
			}
		}
	}

	private void moveAllOnBoard() {
		for (int x = 0; x < boardSize; x++) { // For each row
			for (int y = 0; y < boardSize; y++) { // For each column
				if (world[x][y].size() > 0) {
					moveBeing(x, y);
				}
			}
		}
	}

	private void actAllOnBoard() {
		for (int x = 0; x < boardSize; x++) { // For each row
			for (int y = 0; y < boardSize; y++) { // For each column
				if (world[x][y].size() > 0) {
					actBeing(x, y);
				}
			}
		}
	}

	private void actBeing(int inX, int inY) {
		int x = inX;
		int y = inY;
		boolean zapPresent = false;

		for (int i = 0; i < world[x][y].size(); i++) {
			Being being = world[x][y].get(i);
			if (being.getName().charAt(0) == 'Z') {
				zapPresent = true;
			}
			if (zapPresent) {
				killAllBonks(x, y);
			}
		}
	}

	private void killAllBonks(int inX, int inY) {
		int x = inX;
		int y = inY;
		String output;

		for (int i = 0; i < world[x][y].size(); i++) {
			Being being = world[x][y].get(i);
			if (being.getName().charAt(0) == 'B' && !being.isDead()) {
				being.setDead(true);
				output = (being.getName() + " died!");
				writeToFile(output);
				System.out.println(output);
			}
		}

	}

	/**
	 * Creates a new ArrayList of beings in each room of the world Commented
	 * line for debug purposes
	 */
	private void initialize() {
		for (int x = 0; x < boardSize; x++) {
			for (int y = 0; y < boardSize; y++) {
				world[x][y] = new ArrayList<Being>();
				// System.out.println("Room created at " + x + "," + y);
			}
		}
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
	public Position createRandomPos() {
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
	public void assignToRoom(Being inBeing) {
		Being being = inBeing;
		int x = being.getLocation().getX();
		int y = being.getLocation().getY();
		world[x][y].add(being);
	}

	public void moveBeing(int inX, int inY) {

		int x = inX;
		int y = inY;
		
		for (int i = 0; i < world[x][y].size(); i++) {

			int newX = 0;
			int newY = 0;

			Being being = world[x][y].get(i);

			if (being.checkHasMoved() == false) {
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

				if (pos.toString().equals(oldPos.toString())) {
					output = (being.getName() + " stayed at " + oldPos.toString());
				} else {
					output = (being.getName() + " has moved from " + oldPos.toString() + " to "
							+ being.getLocation().toString());
				}

				System.out.println(output);
				writeToFile(output);
				world[newX][newY].add(being);
				being.hasMoved(true);
			}
			

		}

	}

	private void setUpScanner() throws IOException {
		file = new PrintWriter("log.txt");
		file.println("THE PREVIOUS GAME");
		file.println("~~~~~~~~~~~~~~~~~");
		file.println();
	}

	private void writeToFile(String inputString) {
		file.println(inputString);
	}

	private void writeTurnNumberToFile(int i) {
		writeToFile("");
		writeToFile("TURN " + (i + 1));
		writeToFile("~~~~~~~~");
	}

	private void closeScanner() {
		file.close();
	}
}
