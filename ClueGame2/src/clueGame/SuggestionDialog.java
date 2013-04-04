package clueGame;

import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JComboBox;
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
		JTextField roomText = new JTextField("Room");
        JTextField playerText = new JTextField("Player");
        JTextField weaponText = new JTextField("Weapon");
        String[] roomArray = {roomName};
        JComboBox roomCombo = new JComboBox(roomArray);
		panel.setLayout(new GridLayout(0, 2));
		roomText.setEditable(false);
        roomCombo.setEnabled(false);
        panel.add(roomText);
        panel.add(roomCombo);
        panel.add(playerText);
        panel.add(createComboBox(playerNames));
        panel.add(weaponText);
        panel.add(createComboBox(weaponNames));
	}

    public JComboBox createComboBox(ArrayList<String> options) {
        String[] stringArray = new String[options.size()];
        for (int i  = 0; i < stringArray.length; ++i) {
            stringArray[i] = options.get(i);
        }
        JComboBox box = new JComboBox(stringArray);
        box.setSelectedIndex(0);
        return box;
    }
	
}
