package clueGame;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class AccusationDialog extends JDialog {
	public final int WIDTH = 400;
	public final int HEIGHT = 300;
	private ArrayList<String> roomNames;
	private ArrayList<String> playerNames;
	private ArrayList<String> weaponNames;
	private String title = "Make a Accusation";
	private JTextField roomText;
	private JTextField playerText;
	private JTextField weaponText;
	private JComboBox roomCombo;
	private JComboBox playerCombo;
	private JComboBox weaponCombo;
	private JButton cancelButton;
	private JButton submitButton;
	private boolean submitted;

	public AccusationDialog(ArrayList<String> roomNames, ArrayList<String> playerNames, ArrayList<String> weaponNames) {
		this.roomNames = roomNames;
		this.playerNames = playerNames;
		this.weaponNames = weaponNames;
		setTitle(title);
		setModal(true);
		setSize(WIDTH, HEIGHT);
		this.submitted = false;
		this.roomText = new JTextField("Room");
        this.playerText = new JTextField("Player");
        this.weaponText = new JTextField("Weapon");
        this.roomCombo = createComboBox(roomNames);
		this.playerCombo = createComboBox(playerNames);
		this.weaponCombo = createComboBox(weaponNames);
		this.cancelButton = createCancelButton();
		this.submitButton = createSubmitButton();
		createSections();
		setVisible(true);
	}
	
	public void createSections() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0, 2));
		roomText.setEditable(false);
		playerText.setEditable(false);
		weaponText.setEditable(false);
        panel.add(playerText);
        panel.add(playerCombo);
        panel.add(roomText);
        panel.add(roomCombo);
        panel.add(weaponText);
        panel.add(weaponCombo);
        panel.add(cancelButton);
        panel.add(submitButton);
        this.add(panel);
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
    
    public JButton createCancelButton() {
    	JButton button = new JButton("Cancel");
    	button.addActionListener(new ActionListener() {
    		@Override
    		public void actionPerformed(ActionEvent actionEvent) {
    			setVisible(false);
    		}
    	});
    	return button;
    }
    
    public JButton createSubmitButton() {
    	JButton button = new JButton("Submit");
    	button.addActionListener(new ActionListener() {
    		@Override
    		public void actionPerformed(ActionEvent actionEvent) {
    			submitted = true;
    			setVisible(false);
    		}
    	});
    	return button;
    }
    
    public boolean wasSubmitted() {
    	return this.submitted;
    }
    
    // getters and setters
    
    public String getRoom() {
    	return (String) roomCombo.getSelectedItem();
    }
	
    public String getPlayer() {
    	return (String) playerCombo.getSelectedItem();
    }
    
    public String getWeapon() {
    	return (String) weaponCombo.getSelectedItem();
    }
}
