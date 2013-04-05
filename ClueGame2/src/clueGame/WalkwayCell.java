package clueGame;

import java.awt.Color;
import java.awt.Graphics;


public class WalkwayCell extends BoardCell {
	public static final Color backgroundColor = Color.black;
	public static final Color color = Color.DARK_GRAY;
	
	public WalkwayCell(char type) {
		cellIdentifer = type;
	}

	public WalkwayCell(char type, int row, int col) {
		this.row = row;
		this.col = col;
		cellIdentifer = type;
	}
	
	@Override
	public void draw(Graphics g) {
		int x = col * width;
		int y = row * height;
		g.setColor(backgroundColor);
		g.fillRect(x, y, width, height);
		g.setColor(color);
		g.fillRect(x+1, y+1, width-2, height-2);
	}
	
	
	@Override
	public boolean isWalkway() {
		return true;
	}
} // end class bracket
