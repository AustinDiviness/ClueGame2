package clueTest;


import java.io.FileNotFoundException;

import org.junit.Before;
import org.junit.Test;

import clueGame.BadConfigFormatException;
import clueGame.Board;
import clueGame.BoardCell;
import clueGame.RoomCell;

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
		board = new Board(); 
		try {
			board.loadConfigFiles();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e)
		{
			
		}
	}
	
	@Test
	public void dimensionsOfBoard()
	{// make sure that when loading the board correct dimensions are calculated 
		Assert.assertTrue(board.getNumRows() == 22);
		Assert.assertTrue(board.getNumColumns() == 23);
	}
	
	@Test
	public void testCalcIndex()
	{
		Assert.assertTrue(board.calcIndex(0,0) == 0);
		Assert.assertTrue(board.calcIndex(0,22) == 22);
		Assert.assertTrue(board.calcIndex(1,0) == 23);
		Assert.assertTrue(board.calcIndex(1,10) == 33);
		
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
		Assert.assertTrue(board.getDoorDirection(2, 5) == RoomCell.DoorDirection.RIGHT);
		Assert.assertTrue(board.getDoorDirection(5, 2) == RoomCell.DoorDirection.DOWN);
		Assert.assertTrue(board.getDoorDirection(4, 21) == RoomCell.DoorDirection.DOWN);
		Assert.assertTrue(board.getDoorDirection(1, 18) == RoomCell.DoorDirection.LEFT);
		Assert.assertTrue(board.getDoorDirection(19, 13) == RoomCell.DoorDirection.UP);
	}
	
	@Test
	public void ValidRoom()
	{
		Assert.assertTrue(board.getRoomNameAt(8, 0).equals("Toilet"));
		Assert.assertTrue(board.getRoomNameAt(9, 0).equals("Toilet"));
		Assert.assertTrue(board.getRoomNameAt(8, 1).equals("Toilet"));
		Assert.assertTrue(board.getRoomNameAt(9, 1).equals("Toilet"));
		Assert.assertTrue(!board.getRoomNameAt(7, 0).equals("Toilet"));
	}
	
	@Test (expected=BadConfigFormatException.class)
	public void invalidBoardConfiguration()
	{

		board = new Board("badBoardSize.csv");
		try {
			board.loadConfigFiles();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		board = new Board("badBoardParse.csv");
		try {
			board.loadConfigFiles();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
