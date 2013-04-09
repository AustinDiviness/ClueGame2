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
	
	public ComputerPlayer(String string) {
		// TODO should seenCards include the players own cards? should row/col be set with parameters from the constructor?
		this.name = string;
		this.color = null;
		this.seenCards = new HashSet<Card>();
		this.myCards = new HashSet<Card>();
		this.row = 0;
		this.col = 0;
		this.suggestion = new Solution();
	}
	
	public ComputerPlayer(String string, Color color) {
		// TODO should seenCards include the players own cards? should row/col be set with parameters from the constructor?
		this.name = string;
		this.color = color;
		this.seenCards = new HashSet<Card>();
		this.myCards = new HashSet<Card>();
		this.row = 0;
		this.col = 0;
		this.suggestion = new Solution();
	}

	public void pickLocation(Set<BoardCell> targets) {
		Random rand = new Random();
		
		ArrayList<BoardCell> setArray = new ArrayList<BoardCell>(targets);		
		BoardCell pick = setArray.get(rand.nextInt(setArray.size()));
		while (pick.getCellCharacter() == (lastRoomVisited)) {
			pick = setArray.get(rand.nextInt(setArray.size()));
		}
	
		row = pick.getRow();
		col = pick.getCol();
		
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
}
