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
	protected char cellIdentifer;

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
	public void draw(Graphics g) {

	}
	
	// FOR TESTING
	public char getCellCharacter()
	{
		return cellIdentifer;
	}

}
