package clueGame;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;


public abstract class Player {
	protected String name;
	private Set<Card> myCards;
	
	public Card disproveSuggestion(String person, String room, String weapon) {
		return new Card(CardType.ROOM, "b");
	}

	public Set<Card> getCards() {
		// TODO Auto-generated method stub
		return new HashSet<Card>();
	}

    public void setCards(Set<Card> cards) {
        this.myCards = cards;
    }
        
	
}
