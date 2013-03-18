package Board;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import Board.BadConfigFormatException;
import Board.RoomCell.DoorDirection;

/* DESCRIPTION
 *  This class takes a fileName and loads the config file the board and legend files
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
public class InitializeBoard {
	private String boardFileName;
	private String legendFileName;
	
	// What the class generates
	private ArrayList<BoardCell> cells;
	private Map<Character, String> characerRoomsMap;
	private int numberOfColumns;
	private int numberOfRows;
	private Set<Character> CharactersSeen; // For testing Purposes
	
	// Constructors
	// All constructors must call "__init__" to initialize private variables
	public InitializeBoard()
	{
		__init__("boardConfig.csv", "legendConfig.txt");
	}
	
	public InitializeBoard(String boardFile, String legendFile)
	{
		__init__(boardFile, legendFile);
	}
	
	// initializes all the attributes of the class;
	private void __init__(String boardConfigFile, String legendConfigFile)
	{
		boardFileName = boardConfigFile;
		legendFileName = legendConfigFile;
		cells = new ArrayList<BoardCell>();
		characerRoomsMap = new HashMap<Character, String>();
		numberOfColumns = 0;
		numberOfRows = 0;
		CharactersSeen = new HashSet<Character>(); // FOR TESTING ONLY
	}
	
	
	// Retrievable variables
	public ArrayList<BoardCell> getBoardCells()
	{
		return cells;
	}
	
	public Map<Character, String> getRoomMap()
	{
		return characerRoomsMap;
	}
	
	public int getNumberOfRows()
	{
		return numberOfRows;
	}
	
	public int getNumberOfColumns()
	{
		return numberOfColumns;
	}
	
	public int getNumberOfRooms()
	{
		//System.out.println(CharactersSeen);
		return CharactersSeen.size();
	}
	// Parsing functions
	public void loadFiles() throws FileNotFoundException
	{ // loads legend then the board. The loadLegend function must be called first
		loadLegend();
		loadBoard();
	}
	
// LEGEND PARSE FUNCTIONS
	private void loadLegend() throws FileNotFoundException
	{ // Takes each line in the legend and gives it parseLegend to evulate it
		File file = new File(legendFileName);
		Scanner scan = new Scanner(file);
		int count = 0;
		while(scan.hasNextLine())
		{
			count++;
			parseLegend(scan.nextLine(), count);
		}
		scan.close();
	}
	
	public void parseLegend(String line, int row) throws BadConfigFormatException
	{// takes the line and matches it with an regular expression
	 // if there is a match then the line was a valid legend statement
	// and then the character map is updated
		// regex string for legend
		//System.out.println(line);
		String regex = " *(\\w+) *, *(.*) *";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(line);
		
		if (matcher.find())
		{
			//System.out.println("\""+matcher.group(1)+"\"" + " | " + "\""+matcher.group(2)+"\"");
			characerRoomsMap.put(matcher.group(1).charAt(0), matcher.group(2));
		}
		else
		{
			throw new BadConfigFormatException("Row " + row + " in legend File not formated Correctly");
		}
		
	}
	
// BOARD PARSE FUNCTONS
	private void loadBoard() throws FileNotFoundException
	{ // takes each line of the board and gives it to parseBoard for evaluation
		File file = new File(boardFileName);
		
		Scanner scan = new Scanner(file);
		
		while(scan.hasNextLine())
		{
			numberOfRows++;
			String s = scan.nextLine();
			//System.out.println(s);
			parseBoard(s, numberOfRows);
		}
		
		scan.close();
	}
	
	private void parseBoard(String line, int currentRow)
	{ // Makes sure every cell is correct, correct number of columns
		
		String[] split = line.split(",");
		
		if(split.length == 0) throw new BadConfigFormatException("boardConfig file has no attributes in row: " + numberOfRows);
		
		int count = 0;
		for(String s : split)
		{
			//System.out.println(s);
			count++;
			cells.add(makeCell(s,currentRow, count));
		}//System.out.println(currentRow);
		
		if(numberOfColumns == 0) // Only happen for the first line 
			numberOfColumns = count;
		else if (count != numberOfColumns)
			throw new BadConfigFormatException("Row " + currentRow  + " in the boardConfig file is a defferent length");
	}
	
	private BoardCell makeCell(String cellType, int currentRow, int currentColumn)
	{ // creates a cell given a certain type
		if(cellType.length() == 1) // non door cells
		{
			// Makes sure the type is a valid room type
			if(characerRoomsMap.containsKey(cellType.charAt(0)))
			{ 
				switch(cellType)
				{
				case "W": return new WalkwayCell(cellType.charAt(0));
				default: 
				{
					CharactersSeen.add(cellType.charAt(0)); //System.out.println(cellType);
					return new RoomCell(cellType.charAt(0));
				}
				}
			}
			else // char is not recognized
				throw new BadConfigFormatException("Cell " + currentRow + ", " + currentColumn +" is not containd in legend");
		}
		// For door rooms
		if(cellType.length() == 2 && cellType.charAt(0) != 'W')
		{
			if(characerRoomsMap.containsKey(cellType.charAt(0)))
			{ 
				CharactersSeen.add(cellType.charAt(0)); // for testing
				switch(cellType.charAt(1)) // direction of door
				{
				case 'U': return new RoomCell(cellType.charAt(0), DoorDirection.UP);
				case 'D': return new RoomCell(cellType.charAt(0), DoorDirection.DOWN);
				case 'R': return new RoomCell(cellType.charAt(0), DoorDirection.RIGHT);
				case 'L': return new RoomCell(cellType.charAt(0), DoorDirection.LEFT);
				default: throw new BadConfigFormatException("Door Direction is not Valid");
				}
			}
			else
			{
				throw new BadConfigFormatException("Cell " + currentRow + ", " + currentColumn + " in the boardConfig is not a valid Room");
			}
		}
		else
			throw new BadConfigFormatException("Cell " + currentRow + ", " + currentColumn + " in the boardConfig file contains more than two letters");
	}
}
