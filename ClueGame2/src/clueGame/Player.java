package clueGame;

import java.util.Set;


public abstract class Player {
	private String name;
	private Set<Card> myCards;
	
	public Card disproveSuggestion(String person, String room, String weapon) {
		return new Card(CardType.ROOM, "b");
	}
	
}
