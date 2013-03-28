package clueGame;

import java.awt.Graphics;

/* DESCRIPTION
 * This is an abstract class for each cell of the board
 *  A cell can be a room or a walk way.
 *  If a cell is a room it can also hold a door
 */
public abstract class BoardCell {
	// Location of cell
	// Type of Cell
	protected int row, col;
	protected char cellIdentifer;
	public static final int height = 25;
	public static final int width = height;
	
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
	
	// FOR TESTING
	public char getCellCharacter()
	{
		return cellIdentifer;
	}

}
