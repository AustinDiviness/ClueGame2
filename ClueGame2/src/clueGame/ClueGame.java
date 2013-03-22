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
		lastCardShown = new Card(CardType.WEAPON, "Something");
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
		return true;
	}
	
	public static void main(String[] args) {

		
	}

	public ArrayList<Card> getCards() {
		// TODO Auto-generated method stub
		return new ArrayList<Card>();
	}

	public ArrayList<Player> getPlayers() {
		// TODO Auto-generated method stub
		ArrayList<Player> p = new ArrayList<Player>();
		p.add(new ComputerPlayer("temp"));
		return p;
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
		return new ArrayList<Player>();
	}

}
