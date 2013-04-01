package clueGame;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

public class DetectiveNotes extends JFrame {
	public static final int WIDTH = 580;
	public static final int HEIGHT = 660;
	public static final int THICKNESS = 2;
	public static final String title = "Detective Notes";
	private ArrayList<String> people;
	private ArrayList<String> rooms;
	private ArrayList<String> weapons;
	private ArrayList<JComponent> panels;
	
	public DetectiveNotes(ArrayList<String> people, ArrayList<String> rooms, ArrayList<String> weapons) {
		this.people = new ArrayList<String>();
		this.rooms = new ArrayList<String>();
		this.weapons = new ArrayList<String>();
		this.panels = new ArrayList<JComponent>();
		this.people.addAll(people);
		this.rooms.addAll(rooms);
		this.weapons.addAll(weapons);
		setTitle(title);
		setSize(WIDTH, HEIGHT);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent we) {
				createSections();
			}
			
			@Override
			public void windowClosing(WindowEvent we) {
				for (JComponent c: panels) {
					if (c instanceof JPanel) {
						System.out.println("jpanel");
					}
					else if (c instanceof JComboBox) {
						JComboBox box = (JComboBox) c;
						System.out.println("combo box: " + box.getSelectedItem().toString());
					}
				}
			}
		});
		//setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setVisible(true);

	}
	
	public void createSections() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0, 2));
		panel.add(createCheckBoxes("People", people));
		panel.add(createComboBox("Person Guess", people));
		panel.add(createCheckBoxes("Rooms", rooms));
		panel.add(createComboBox("Room Guess", rooms));
		panel.add(createCheckBoxes("Weapons", weapons));
		panel.add(createComboBox("Weapon Guess", weapons));
		add(panel);
	}
	
	public JPanel createCheckBoxes(String name, ArrayList<String> options) {
		
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0, 2));
		panel.setPreferredSize(new Dimension(290, 220));
		TitledBorder border = new TitledBorder(new LineBorder(Color.BLACK, THICKNESS), name);
		panel.setBorder(border);
		for (String option: options) {
			JCheckBox checkBox = new JCheckBox(option);
			panel.add(checkBox);
		}
		panels.add(panel);
		return panel;
	}
	
	public JComboBox createComboBox(String name, ArrayList<String> options) {
		// create string array from options
		String[] stringArray = new String[options.size() + 1];
		stringArray[0] = "None";
		for (int i = 1; i < stringArray.length; ++i) {
			stringArray[i] = options.get(i - 1);
		}
		JComboBox panel = new JComboBox(stringArray);
		panel.setLayout(null);
		TitledBorder border = new TitledBorder(new LineBorder(Color.BLACK, THICKNESS), name);
		panel.setBorder(border);
		panel.setSelectedIndex(0);
		panels.add(panel);
		return panel;
	}
}
	

