package clueGame;

import java.awt.Graphics;

/* DESCRIPTION
 * Cell that is a walkway
 */

public class WalkwayCell extends BoardCell {

	public WalkwayCell(char type)
	{
		cellIdentifer = type;
	}

	public WalkwayCell(char type, int row, int col)
	{
		this.row = row;
		this.col = col;
		cellIdentifer = type;
	}
	
	@Override
	public void draw(Graphics g) {
		// TODO implement function
	}
	
	
	@Override
	public boolean isWalkway() {
		return true;
	}
}
