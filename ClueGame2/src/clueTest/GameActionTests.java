package clueTest;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

import org.junit.Before;
import org.junit.BeforeClass;
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
	private final int TOTALCARDS = 2;
	private final int TOTALPEOPLE = 2;
	
	@Before
	public void setup() {
		cg = new ClueGame("person.csv","weaponCards.csv","boardConfig.csv", "legendConfig.txt");
		try {
			cg.loadConfigFiles();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testCardsFromFile() {
		ArrayList<Card> deck =  cg.getCards();
		
		assertEquals(TOTALCARDS, deck.size());

		assertTrue(deck.contains("Pipe"));
		assertTrue(deck.contains("Colonel Mustard"));
		assertTrue(deck.contains("Animal Room"));
		assertTrue(deck.get(10).type == CardType.ROOM);
	}

	@Test
	public void testPeopleFromFile() {
		ArrayList<Player> crowd =  cg.getPeople();
		
		assertEquals(TOTALPEOPLE, crowd.size());

		assertTrue(crowd.contains("Professor Plum"));
		assertTrue(crowd.contains("Colonel Mustard"));
		assertTrue(crowd.contains("Mrs. White"));
	}

	@Test
	public void testDealTest() {
		
		
		
		cg.deal();
		
		assertEquals(0, cg.getCards().size());
		
		ArrayList<Player> p = cg.getPlayers();
		
		int i = p.get(0).getCards().size();
		ArrayList<Card> testDeck = new ArrayList<Card>();
		
		for (Player o : p) {
			assertFalse(Math.abs(o.getCards().size()-i)>1);
			testDeck.addAll(o.getCards());
		}
		
		i = cg.getCards().size();
		for (Card c : cg.getCards()) {
			assertTrue(testDeck.contains(c.name));
			i--;
		}
		assertEquals(0,i);
	}
	
	@Test
	public void testCheckingAndAccusationTrue() {
        Solution answer = cg.getSolution();
        assertTrue(cg.checkAccusation(answer));
	}

	@Test
	public void testCheckingAndAccusationWrongPerson() {
        Solution wrongPerson = cg.getSolution();
        wrongPerson.setPerson("President Bush");
        assertFalse(cg.checkAccusation(wrongPerson));
	}

	@Test
	public void testCheckingAndAccusationWrongWeapon() {
        Solution wrongWeapon = cg.getSolution();
        wrongWeapon.setWeapon("Military");
        assertFalse(cg.checkAccusation(wrongWeapon));
	}
	
	@Test
	public void testCheckingAndAccusationWrongRoom() {
        Solution wrongRoom = cg.getSolution();
        wrongRoom.setRoom("Iraq");
        assertFalse(cg.checkAccusation(wrongRoom));
	}

	@Test
	public void test1Player1Card() {
        String tempString = "Bobby Pin";
        ComputerPlayer one = new ComputerPlayer("ComputerPlayer one");
        ComputerPlayer two = new ComputerPlayer("ComputerPlayer two");
        ArrayList<Player> players = new ArrayList<Player>();
        players.add(one);
        players.add(two);
        Solution solution = cg.getSolution();
        HashSet cards = new HashSet();
        cards.add(new Card(CardType.WEAPON, tempString));
        one.setCards(cards);
        cg.setPlayers(players);
        cg.handleSuggestion(solution.person, solution.room, tempString, two);
        assertEquals(tempString, cg.getLastCardShown());
	}
	
	@Test
	public void test1Player2Cards() {
        int card1 = 0;
        int card2 = 0;
        String tempString = "Bobby Pin";
        String tempString2 = "Piano Wire";
        ComputerPlayer one = new ComputerPlayer("ComputerPlayer one");
        ComputerPlayer two = new ComputerPlayer("ComputerPlayer two");
        ArrayList<Player> players = new ArrayList<Player>();
        players.add(one);
        players.add(two);
        Solution solution = cg.getSolution();
        HashSet cards = new HashSet();
        cards.add(new Card(CardType.WEAPON, tempString));
        cards.add(new Card(CardType.WEAPON, tempString2));
        one.setCards(cards);
        cg.setPlayers(players);
        for (int i = 0; i < 100; ++i) {
            cg.handleSuggestion(solution.person, solution.room, tempString, two);
            if (cg.getLastCardShown().equals(tempString)) {
                ++card1;
            }
            else if (cg.getLastCardShown().equals(tempString2)) {
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
        int card1 = 0;
        int card2 = 0;
        String tempString = "Bobby Pin";
        String tempString2 = "Piano Wire";
        ComputerPlayer one = new ComputerPlayer("ComputerPlayer one");
        ComputerPlayer two = new ComputerPlayer("ComputerPlayer two");
        ComputerPlayer three = new ComputerPlayer("ComputerPlayer three");
        ArrayList<Player> players = new ArrayList<Player>();
        players.add(one);
        players.add(two);
        players.add(three);
        Solution solution = cg.getSolution();
        HashSet cards = new HashSet();
        cards.add(new Card(CardType.WEAPON, tempString));
        HashSet otherCards = new HashSet();
        cards.add(new Card(CardType.WEAPON, tempString2));
        one.setCards(cards);
        two.setCards(otherCards);
        cg.setPlayers(players);
        for (int i = 0; i < 100; ++i) {
            cg.handleSuggestion(solution.person, solution.room, tempString, three);
            if (cg.getLastCardShown().equals(tempString)) {
                ++card1;
            }
            else if (cg.getLastCardShown().equals(tempString2)) {
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
        int card1 = 0;
        int card2 = 0;
        String tempString = "Bobby Pin";
        String tempString2 = "Piano Wire";
        ComputerPlayer one = new ComputerPlayer("ComputerPlayer one");
        HumanPlayer two = new HumanPlayer("HumanPlayer two");
        ComputerPlayer three = new ComputerPlayer("ComputerPlayer three");
        ArrayList<Player> players = new ArrayList<Player>();
        players.add(one);
        players.add(two);
        players.add(three);
        Solution solution = cg.getSolution();
        HashSet cards = new HashSet();
        cards.add(new Card(CardType.WEAPON, tempString));
        HashSet otherCards = new HashSet();
        otherCards.add(new Card(CardType.WEAPON, tempString2));
        one.setCards(cards);
        two.setCards(otherCards);
        cg.setPlayers(players);
        for (int i = 0; i < 100; ++i) {
            cg.handleSuggestion(solution.person, solution.room, tempString, three);
            if (cg.getLastCardShown().equals(tempString)) {
                ++card1;
            }
            else if (cg.getLastCardShown().equals(tempString2)) {
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
        String tempString = "Bobby Pin";
        ComputerPlayer one = new ComputerPlayer("ComputerPlayer one");
        ComputerPlayer two = new ComputerPlayer("ComputerPlayer two");
        ArrayList<Player> players = new ArrayList<Player>();
        players.add(one);
        players.add(two);
        Solution solution = cg.getSolution();
        HashSet cards = new HashSet();
        cards.add(new Card(CardType.WEAPON, tempString));
        one.setCards(cards);
        cg.setPlayers(players);
        cg.handleSuggestion(solution.person, solution.room, solution.weapon, two);
        assertTrue(cg.getLastCardShown() == null);
	}

	@Test
	public void testTargetComputerPlayerIncludesRoom() {
        // row: 9 col: 3 distance 3
        int distance = 3;
        ComputerPlayer player = new ComputerPlayer("player one");
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
        // row: 9 col: 3 distance 2
        int distance = 3;
        ComputerPlayer player = new ComputerPlayer("player one");
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
        // row: 9 col: 4 distance 2
        int distance = 2;
        ComputerPlayer player = new ComputerPlayer("player one");
        player.setRow(9);
        player.setCol(4);
        player.setLastRoomVisited('C');
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
	public void testCorrectSuggestion() {
        String tempString = "Bobby Pin";
        ComputerPlayer one = new ComputerPlayer("ComputerPlayer one");
        ComputerPlayer two = new ComputerPlayer("ComputerPlayer two");
        ArrayList<Player> players = new ArrayList<Player>();
        players.add(one);
        players.add(two);
        Solution solution = cg.getSolution();
        HashSet cards = new HashSet();
        cards.add(new Card(CardType.WEAPON, tempString));
        one.setCards(cards);
        cg.setPlayers(players);
        cg.handleSuggestion(solution.person, solution.room, solution.weapon, two);
        assertTrue(cg.getLastCardShown() == null);
	}
	
	@Test
	public void testRandomSuggestion() {
        Random rand = new Random();
        int card1 = 0;
        int card2 = 0;
        String tempString = "Bobby Pin";
        String tempString2 = "Piano Wire";
        ComputerPlayer one = new ComputerPlayer("ComputerPlayer one");
        HumanPlayer two = new HumanPlayer("HumanPlayer two");
        ComputerPlayer three = new ComputerPlayer("ComputerPlayer three");
        ArrayList<Player> players = new ArrayList<Player>();
        players.add(one);
        players.add(two);
        players.add(three);
        Solution solution = cg.getSolution();
        HashSet cards = new HashSet();
        cards.add(new Card(CardType.WEAPON, tempString));
        HashSet otherCards = new HashSet();
        otherCards.add(new Card(CardType.WEAPON, tempString2));
        one.setCards(cards);
        two.setCards(otherCards);
        cg.setPlayers(players);
        for (int i = 0; i < 100; ++i) {
            if (rand.nextInt(2) == 1) { 
                cg.handleSuggestion(solution.person, solution.room, tempString, three);
            }
            else {
                cg.handleSuggestion(solution.person, solution.room, tempString2, three);
            }
            if (cg.getLastCardShown().equals(tempString)) {
                ++card1;
            }
            else if (cg.getLastCardShown().equals(tempString2)) {
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
