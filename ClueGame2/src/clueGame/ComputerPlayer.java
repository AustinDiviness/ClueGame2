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

	public void pickLocation(Set<BoardCell> targets) {
		Random rand = new Random();
		boolean cellFound = false;
		
		ArrayList<BoardCell> setArray = new ArrayList<BoardCell>(targets);		
		BoardCell pick = setArray.get(rand.nextInt(setArray.size()));
		while (pick.getCellCharacter() == (lastRoomVisited) && !cellFound) {
			pick = setArray.get(rand.nextInt(setArray.size()));
			if(row < (travelTarget.getRow() - previousRow) && col < (travelTarget.getCol() - previousCol)){
				row = pick.getRow();
				col = pick.getCol();
				cellFound = true;
			}else{
				continue;
			}
		}
		
		cellFound = false;
		
		
		
		
		
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
		Random generator = new Random();
		boolean cont = true;
		int roomToPick = generator.nextInt(cells.size());
		do{
			if(cells.get(roomToPick).getCellCharacter() != c){
				travelTarget = cells.get(roomToPick);
				cont = false;
			} else{
				roomToPick = generator.nextInt(cells.size());
			}
		}while(cont);
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

}
