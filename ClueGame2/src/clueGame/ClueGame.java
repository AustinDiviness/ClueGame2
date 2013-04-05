package clueGame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

public class ClueGame extends JFrame {
	// TODO somehow there is cards being created for the closet room (which has no entrance) and for the walkways. this
	// needs to be fixed or the game might pick a room card that is impossible to guess

    private Solution solution;
    private ArrayList<Player> players;
    private Card lastCardShown;
	public Board board;
	private ArrayList<Card> deck;
	private ArrayList<Card> allCards;
	private String peopleConfig;
	private String weaponsConfig;
	public static final String gameTitle = "Clue!";
	public HumanPlayer human = null;
	private JDialog notes = null;
	
	//used to make sure the board has a fixed minimum size
	//so everything displays
	private static final int EXTRA_WIDTH = 200;
	private static final int EXTRA_HEIGHT = 100;
	
	//running the main game 
	private int humanPlayerIndex;
	private int turnCount;
	private Player activePlayer = null;
	public static ClueGame instance = null;
	private int die;
	private boolean canGoToNextPlayer;
	// gui controls objects
	private JTextField whoseTurn;
	private JTextField dieRoll;
	private JTextField guess;
	private JTextField guessResult;
	private JButton nextPlayer;
	private JButton makeAccusation;

	
	public ClueGame(String peopleConfig, String weaponsConfig, String boardConfig, String roomLegendConfig) {
		this.solution = new Solution();
		this.lastCardShown = new Card(CardType.WEAPON, "Something");
		this.board = new Board(boardConfig, roomLegendConfig);
		this.deck = new ArrayList<Card>();
		this.allCards = new ArrayList<Card>();
		this.players = new ArrayList<Player>();
		this.lastCardShown = new Card(CardType.ROOM, "");
		this.peopleConfig = peopleConfig;
		this.weaponsConfig = weaponsConfig;
		this.canGoToNextPlayer = false;
		this.whoseTurn = new JTextField();
		this.dieRoll = new JTextField();
		this.guess = new JTextField();
		this.guessResult = new JTextField();
		this.nextPlayer = new JButton();
		this.makeAccusation = new JButton();
		this.turnCount = 0;
		instance = this;
	}

	public void deal() {
		// deal cards to all players
		int cardCount = 0;
		Player tempPlayer = null;
		for (Card card: deck) {
			tempPlayer = players.get(cardCount % players.size());
			tempPlayer.giveCard(card);
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
		deal();
		createDetectiveNotes();
	}
	
	public void createDetectiveNotes() {
			ArrayList<String> playerNames = new ArrayList<String>();
			ArrayList<String> rooms = new ArrayList<String>();
			ArrayList<String> weapons = new ArrayList<String>();
		
			// get names of all weapons
			for (Card card: allCards) {
				switch(card.getType()) {
				case PERSON:
					playerNames.add(card.getName());
					break;
				case ROOM:
					rooms.add(card.getName());
					break;
				case WEAPON:
					weapons.add(card.getName());
					break;
				}
			}
			notes = new DetectiveNotes(playerNames, rooms, weapons);
	}
	
	public void loadSplashScreen() {
		JOptionPane.showMessageDialog(this, "You are " + human.getName(), "Welcome to Clue", JOptionPane.INFORMATION_MESSAGE);
	}
	
	public void loadMenu(){
		//Create menu bar
		JMenuBar menuBar = new JMenuBar();
		//File Menu
		JMenu fileMenu = new JMenu("File");
		menuBar.add(fileMenu);
		//Create Detective Notes and exit
		JMenuItem detectiveNotes = new JMenuItem("Show Detective Notes");
		JMenuItem exitAction = new JMenuItem("Exit");

		detectiveNotes.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// set detective notes to visible
				notes.setVisible(true);
			}
		});
		
		//Adding Actions to menuItems
		exitAction.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e){
				System.exit(0);
			}
		});
		//Add different menuItems to menu
		fileMenu.add(detectiveNotes);
		fileMenu.add(exitAction);
		//setting menuBar on JFrame
		setJMenuBar(menuBar);
	}

	public void createGameControls() {

		//size of stuff in panels
		int width = 200;
		int height = 30;
		//create gui pieces
		JPanel total = new JPanel(new BorderLayout());
		JPanel top = new JPanel();
		//top.setPreferredSize(new Dimension(widthPanel, heightPanel));
		JPanel bottom = new JPanel();
		//bottom.setPreferredSize(new Dimension(widthPanel, heightPanel));
		JLabel whoseTurnLabel = new JLabel();
		JLabel dieRollLabel = new JLabel();
		JLabel guessLabel = new JLabel();
		JLabel guessResultLabel = new JLabel();
		
		// set editable state for text fields
		dieRoll.setEditable(false);
		guess.setEditable(false);
		guessResult.setEditable(false);
		whoseTurn.setEditable(false);
		// set preferred size for text fields
		Dimension dim = new Dimension(width, height);
		whoseTurn.setPreferredSize(dim);
		dieRoll.setPreferredSize(new Dimension(30, height));
		guess.setPreferredSize(new Dimension(400, height));
		guessResult.setPreferredSize(dim);
		// set starting text
		whoseTurnLabel.setText("Whose turn?");
		nextPlayer.setText("Next Player");
		makeAccusation.setText("Make an Accusation");
		dieRollLabel.setText("DieRoll");
		guessLabel.setText("Guess");
		guessResultLabel.setText("Guess Result");
		whoseTurn.setText(human.getName());
		dieRoll.setText("" + die);
		guess.setText("None");
		guessResult.setText("No New Clue");
		// add text items to their respective labels
		whoseTurnLabel.add(whoseTurn);
		dieRollLabel.add(dieRoll);
		guessLabel.add(guess);
		guessResultLabel.add(guessResult);
		// add items to bottom panel
		bottom.add(dieRollLabel, BorderLayout.WEST);
		bottom.add(dieRoll, BorderLayout.WEST);
		bottom.add(guessLabel, BorderLayout.NORTH);
		bottom.add(guess, BorderLayout.NORTH);
		bottom.add(guessResultLabel, BorderLayout.EAST);
		bottom.add(guessResult, BorderLayout.EAST);
		bottom.setVisible(true);
		// add items to top panel
		top.add(whoseTurnLabel, BorderLayout.WEST);
		top.add(whoseTurn, BorderLayout.WEST);
		top.add(nextPlayer, BorderLayout.NORTH);
		top.add(makeAccusation, BorderLayout.NORTH);
		top.setVisible(true);
		// add total to game
		total.add(top, BorderLayout.NORTH);
		total.add(bottom, BorderLayout.SOUTH);
		total.setVisible(true);
		this.add(total, BorderLayout.SOUTH);
	}


	public void createHumanCards() {
		int THICKNESS = 2;
		JTextField tempField = null;
		JPanel textFields = new JPanel(new GridLayout(0, 1)); 
		TitledBorder border = new TitledBorder(new LineBorder(Color.BLACK, THICKNESS), "My Cards");
		textFields.setBorder(border);
		textFields.setPreferredSize(new Dimension(200, 200));
		for (Card card: human.getCards()) {
			tempField = new JTextField(card.getName(), 30);
			tempField.setEditable(false);
			textFields.add(tempField);
			System.out.println(card.getName());
		}
		textFields.setVisible(true);
		this.add(textFields, BorderLayout.EAST);
	}
	
	public void movePlayersToStartingSpots() {
		// moves players to random predefined starting positions on the board
		Random rand = new Random();
		int i;
		int[] startRow = {0, 0, 6, 15, 21, 21, 15, 4, 9, 7, 14};
		int[] startCol = {8, 16, 22, 22, 16, 7, 0, 0, 9, 12, 14};
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
		// move player that was suggested to location
		for (Player player: players) {
			if (player.getName().equals(person)) {
				player.setRow(activePlayer.getRow());
				player.setCol(activePlayer.getCol());
				board.repaint();
			}
		}
		// set guess text box to suggestion
		guess.setText(person + " in the " + room + " with the " + weapon);
		// get cards that can disprove the suggestion
		ArrayList<Card> disproveCards = new ArrayList<Card>();
		Card card;
		for (Player player: players) {
			for (Object c: player.getCards().toArray()) {
				card = (Card) c;
				if (card.toString().equals(person) || card.toString().equals(weapon) || card.toString().equals(room)) {
					disproveCards.add(card);
				}
			}
		}
		// pick card to show, if one exists
		if (disproveCards.size() > 0) {
			Random rand = new Random();
			lastCardShown = disproveCards.get(rand.nextInt(disproveCards.size()));
			accusingPerson.showCard(lastCardShown);
			guessResult.setText(lastCardShown.getName());
		}
		else {
			lastCardShown = new Card(CardType.ROOM, "");
			guessResult.setText("No New Clue");
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
		ArrayList<String> playerInputStrings = new ArrayList<String>();
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
			playerInputStrings.add(line);
			splitLine = line.split(",");
			deck.add(new Card(CardType.PERSON, splitLine[0]));
			//deck.addAll(playerCards);
		}
		humanPlayerIndex = new Random().nextInt(playerInputStrings.size());
		Player player = null;
		Color color = null;
		for (int i = 0; i < playerInputStrings.size(); ++i) {
			splitLine = playerInputStrings.get(i).split(",");
			if (i == humanPlayerIndex) {
				player = new HumanPlayer(splitLine[0]);
				human = (HumanPlayer) player;
				// TODO remove line later
				System.out.println(splitLine[0]);
			}
			else {
				player = new ComputerPlayer(splitLine[0]);
			}
			
			// determine which color is needed
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
			player.setColor(color);
			players.add(player);
		}

	}
	
	public void loadRoomCards() {
		// loads room cards to card deck
		for (Entry<Character, String> item: board.getRooms().entrySet()) {
			deck.add(new Card(CardType.ROOM, item.getValue()));
		}
	}
	
	public void paintBoard(Graphics g) {
		board.paintComponent(g);
	}
	
	public void rollDie() {
		Random rand = new Random();
		// add one because nextInt(x) returns value in the range [0, x)
		die = rand.nextInt(6) + 1;
	}
	
	public void nextPlayer() {
		canGoToNextPlayer = false;
		rollDie();
		++turnCount;
		activePlayer = players.get((humanPlayerIndex + turnCount) % players.size());
		whoseTurn.setText(activePlayer.getName());
		dieRoll.setText("" + die);
		if (activePlayer instanceof ComputerPlayer) {
			runAI();
		}
	}
	
	public void runAI() {
		board.calcTargets(activePlayer.getRow(), activePlayer.getCol(), die);
		ArrayList<BoardCell> targets = new ArrayList<BoardCell>(board.getTargets());
		Random rand = new Random();
		int index;
		if (targets.size() > 1) {
			index = rand.nextInt(targets.size() - 1);
		}
		else {
			index = 0;
		}
		BoardCell tempCell = targets.get(index);
		activePlayer.setRow(tempCell.getRow());
		activePlayer.setCol(tempCell.getCol());
		canGoToNextPlayer = true;
	}
	
	public void runAccusation() {
		ArrayList<String> roomNames = new ArrayList<String>();
		ArrayList<String> playerNames = new ArrayList<String>();
		ArrayList<String> weaponNames = new ArrayList<String>();
		for (Card card: allCards) {
			switch(card.getType()) {
			case ROOM:
				roomNames.add(card.getName());
			case PERSON:
				playerNames.add(card.getName());
				break;
			case WEAPON:
				weaponNames.add(card.getName());
				break;
			default:
				break;
			}
		}
		AccusationDialog dialog = new AccusationDialog(roomNames, playerNames, weaponNames);
	}
	
	public void addEvents() {
		nextPlayer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				if (canGoToNextPlayer) {
					nextPlayer();
					board.repaint();
				}
				else {
					JOptionPane.showMessageDialog(ClueGame.instance, "You need to move first!", "Can't Finish Turn", JOptionPane.INFORMATION_MESSAGE);
					return;
				}
			}
		});
		
		makeAccusation.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				runAccusation();
			}
		});
		
		board.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent mouseEvent) {
				int col = mouseEvent.getX() / BoardCell.width;
				int row = mouseEvent.getY() / BoardCell.height;
				board.calcTargets(activePlayer.getRow(), activePlayer.getCol(), die);
				Set<BoardCell> targets = board.getTargets();
				if (canGoToNextPlayer == false && targets.contains(board.getCellAt(board.calcIndex(row, col)))) {
					canGoToNextPlayer = true;
					activePlayer.setRow(row);
					activePlayer.setCol(col);
					board.repaint();
					// if room was a doorway, run the suggestionDialog
					if (board.getCellAt(board.calcIndex(row, col)).isDoorway()) {
						String roomName = board.getRoomNameAt(row, col);
						ArrayList<String> playerNames = new ArrayList<String>();
						ArrayList<String> weaponNames = new ArrayList<String>();
						for (Card card: allCards) {
							switch(card.getType()) {
							case PERSON:
								playerNames.add(card.getName());
								break;
							case WEAPON:
								weaponNames.add(card.getName());
								break;
							default:
								break;
							}
						}
						SuggestionDialog suggestionDialog = new SuggestionDialog(roomName, playerNames, weaponNames);
						// get information
						if (suggestionDialog.wasSubmitted()) {
							handleSuggestion(suggestionDialog.getPlayer(), suggestionDialog.getRoom(), suggestionDialog.getWeapon(), activePlayer);
						}
					}
				}
				// player has already moved this turn
				else if (canGoToNextPlayer == true) {
					JOptionPane.showMessageDialog(ClueGame.instance, "You've already moved this turn!", "Can't Move Again", JOptionPane.INFORMATION_MESSAGE);
				}
				// player clicks somewhere that they can't move to
				else {
					JOptionPane.showMessageDialog(ClueGame.instance, "Please select one of the highlighted squares.", "Can't Move There", JOptionPane.INFORMATION_MESSAGE);
				}
			}
			
			// insofar as I can tell, we don't need these. but they're required to be there
			// to fully implement the MouseListener interface
			@Override
			public void mouseEntered(MouseEvent mouseEvent) {
				
			}
			
			@Override
			public void mouseExited(MouseEvent mouseEvent) {
				
			}

			@Override
			public void mousePressed(MouseEvent mouseEvent) {
				
			}

			@Override
			public void mouseReleased(MouseEvent mouseEvent) {
				
			}
		});		
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
		return players;
	}
	
	public void setActivePlayer(Player player) {
		activePlayer = player;
	}
	
	public Player getActivePlayer() {
		return activePlayer;
	}
	
	public void setDie(int roll) {
		die = roll;
	}
	
	public int getDie() {
		return die;
	}
	
	public void setCanGoToNextPlayer(boolean value) {
		this.canGoToNextPlayer = value;
	}
	
	public boolean getCanGoToNextPlayer() {
		return this.canGoToNextPlayer;
	}
	
	public static void main(String[] args) {
		ClueGame game = new ClueGame("people.csv", "weaponCards.csv",
				"boardConfig.csv", "legendConfig.txt");
		try {
			game.loadConfigFiles();
		} catch (Exception e) {
			e.printStackTrace();
		}
		//game.setContentPane(game.board);
		game.add(game.board, BorderLayout.CENTER);
		game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		game.setMinimumSize(new Dimension((26 * BoardCell.width) + EXTRA_WIDTH, (26 * BoardCell.height) + EXTRA_HEIGHT));
		game.setTitle(gameTitle);
		game.loadMenu();
		game.createHumanCards();
		game.setActivePlayer(game.human);
		game.rollDie();
		game.createGameControls();
		game.setVisible(true);
		//loading the splash screen
		game.loadSplashScreen();
		
		//Running the game, looping through until finished
		game.addEvents();
			
		
	}

	

}
