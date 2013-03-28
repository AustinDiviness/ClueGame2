package clueGame;

import java.awt.Color;
import java.awt.Graphics;

/* DESCRIPTION
 * This is a cell to denote a Room
 * 
 * If a room does not have a Door it is given the direction of NONE
 */
public class RoomCell extends BoardCell {
	public enum DoorDirection {
		UP, DOWN, LEFT, RIGHT, NONE
	}
	public static final Color color = Color.gray;
	private DoorDirection doorDirection;

	public RoomCell(char roomName)
	{
		__init__(roomName, DoorDirection.NONE);
	}
	
	public RoomCell(char roomName, int row, int col)
	{
		__init__(roomName, DoorDirection.NONE);
		this.row = row;
		this.col = col;
	}

	public RoomCell(char roomName, DoorDirection direction, int row, int col)
	{
		__init__(roomName, direction);
		this.row = row;
		this.col = col;
	}

	
	public RoomCell(char roomName, DoorDirection direction)
	{
		__init__(roomName, direction);
	}
	
	private void __init__(char roomName, DoorDirection direction)
	{
		cellIdentifer = roomName;
		doorDirection = direction;
	}
	
	@Override
	public boolean isRoom() {
		return true;
	}
	
	@Override
	public boolean isDoorway() {
		if(doorDirection == DoorDirection.NONE)
			return false;
		else
			return true;
	}
	
	@Override
	public void draw(Graphics g, Board board) {
		int x = col * width;
		int y = row * height;
		g.setColor(color);
		g.fillRect(x, y, width, height);
	}
	
	// FOR TESTING PURPOSES ONLY
	public DoorDirection getDoorDirection() {
		return doorDirection;
	}
	
	public char getInitial()
	{
		return cellIdentifer;
	}
	
}
