package res;

import java.awt.Color;
import java.util.ArrayList;

public class AppData {
	public static final int GRID_ROWS = LearningData.N_ROWS;
	public static final int GRID_COLS = LearningData.N_COLS;
	
	public static final double CELL_WIDTH = 15;
	public static final double CELL_HEIGHT = 20;
	
	public static final int CELL_VALUE_SELECTED = 1;
	public static final int CELL_VALUE_NOT_SELECTED = -1;
	
	public final static Color CELL_COLOR_SELECTED = Color.red.darker();
	public final static Color CELL_COLOR_NOT_SELECTED = Color.white;
	
	public static final ArrayList<int[]> LEARNING_DATA = new ArrayList<int[]>();
	
	public static final int DIM = GRID_COLS * GRID_ROWS;
	
	public static final int N_DATA_VEC = 100;
	
	public static final int[][] learningVectors = new int[N_DATA_VEC][DIM];
	
	static {
		
	}
}
