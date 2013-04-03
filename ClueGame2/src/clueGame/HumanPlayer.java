package clueGame;

import java.util.HashSet;

public class HumanPlayer extends Player {

	public HumanPlayer(String string) {
		myCards = new HashSet<Card>();
		name = string;
		color = null;
		row = 0;
		col = 0;
	}

	@Override
	public void showCard(Card card) {
		// TODO add method
		
	}

}
