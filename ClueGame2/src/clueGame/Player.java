package clueGame;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;


public abstract class Player {
	protected String name;
	protected Set<Card> myCards;
	protected int row;
	protected int col;
	
	public Card disproveSuggestion(String person, String room, String weapon) {
		// TODO actually create function. what happens if player has more than one card that could disprove the suggestion?
		// should this take a solution object as a single argument instead of us having to divide the solution into 3 strings?
		return new Card(CardType.ROOM, "b");
	}

	// getters and setters
	public Set<Card> getCards() {
		// TODO actually return cards
		return new HashSet<Card>();
	}

    public void setCards(Set<Card> cards) {
        this.myCards = cards;
    }
    
    public String getName() {
    	return this.name;
    }
    
    public void setName(String name) {
    	this.name = name;
    }
      
	public int getRow() {
		return this.row;
	}

	public int getCol() {
		return this.col;
	}

	public void setRow(int row) {
		this.row = row;
		
	}

	public void setCol(int col) {
		this.col = col;
	}
	
}
