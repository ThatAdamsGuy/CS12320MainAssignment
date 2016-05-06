package uk.ac.aber.dcs.haa14;

public class Zap extends Creature {

	private String name;
	private Position currentPos;
	private boolean hasMoved;
	
	/**
	 * Zap constructor. Includes name, position, and initially allows it to move
	 * @param inName
	 * @param pos
	 */
	public Zap(String inName, Position pos){
		name = inName;
		currentPos = pos;
		hasMoved = false;
	}
	
	/**
	 * return name of zap
	 */
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}

	/**
	 * As with bonks, was unsure of how to implement this act class
	 */
	@Override
	public void act() throws CannotActException {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Returns zap location
	 */
	@Override
	public Position getLocation() {
		// TODO Auto-generated method stub
		return currentPos;
	}

	/**
	 * Sets new zap location
	 */
	@Override
	public void setLocation(Position location) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * Checks if zap moved this turn
	 */
	@Override
	public boolean checkHasMoved(){
		return hasMoved;
	}
	
	/**
	 * Sets whether zap moved this turn
	 */
	@Override
	public void hasMoved(Boolean in){
		hasMoved = in;
	}
	
	/**
	 * toString for Zap, including vital info.
	 */
	public String toString(){
		String string = "ZAP: Name = " + name + ". Position: " + currentPos.toString() + ". Moved this turn? " + hasMoved + ".";
		return string;
		
	}

}
