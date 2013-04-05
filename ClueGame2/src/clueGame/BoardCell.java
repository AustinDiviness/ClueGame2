package clueGame;

import java.awt.Color;
import java.awt.Graphics;

//DESCRIPTION
//This is an abstract class for each cell of the board. 


public abstract class BoardCell {
	// Location of cell
	// Type of Cell
	protected int row, col;
	protected char cellIdentifer;
	public static final int height = 25;
	public static final int width = height;
	public static final Color highlightColor = new Color(224, 27, 221);
	
	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getCol() {
		return col;
	}

	public void setCol(int col) {
		this.col = col;
	}


	// main Functions
	public boolean isWalkway() {
		return false;
	}

	public boolean isRoom() {
		return false;
	}

	public boolean isDoorway() {
		return false;
	}

	// Drawing the Cells

	public abstract void draw(Graphics g);
	
	public void highlight(Graphics g) {
		int x = col * width;
		int y = row * height;
		g.setColor(highlightColor);
		g.fillRect(x, y, width, height);
	}
	
	public char getCellCharacter() {
		return cellIdentifer;
	}

} // end class bracket
