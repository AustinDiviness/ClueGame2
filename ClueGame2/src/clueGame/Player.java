package clueGame;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;
import java.util.Set;

import javax.swing.JPanel;


public abstract class Player {
	protected String name;
	protected Set<Card> myCards;
	protected int row;
	protected int col;
	protected Color color;
	
	protected static int width, height;
	
	public Card disproveSuggestion(String person, String room, String weapon) {
		// TODO actually create function. what happens if player has more than one card that could disprove the suggestion?
		// should this take a solution object as a single argument instead of us having to divide the solution into 3 strings?
		return new Card(CardType.ROOM, "b");
	}
	
	@Override
	public String toString() {
		return name;
	}

	// getters and setters
	public Set<Card> getCards() {
		// TODO actually return cards
		return myCards;
	}

    public void setCards(Set<Card> cards) {
        this.myCards = cards;
    }
    
    public String getName() {
    	return this.name;
    }
    
    public void setName(String name) {
    	this.name = name;
    }
      
	public int getRow() {
		return this.row;
	}

	public int getCol() {
		return this.col;
	}

	public void setRow(int row) {
		this.row = row;
		
	}

	public void setCol(int col){
		this.col = col;
	}
	
	public void setColor() {
		Random rand = new Random();
		float r, gr, bl;
		
//		r = rand.nextFloat();
//		gr = rand.nextFloat();
//		bl = rand.nextFloat();
		
		r = 200; 
		gr = 200; 
		bl = 200;
		
		Color randomColor = Color.black;
		
		this.color = color;
	}
	
	public void giveCard(Card card) {
		this.myCards.add(card);
	}
	

	abstract public void showCard(Card card);
	
	public void draw(Graphics g){
		setColor();
		
		width = BoardCell.width;
		height = BoardCell.height;
		
		g.setColor(color);
		
		g.fillOval(getRow()*height + (height/2), getCol()*width + (width/2), width, height);
		
		
		
	}
}
