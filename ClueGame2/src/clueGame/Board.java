package clueGame;

import java.awt.Color;
import java.awt.Graphics;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;

import javax.swing.JPanel;

import clueGame.RoomCell.DoorDirection;


/* DESCRIPTION
 * 
 * This class keeps tracks of all the cells in the game
 * 
 * Board calls InitializeBoard to get all of the board contents
 * 
 * and handles any error that are thrown by the InitializeBoard class
 */

public class Board extends JPanel {
	private ArrayList<BoardCell> cells;
	private ArrayList<Player> players;
	private Map<Character, String> rooms;
	private Map<Integer, LinkedList<Integer>> adjacencyList;
	private Set<BoardCell> targets;
	private boolean[] visited;
	private int numRows;
	private int numColumns;
	private int numberOfRooms;
	public static final Color textColor = Color.ORANGE;
	private String boardConfigFile;
	private String legendConfigFile;
	private ArrayList<RoomCell> doorCells;

	public Board() {
		init("boardConfig.csv", "legendConfig.txt");
	}
	
	public Board(String boardFileName) {
		init(boardFileName, "legendConfig.txt");
	}
	
	public Board(String boardFileName, String logFileName) {
		init(boardFileName, logFileName);
	}
	
	public void init(String boardFileName, String legendFileName) {
		boardConfigFile = boardFileName;
		legendConfigFile = legendFileName;
		players = new ArrayList<Player>();
		doorCells = new ArrayList<RoomCell>();
	}
	
	
	public void loadConfigFiles() throws FileNotFoundException {
		BoardFactory initBoard = new BoardFactory(boardConfigFile, legendConfigFile);
		initBoard.loadFiles();
		rooms = initBoard.getRoomMap();
		cells = initBoard.getBoardCells();
		numRows = initBoard.getNumberOfRows();
		numColumns = initBoard.getNumberOfColumns();
		numberOfRooms = initBoard.getNumberOfRooms();
		adjacencyList = initBoard.getAdjMatrix();
		visited = new boolean[numRows * numColumns];
		for(int i = 0; i < numRows * numColumns; i++) {
			visited[i] = false;
		}
		// grab add room cells that are a door
		for (BoardCell cell: cells) {
			if (cell.isDoorway()) {
				doorCells.add((RoomCell) cell);
			}
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
	
	public int numberOfRooms() { 
		return numberOfRooms;
	}
	
	public String roomNameWithChar(char c) { 
		// Does not check errors
		return rooms.get(c);
	}
	
	public ArrayList<BoardCell> getBoard() {
		return cells;
	}
	
	public Map<Character,String> getRooms() {
		return rooms;
	}
	
	public int getNumRows() {
		return numRows;
	}
	
	public int getNumColumns() {
		return numColumns;
	}
	
	public void setPlayers(ArrayList<Player> players) {
		this.players = players;
	}
	
	public ArrayList<Player> getPlayers() {
		return players;
	}
	
	public RoomCell getRoomCellAt(int row, int column) {
		return (RoomCell) cells.get(calcIndex(row, column));
	}
	
	public BoardCell getCellAt(int location) {
		return cells.get(location);
	}
	
	public BoardCell cellAt(int row, int column) {
		return cells.get(calcIndex(row, column));
	}

	public LinkedList<Integer> getAdjList(int index) {
		return adjacencyList.get(index);
	}
	
	public DoorDirection getDoorDirection(int row, int column) {
		if (cells.get(calcIndex(row, column)) instanceof RoomCell ) {
			return ((RoomCell)cells.get(calcIndex(row, column))).getDoorDirection();
		}
		return DoorDirection.NONE;
			
	}
	
	public String getRoomNameAt(int row, int column) { 
		// Return the room name of a given cell
		String s = rooms.get(cells.get(calcIndex(row,column)).getCellCharacter());
		return s;
	}

	public void calcTargets(int row, int column, int numberOfSteps) {
		targets = null;
		targets = new HashSet<BoardCell>();
		startTargets(calcIndex(row, column), numberOfSteps, true);
	}

	public void startTargets(int location, int steps, boolean firstTime) {
		// adds location to targetCells if last step
		if(steps == 0) { 
			targets.add(cells.get(location) );
		}
		else if(cells.get(location).isDoorway() && !firstTime) {
			targets.add(cells.get(location));
		}
		else { // mark that we visited this location
			visited[location] = true;
			// make an Iterator to step though the adjacency matrix
			ListIterator<?> it_adjMat = adjacencyList.get(location).listIterator();
			while(it_adjMat.hasNext()) {
				int next = (Integer) it_adjMat.next();
				// check to see if we visited the location before
				if(!visited[next]) {
					startTargets(next, steps -1, false);
				}
			}
		}
		// reset visited to false
		visited[location] = false;
	}

	public Set<BoardCell> getTargets() {
		return targets;
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		HashMap<Character, Boolean> drawn = new HashMap<Character, Boolean>();
		// load HashMap with all the rooms that need to be drawn and false
		ArrayList<Character> roomInitials = new ArrayList<Character>(rooms.keySet());
		for (Character ch: roomInitials) {
			drawn.put(ch, false);
		}
		// draw board cells
		for (BoardCell cell: cells) {
			cell.draw(g);
		}

		if (ClueGame.instance.getCanGoToNextPlayer() == false) {
			// highlight squares that can be moved to
			highlightMovableLocations(g);
		}
		// draw players
		drawPlayers(g);
		// draw room names
		int x = 0;
		int y = 0;
		int yOffset = 2;
		int xOffset = 1;
		String roomName;
		g.setColor(textColor);
		// draw room name according to the direction of the door
		for (RoomCell cell: doorCells) {
			if (cell.getDoorDirection() == DoorDirection.UP && drawn.get(cell.getCellCharacter()) == false) {
				x = (cell.getCol() - xOffset) * BoardCell.width;
				y = (cell.getRow() + yOffset) * BoardCell.height;
				roomName = rooms.get(cell.getCellCharacter());
				g.drawString(roomName, x, y);
				drawn.put(cell.getCellCharacter(), true);
			}
			if (cell.getDoorDirection() == DoorDirection.DOWN && drawn.get(cell.getCellCharacter()) == false) {
				x = (cell.getCol() - xOffset) * BoardCell.width;
				y = (cell.getRow() - yOffset) * BoardCell.height;
				roomName = rooms.get(cell.getCellCharacter());
				g.drawString(roomName, x, y);
				drawn.put(cell.getCellCharacter(), true);
			}
			if (cell.getDoorDirection() == DoorDirection.LEFT && drawn.get(cell.getCellCharacter()) == false) {
				x = (cell.getCol() + xOffset) * BoardCell.width;
				y = (cell.getRow() + 1) * BoardCell.height;
				roomName = rooms.get(cell.getCellCharacter());
				g.drawString(roomName, x, y);
				drawn.put(cell.getCellCharacter(), true);
			}
			if (cell.getDoorDirection() == DoorDirection.RIGHT && drawn.get(cell.getCellCharacter()) == false) {
				x = (cell.getCol() - xOffset) * BoardCell.width;
				y = (cell.getRow() + 1) * BoardCell.height;
				roomName = rooms.get(cell.getCellCharacter());
				g.drawString(roomName, x, y);
				drawn.put(cell.getCellCharacter(), true);
			}
		}
	}
	  
	public void highlightMovableLocations(Graphics g) {
		ClueGame game = ClueGame.instance;
		int row = game.getActivePlayer().getRow();
		int col = game.getActivePlayer().getCol();
		calcTargets(row, col, game.getDie());
		Set<BoardCell> cells = getTargets();
		for (BoardCell cell: cells) {
			cell.highlight(g);
		}
	}

	public void drawPlayers(Graphics g) {
		int width = BoardCell.width;
		int height = BoardCell.height;
		ArrayList<Player> playersNotDrawn = ClueGame.instance.getPlayers();
		HashMap<Player, Boolean> playerMap = new HashMap<Player, Boolean>();
		// set all players notDrawn value to true, since they haven't been drawn
		for (Player player: playersNotDrawn) {
			playerMap.put(player, true);
		}
		// draw players in the ArrayList
		for (Player player: playersNotDrawn) {
			// skip over those that have already been drawn
			if (playerMap.get(player) == false) {
				continue;
			}
			else {
				ArrayList<Player> overlaps = new ArrayList<Player>();
				// get players on the same cell. will include this player
				for (Player possPlayer: ClueGame.instance.getPlayers()) {
					if (possPlayer.getRow() == player.getRow() && 
							possPlayer.getCol() == player.getCol() && 
							playerMap.get(possPlayer) == true) {
						overlaps.add(possPlayer);
					}
				}
				int arcLength = 360 / overlaps.size();
				for (int i = 0; i < overlaps.size(); ++i) {
					Player temp = overlaps.get(i);
					int x = temp.getCol() * width;
					int y = temp.getRow() * height;
					g.setColor(temp.getColor());
					g.fillArc(x, y, width, height, arcLength * i, arcLength);
					playerMap.put(temp, false);
				}
			}
		}
	}
	
} // end class bracket
