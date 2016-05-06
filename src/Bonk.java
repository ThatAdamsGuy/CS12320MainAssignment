import java.util.ArrayList;

public class Bonk extends Creature {
	
	private char gender;			//Male or female
	private String name;			//Unique identifier
	private boolean canMate;		//Set to true at start of turn (not including first), false once mated
	private boolean canAct;			//Has it already acted this turn?
	private boolean dead;			//If dead, will not act
	private Position currentPos;	//Current position of the bonk
	private boolean hasMoved;
	
	public Bonk(String inName, char gen, Position pos){
		name = inName;
		gender = gen;
		currentPos = pos;
		canMate = false;
		canAct = false;
		dead = false;
		hasMoved = false;
	}
	
	public void setCanMate(boolean mate){
		canMate = mate;
	}
	
	public void setCanAct(boolean act){
		canAct = act;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}

	public void act(ArrayList<Being> thisCell) throws CannotActException {
		// TODO Auto-generated method stub
		ArrayList<Being> cell = thisCell;
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
	
	@Override
	public void setDead(Boolean death){
		dead = death;
		this.name = name + 'D';
	}
	
	public boolean isDead(){
		return dead;
	}

	@Override
	public void act() throws CannotActException {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public boolean checkHasMoved(){
		return hasMoved;
	}
	
	@Override
	public void hasMoved(Boolean in){
		hasMoved = in;
	}
}
