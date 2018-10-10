package ui;

import java.awt.Color;

import res.AppData;
import acm.graphics.GRect;

public class Cell extends GRect {

	private final static double WIDTH = AppData.CELL_WIDTH;
	private final static double HEIGHT = AppData.CELL_HEIGHT;
	
	private final static Color COLOR_SELECTED = AppData.CELL_COLOR_SELECTED;
	private final static Color COLOR_NOT_SELECTED = AppData.CELL_COLOR_NOT_SELECTED;
	
	private final static int VALUE_SELECTED = AppData.CELL_VALUE_SELECTED;
	private final static int VALUE_NOT_SELECTED = AppData.CELL_VALUE_NOT_SELECTED;
	
	private boolean selected;
	
	public Cell() {
		super(WIDTH,HEIGHT);
		setColor(Color.black);
		setSelected(false);
	}
	
	public int value() {
		return (selected ? VALUE_SELECTED : VALUE_NOT_SELECTED );
	}
	
	public void setSelected(boolean state) {
		selected = state;
		setFillColor(selected ? COLOR_SELECTED : COLOR_NOT_SELECTED);
		setFilled(true);
	}
	
	public boolean isSelected() {
		return selected;
	}
	
	public void toggleSelection() {
		setSelected(!selected);
	}
	
	public String toString() {
		String str = selected ? "selected" : "not selected";
		return str;
	}
}
