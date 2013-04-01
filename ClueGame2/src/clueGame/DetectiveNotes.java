package clueGame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JCheckBox;
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
	
	public DetectiveNotes(ArrayList<String> people, ArrayList<String> rooms, ArrayList<String> weapons) {
		this.people = new ArrayList<String>();
		this.rooms = new ArrayList<String>();
		this.weapons = new ArrayList<String>();
		this.people.addAll(people);
		this.rooms.addAll(rooms);
		this.weapons.addAll(weapons);
		createSections();
		setTitle(title);
		setSize(WIDTH, HEIGHT);

	}
	
	public void createSections() {
		add(createCheckBoxes("People", people));
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
		return panel;
	}
}
