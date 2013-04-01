package clueGame;

import java.awt.Color;
import java.awt.Graphics;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;

public class ClueGame extends JFrame {
	// TODO add an instance of Board to BoardLayout.CENTER
	// TODO add a file menu 

    private Solution solution;
    private ArrayList<Player> players;
    private Card lastCardShown;
	public Board board;
	private ArrayList<Card> deck;
	private ArrayList<Card> allCards;
	private String peopleConfig;
	private String weaponsConfig;
	public static final String gameTitle = "Clue!";
	

	public ClueGame(String peopleConfig, String weaponsConfig, String boardConfig, String roomLegendConfig) {
		solution = new Solution();
		lastCardShown = new Card(CardType.WEAPON, "Something");
		board = new Board(boardConfig, roomLegendConfig);
		deck = new ArrayList<Card>();
		allCards = new ArrayList<Card>();
		players = new ArrayList<Player>();
		lastCardShown = new Card(CardType.ROOM, "");
		this.peopleConfig = peopleConfig;
		this.weaponsConfig = weaponsConfig;
	}

	public void deal() {
		// deal cards to all players. Tests currently think that deal() function is non destructive if I remember right,
		// however testDealTest expects the deck to be empty after dealing, so we need to decide if it's destructive or not.
		// was solved by having a destructive deck of cards, and a separate ArrayList that stores all the cards in the game.
		int cardCount = 0;
		for (Card card: deck) {
			players.get(cardCount % players.size()).giveCard(card);
			++cardCount;
		}
		deck = new ArrayList<Card>();
	}
	
	public void loadConfigFiles() throws FileNotFoundException {
		board.loadConfigFiles();
		loadWeapons(weaponsConfig);
		loadPeople(peopleConfig);
		loadRoomCards();
		allCards.addAll(deck); // should store all cards that exist in game into a separate ArrayList
		selectAnswer(); // select answer to game
		movePlayersToStartingSpots();
		board.setPlayers(players);
	}
	
	public void movePlayersToStartingSpots() {
		Random rand = new Random();
		int i;
		int[] startRow = {1,  1,  7, 16, 22, 22, 16, 5, 10};
		int[] startCol = {9, 17, 23, 23, 17,  8,  1, 1, 10};
		ArrayList<Integer> usedLocations = new ArrayList<Integer>();
		for (Player player: players) {
			while (true) {
				// if the locations are all used up, we throw an exception so as to not get 
				// stuck in an infinite loop
				if (usedLocations.size() >= startRow.length) {
					throw new RuntimeException();
				}
				i = rand.nextInt(startRow.length);
				if (usedLocations.contains(i) == false) {
					usedLocations.add(i);
					break;
				}
			}
			player.setRow(startRow[i]);
			player.setCol(startCol[i]);
		}
		
	}
	
	public void selectAnswer() {
		// selects 3 cards from the decks to be the solution to the game, removes said cards from the deck after
		ArrayList<Card> personCards = new ArrayList<Card>();
		ArrayList<Card> weaponCards = new ArrayList<Card>();
		ArrayList<Card> roomCards = new ArrayList<Card>();
		for (Card card: deck) {
			//System.out.println("number of players: " + players.size());
			switch(card.getType()) {
				case PERSON:
					personCards.add(card);
				case WEAPON:
					weaponCards.add(card);
				case ROOM:
					roomCards.add(card);
			}
		}
		Random rand = new Random();
		solution.setPerson(personCards.get(rand.nextInt(personCards.size())).toString());
		solution.setWeapon(weaponCards.get(rand.nextInt(personCards.size())).toString());
		solution.setRoom(weaponCards.get(rand.nextInt(personCards.size())).toString());
		// remove cards that were chosen from deck
		for (int i = deck.size() - 1; i >= 0; --i) {
			String tempString = deck.get(i).toString();
			if (tempString.equals(solution.getPerson()) || tempString.equals(solution.getWeapon()) || tempString.equals(solution.getRoom())) {
				deck.remove(i);
			}
		}
	}
	
	public void handleSuggestion(String person, String room, String weapon, Player accusingPerson) {
		ArrayList<Card> disproveCards = new ArrayList<Card>();
		//System.out.println(players.size());
		//System.out.println("# of players :"+ players.size());
		Card card;
		for (Player player: players) {
			//System.out.println("Player has " + player.getCards().size() + " cards");
			for (Object c: player.getCards().toArray()) {
				card = (Card) c;
				//System.out.println("x");
				if (card.toString().equals(person) || card.toString().equals(weapon) || card.toString().equals(room)) {
					disproveCards.add(card);
					//System.out.println(card.toString());
				}
			}
		}
		
		if (disproveCards.size() > 0) {
			Random rand = new Random();
			lastCardShown = disproveCards.get(rand.nextInt(disproveCards.size()));
			accusingPerson.showCard(lastCardShown);
		}
		else {
			lastCardShown = new Card(CardType.ROOM, "");
		}
	}
	
	public boolean checkAccusation(Solution solution) {
		// should this function eliminate player if accusation is incorrect, or should that
		// be handled by a separate function? I lean towards the latter option.
		return (this.solution.getPerson().equals(solution.getPerson()) &&
				this.solution.getWeapon().equals(solution.getWeapon()) &&
				this.solution.getRoom().equals(solution.getRoom()));
	}
	
	public void loadWeapons(String inputFile) {
		// loads weapons from config file into card deck
		ArrayList<Card> weaponCards = new ArrayList<Card>();
		FileReader fileReader = null;
		String currentPath = "";
		String line = null;
		try {
			currentPath = new java.io.File(".").getCanonicalPath();
		}
		catch (IOException e) {
			e.printStackTrace();
			System.out.println("Unable to find path.");
			System.exit(2);
		}
		currentPath = currentPath + File.separatorChar;
		try {
			fileReader = new FileReader(currentPath + inputFile);
		}
		catch (FileNotFoundException e) {
			System.out.println("Unable to load file: " + currentPath + inputFile);
			System.exit(1);
		}
		Scanner in = new Scanner(fileReader);
		while (in.hasNext()) {
			line = in.nextLine().trim();
			deck.add(new Card(CardType.WEAPON, line));
		}
		deck.addAll(weaponCards);
	}
	public void loadPeople(String inputFile) {
		// loads people from config file into card deck and as Player objects to players list
		ArrayList<Card> playerCards = new ArrayList<Card>();
		String[] splitLine = null;
		FileReader fileReader = null;
		String currentPath = "";
		String line = null;
		try {
			currentPath = new java.io.File(".").getCanonicalPath();
		}
		catch (IOException e) {
			e.printStackTrace();
			System.out.println("Unable to find path.");
			System.exit(2);
		}
		currentPath = currentPath + File.separatorChar;
		try {
			fileReader = new FileReader(currentPath + inputFile);
		}
		catch (FileNotFoundException e) {
			System.out.println("Unable to load file: " + currentPath + inputFile);
			System.exit(1);
		}
		Scanner in = new Scanner(fileReader);
		while (in.hasNext()) {
			line = in.nextLine().trim();
			splitLine = line.split(",");
			deck.add(new Card(CardType.PERSON, splitLine[0]));
			// TODO I'm not quite sure how to load players for the human player, so I just made them 
			// all soul less automations 
			Color color = null;
			if (splitLine[1].equalsIgnoreCase("red")) {
				color = Color.RED;
			}
			else if (splitLine[1].equalsIgnoreCase("yellow")) {
				color = Color.YELLOW;
			}
			else if (splitLine[1].equalsIgnoreCase("white")) {
				color = Color.WHITE;
			}
			else if (splitLine[1].equalsIgnoreCase("green")) {
				color = Color.GREEN;
			}
			else if (splitLine[1].equalsIgnoreCase("blue")) {
				color = Color.BLUE;
			}
			else if (splitLine[1].equalsIgnoreCase("purple")) {
				color = new Color(159, 0, 197);
			}
			players.add(new ComputerPlayer(splitLine[0], color));
			//System.out.println(line); // test code to print out players that were loaded
		}
		//System.out.println("-----------"); // test code
		deck.addAll(playerCards);
	}
	public void loadRoomCards() {
		// loads room cards to card deck
		for (Entry<Character, String> item: board.getRooms().entrySet()) {
			deck.add(new Card(CardType.ROOM, item.getValue()));
			//System.out.println(item.getValue()); // test code to print out rooms that were turned into cards
		}
		//System.out.println("-----------"); // test code
	}
	
	public void paintBoard(Graphics g) {
		board.paintComponent(g);
	}
	

    // getters and setters
	
	public ArrayList<Card> getCards() {
		return allCards;
	}
	
	public ArrayList<Card> getDeck() {
		return deck;
	}

	public ArrayList<Player> getPlayers() {
		return players;
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
		// TODO how should/does this function differ from getPlayers?
		return players;
	}
	
	public static void main(String[] args) {
		ClueGame game = new ClueGame("people.csv", "testWeaponCards.csv",
				"boardConfig.csv", "legendConfig.txt");
		try {
			game.loadConfigFiles();
		} catch (Exception e) {
			e.printStackTrace();
		}
		game.setContentPane(game.board);
		game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		game.setSize(25 * BoardCell.width, 25 * BoardCell.height);
		game.setTitle(gameTitle);
		game.setVisible(true);
	}

}
