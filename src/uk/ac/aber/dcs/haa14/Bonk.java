package uk.ac.aber.dcs.haa14;
import java.util.ArrayList;

/**
 * 
 * @author Harry Adams
 *
 */

public class Bonk extends Creature {
	
	private char gender;			//Male or female
	private String name;			//Unique identifier
	private boolean canMate;		//Set to true at start of turn (not including first), false once mated
	private boolean canAct;			//Has it already acted this turn?
	private boolean dead;			//If dead, will not act
	private Position currentPos;	//Current position of the bonk
	private boolean hasMoved;
	
	/**
	 * Bonk constructor, including their name/gender/position, and makes it alive 
	 * and cannot do anything in first turn
	 * @param inName
	 * @param gen
	 * @param pos
	 */
	public Bonk(String inName, char gen, Position pos){
		name = inName;
		gender = gen;
		currentPos = pos;
		canMate = false;
		canAct = false;
		dead = false;
		hasMoved = false;
	}
	
	/**
	 * Sets whether bonk can mate
	 * @param mate
	 */
	public void setCanMate(boolean mate){
		canMate = mate;
	}
	
	/**
	 * Sets whether bonk can act
	 */
	public void setCanAct(boolean act){
		canAct = act;
	}

	/**
	 * returns bonk name
	 */
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}

	/**
	 * An act function I didn't end up using. Not entirely sure how to use it at this point
	 * @param thisCell
	 * @throws CannotActException
	 */
	public void act(ArrayList<Being> thisCell) throws CannotActException {
		// TODO Auto-generated method stub
		
	}

	/**
	 * returns location of bonk
	 */
	@Override
	public Position getLocation() {
		// TODO Auto-generated method stub
		return this.currentPos;
	}

	/**
	 * sets new location for bonk
	 */
	@Override
	public void setLocation(Position location) {
		// TODO Auto-generated method stub
		currentPos = location;
	}

	/**
	 * toString function, printing all necessary information
	 */
	public String toString(){
		
		String string = "BONK: Name = " + name + ". Gender: " + gender + ". Breedable? " + canMate + ". Can act? " + canAct + ". Position: " + currentPos.toString() + ".";
		return string;
		
	}
	
	/**
	 * Sets to dead, and adds a 'D' to name to show this
	 */
	@Override
	public void setDead(Boolean death){
		dead = death;
		this.name = name + 'D';
	}
	
	/**
	 * Returns whether bonk is dead
	 */
	public boolean isDead(){
		return dead;
	}

	/**
	 * Another act function. As above.
	 */
	@Override
	public void act() throws CannotActException {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * Checks if bonk has moved this turn
	 */
	@Override
	public boolean checkHasMoved(){
		return hasMoved;
	}
	
	/**
	 * Sets whether bonk has moved this turn
	 */
	@Override
	public void hasMoved(Boolean in){
		hasMoved = in;
	}
	
	/**
	 * Returns bonk gender
	 */
	public char getGender(){
		return gender;
	}
	
	/**
	 * Returns whether bonk can act this turn
	 */
	public boolean getCanAct(){
		return canAct;
	}
}
