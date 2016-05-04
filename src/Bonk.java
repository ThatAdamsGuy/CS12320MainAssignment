
public class Bonk implements Being {
	
	private char gender;
	private String name;
	private boolean canMate;
	private boolean canAct;
	private int xPos;
	private int yPos;
	private Position currentPos;
	
	public Bonk(String inName, char gen, Position pos){
		name = inName;
		gender = gen;
		currentPos = pos;
		canMate = false;
		canAct = false;
	}
	
	public void setCanMate(boolean mate){
		canMate = mate;
	}
	
	public void setActedThisTurn(boolean act){
		
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void act() throws CannotActException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Position getLocation() {
		// TODO Auto-generated method stub
		return this.currentPos;
	}

	@Override
	public void setLocation(Position location) {
		// TODO Auto-generated method stub
		currentPos = location;
	}

	public String toString(){
		
		String string = "BONK: Name = " + name + ". Gender: " + gender + ". Breedable? " + canMate + ". Can act? " + canAct + ". Position: " + currentPos.toString() + ".";
		return string;
		
	}
}
