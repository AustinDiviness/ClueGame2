package clueGame;

/* DESCRIPTION
 * Cell that is a walkway
 */

public class WalkwayCell extends BoardCell {

	public WalkwayCell(char type)
	{
		cellIdentifer = type;
	}
	
	@Override
	public boolean isWalkway() {
		return true;
	}
}
