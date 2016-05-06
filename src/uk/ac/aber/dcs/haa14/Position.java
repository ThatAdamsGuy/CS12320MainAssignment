package uk.ac.aber.dcs.haa14;

public class Position {

	private int x;
	private int y;

	public Position(int x, int y) {
		this.setX(x);   
		this.setY(y);
	}

	/**
	 * @return the x
	 */
	public int getX() {
		return x;
	}

	/**
	 * @param x the x to set
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * @return the y
	 */
	public int getY() {
		return y;
	}

	/**
	 * @param y the y to set
	 */
	public void setY(int y) {
		this.y = y;
	}
	
	/**
	 * toString of position (gives co-ordinates as a grid reference
	 */
	public String toString(){
		String string = "(" + x + "," + y + ")";
		return string;
	}

}
