package clueGame;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Map;

public class ClueGame {

    private Solution solution;
    private ArrayList<Player> players;
    private Card lastCardShown = null;
	public Board board;

	public ClueGame(String string, String string2, String string3,
			String string4) {
		// TODO Auto-generated constructor stub
		solution = new Solution();
	}

	public void deal() {
		
	}
	
	public void loadConfigFiles() throws FileNotFoundException {
		
	}
	
	public void selectAnswer() {
		
	}
	
	public void handleSuggestion(String person, String room, String weapon, Player accusingPerson) {
	    // TODO make sure that lastCardShown is set	
	}
	
	public boolean checkAccusation(Solution solution) {
		return false;
	}
	
	public static void main(String[] args) {

		
	}

	public ArrayList<Card> getCards() {
		// TODO Auto-generated method stub
		return null;
	}

	public ArrayList<Player> getPlayers() {
		// TODO Auto-generated method stub
		return null;
	}

    // getters and setters

    public void setSolution(Solution solution) {
        this.solution = solution;
    }

    public Solution getSolution() {
        return solution;
    }
    
    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    public Card getLastCardShown() {
        return lastCardShown;
    }

	public ArrayList<Player> getPeople() {
		// TODO Auto-generated method stub
		return null;
	}

}
