package clueGame;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class ClueGUI {

    public void createGUI() {
        JFrame j = new JFrame();
        JPanel pTop = new JPanel();
        JPanel pBottom = new JPanel();
        j.setSize(600, 150);
        
        // Create Top of Controls
        JTextField whoseTurn = new JTextField();
        JLabel whoseTurnLabel = new JLabel();
        whoseTurnLabel.setText("Whose Turn?");
        Dimension d = whoseTurn.getPreferredSize();  
        whoseTurn.setPreferredSize(new Dimension(d.width+160,d.height));
        JPanel pTurn = new JPanel();
        pTurn.add(whoseTurnLabel, BorderLayout.NORTH);
        pTurn.add(whoseTurn, BorderLayout.SOUTH);
        pTop.add(pTurn, BorderLayout.EAST);
        
        JButton nextPlayer = new JButton();
        nextPlayer.setText("Next Player");        
        pTop.add(nextPlayer, BorderLayout.EAST);
        
        JButton makeAnAccusation = new JButton();
        makeAnAccusation.setText("Make an Accusation");
        pTop.add(makeAnAccusation, BorderLayout.EAST);
        
        j.add(pTop);
        
        // Create Bottom panel of controls
        
        JTextField dieRoll = new JTextField();
        JLabel dieRollLabel = new JLabel();
        dieRollLabel.setText("Die Roll : ");
        dieRoll.setEditable(false);
        d = dieRoll.getPreferredSize();  
        dieRoll.setPreferredSize(new Dimension(d.width+60,d.height));
        
        pBottom.add(dieRollLabel, BorderLayout.EAST);
        pBottom.add(dieRoll, BorderLayout.EAST);
        
        JTextField guess = new JTextField();
        JLabel guessLabel = new JLabel();
        guessLabel.setText("Guess : ");
        guess.setEditable(false);
        d = guess.getPreferredSize();  
        guess.setPreferredSize(new Dimension(d.width+160,d.height));
        
        pBottom.add(guessLabel, BorderLayout.EAST);
        pBottom.add(guess, BorderLayout.EAST);
        
        JTextField guessResult = new JTextField();
        JLabel guessResultLabel = new JLabel();
        guessResultLabel.setText("Guess Result : ");
        guessResult.setEditable(false);
        d = guessResult.getPreferredSize();  
        guessResult.setPreferredSize(new Dimension(d.width+80,d.height));
        
        pBottom.add(guessResultLabel, BorderLayout.EAST);
        pBottom.add(guessResult, BorderLayout.EAST);
        
        
        j.add(pBottom, BorderLayout.SOUTH);
        
        j.setVisible(true);
        j.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    public static void main(String[] args) {
        ClueGUI m = new ClueGUI();
        m.createGUI();
        
    }

}