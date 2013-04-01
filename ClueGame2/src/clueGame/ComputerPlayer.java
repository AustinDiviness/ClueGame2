package clueGame;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;


public class ComputerPlayer extends Player {
	private char lastRoomVisited;
	private Set<Card> seenCards;
	
	public ComputerPlayer(String string) {
		// TODO should seenCards include the players own cards? should row/col be set with parameters from the constructor?
		name = string;
		this.color = null;
		seenCards = new HashSet<Card>();
		myCards = new HashSet<Card>();
		row = 0;
		col = 0;
	}
	
	public ComputerPlayer(String string, Color color) {
		// TODO should seenCards include the players own cards? should row/col be set with parameters from the constructor?
		name = string;
		this.color = color;
		seenCards = new HashSet<Card>();
		myCards = new HashSet<Card>();
		row = 0;
		col = 0;
	}

	public void pickLocation(Set<BoardCell> targets) {
		Random rand = new Random();
		
		ArrayList<BoardCell> setArray = new ArrayList<BoardCell>(targets);
		/*
		for (Object bc : targets.toArray()) {
			setArray.add((BoardCell) bc);
		}
		*/
		
		BoardCell pick = setArray.get(rand.nextInt(setArray.size()));
		while (pick.getCellCharacter() == (lastRoomVisited)) {
			pick = setArray.get(rand.nextInt(setArray.size()));
		}
	
		row = pick.getRow();
		col = pick.getCol();
		
	}
	
	public void createSuggestion() {
		// TODO create function. are we going to store previous suggestion so the computer learns somehow?
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
}
