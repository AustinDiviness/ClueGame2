package clueTest;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.Card;
import clueGame.CardType;
import clueGame.ClueGame;
import clueGame.Player;

public class GameActionTests {
	ClueGame cg;
	private final int TOTALCARDS = 2;
	private final int TOTALPEOPLE = 2;
	
	@BeforeClass
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
		
		assertEquals(0, cg.getCards());
		
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

		fail("Not yet implemented");
	}
	
	@Test
	public void test1Player2Cards() {
		fail("Not yet implemented");
	}
	
	@Test
	public void test2Players2Cards() {
		fail("Not yet implemented");
	}

	@Test
	public void testHumanPlayerCard() {
		fail("Not yet implemented");
	}

	@Test
	public void testNoCard() {
		fail("Not yet implemented");
	}

	@Test
	public void testTargetComputerPlayerIncludesRoom() {
		fail("Not yet implemented");
	}

	@Test
	public void testTargetComputerPlayerIncludesNoRoom() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testTargetComputerPlayerIncludesLastVisitedRoom() {
		fail("Not yet implemented");
	}

	@Test
	public void testCorrectSuggestion() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testRandomSuggestion() {
		fail("Not yet implemented");
	}
}
