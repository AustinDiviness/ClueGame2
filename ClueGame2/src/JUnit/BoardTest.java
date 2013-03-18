package JUnit;

import Board.Board;
import Board.BoardCell;
import Board.RoomCell;
import Board.BadConfigFormatException;

import org.junit.Before;
import org.junit.Test;

import junit.framework.Assert;

/* DESCRIPTION
 * 
 * These test are designed to test the board its self 
 * 
 * All tests are made for the current legend and board config files
 * found in the JUnit directory
 * Loading the config files are hard coded
 * 
 * Type of tests
 *  x	- Dimensions of board
 *  		- rows and columns are correct
 * 	x	- calcIndex
 *  x	- Number of rooms
 *  x	- Character mapping to room names
 *  x	- Correct Number of Doors
 *  		- isDoorway
 *  x	- Door direction
 *  x	- isValid room
 *  		- Make sure that all same letters are connecting for rooms
 *		- Exception is thrown when appropriate
 *			- Invalid board file
 */

public class BoardTest {
	Board board;
	
	@Before
	public void  reset()
	{ // Reset the board to original state
		board = new Board(); // TODO input file name
	}
	
	@Test
	public void dimensionsOfBoard()
	{// make sure that when loading the board correct dimensions are calculated 
		Assert.assertTrue(board.getNumberRow() == 22);
		Assert.assertTrue(board.getNumberColumn() == 23);
	}
	
	@Test
	public void testCalcIndex()
	{
		Assert.assertTrue(board.calcIndex(1,1) == 0);
		Assert.assertTrue(board.calcIndex(1,23) == 22);
		Assert.assertTrue(board.calcIndex(2,1) == 23);
		Assert.assertTrue(board.calcIndex(2,11) == 33);
		
		//Assert.assertTrue(board.calcIndex(11,13) == 11*22+13);
		//Assert.assertTrue(board.calcIndex(21,23) == 21*22+23);
	}

	@Test
	public void characterMap()
	{ // test if characters map to the correct room
		
		Assert.assertTrue(board.roomNameWithChar('R').equals("Trophy Room"));
		Assert.assertTrue(board.roomNameWithChar('T').equals("Toilet"));
		Assert.assertTrue(board.roomNameWithChar('C').equals("Crown Gem Room"));
		Assert.assertTrue(board.roomNameWithChar('D').equals("Dungeon Room"));
		Assert.assertTrue(board.roomNameWithChar('A').equals("Animal Room"));
		Assert.assertTrue(board.roomNameWithChar('M').equals("Computer Room"));
		Assert.assertTrue(board.roomNameWithChar('O').equals("Deadly Objects Room"));
		Assert.assertTrue(board.roomNameWithChar('S').equals("Server Room"));
		Assert.assertTrue(board.roomNameWithChar('P').equals("Python Room"));
		Assert.assertTrue(board.roomNameWithChar('X').equals("Closet"));
	}
	
	
	@Test
	public void NumberOfRooms()
	{ // Make sure that rooms loaded correctly
		Assert.assertTrue(board.numberOfRooms() == 10);
	}
	
	@Test
	public void correctNumberOfDoors()
	{ // Checks if the doors got loaded correctly
		// does not check direction of the doors
		// check the isDorway function
		int totalNumberOfDoors = 13;
		int count = 0;
		for(BoardCell c: board.getBoard())
		{
			if (c.isDoorway())
				count++;
		}
		Assert.assertTrue(count == totalNumberOfDoors);
	}
	
	@Test
	public void DoorDirection()
	{
		Assert.assertTrue(board.getDoorDirection(3, 6).equals("RIGHT"));
		Assert.assertTrue(board.getDoorDirection(6,3).equals("DOWN"));
		Assert.assertTrue(board.getDoorDirection(5,22).equals("DOWN"));
		Assert.assertTrue(board.getDoorDirection(2,19).equals("LEFT"));
		Assert.assertTrue(board.getDoorDirection(20,14).equals("UP"));
	}
	
	@Test
	public void ValidRoom()
	{
		Assert.assertTrue(board.getRoomNameAt(9, 1).equals("Toilet"));
		Assert.assertTrue(board.getRoomNameAt(10, 1).equals("Toilet"));
		Assert.assertTrue(board.getRoomNameAt(9, 2).equals("Toilet"));
		Assert.assertTrue(board.getRoomNameAt(10, 2).equals("Toilet"));
		Assert.assertTrue(!board.getRoomNameAt(7, 0).equals("Toilet"));
	}
	
	@Test
	public void invalidBoardConfiguration()
	{
		board = null;
		try 
		{
			board = new Board("badBoardSize.csv");
			Assert.assertTrue(false);
		}
		catch(BadConfigFormatException e)
		{
			Assert.assertTrue(true);
		}
		
		board = null;
		try 
		{
			board = new Board("badBoardParse.csv");
			Assert.assertTrue(false);
		}
		catch(BadConfigFormatException e)
		{
			Assert.assertTrue(true);
		}
	}
	
}
