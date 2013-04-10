package clueTest;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.awt.Color;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import clueGame.BoardCell;
import clueGame.Card;
import clueGame.CardType;
import clueGame.ClueGame;
import clueGame.ComputerPlayer;
import clueGame.HumanPlayer;
import clueGame.Player;
import clueGame.Solution;

public class GameActionTests {
	ClueGame cg;
	private final int TOTALCARDS = 14;
	private final int TOTALPEOPLE = 3;
	private Color color = Color.BLUE;
	
	@Before
	public void setup() {
        // create and load Clue Game with default config files
		cg = new ClueGame("testPeople.csv","testWeaponCards.csv","boardConfig.csv", "legendConfig.txt");
		try {
			cg.loadConfigFiles();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testCardsFromFile() {
        // test that cards were properly created from config files
		ArrayList<Card> allCards = cg.getCards();
		ArrayList<String> cardNames = new ArrayList<String>();
		for (Card card: allCards) {
			cardNames.add(card.getName());
		}
		assertEquals(TOTALCARDS, allCards.size());
		assertTrue(cardNames.contains("Lead Pipe")); // tests weapon cards
		assertTrue(cardNames.contains("Colonel Mustard")); // tests people cards
		assertTrue(cardNames.contains("Animal Room")); // tests room cards
		assertTrue(allCards.get(10).getType() == CardType.ROOM);
	}

	@Test
	public void testPeopleFromFile() {
        // test that players were properly created from config file
		ArrayList<Player> crowd =  cg.getPeople();
		ArrayList<String> playerNames = new ArrayList<String>();
		for (Player player: crowd) {
			playerNames.add(player.getName());
		}
		assertEquals(TOTALPEOPLE, crowd.size());
		assertTrue(playerNames.contains("Professor Plum"));
		assertTrue(playerNames.contains("Colonel Mustard"));
		assertTrue(playerNames.contains("Mrs. White"));
	}

	@Test
	public void testDealTest() {
        // test that all cards were properly dealt and that players have about
        // the same number of cards
		cg.deal();
		assertEquals(0, cg.getDeck().size());
		ArrayList<Player> p = cg.getPlayers();
		int i = p.get(0).getCards().size();
		ArrayList<Card> testDeck = new ArrayList<Card>();
		
		for (Player o : p) {
			assertFalse(Math.abs(o.getCards().size()-i)>1);
			testDeck.addAll(o.getCards());
		}
		
		i = cg.getDeck().size();
		for (Card c : cg.getDeck()) {
			assertTrue(testDeck.contains(c.getName()));
			i--;
		}
		
		assertEquals(0,i);
	}
	
	@Test
	public void testCheckingAndAccusationTrue() {
        // test that a correct accusation can be made
        Solution answer = cg.getSolution();
        assertTrue(cg.checkAccusation(answer));
	}
	
	@Test
	public void testCheckingAndAccusationWrongPerson() {
        // test an accusation with a wrong person
        Solution wrongPerson = new Solution();
        wrongPerson.setPerson("President Bush");
        wrongPerson.setWeapon(cg.getSolution().getWeapon());
        wrongPerson.setRoom(cg.getSolution().getRoom());
        assertFalse(cg.checkAccusation(wrongPerson)); 
	}

	@Test
	public void testCheckingAndAccusationWrongWeapon() {
        // test an accusation with a wrong weapon
        Solution wrongWeapon = new Solution();
        wrongWeapon.setWeapon("Military");
        wrongWeapon.setPerson(cg.getSolution().getPerson());
        wrongWeapon.setRoom(cg.getSolution().getRoom());
        assertFalse(cg.checkAccusation(wrongWeapon));
	}
	
	@Test
	public void testCheckingAndAccusationWrongRoom() {
        // test and accusation with a wrong room
        Solution wrongRoom = new Solution();
        wrongRoom.setRoom("Iraq");
        wrongRoom.setPerson(cg.getSolution().getPerson());
        wrongRoom.setWeapon(cg.getSolution().getWeapon());
        assertFalse(cg.checkAccusation(wrongRoom));
	}

	@Test
	public void test1Player1Card() {
        // test that suggestions are handled properly. One player
        // with one card version
        String tempString = "Bobby Pin";
        ComputerPlayer one = new ComputerPlayer("ComputerPlayer one", color);
        ComputerPlayer two = new ComputerPlayer("ComputerPlayer two", color);
        ArrayList<Player> players = new ArrayList<Player>();
        Solution solution = cg.getSolution();
        //HashSet<Card> cards = new HashSet<Card>();
        Card tempCard = new Card(CardType.WEAPON, tempString);
        //cards.add(tempCard);
        //one.setCards(cards);
        one.giveCard(tempCard);
        players.add(one);
        players.add(two);
        cg.setPlayers(players);
        cg.handleSuggestion(solution.getPerson(), solution.getRoom(), tempString, two);
        assertEquals(tempString, cg.getLastCardShown().getName());
        
	}
	
	@Test
	public void test1Player2Cards() {
        // test that suggestions are handled properly. One player
        // with two cards version
        int card1 = 0;
        int card2 = 0;
        String tempString = "Bobby Pin";
        String tempString2 = "The White House";
        ComputerPlayer one = new ComputerPlayer("ComputerPlayer one", color);
        ComputerPlayer two = new ComputerPlayer("ComputerPlayer two", color);
        ArrayList<Player> players = new ArrayList<Player>();
        players.add(one);
        players.add(two);
        Solution solution = cg.getSolution();
        //HashSet<Card> cards = new HashSet<Card>();
        //cards.add(new Card(CardType.WEAPON, tempString));
        //cards.add(new Card(CardType.WEAPON, tempString2));
        //one.setCards(cards);
        one.giveCard(new Card(CardType.WEAPON, tempString));
        one.giveCard(new Card(CardType.ROOM, tempString2));
        cg.setPlayers(players);
        for (int i = 0; i < 100; ++i) {
            cg.handleSuggestion(solution.getPerson(), tempString2, tempString, two);
            if (cg.getLastCardShown().getName().equals(tempString)) {
                ++card1;
            } else if (cg.getLastCardShown().getName().equals(tempString2)) {
                ++card2;
            }
            else {
                fail("unknown card was shown");
            }
        }
        assertTrue(card1 > 0);
        assertTrue(card2 > 0);
	}
	
	@Test
	public void test2Players2Cards() {
        // test that suggestions are handled properly. Two players
        // with two cards version
        int card1 = 0;
        int card2 = 0;
        String tempString = "Bobby Pin";
        String tempString2 = "Detroit";
        ComputerPlayer one = new ComputerPlayer("ComputerPlayer one", color);
        ComputerPlayer two = new ComputerPlayer("ComputerPlayer two", color);
        ComputerPlayer three = new ComputerPlayer("ComputerPlayer three", color);
        ArrayList<Player> players = new ArrayList<Player>();
        players.add(one);
        players.add(two);
        players.add(three);
        Solution solution = cg.getSolution();
        //HashSet<Card> cards = new HashSet<Card>();
        //cards.add(new Card(CardType.WEAPON, tempString));
        //HashSet<Card> otherCards = new HashSet<Card>();
        //cards.add(new Card(CardType.WEAPON, tempString2));
        one.giveCard(new Card(CardType.WEAPON, tempString));
        two.giveCard(new Card(CardType.ROOM, tempString2));
        cg.setPlayers(players);
        for (int i = 0; i < 100; ++i) {
            cg.handleSuggestion(solution.getPerson(), tempString2, tempString, three);
            if (cg.getLastCardShown().getName().equals(tempString)) {
                ++card1;
            }
            else if (cg.getLastCardShown().getName().equals(tempString2)) {
                ++card2;
            }
            else {
                fail("unknown card was shown");
            }
        }
        assertTrue(card1 > 0);
        assertTrue(card2 > 0);
	}

	@Test
	public void testHumanPlayerCard() {
        // test that suggestions are handled properly with human players
        int card1 = 0;
        int card2 = 0;
        String tempString = "Bobby Pin";
        String tempString2 = "International Space Station";
        ComputerPlayer one = new ComputerPlayer("ComputerPlayer one", color);
        HumanPlayer two = new HumanPlayer("HumanPlayer two");
        ComputerPlayer three = new ComputerPlayer("ComputerPlayer three", color);
        ArrayList<Player> players = new ArrayList<Player>();
        players.add(one);
        players.add(two);
        players.add(three);
        Solution solution = cg.getSolution();
        //HashSet<Card> cards = new HashSet<Card>();
        //cards.add(new Card(CardType.WEAPON, tempString));
        //HashSet<Card> otherCards = new HashSet<Card>();
        //otherCards.add(new Card(CardType.WEAPON, tempString2));
        one.giveCard(new Card(CardType.WEAPON, tempString));
        two.giveCard(new Card(CardType.ROOM, tempString2));
        cg.setPlayers(players);
        for (int i = 0; i < 100; ++i) {
            cg.handleSuggestion(solution.getPerson(), tempString2, tempString, three);
            if (cg.getLastCardShown().getName().equals(tempString)) {
                ++card1;
            }
            else if (cg.getLastCardShown().getName().equals(tempString2)) {
                ++card2;
            }
            else {
                fail("unknown card was shown");
            }
        }
        assertTrue(card1 > 0);
        assertTrue(card2 > 0);
	}

	@Test
	public void testNoCard() {
        // test for when there is no card to reveal from suggestion
        String tempString = "Bobby Pin";
        ComputerPlayer one = new ComputerPlayer("ComputerPlayer one", color);
        ComputerPlayer two = new ComputerPlayer("ComputerPlayer two", color);
        ArrayList<Player> players = new ArrayList<Player>();
        players.add(one);
        players.add(two);
        Solution solution = cg.getSolution();
        //HashSet<Card> cards = new HashSet<Card>();
        //cards.add(new Card(CardType.WEAPON, tempString));
        one.giveCard(new Card(CardType.WEAPON, tempString));
        cg.setPlayers(players);
        cg.handleSuggestion(solution.getPerson(), solution.getRoom(), solution.getWeapon(), two);
        assertTrue(cg.getLastCardShown().getName() == "");
	}


	@Test
	public void testTargetComputerPlayerIncludesRoom() {
        // test that a computer player can potentially move to a room
        // row: 9 col: 3 distance 3
        int distance = 3;
        ComputerPlayer player = new ComputerPlayer("player one", color);
        player.setRow(9);
        player.setCol(3);
        cg.board.calcTargets(player.getRow(), player.getCol(), distance);
        HashSet<BoardCell> targets = (HashSet<BoardCell>) cg.board.getTargets();
        
        boolean doorwayFlag = false;
        for (BoardCell cell: targets) {
            if (cell.isDoorway()) {
                doorwayFlag = true;
                break;
            }
        }
        assertTrue(doorwayFlag);
	}

	@Test
	public void testTargetComputerPlayerIncludesNoRoom() {
        // test when a computer player can move with no rooms available
        // row: 9 col: 3 distance 2
        int distance = 2;
        ComputerPlayer player = new ComputerPlayer("player one", color);
        player.setRow(9);
        player.setCol(3);
        cg.board.calcTargets(player.getRow(), player.getCol(), distance);
        HashSet<BoardCell> targets = (HashSet<BoardCell>) cg.board.getTargets();
        boolean doorwayFlag = false;
        for (BoardCell cell: targets) {
            if (cell.isDoorway()) {
                doorwayFlag = true;
                break;
            }
        }
        assertFalse(doorwayFlag);
	}
	
	@Test
	public void testTargetComputerPlayerIncludesLastVisitedRoom() {
        // test when the only room available is the last room visited for the 
        // computer player
        // row: 9 col: 4 distance 2
        int distance = 2;
        ComputerPlayer player = new ComputerPlayer("player one", color);
        player.setRow(9);
        player.setCol(4);
        player.setLastRoomVisited('C');

        cg.board.calcTargets(player.getRow(), player.getCol(), distance);
        HashSet<BoardCell> targets = (HashSet<BoardCell>) cg.board.getTargets();
        
        for (int i = 1; i < 100; i++) {
        	player.pickLocation(targets);
        	assertTrue(player.getCol() != 11 && player.getRow() != 4);
        }

	}

	@Test
	public void testCorrectSuggestion() {
        // test that the suggestion was correct on all accounts
        String tempString = "Bobby Pin";
        ComputerPlayer one = new ComputerPlayer("ComputerPlayer one", color);
        ComputerPlayer two = new ComputerPlayer("ComputerPlayer two", color);
        ArrayList<Player> players = new ArrayList<Player>();
        players.add(one);
        players.add(two);
        Solution solution = cg.getSolution();
        HashSet<Card> cards = new HashSet<Card>();
        cards.add(new Card(CardType.WEAPON, tempString));
        one.setCards(cards);
        cg.setPlayers(players);
        cg.handleSuggestion(solution.getPerson(), solution.getRoom(), solution.getWeapon(), two);
        assertTrue(cg.getLastCardShown().getName() == "");
	}
	
	@Test
	public void testRandomSuggestion() {
        // test random suggestions
        Random rand = new Random();
        int card1 = 0;
        int card2 = 0;
        String tempString = "Bobby Pin";
        String tempString2 = "Piano Wire";
        ComputerPlayer one = new ComputerPlayer("ComputerPlayer one", color);
        HumanPlayer two = new HumanPlayer("HumanPlayer two");
        ComputerPlayer three = new ComputerPlayer("ComputerPlayer three", color);
        ArrayList<Player> players = new ArrayList<Player>();
        players.add(one);
        players.add(two);
        players.add(three);
        Solution solution = cg.getSolution();
        HashSet<Card> cards = new HashSet<Card>();
        cards.add(new Card(CardType.WEAPON, tempString));
        HashSet<Card> otherCards = new HashSet<Card>();
        otherCards.add(new Card(CardType.WEAPON, tempString2));
        one.setCards(cards);
        two.setCards(otherCards);
        cg.setPlayers(players);
        for (int i = 0; i < 100; ++i) {
            if (rand.nextInt(2) == 1) { 
                cg.handleSuggestion(solution.getPerson(), solution.getRoom(), tempString, three);
            }
            else {
                cg.handleSuggestion(solution.getPerson(), solution.getRoom(), tempString2, three);
            }
            if (cg.getLastCardShown().getName().equals(tempString)) {
                ++card1;
            }
            else if (cg.getLastCardShown().getName().equals(tempString2)) {
                ++card2;
            }
            else {
                fail("unknown card was shown");
            }
        }
        assertTrue(card1 > 0);
        assertTrue(card2 > 0);
	}
}
