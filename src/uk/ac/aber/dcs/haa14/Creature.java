package uk.ac.aber.dcs.haa14;
/**
 * 
 * @author Harry Adams
 *
 */
public class Creature implements Being {
	/**
	 * Return creature name
	 */
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * return creature location
	 */
	@Override
	public Position getLocation() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * set new location
	 */
	@Override
	public void setLocation(Position location) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * This would be the acting class
	 * However, I couldn't figure out how to get it to interact with the rest of the world
	 * And then it broke
	 * So I put it in the World class
	 */
	@Override
	public void act() throws CannotActException {
		// TODO Auto-generated method stub
		
	}

	/**
	 * checks if dead
	 */
	@Override
	public boolean isDead() {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * checks if being has moved
	 */
	@Override
	public boolean checkHasMoved() {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * Sets if being has moved
	 */
	@Override
	public void hasMoved(Boolean in) {
		// TODO Auto-generated method stub
	}

	/**
	 * Sets death state
	 */
	@Override
	public void setDead(Boolean death) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public char getGender() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean getCanAct() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setCanAct(boolean b) {
		// TODO Auto-generated method stub
		
	}



	

}
