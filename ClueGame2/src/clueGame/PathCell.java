package clueGame;

public class PathCell {
	public BoardCell boardCell;
	public PathCell parentCell;
	public int cost, g, h;
	
	public PathCell(BoardCell boardCell, PathCell parentCell, int g, int h) {
		this.boardCell = boardCell;
		this.parentCell = parentCell;
		this.g = g;
		this.h = h;
		this.cost = g + h;
	}
}
