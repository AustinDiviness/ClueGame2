package clueGame;

import java.util.ArrayList;
import java.util.Set;


public abstract class Player {
	private String name;
	private Set<Card> myCards;
	
	public Card disproveSuggestion(String person, String room, String weapon) {
		return new Card(CardType.ROOM, "b");
	}

	public Set<Card> getCards() {
		// TODO Auto-generated method stub
		return new Set<Card>();
	}

    public void setCards(Set<Card> cards) {
        this.mycards = cards;
    }
        
	
}
