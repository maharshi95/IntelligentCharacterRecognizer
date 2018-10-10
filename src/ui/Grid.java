package ui;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.SwingUtilities;

import res.AppData;
import acm.graphics.GCanvas;
import acm.graphics.GCompound;

public class Grid extends GCompound {
	
	private static final int N = AppData.GRID_COLS * AppData.GRID_ROWS;
	
	private final int rows;
	private final int cols;
	private final double width;
	private final double height;
	
	private boolean listening;
	private boolean leftPress;
	private boolean rightPress;
	private MouseAdapter mouseAdatper;
	private Cell[][] cellArray;
	
	public Grid() {
		super();
		rows = AppData.GRID_ROWS;
		cols = AppData.GRID_COLS;
		width = rows * AppData.CELL_HEIGHT;
		height = rows * AppData.CELL_HEIGHT;
		
		initMouseAdapter();
		cellArray = new Cell[rows][cols];
		
		for(int i=0; i<rows; i++) {
			for(int j=0; j<cols; j++) {
				cellArray[i][j] = new Cell();
				cellArray[i][j].setLocation(j*AppData.CELL_WIDTH, i*AppData.CELL_HEIGHT);
				add(cellArray[i][j]);
			}
		}
		leftPress = false;
		rightPress = false;
		startListening();
	}
	
	private void initMouseAdapter() {
		mouseAdatper = new MouseAdapter() {
			
			@Override
			public void mousePressed(MouseEvent e) {
				Cell cell = (Cell)e.getSource();
				if(e.getButton() == 1) {
					cell.setSelected(true);
					leftPress = true;
				}
				else {
					cell.setSelected(false);
					rightPress = true;
				}
				
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				Cell cell = (Cell) e.getSource();
				if(leftPress) {
					cell.setSelected(true);
				}
				else if(rightPress) {
					cell.setSelected(false);
				}
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				leftPress = false;
				rightPress = false;
			}
		};
	}
	
	void refresh() {
		for(int i=0; i<rows; i++) {
			for(int j=0; j<cols; j++) {
				cellArray[i][j].setSelected(false);
			}
		}
	}
	
	public void startListening() {
		if(!listening) {
			for(int i=0; i<rows; i++) {
				for(int j=0; j<cols; j++) {
					cellArray[i][j].addMouseListener(mouseAdatper);
				}
			}
			listening = true;
		}
	}
	
	public void stopListening() {
		if(listening) {
			for(int i=0; i<rows; i++) {
				for(int j=0; j<cols; j++) {
					cellArray[i][j].removeMouseListener(mouseAdatper);
				}
			}
			listening = false;
		}
	}
	
	int[] vector() {
		int[] vec = new int[N];
		int k = 0;
		for(int i=0; i<rows; i++) {
			for(int j=0; j<cols; j++) {
				vec[k++] = cellArray[i][j].isSelected() ? 1 : -1;
			}
		}
		return vec;
	}
	
	void displayVector(int[] vector) {
		refresh();
		for(int k=0; k<vector.length; k++) {
			cellArray[k/cols][k%cols].setSelected(vector[k] > 0);
		}
	}
	
}
