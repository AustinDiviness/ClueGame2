package clueGame;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import clueGame.BadConfigFormatException;
import clueGame.RoomCell.DoorDirection;

/* DESCRIPTION
 *  This class takes two fileNames the board and legend file
 *  
 *  and will make a ArrayList of BoardCells and the Map for the characters
 *  
 *  and will tell the number of rows, number of columns and the number or Rooms
 *  
 *  This class throws a FileNotFoundException and a BadConfigFormatException in function parse()
 *  
 *  Rooms can be named anything but walkways must have the char 'W' as the identifier
 *  
 *  This class has a lot of nested if statements. These statements are used to produce good error messages
 *  
 *  Functions that take a row and or column for a parameters is for the error messages
 */
public class BoardFactory {
	private String boardFileName;
	private String legendFileName;
	
	// What the class generates
	private ArrayList<BoardCell> cells;
	private Map<Character, String> characerRoomsMap;
	private Map<Integer, LinkedList<Integer>> adjMatrix;
	private int numberOfColumns;
	private int numberOfRows;
	private Set<Character> CharactersSeen; // For testing Purposes
	
	// Constructors
	// All constructors must call "init" to initialize private variables
	public BoardFactory() {
		init("boardConfig.csv", "legendConfig.txt");
	}
	
	public BoardFactory(String boardFile, String legendFile) {
		init(boardFile, legendFile);
	}
	
	// initializes all the attributes of the class;
	private void init(String boardConfigFile, String legendConfigFile) {
		boardFileName = boardConfigFile;
		legendFileName = legendConfigFile;
		cells = new ArrayList<BoardCell>();
		adjMatrix = new HashMap<Integer, LinkedList<Integer>>();
		characerRoomsMap = new HashMap<Character, String>();
		numberOfColumns = 0;
		numberOfRows = 0;
		CharactersSeen = new HashSet<Character>(); // FOR TESTING ONLY
	}
	
	
	// Retrievable variables
	public ArrayList<BoardCell> getBoardCells() {
		return cells;
	}
	
	public Map<Character, String> getRoomMap() {
		return characerRoomsMap;
	}
	
	public int getNumberOfRows() {
		return numberOfRows;
	}
	
	public int getNumberOfColumns() {
		return numberOfColumns;
	}
	
	public int getNumberOfRooms() {
		return CharactersSeen.size();
	}
	
	public Map<Integer, LinkedList<Integer>> getAdjMatrix() {
		return adjMatrix;
	}
	// Parsing functions
	public void loadFiles() throws FileNotFoundException { 
		// loads legend then the board. The loadLegend function must be called first
		loadLegend();
		loadBoard();
		createAdjList();
	}
	
// LEGEND PARSE FUNCTIONS
	private void loadLegend() throws FileNotFoundException { // Takes each line in the legend and gives it parseLegend to evaluate it
		File file = new File(legendFileName);
		Scanner scan = new Scanner(file);
		int count = 0;
		while(scan.hasNextLine()) {
			count++;
			parseLegend(scan.nextLine(), count);
		}
		scan.close();
	}
	
	public void parseLegend(String line, int row) throws BadConfigFormatException {// takes the line and matches it with an regular expression
	 // if there is a match then the line was a valid legend statement
	// and then the character map is updated
		// regex string for legend
		String regex = " *(\\w+) *, *(.*) *";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(line);
	
		// Test if there is multiple "," in a row
		String badRegex = ".*?,.*?,.*";
		Pattern badPattern = Pattern.compile(badRegex);
		Matcher badMatcher = badPattern.matcher(line);
		if(badMatcher.find()) {
			 throw new BadConfigFormatException("Row " + row + " in legend File not formated Correctly");
		}
		else if (matcher.find()) {
			characerRoomsMap.put(matcher.group(1).charAt(0), matcher.group(2));
		}
		else {
			throw new BadConfigFormatException("Row " + row + " in legend File not formated Correctly");
		}
		
	}
	
// BOARD PARSE FUNCTONS
	private void loadBoard() throws FileNotFoundException { 
		// takes each line of the board and gives it to parseBoard for evaluation
		File file = new File(boardFileName);
		Scanner scan = new Scanner(file);
		
		while(scan.hasNextLine()) {
			numberOfRows++;
			String s = scan.nextLine();
			parseBoard(s, numberOfRows);
		}
		
		scan.close();
	}
	
	private void parseBoard(String line, int currentRow) { 
		// Makes sure every cell is correct, correct number of columns
		
		String[] split = line.split(",");
		
		if(split.length == 0) throw new BadConfigFormatException("boardConfig file has no attributes in row: " + numberOfRows);
		
		int count = 0;
		for(String s : split) {
			count++;
			cells.add(makeCell(s,currentRow, count));
		}
		
		if(numberOfColumns == 0) // Only happen for the first line 
			numberOfColumns = count;
		else if (count != numberOfColumns)
			throw new BadConfigFormatException("Row " + currentRow  + " in the boardConfig file is a different length");
	}
	
	private BoardCell makeCell(String cellType, int currentRow, int currentColumn) { 
		// creates a cell given a certain type
		if(cellType.length() == 1) { // non door cells
			// Makes sure the type is a valid room type
			if(characerRoomsMap.containsKey(cellType.charAt(0))) { 
				switch(cellType.charAt(0)) {
					case 'W': 
						return new WalkwayCell(cellType.charAt(0), currentRow - 1, currentColumn - 1);
					default: {
						CharactersSeen.add(cellType.charAt(0)); 
						return new RoomCell(cellType.charAt(0), currentRow - 1, currentColumn - 1);
					}
				}
			}
			throw new BadConfigFormatException("Cell " + currentRow + ", " + currentColumn +" is not contained in legend");
		}
		// For door rooms
		if(cellType.length() == 2 ) {
			if(characerRoomsMap.containsKey(cellType.charAt(0)) && cellType.charAt(0) != 'W') { 
				CharactersSeen.add(cellType.charAt(0)); // for testing
				switch(cellType.charAt(1)) { // direction of door 
					case 'U': 
						return new RoomCell(cellType.charAt(0), DoorDirection.UP, currentRow - 1, currentColumn - 1);
					case 'D': 
						return new RoomCell(cellType.charAt(0), DoorDirection.DOWN, currentRow - 1, currentColumn - 1);
					case 'R': 
						return new RoomCell(cellType.charAt(0), DoorDirection.RIGHT, currentRow - 1, currentColumn - 1);
					case 'L': 
						return new RoomCell(cellType.charAt(0), DoorDirection.LEFT, currentRow - 1, currentColumn - 1);
					case 'N': 
						return new RoomCell(cellType.charAt(0));
					default: 
						throw new BadConfigFormatException("Door direction in row " + currentRow + ", column " + currentColumn +" is not valid");
				}
			}
			else {
				throw new BadConfigFormatException("Cell " + currentRow + ", " + currentColumn + " in the boardConfig is not a valid Room");
			}
		}
		else {
			throw new BadConfigFormatException("Cell " + currentRow + ", " + currentColumn + " in the boardConfig file contains no letters or more than two letters");
		}
	}
	
	public void createAdjList() { 
		// Assuming board is correct
		for(int i = 0; i < cells.size(); i++) {
			if(cells.get(i).isRoom()) {
				if(cells.get(i).isDoorway()) {
					LinkedList<Integer> temp = new LinkedList<Integer>();
					switch(((RoomCell)cells.get(i)).getDoorDirection()) {
						case UP: 
							temp.add(i - numberOfColumns);
							break;
						case DOWN: 
							temp.add(numberOfColumns + i);
							break;
						case RIGHT: 
							temp.add(i + 1);
							break;
						case LEFT: 
							temp.add(i - 1);
							break;
					}
					
					adjMatrix.put(i, temp);
				}
				else {
					LinkedList<Integer> temp = new LinkedList<Integer>();
					adjMatrix.put(i, temp);
				}
			}
			else {
				LinkedList<Integer> temp = new LinkedList<Integer>();
				int up = i - numberOfColumns;
				if(up > 0) {
					if(cells.get(up).isDoorway()) {
						if(i == validDoorwayCell((RoomCell)cells.get(up), up)) { 
							temp.add(up);
						}
					}
					else if (!cells.get(up).isRoom()) {
						temp.add(up);
					}
				}
				
				int down = i + numberOfColumns;
				if(down < numberOfRows*numberOfColumns)  {
					if(cells.get(down).isDoorway()) {
						if(i == validDoorwayCell((RoomCell)cells.get(down), down)) {
							temp.add(down);
						}
					}
					else if (!cells.get(down).isRoom()) {
						temp.add(down);
					}
				}
				
				
				int right = i + 1;
				if(0 != right % numberOfColumns) {
					if(cells.get(right).isDoorway()) {
						if(i == validDoorwayCell((RoomCell)cells.get(right), right)) {
							temp.add(right);
						}
					}
					else if (!cells.get(right).isRoom()) {
						temp.add(right);
					}
				}
				
				
				int left = i - 1;
				if(i > 0 && 0 != i % numberOfColumns) {
					if(cells.get(left).isDoorway()) {
						if(i == validDoorwayCell((RoomCell)cells.get(left), left)) {
							temp.add(left);
						}
					}
					else if (!cells.get(left).isRoom()) {
						temp.add(left);
					}
				
				adjMatrix.put(i, temp);
				}
			}
		}
	}
	
	public int validDoorwayCell(RoomCell doorway, int location) {
		switch(doorway.getDoorDirection()) {
			case UP: 
				return location - numberOfColumns;
			case DOWN: 
				return location + numberOfColumns;
			case RIGHT: 
				return location + 1;
			default: 
				return location - 1;
		}
	}
}
