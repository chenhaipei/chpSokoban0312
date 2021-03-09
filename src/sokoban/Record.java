package sokoban;
/**
 * Record every step of the player, in case they want to step back
 * @author Chen Haipei
 */
public class Record {
	
	public int x; //X coordinates
	public int y; //Y coordinates
	public int flag; // Icon values on maps
	//1: wall, 2: floor, 3: empty pit, 4: full pit, 5: box, 6: up, 7: down, 8: left, 9: right
	public int curManX; //Current person's X coordinates
	public int curManY; //Current person's X coordinates
	private int[][] map;
	private int manX;
	private int manY;

	public Record(int[][] map, int manX, int manY) {
		this.map = map;
		this.manX = manX;
		this.manY = manY;
	}

	public int[][] getMap() {
		return map;
	}

	public int getManX() {
		return manX;
	}

	public int getManY() {
		return manY;
	}
}