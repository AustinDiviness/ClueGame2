package clueGame;

import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class SuggestionDialog extends JDialog {
	private String roomName;
	private ArrayList<String> playerNames;
	private ArrayList<String> weaponNames;
	private String title = "Make a Suggestion";

	public SuggestionDialog(String roomName, ArrayList<String> playerNames, ArrayList<String> weaponNames) {
		this.roomName = roomName;
		this.playerNames = playerNames;
		this.weaponNames = weaponNames;
		setTitle(title);
		setModal(true);
		createSections();
	}
	
	public void createSections() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0, 2));
		JTextField roomText = new JTextField(roomName);
		roomText.setEditable(false);
	}
	
}
