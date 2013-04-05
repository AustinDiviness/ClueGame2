package clueGame;

public class Solution {
	private String person;
	private String weapon;
	private String room;
	
	public Solution() {
		
	}
	
	public String toString() {
		return "Person: " + person + " Weapon: " + weapon + " Room: " + room;
	}
	
	public Solution(String person, String room, String weapon) {
		this.person = person;
		this.room = room;
		this.weapon = weapon;
	}
	
	public void setRoom(String room) {
		this.room = room;
	}

	public void setPerson(String person) {
		this.person = person;
	}

	public void setWeapon(String weapon) {
		this.weapon = weapon;
	}
	
	public String getPerson() {
		return this.person;
	}
	
	public String getWeapon() {
		return this.weapon;
	}
	
	public String getRoom() {
		return this.room;
	}
}
