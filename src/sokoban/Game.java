package sokoban;

import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Stack;

import javax.swing.*;
import java.awt.Graphics;

/**
 *  Main algorithms for Games 
* @author Chen Haipei
 */

public class Game extends JPanel implements KeyListener {
	//SokobanPanel
	//Creating relationships between classes. 

	private static final long serialVersionUID = 1246914528574248907L;
	// The game will require 7 levels. 
	public final static int MAXLEVEL = 10;  // Maximum level
	// Define Variable Select level
	int level = 1;
	// Preserve the current coordinates of the "man"
	int manX, manY;  
	private Map lm ;
	//The program  record how many moves a player takes to solve a level, 
	//and output this information visually. 
	private Stack<Record> stack = new Stack<Record>();
	private Record bk;
	private int step =0;
	SokobanMain main;
	// Define an array to load images
	final Image pic[] = { new ImageIcon("pic/bgpinkfloor.jpeg").getImage(), 
			new ImageIcon("pic/cookie.jpg").getImage(),
			new ImageIcon("pic/bgpinkfloor1.jpeg").getImage(), 
			new ImageIcon("pic/ah.jpg").getImage(),
			new ImageIcon("pic/yes.jpg").getImage(), 
			new ImageIcon("pic/candy.jpg").getImage(),
			new ImageIcon("pic/up.jpg").getImage(), 
			new ImageIcon("pic/down.jpg").getImage(),
			new ImageIcon("pic/left.jpg").getImage(), 
			new ImageIcon("pic/right.jpg").getImage() 
			};

	public int temp_map[][], map[][], record[][];

	// Define a constructor
	public Game() {
		level = 1;
		lm = new Map(level);
		map = lm.getMap();
		init(level);
		// Registration event
		addKeyListener(this);
		// Focus of acquisition
		requestFocus();
	}

	public void back() {
		if (!stack.isEmpty()) {
			Record bk = stack.pop();
			record = new int[16][16];
			record = bk.getMap();
			for (int i = 0; i < 16; i++) {
				for (int j = 0; j < 16; j++) {
					temp_map[i][j] = record[i][j];
				}
			}
			copydata(record);
			repaint();
			step--;	
		}
	}

	void copydata(int data[][]) {
		// Keep the original array unchanged
		temp_map = new int[16][16];
		manX = manY = 0;   // Initialization
		for (int i = 0; i < 16; i++)
			for (int j = 0; j < 16; j++) {
				// man��s Initial Position
				if (data[i][j] == 6 || data[i][j] == 7 || data[i][j] == 8 || data[i][j] == 9) {
					manX = i;
					manY = j;
				}
				temp_map[i][j] = data[i][j];
			}
	}

	public int getStep() {
		return step;
	}

	public void goDown () {
		// There are boxes (5) in the first place below, or pits filled with boxes.
		step++;
		if (temp_map [manX + 1] [manY]== 5 || temp_map [manX + 1] [manY]== 4) {
			// The second position below is floor 2
			if (temp_map [manX + 2] [manY]== 2) {
				saveData ();
				// The second position below is box 5
				temp_map [manX + 2] [manY] = 5; 
				// The first position below is person 7
				temp_map [manX + 1] [manY] = 7; 
				// After moving, the current person's position is replaced by executing this method.
				manOldPos (); 
				manX++;
			// The second position below is empty Pit 3
			}else if (temp_map [manX + 2] [manY]== 3) {
				saveData ();
				// The second position below is the box pushed into the empty pit to fill the pit 4
				temp_map [manX + 2] [manY] = 4; 
				temp_map [manX + 1] [manY] = 7; // The first place below is the person
				//After moving, the current person's position is changed to execute this method.
				manOldPos (); 
				manX++;
			}else {
				step--;	
			}
		// the first floor below, or an empty pit
		}else if (temp_map [manX + 1] [manY]== 2 || temp_map [manX + 1] [manY]== 3) {
			saveData ();
			temp_map [manX + 1] [manY] = 7; // The first position below is person 7
			//After moving, the current person's position is changed to execute this method.
			manOldPos (); 
			manX++;
		}else {
			step--;
		}
		SokobanMain.lblStep.setText(step+"");
	}

	public void goLeft() {
		// There are boxes (5) in the first position on the left or boxes (4) in the empty pit.
		step++;
		if (temp_map [manX] [manY-1]== 5 || temp_map [manX] [manY-1]== 4) {
			if (temp_map [manX] [manY-2]== 2) {
				saveData ();
				// The second position on the left is the box (5)
				temp_map [manX] [manY-2] = 5; 
				// The first position on the left is human (8)
				temp_map [manX] [manY-1] = 8; 
				// After moving, the current person's position is replaced by executing this method.
				manOldPos (); 
				manY--;
				saveData ();
			// The second position on the left is the empty pit (3)
			}else if (temp_map [manX] [manY-2]== 3) {
				saveData ();
				// The second position on the left is the box pushed into the empty pit to fill the pit (4)
				temp_map [manX] [manY-2] = 4; 
				// The first position on the left is human (8)
				temp_map [manX] [manY-1] = 8; 
				// After moving, the current person's position is changed to perform this method
				manOldPos (); 
				manY--;
			}else {
				step--;
			}
		// the first floor on the left, or an empty pit
		}else  if (temp_map [manX] [manY-1]== 2 || temp_map [manX] [manY-1]== 3) {
			saveData ();
			// The first position on the left is human (8)
			temp_map [manX] [manY-1] = 8;
			// After moving, the current person's position is replaced by executing this method.
			manOldPos (); 
			manY--;
		}else {
			step--;
		}
		SokobanMain.lblStep.setText(step+"");
	}

	public void goRight() {
		// The first place on the right is the box (5), or the box on the empty pit (4)
		step++;
		if (temp_map [manX] [manY + 1]== 5 || temp_map [manX] [manY + 1]== 4) {
			// The second position on the right is the floor (2)
			if (temp_map [manX] [manY + 2]== 2) {
				saveData ();
				// The second position on the right is the box (5)
				temp_map [manX] [manY + 2] = 5; 
				// The first position on the right is human (9)
				temp_map [manX] [manY + 1] = 9; 
				// After moving, the current person's position is replaced by executing this method.
				manOldPos (); 
				manY++;
			// The second place on the right is the empty pit (3)
			}else if (temp_map [manX] [manY + 2]== 3) {
				saveData ();
				// The second position on the right is the box pushed into the empty pit to fill the pit (4)
				temp_map [manX] [manY + 2] = 4; 
				// The first position on the right is human (9)
				temp_map [manX] [manY + 1] = 9; 
				// After moving, the current person's position is changed to perform this method
				manOldPos (); 
				manY++;
			}else {
				step--;
			}
		// the first floor on the right, or an empty pit
		}else if (temp_map [manX] [manY + 1]== 2 || temp_map [manX] [manY + 1]== 3) {
			saveData ();
			// The first position on the right is human (9)
			temp_map [manX] [manY + 1] = 9; 
			// After moving, the current person's position is replaced by executing this method.
			manOldPos (); 
			manY++;
		}else {
			step--;
		}
		SokobanMain.lblStep.setText(step+"");
	}

	public void goUp() {
		step++;
		if (temp_map [manX-1] [manY]== 5 || temp_map [manX-1] [manY]== 4) {
			//The second position in the moving direction is the floor.
			if (temp_map [manX-2] [manY]== 2) {
				saveData ();
				// The second position above is the box (5).
				temp_map [manX-2] [manY] = 5; 
				// The first position above is person (6)
				temp_map [manX-1] [manY] = 6; 
				// After moving, the current person's position is changed to execute this method.
				manOldPos (); 
				manX--;
				// The second position on the left is empty Pit 3
			}else if (temp_map [manX-2] [manY]== 3) {
				saveData ();
				// The second position on the left is the box pushed into the empty pit for full pit 4
				temp_map [manX-2] [manY] = 4; 
				// The first position on the left is person 6
				temp_map [manX-1] [manY] = 6; 
				// After moving, the current person's position is changed to execute this method.
				manOldPos (); 
				manX--;
			}else {
				step--;
			}
		// the first floor on the left, or an empty pit
		}else if (temp_map [manX-1] [manY]== 2 || temp_map [manX-1] [manY]== 3) {
			saveData ();
			// The first position on the left is person 6
			temp_map [manX-1] [manY] = 6;
			// After moving, the current person's position is changed to execute this method.
			manOldPos (); 
			manX--;
		}else {
			step--;
		}
		SokobanMain.lblStep.setText(step+"");
	}

	public void init(int level) {
		this.level = level;
		lm = new Map(level);
		map = lm.getMap();
		//map = new Map(level).getMap();
		copydata(map);
		stack.clear();
		step=0;
		SokobanMain.lblGate.setText(level+"");
		SokobanMain.lblStep.setText(step+"");
		requestFocus();
		repaint();// Call update() function - call paint (),
		//press any one of the keys to determine whether to clear customs
	}

	/*Check whether the current clearance has been completed
	There are no empty pits in the map and no ordinary boxes to complete the level.*/
	public boolean isWin() {
		for (int i = 0; i < 16; i++) {
			for (int j = 0; j < 16; j++) {
				if (temp_map[i][j] == 5||temp_map[i][j]==3)
					return false;
			}
		}
		return true;
	}

	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_LEFT:
			//Press the "left" direction key
			goLeft();
			break;
		case KeyEvent.VK_UP: 
			// Press the "up" direction key
			goUp();
			break;
		case KeyEvent.VK_RIGHT: 
			// Press the "right" direction key
			goRight();
			break;
		case KeyEvent.VK_DOWN: 
			// Press the "down" direction key
			goDown();
			break;
		default:
			break;	
		}
		repaint (); 
		// Register events
		if (isWin ()) {
			//JOptionPane. showMessageDialog (null, "Congratulations!");
			Object[] options ={ "Next", "Play again" };  
			//Text on custom buttons
			ImageIcon win=new ImageIcon("pic/downwin.jpg");		
			//set size of icon, but it is not clear	
			//win.setImage(win.getImage().getScaledInstance(200,200,Image.SCALE_DEFAULT));
			int m = JOptionPane.showOptionDialog(null, "", "Congratulations!",
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, 
					win, options, options[0]);  
			if (m == JOptionPane.YES_OPTION)
			Sel ("Next");           // Enter the next level
			if (m == JOptionPane.NO_OPTION)
			reStart();           // Play again
		}
	}
	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	//1: Wall 2: Floor 3: Empty pit 4: Full pit 5: Box 6: Man (pushing up) 7: Man (pushing down) 8: Man (pushing left) 9: Man (pushing right)

	//Processing of the original map person, box, empty pit, full pit, floor in map
	// After moving, the current person's position is replaced by executing this code
	public void manOldPos () {
		if (map [manX] [manY]== 5 || map [manX] [manY] >= 6)
			temp_map [manX] [manY] = 2;
		// originally full pit 4, it becomes empty Pit 3
		else if (map [manX] [manY]== 4)
			temp_map [manX] [manY] = 3;
		// The original person's place is the original picture;
		else
			temp_map [manX] [manY] = map [manX] [manY]; 
	}

	@Override
	public void paint(Graphics g) {
		int left, top;
		for (int i = 0; i < 16; i++) {
			for (int j = 0; j < 16; j++) {
				left = j * 35;
				top = i * 35;
				g.drawImage(pic[temp_map[i][j]], left, top, 35, 35, this);
			}
		}
	}

	public void reStart() {
		this.manX = lm.getManX();
		this.manY = lm.getManY();
		init(level);
		repaint();
	}

	public void saveData () {
		record = new int [16] [16];
		for (int i = 0; i < 16; i++) {
			for (int j = 0; j < 16; j++) {
				// save the map information of the previous step
				record [i] [j] = temp_map [i] [j]; 
			}
		}
		bk = new Record (record, manX, manY);
		stack. push (bk);
	}

	public void Sel(String opr) {
		// First Next Last
		if (opr.equals("First")) {
			level = 1;
		} else if (opr.equals("Last")) {
			level = MAXLEVEL;
		} else if (opr.equals("Prev")) {
			if (level <= 1)
				level = 1;
			else
				level--;
		}else if (opr.equals("Next")) {
			if (level >= MAXLEVEL)
				level = MAXLEVEL;
			else
				level++;
		}
		init(level);
	}

	public void setStep(int step) {
		this.step = stack.size();
	}
}