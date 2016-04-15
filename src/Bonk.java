
public class Bonk implements Being {
	
	private char gender;
	private String name;
	private boolean canMate;
	
	public Bonk(String inName, char gen){
		name = inName;
		gender = gen;
		canMate = false;
	}
	
	public void setCanMate(boolean mate){
		canMate = mate;
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
		return null;
	}

	@Override
	public void setLocation(Position location) {
		// TODO Auto-generated method stub
		
	}

}
