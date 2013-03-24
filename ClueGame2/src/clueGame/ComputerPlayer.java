package clueGame;

import java.util.HashSet;
import java.util.Set;


public class ComputerPlayer extends Player {
	private char lastRoomVisited;
	private Set<Card> seenCards;
	
	public ComputerPlayer(String string) {
		// TODO should seenCards include the players own cards? should row/col be set with parameters from the constructor?
		name = string;
		seenCards = new HashSet<Card>();
		row = 0;
		col = 0;
	}

	public void pickLocation(Set<BoardCell> targets) {
		// TODO create function. Should this be entirely random?
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
	
}
