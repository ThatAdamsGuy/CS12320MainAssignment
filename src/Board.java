import java.util.ArrayList;
import java.util.Random;

public class Board {
	
	private int boardSize;
	Random rand = new Random();
	private ArrayList<Being>[][] world;
	private int numberOfBonks;
	private int numberOfZaps;
	private int currentBonk = numberOfBonks + 1;
	
	@SuppressWarnings("unchecked")
	public Board(int bonks, int zaps, int board){
		numberOfBonks = bonks;
		numberOfZaps = zaps;
		boardSize = board;
	}
	
	public void displayBoard(){
		/**
		 * For 0 to boardsize, write "######"
		 * 		for each cell per row, check number of beings in cell
		 * 		if 0,1,2 - ##
		 * 		else - number of beings
		 */
	}
	
	public void start(){
		world = new ArrayList[boardSize][boardSize];
		initialize();
		createBonksAndZaps();
	}
	
	private void initialize() {
		for (int x = 0; x < boardSize; x++) {
			for (int y = 0; y < boardSize; y++) {
				world[x][y] = new ArrayList<Being>();
				System.out.println("Room created at " + x + "," + y);
			}
		}
	}
	
	private void createBonksAndZaps(){
		for (int i = 1; i < numberOfBonks + 1; i++){
			String name = "B" + i;
			char gender = randomGender();
			Position pos = createRandomPos();
			Bonk bonk = new Bonk(name, gender, pos);
			System.out.println(bonk.toString());
			
			
			//assignToRandomRoom(bonk);
		}
		for (int j = 1; j < numberOfZaps + 1; j++){
			String name = "Z" + j;
			Zap zap = new Zap(name);
			System.out.println(zap.toString());
		}
	}
	
	private char randomGender(){
		char gender;
		
		int gendNo = rand.nextInt(2);
		if (gendNo == 0){
			gender = 'M';
		}
		else {
			gender = 'F';
		}
		return gender;
	}
	
	public Position createRandomPos(){
		int newX = rand.nextInt(boardSize + 1);
		int newY = rand.nextInt(boardSize + 1);
		Position newPos = new Position(newX,newY);
		return newPos;
	}
	
	public void assignToRandomRoom(Being inBeing){
		Being being = inBeing;
		int x = being.getLocation().getX();
		int y = being.getLocation().getY();
		world[x][y].add(being);
	}

}
