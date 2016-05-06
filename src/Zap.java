
public class Zap extends Creature {

	private String name;
	private Position currentPos;
	private boolean actedThisTurn;
	
	public Zap(String inName, Position pos){
		name = inName;
		currentPos = pos;
		actedThisTurn = false;
	}
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}

	@Override
	public void act() throws CannotActException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Position getLocation() {
		// TODO Auto-generated method stub
		return currentPos;
	}

	@Override
	public void setLocation(Position location) {
		// TODO Auto-generated method stub
		
	}
	
	public String toString(){
		String string = "ZAP: Name = " + name + ". Position: " + currentPos.toString() + ".";
		return string;
		
	}

}
