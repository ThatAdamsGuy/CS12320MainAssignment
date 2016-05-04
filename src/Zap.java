
public class Zap implements Being {

	private String name;
	private boolean actedThisTurn;
	
	public Zap(String inName){
		name = inName;
		actedThisTurn = false;
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
	
	public String toString(){
		String string = "ZAP: Name = " + name;
		return string;
		
	}

}
