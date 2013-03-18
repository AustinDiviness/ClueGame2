package clueGame;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;

import javax.swing.JOptionPane;

import clueGame.RoomCell.DoorDirection;


/* DESCRIPTION
 * 
 * This class keeps tracks of all the cells in the game
 * 
 * Board calls InitializeBoard to get all of the board contents
 * 
 * and handles any error that are thrown by the InitializeBoard class
 */

public class Board {
	private ArrayList<BoardCell> cells;
	private Map<Character, String> rooms;
	private Map<Integer, LinkedList<Integer>> adjacencyList;
	private Set<BoardCell> targets;
	private boolean[] visited;
	private int numRows;
	private int numColumns;
	private int numberOfRooms;

	private String boardConfigFile;
	private String legendConfigFile;

	public Board()
	{
		__init__("boardConfig.csv", "legendConfig.txt");
	}
	
	public Board(String boardFileName)
	{
		__init__(boardFileName, "legendConfig.txt");
	}
	public Board(String boardFileName, String logFileName)
	{
		__init__(boardFileName, logFileName);
	}
	
	public void __init__(String boardFileName, String legendFileName)
	{
		boardConfigFile = boardFileName;
		legendConfigFile = legendFileName;
	}
	
	
	public void loadConfigFiles() throws FileNotFoundException
	{
		BoardFactory initBoard = new BoardFactory(boardConfigFile, legendConfigFile);
		initBoard.loadFiles();
		rooms = initBoard.getRoomMap();
		cells = initBoard.getBoardCells();
		numRows = initBoard.getNumberOfRows();
		numColumns = initBoard.getNumberOfColumns();
		numberOfRooms = initBoard.getNumberOfRooms();
		adjacencyList = initBoard.getAdjMatrix();
		visited = new boolean[numRows * numColumns];
		for(int i = 0; i < numRows * numColumns; i++)
		{
			visited[i] = false;
		}
	}
	public int calcIndex(int row, int column) {
		row = row + 1;
		column = column + 1;
		return (column - 1) + (row - 1) * (numRows + 1);
	}

	public BoardCell GetRoomCellAt(int row, int column) {
		return null;
	}
	
	public int numberOfRooms()
	{ 
		return numberOfRooms;
	}
	
	public String roomNameWithChar(char c)
	{ // Does not check errors
		return rooms.get(c);
	}
	
// FOR TESTING ONLY
	
	public ArrayList<BoardCell> getBoard()
	{ // Returns the board
		return cells;
	}
	
	public Map<Character,String> getRooms()
	{
		return rooms;
	}
	
	public int getNumRows()
	{
		return numRows;
	}
	
	public int getNumColumns()
	{
		return numColumns;
	}
	
	public RoomCell getRoomCellAt(int row, int column) 
	{
		int location = calcIndex(row, column);
		return (RoomCell) cells.get(location);
	}
	public BoardCell getCellAt(int location)
	{
		return cells.get(location);
	}
	
	public BoardCell cellAt(int row, int column)
	{ // returns a cell at a row and column
		int location = calcIndex(row, column);
		return cells.get(location);
	}

	public LinkedList<Integer> getAdjList(int index) 
	{
		return adjacencyList.get(index);
	}
	
	//For test purposes only
	public DoorDirection getDoorDirection(int row, int column)
	{ // Returns the Door Direction as a string
	  // It is returned as a string and not a DoorDirection because making DoorDirection
		int location = calcIndex(row, column);
		if (cells.get(location) instanceof RoomCell )
			return ((RoomCell)cells.get(location)).getDoorDirection();
		return DoorDirection.NONE;
			
	}
	
	public String getRoomNameAt(int row, int column)
	{ // Return the room name of a given cell
		int cal = calcIndex(row, column);
		String s = rooms.get(cells.get(calcIndex(row,column)).getCellCharacter());
		return s;
	}

	public void calcTargets(int row, int column, int numberOfSteps)
	{
		targets = null;
		targets = new HashSet<BoardCell>();
		startTargets(calcIndex(row, column), numberOfSteps, true);
	}

	  public void startTargets(int location, int steps, boolean firstTime) {
			// adds location to targetCells if last step
		if(steps == 0) 
		{ 
			targets.add(cells.get(location) );
		}
		else if(cells.get(location).isDoorway() && !firstTime)
		{
			targets.add(cells.get(location));
		}
		else // mark that we visited this location
		{
			visited[location] = true;
			
			// make an Iterator to step though the adjacency matrix
			ListIterator it_adjMat = adjacencyList.get(location).listIterator();
		
			while(it_adjMat.hasNext())
			{
				int next = (Integer) it_adjMat.next();
				// checvk to see if we visited the location before
				if(!visited[next])
					startTargets(next, steps -1, false);
			}
		}
		// reset visted to false
		visited[location] = false;
	}
 
  public Set<BoardCell> getTargets() {
		return targets;
	}
}
