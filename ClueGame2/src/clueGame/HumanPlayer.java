package clueGame;

import java.util.HashSet;

public class HumanPlayer extends Player {

	public HumanPlayer(String string) {
		this.myCards = new HashSet<Card>();
		this.name = string;
		this.color = null;
		this.row = 0;
		this.col = 0;
		this.shouldAccuse = false;
	}

	@Override
	public void showCard(Card card) {
		// TODO add method
		
	}

}
