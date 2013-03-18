package clueTest;

import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Set;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import clueGame.Board;
import clueGame.BoardCell;

public class AdjacenciesTest {
	Board board;
	
	@Before
	public void setUp()
	{
		board = new Board();
		try {
			board.loadConfigFiles();
		} catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		}
	
	}
	
	@Test
	public void testWalkwayAdjacencies()
	{
		
		//Tests adjacencies of walkway cells
		LinkedList<Integer> testList = board.getAdjList(board.calcIndex(0, 17));
			Assert.assertEquals(3, testList.size());
				Assert.assertTrue(testList.contains(board.calcIndex(0, 16)));
				Assert.assertTrue(testList.contains(board.calcIndex(0, 18)));
				Assert.assertTrue(testList.contains(board.calcIndex(1, 17)));
				
		testList = board.getAdjList(board.calcIndex(9, 8));
			Assert.assertEquals(4, testList.size());
				Assert.assertTrue(testList.contains(board.calcIndex(9, 7)));
				Assert.assertTrue(testList.contains(board.calcIndex(9, 9)));
				Assert.assertTrue(testList.contains(board.calcIndex(8, 8)));
				Assert.assertTrue(testList.contains(board.calcIndex(10, 8)));
				
		testList = board.getAdjList(board.calcIndex(15, 22));
			Assert.assertEquals(3, testList.size());
				Assert.assertTrue(testList.contains(board.calcIndex(15, 21)));
				Assert.assertTrue(testList.contains(board.calcIndex(14, 22)));
				Assert.assertTrue(testList.contains(board.calcIndex(16, 22)));
				
		testList = board.getAdjList(board.calcIndex(18, 7));
			Assert.assertEquals(4, testList.size());
				Assert.assertTrue(testList.contains(board.calcIndex(18, 6)));
				Assert.assertTrue(testList.contains(board.calcIndex(18, 8)));
				Assert.assertTrue(testList.contains(board.calcIndex(17, 7)));
				Assert.assertTrue(testList.contains(board.calcIndex(19, 7)));
				
		testList = board.getAdjList(board.calcIndex(21, 2));
			Assert.assertEquals(1, testList.size());
				Assert.assertTrue(testList.contains(board.calcIndex(21, 3)));
			
		testList = board.getAdjList(board.calcIndex(7, 0));
			Assert.assertEquals(2, testList.size());
				Assert.assertTrue(testList.contains(board.calcIndex(6, 0)));
				Assert.assertTrue(testList.contains(board.calcIndex(7, 1)));
	}

	@Test
	public void testInsideRoomAdjacencies()
	{
		LinkedList<Integer> testList = board.getAdjList(board.calcIndex(0, 0));
		Assert.assertEquals(0, testList.size());
		testList = board.getAdjList(board.calcIndex(4, 4));
		Assert.assertEquals(0, testList.size());
		testList = board.getAdjList(board.calcIndex(7, 7));
		Assert.assertEquals(0, testList.size());
		testList = board.getAdjList(board.calcIndex(0, 0));
		Assert.assertEquals(0, testList.size());
		testList = board.getAdjList(board.calcIndex(0, 10));
		Assert.assertEquals(0, testList.size());
		testList = board.getAdjList(board.calcIndex(10, 22));
		Assert.assertEquals(0, testList.size());
		testList = board.getAdjList(board.calcIndex(21, 22));
		Assert.assertEquals(0, testList.size());
		testList = board.getAdjList(board.calcIndex(16, 10));
		Assert.assertEquals(0, testList.size());
		testList = board.getAdjList(board.calcIndex(21, 14));
		Assert.assertEquals(0, testList.size());
	}
	
	@Test
	public void testRoomExits()
	{
		LinkedList<Integer> testList = board.getAdjList(board.calcIndex(8, 1));
			Assert.assertEquals(1, testList.size());		
				Assert.assertTrue(testList.contains(board.calcIndex(8, 2)));
			
		testList = board.getAdjList(board.calcIndex(11, 4));
			Assert.assertEquals(1, testList.size());		
				Assert.assertTrue(testList.contains(board.calcIndex(10, 4)));
		
		testList = board.getAdjList(board.calcIndex(16, 5));
			Assert.assertEquals(1, testList.size());		
				Assert.assertTrue(testList.contains(board.calcIndex(16,6)));
	}
	
	@Test
	public void testBesideRoomEntrance()
	{
		LinkedList<Integer> testList = board.getAdjList(board.calcIndex(8, 2));
			Assert.assertEquals(4, testList.size());		
				Assert.assertTrue(testList.contains(board.calcIndex(8, 1)));
				Assert.assertTrue(testList.contains(board.calcIndex(8, 3)));
				Assert.assertTrue(testList.contains(board.calcIndex(7, 2)));
				Assert.assertTrue(testList.contains(board.calcIndex(9, 2)));
				
		testList = board.getAdjList(board.calcIndex(10, 4));
			Assert.assertEquals(4, testList.size());		
				Assert.assertTrue(testList.contains(board.calcIndex(10, 3)));
				Assert.assertTrue(testList.contains(board.calcIndex(10, 5)));
				Assert.assertTrue(testList.contains(board.calcIndex(9, 4)));
				Assert.assertTrue(testList.contains(board.calcIndex(11, 4)));
		
		testList = board.getAdjList(board.calcIndex(10, 18));
			Assert.assertEquals(4, testList.size());		
				Assert.assertTrue(testList.contains(board.calcIndex(10, 17)));
				Assert.assertTrue(testList.contains(board.calcIndex(10, 19)));
				Assert.assertTrue(testList.contains(board.calcIndex(9, 18)));
				Assert.assertTrue(testList.contains(board.calcIndex(11, 18)));
		
		testList = board.getAdjList(board.calcIndex(17, 17));
			Assert.assertEquals(4, testList.size());		
				Assert.assertTrue(testList.contains(board.calcIndex(17, 18)));
				Assert.assertTrue(testList.contains(board.calcIndex(17, 16)));
				Assert.assertTrue(testList.contains(board.calcIndex(16, 17)));
				Assert.assertTrue(testList.contains(board.calcIndex(18, 17)));
	}
	
	@Test
	public void testBesideRoom()
	{
		LinkedList<Integer> testList = board.getAdjList(board.calcIndex(16, 2));
			Assert.assertEquals(3, testList.size());
			Assert.assertTrue(testList.contains(board.calcIndex(15, 2)));
			Assert.assertTrue(testList.contains(board.calcIndex(16, 1)));
			Assert.assertTrue(testList.contains(board.calcIndex(16, 3)));
			
		testList = board.getAdjList(board.calcIndex(13, 21));
			Assert.assertEquals(1, testList.size());
				Assert.assertTrue(testList.contains(board.calcIndex(14, 21)));
		
		testList = board.getAdjList(board.calcIndex(1, 3));
			Assert.assertEquals(2, testList.size());
				Assert.assertTrue(testList.contains(board.calcIndex(0, 3)));
				Assert.assertTrue(testList.contains(board.calcIndex(1, 4)));
				
		testList = board.getAdjList(board.calcIndex(20, 17));
			Assert.assertEquals(3, testList.size());
				Assert.assertTrue(testList.contains(board.calcIndex(20, 16)));
				Assert.assertTrue(testList.contains(board.calcIndex(19, 17)));
				Assert.assertTrue(testList.contains(board.calcIndex(21, 17)));
	}
	
	@Test
	public void testWalkwayScenario()
	{ // test if a target is reachable
		// One step
		board.calcTargets(0, 6, 1);
			Set<BoardCell> targets= board.getTargets();
				Assert.assertEquals(3, targets.size());
				Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(0, 7))));
				Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(0, 5))));
				Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(1, 6))));
	
	
		board.calcTargets(14, 13, 1);
			targets= board.getTargets();
				Assert.assertEquals(3, targets.size());
				Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(15, 13))));
				Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(14, 14))));
				Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(14, 12))));
	
		// two step
		board.calcTargets(13, 0, 2);
			targets= board.getTargets();
				Assert.assertEquals(4, targets.size());
				Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(11, 0))));
				Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(12, 1))));
				Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(14, 1))));
				Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(15, 0))));
				
		// four step
			board.calcTargets(0, 21, 4);
				targets= board.getTargets();
					Assert.assertEquals(1, targets.size());
					Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(0, 17))));
						
			board.calcTargets(11, 9, 4);
				targets= board.getTargets();
					Assert.assertEquals(13, targets.size());
					Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(11, 7))));
					Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(14, 10))));
		// six step
			board.calcTargets(21, 4, 6);
				targets= board.getTargets();
					Assert.assertEquals(10, targets.size());
					Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(20, 5))));
					Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(17, 6))));
					Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(20, 9))));
	}
	
	@Test
	public void toRoomPath()
	{// targets into rooms
		// includes not all steps taken
		// starting location is the brown squares
		board.calcTargets(16, 17, 3);
			Set<BoardCell> targets= board.getTargets();
				Assert.assertEquals(15, targets.size());
				Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(17, 18))));
				Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(13, 17))));
				Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(17, 17))));	
				
		board.calcTargets(16, 17, 3);
			targets= board.getTargets();
				Assert.assertEquals(15, targets.size());
				Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(17, 15))));
				Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(18, 16))));
				Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(15, 17))));
				Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(17, 18))));	
	}
	
	// target from room exit
	@Test
	public void exitRoom()
	{
		board.calcTargets(2, 5, 1);
		Set<BoardCell> targets= board.getTargets();
				Assert.assertEquals(1, targets.size());
				Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(2, 6))));

		board.calcTargets(1, 18, 3);
			targets= board.getTargets();
				Assert.assertEquals(5, targets.size());
				Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(0, 18))));
				Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(0, 16))));
				Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(2, 16))));
				Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(2, 18))));
				Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(3, 17))));
	}
	
}
