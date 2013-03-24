package clueGame;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Map;

public class ClueGame {

    private Solution solution;
    private ArrayList<Player> players;
    private Card lastCardShown = null;
	public Board board;

	public ClueGame(String peopleConfig, String weaponsConfig, String boardConfig, String roomLegendConfig) {
		// TODO properly set variables
		solution = new Solution();
		lastCardShown = new Card(CardType.WEAPON, "Something");
		board = new Board(boardConfig, roomLegendConfig);
	}

	public void deal() {
		// TODO deal cards to all players. Tests currently think that deal() function is non destructive
	}
	
	public void loadConfigFiles() throws FileNotFoundException {
		// TODO load config files for game board, rooms, players, and weapons
		board.loadConfigFiles();
	}
	
	public void selectAnswer() {
		
	}
	
	public void handleSuggestion(String person, String room, String weapon, Player accusingPerson) {
	    // TODO make sure that lastCardShown is set	
	}
	
	public boolean checkAccusation(Solution solution) {
		// TODO check solution against game solution. should this function eliminate player if accusation is incorrect, or should that
		// be handled by a separate function? I lean towards the latter option.
		return true;
	}

    // getters and setters
	
	public ArrayList<Card> getCards() {
		// TODO actually return the cards
		return new ArrayList<Card>();
	}

	public ArrayList<Player> getPlayers() {
		// TODO actually return players
		ArrayList<Player> p = new ArrayList<Player>();
		p.add(new ComputerPlayer("temp"));
		return p;
	}

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
		// TODO actually get people. how should/does this function differ from getPlayers?
		return new ArrayList<Player>();
	}

}
