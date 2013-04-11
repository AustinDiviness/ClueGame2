package clueGame;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;


public class ComputerPlayer extends Player {
	private char lastRoomVisited;
	private Set<Card> seenCards;
	private Solution suggestion;
	private RoomCell travelTarget;
	private int previousRow = 0, previousCol = 0;
	
	public ComputerPlayer(String string) {
		this.name = string;
		this.color = null;
		this.seenCards = new HashSet<Card>();
		this.myCards = new HashSet<Card>();
		this.row = 0;
		this.col = 0;
		this.suggestion = new Solution();
		this.shouldAccuse = false;
		this.travelTarget = null;
		this.previousRow = 0;
		this.previousCol = 0;
	}
	
	public ComputerPlayer(String string, Color color) {
		this.name = string;
		this.color = color;
		this.seenCards = new HashSet<Card>();
		this.myCards = new HashSet<Card>();
		this.row = 0;
		this.col = 0;
		this.suggestion = new Solution();
		this.shouldAccuse = false;
		this.travelTarget = null;
	}
	
	public int abs(int x) {
		if (x < 0) {
			x = x * -1;
		}
		return x;
	}
	
	public int calcH(BoardCell cell) {
		int h = abs(cell.getRow() - travelTarget.getRow()) +
				abs(cell.getCol() - travelTarget.getCol());
		return h;
	}
	
	public int calcG(BoardCell cell) {
		int g = abs(cell.getRow() - row) +
				abs(cell.getCol() - col);
		return g;
	}

	public void pickLocation(Set<BoardCell> targets) {
		Board board = ClueGame.instance.board;
		int limit = 1000;
		ArrayList<PathCell> closedCells = new ArrayList<PathCell>();
		ArrayList<PathCell> openCells = new ArrayList<PathCell>();
		ArrayList<BoardCell> path = new ArrayList<BoardCell>();
		// add the starting cell to the open cells
		int h = abs(board.getCellAt(board.calcIndex(row, col)).getRow() - travelTarget.getRow()) + 
				abs(board.getCellAt(board.calcIndex(row, col)).getCol() - travelTarget.getCol());
		openCells.add(new PathCell(board.getCellAt(board.calcIndex(row, col)), null, 0, h));
		int breakCount = 0;
		while (breakCount < limit) {
			++breakCount;
			// find lowest cost cell in the openCells
			PathCell cheapest = openCells.get(0);
			for (PathCell cell: openCells) {
				if (cell.cost < cheapest.cost) {
					cheapest = cell;
				}
			}
			// move cheapest to closeCells
			closedCells.add(cheapest);
			openCells.remove(cheapest);
			// get adjacencies
			ArrayList<BoardCell> adjCells = board.getMovableAdj(cheapest.boardCell);
			ArrayList<BoardCell> boardCellRemove = new ArrayList<BoardCell>();
			// find cells that need to be removed
			for (BoardCell boardCell: adjCells) {
				for (PathCell pathCell: closedCells) {
					if (pathCell.boardCell == boardCell) {
						boardCellRemove.add(boardCell);
					}
				}
			}
			// remove cells that match a cell already in closed cells
			adjCells.removeAll(boardCellRemove);
			ArrayList<PathCell> pathCellRemove = new ArrayList<PathCell>();
			for (BoardCell boardCell: adjCells) {
				// check to see if its already on the open list
				for (PathCell pathCell: openCells) {
					// if the boardCell is already on the list and its g value is less than the current one, replace it
					if (pathCell.boardCell == boardCell && calcG(boardCell) < pathCell.g) {
						pathCellRemove.add(pathCell);
					}

				}
				// add the board cell to the open cells
				openCells.add(new PathCell(boardCell, cheapest, calcG(boardCell), calcH(boardCell)));
			}
			// remove path cells that need to be
			openCells.removeAll(pathCellRemove);
			if (cheapest.boardCell == travelTarget) {
				path.add(cheapest.boardCell);
				while (cheapest.parentCell != null) {
					cheapest = cheapest.parentCell;
					path.add(cheapest.boardCell);
				}
				break;
			}
		}
		// remove cells that are on the path and not in the targets
		ArrayList<BoardCell> boardCellRemove = new ArrayList<BoardCell>();
		for (BoardCell cell: path) {
			if (targets.contains(cell) == false) {
				boardCellRemove.add(cell);
			}
		}
		path.removeAll(boardCellRemove);
		// hacky error fix
		if (path.size() == 0 || breakCount >= limit) {
			Random rand = new Random();
			BoardCell target = (BoardCell) (targets.toArray())[rand.nextInt(targets.size())];
			this.row = target.getRow();
			this.col = target.getCol();
			return;
		}
		// what normally should happen
		BoardCell possTarget = path.get(0);
		for (BoardCell cell: path) {
			if (calcG(cell) < calcG(possTarget)) {
				possTarget = cell;
			}
		}
		this.row = possTarget.getRow();
		this.col = possTarget.getCol();
		
//		Random rand = new Random();
//		boolean cellFound = false;
//		
//		ArrayList<BoardCell> setArray = new ArrayList<BoardCell>(targets);		
//		BoardCell pick = setArray.get(rand.nextInt(setArray.size()));
//		while (pick.getCellCharacter() == (lastRoomVisited) && !cellFound) {
//			pick = setArray.get(rand.nextInt(setArray.size()));
//			if(row < (travelTarget.getRow() - previousRow) && col < (travelTarget.getCol() - previousCol)){
//				row = pick.getRow();
//				col = pick.getCol();
//				cellFound = true;
//			}else{
//				continue;
//			}
//		}
//		
//		cellFound = false;
		
	}		
		
		
		
	
	public void createSuggestion(ArrayList<String> playerNames, String roomName, ArrayList<String> weaponNames) {
		Random rand = new Random();
		String playerChoice = null;
		String roomChoice = roomName;
		String weaponChoice = null;
		// find player to suggest
		while (playerChoice == null) {
			playerChoice = playerNames.get(rand.nextInt(playerNames.size() - 1));
			for (Card card: seenCards) {
				if (card.getName().equals(playerChoice)) {
					playerChoice = null;
					break;
				}
			}
		}
		// find weapon to suggest
		while(weaponChoice == null) {
			weaponChoice = weaponNames.get(rand.nextInt(weaponNames.size() - 1));
			for (Card card: seenCards) {
				if (card.getName().equals(weaponChoice)) {
					weaponChoice = null;
					break;
				}
			}
		}
		suggestion.setPerson(playerChoice);
		suggestion.setRoom(roomChoice);
		suggestion.setWeapon(weaponChoice);
	}
	
	public void updateSeen(Card seen) {
		seenCards.add(seen);
	}
	
	public void giveCard(Card card) {
		this.myCards.add(card);
		this.seenCards.add(card);
	}
	
	public void setRoomToTravel(ArrayList<RoomCell> cells) {
		char c = getLastRoomVisited();
		RoomCell possRoomCell = null;
		Random generator = new Random();
		while (true) {
			possRoomCell = cells.get(generator.nextInt(cells.size()));
			if (possRoomCell.getCellCharacter() != c) {
				travelTarget = possRoomCell;
				break;
			}
		}
//		boolean cont = true;
//		int roomToPick = generator.nextInt(cells.size());
//		do{
//			if(cells.get(roomToPick).getCellCharacter() != c){
//				travelTarget = cells.get(roomToPick);
//				cont = false;
//			} else{
//				roomToPick = generator.nextInt(cells.size());
//			}
//		}while(cont);
	}

	public void setLastRoomVisited(char c) {
		this.lastRoomVisited = c;
	}
	
	public void showCard(Card card) {
		updateSeen(card);
	}
	
	public Solution getSolution() {
		return this.suggestion;
	}
	
	public char getLastRoomVisited() {
		return this.lastRoomVisited;
	}
	
	public boolean isComputer() {
		return true;
	}
	
	public void setTravelTarget(RoomCell cell) {
		this.travelTarget = cell;
	}

}
