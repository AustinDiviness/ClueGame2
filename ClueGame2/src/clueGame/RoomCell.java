package clueGame;

import java.awt.Color;
import java.awt.Graphics;

//DESCRIPTION
//This is a cell to denote a Room
//If a room does not have a Door it is given the direction of NONE

public class RoomCell extends BoardCell {
	public enum DoorDirection {
		UP, DOWN, LEFT, RIGHT, NONE
	}
	public static final Color color = Color.gray;
	public static final Color doorColor = Color.cyan;
	public static final int doorWidth = 5;
	private DoorDirection doorDirection;

	public RoomCell(char roomName) {
		init(roomName, DoorDirection.NONE);
	}
	
	public RoomCell(char roomName, int row, int col) {
		init(roomName, DoorDirection.NONE);
		this.row = row;
		this.col = col;
	}

	public RoomCell(char roomName, DoorDirection direction, int row, int col) {
		init(roomName, direction);
		this.row = row;
		this.col = col;
	}

	
	public RoomCell(char roomName, DoorDirection direction) {
		init(roomName, direction);
	}
	
	private void init(char roomName, DoorDirection direction) {
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
	public void draw(Graphics g) {
		int x = col * width;
		int y = row * height;
		g.setColor(color);
		g.fillRect(x, y, width, height);
		if (isDoorway()) {
			drawDoor(g);
		}
	}
	
	public void drawDoor(Graphics g) {
		int x = col * width;
		int y = row * height;
		g.setColor(doorColor);
		switch(doorDirection) {
			case UP:
				g.fillRect(x, y, width, doorWidth);
				break;
			case DOWN:
				g.fillRect(x, y+height-doorWidth, width, doorWidth);
				break;
			case LEFT:
				g.fillRect(x, y, doorWidth, height);
				break;
			case RIGHT:
				g.fillRect(x+width-doorWidth, y, doorWidth, height);
				break;
		}
	}

	public DoorDirection getDoorDirection() {
		return doorDirection;
	}
	
	public char getInitial() {
		return cellIdentifer;
	}
	
} // end class bracket
