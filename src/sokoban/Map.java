package sokoban;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


/**
 * Implementation of loading map files, that is, reading map files in maps folder
 * @author Chen Haipei
 */
public class Map {

	private int[][] map = new int[20][20];
	private int manX, manY;

	@SuppressWarnings("null")
	public Map(int level) {
		String filepath = "Maps/map" + level + ".txt";
		File file = new File(filepath);
		FileReader fr = null;
		BufferedReader br = null;
		try {
			fr = new FileReader(file);
			br = new BufferedReader(fr);
			for (int i = 0; i < 16; i++) {
				String line = br.readLine();
				byte[] point = line.getBytes();
				for (int j = 0; j < 16; j++) {
					map[i][j] = point[j] - 48;
					if (map[i][j] == 5 || map[i][j] == 6 || map[i][j] == 7
							|| map[i][j] == 8) {
						manX = i;
						manY = j;
					}
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			if (br == null) {
				try {
					br.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				br = null;
			}
			if (fr == null) {
				try {
					fr.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				fr = null;
			}
		}

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