package clueGame;

public class Card {
	private CardType type;
	private String name;
	
	public Card(CardType type, String name) {
		super();
		this.type = type;
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}
	
	public String getName() {
		return this.name;
	}
	
	public CardType getType() {
		return this.type;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setType(CardType type) {
		this.type = type;
	}
	
}
