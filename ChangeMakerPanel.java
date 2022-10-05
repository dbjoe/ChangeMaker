package p1;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import javax.swing.*;

//Creates a single ChangeMakerPanel
public class ChangeMakerPanel extends JPanel{

	private JLabel titleLabel;
	private JLabel amountLabel;
	private JLabel balanceLabel;
	private JLabel transferLabel;
	private JLabel quartersLabel;
	private JLabel dimesLabel;
	private JLabel nickelsLabel;
	private JLabel penniesLabel;
	private JLabel qSuspendedLabel;
	private JLabel dSuspendedLabel;
	private JLabel nSuspendedLabel;
	private JLabel pSuspendedLabel;

	private JMenuItem quit;
	private JCheckBoxMenuItem quarters;
	private JCheckBoxMenuItem dimes;
	private JCheckBoxMenuItem nickels;
	private JCheckBoxMenuItem pennies;

	private JButton loadB; //"Load" button
	private JButton takeB; //"Take Out!" button
	private JButton loadFileB; //"Load from File" button
	private JButton saveFileB; //"Save to File" button

	private JTextField textField;

	private ChangeMaker myChangeMaker; 

	//-----------------------------------------------------------------
	//  Constructor: Sets up the main GUI components.
	//-----------------------------------------------------------------
	public ChangeMakerPanel(JMenuItem quitItem, JCheckBoxMenuItem quartersItem, 
			JCheckBoxMenuItem dimesItem, JCheckBoxMenuItem nickelsItem, JCheckBoxMenuItem penniesItem) {

		myChangeMaker = new ChangeMaker(1000); 

		titleLabel = new JLabel("Change Maker 1.0");
		//http://www.fredosaurus.com/notes-java/GUI/components/10labels/12labelfontcolor.html
		titleLabel.setFont(new Font("Serif", Font.BOLD, 35)); 
		titleLabel.setForeground(Color.BLUE); 

		MenuListener mListener = new MenuListener();

		quit = quitItem;
		quit.addActionListener(mListener);
		quarters = quartersItem;
		quarters.addActionListener(mListener);
		dimes = dimesItem;
		dimes.addActionListener(mListener);
		nickels = nickelsItem;
		nickels.addActionListener(mListener);
		pennies = penniesItem;
		pennies.addActionListener(mListener);

		amountLabel = new JLabel("Amount:");
		balanceLabel = new JLabel(formatAmount());
		transferLabel = new JLabel("Transfer Amount");
		quartersLabel = new JLabel("Quarters:");
		dimesLabel = new JLabel("Dimes:");
		nickelsLabel = new JLabel("Nickels:");
		penniesLabel = new JLabel("Pennies:");
		qSuspendedLabel = new JLabel("Suspended");
		dSuspendedLabel = new JLabel("Suspended");
		nSuspendedLabel = new JLabel("Suspended");
		pSuspendedLabel = new JLabel("Suspended");
		qSuspendedLabel.setVisible(false);
		dSuspendedLabel.setVisible(false);
		nSuspendedLabel.setVisible(false);
		pSuspendedLabel.setVisible(false);

		ButtonListener bListener = new ButtonListener();

		loadB = new JButton("Load");
		loadB.addActionListener(bListener);
		takeB = new JButton("Take Out!");
		takeB.addActionListener(bListener);

		loadFileB = new JButton("Load from File");
		loadFileB.addActionListener(bListener);
		saveFileB = new JButton("Save to File");
		saveFileB.addActionListener(bListener);

		textField = new JTextField("0.00");

		this.setBorder(BorderFactory.createLineBorder(Color.BLUE, 3)); 

		JPanel titlePanel = new JPanel();
		GridLayout titleLayout = new GridLayout(1, 2);
		titlePanel.setLayout(titleLayout);
		add(titlePanel);
		titlePanel.add(titleLabel); 

		JPanel interfacePanel = new JPanel();
		GridLayout interfaceLayout = new GridLayout(11, 2);
		interfacePanel.setLayout(interfaceLayout);
		add(interfacePanel);

		interfacePanel.add(amountLabel);
		interfacePanel.add(balanceLabel);
		interfacePanel.add(new JLabel(""));
		interfacePanel.add(new JLabel(""));

		interfacePanel.add(transferLabel);
		interfacePanel.add(textField);
		interfacePanel.add(new JLabel(""));
		interfacePanel.add(new JLabel(""));

		interfacePanel.add(loadB);
		interfacePanel.add(takeB);
		interfacePanel.add(new JLabel(""));
		interfacePanel.add(new JLabel(""));

		interfacePanel.add(quartersLabel);
		interfacePanel.add(qSuspendedLabel);

		interfacePanel.add(dimesLabel);
		interfacePanel.add(dSuspendedLabel);

		interfacePanel.add(nickelsLabel);
		interfacePanel.add(nSuspendedLabel);

		interfacePanel.add(penniesLabel);
		interfacePanel.add(pSuspendedLabel);

		interfacePanel.add(loadFileB);
		interfacePanel.add(saveFileB);

	}	

	private class ButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {

			if (loadB == event.getSource()) {
				try {
					myChangeMaker.loadMachine(Double.parseDouble(textField.getText())); 
				} catch (NumberFormatException e) {
					/* Exception message adapted from:
					 * http://www.java2s.com/example/java/swing/show-exception-message-via-joptionpane.html
					 */
					JOptionPane.showMessageDialog(null, "Error: please enter a number", 
							"NumberFormatException", JOptionPane.ERROR_MESSAGE);
				} catch (IllegalArgumentException e) {
					JOptionPane.showMessageDialog(null, e.getMessage(),
							"IllegalArgumentException", JOptionPane.ERROR_MESSAGE);
				} 
				balanceLabel.setText(formatAmount());
			}

			if (takeB == event.getSource()) {
				try {
					myChangeMaker.takeOut(Double.parseDouble(textField.getText()));
				} catch (NumberFormatException e) {
					JOptionPane.showMessageDialog(null, "Error: please enter a number", 
							"NumberFormatException", JOptionPane.ERROR_MESSAGE);
				} catch (IllegalArgumentException e) {
					JOptionPane.showMessageDialog(null, e.getMessage(),
							"IllegalArgumentException", JOptionPane.ERROR_MESSAGE);
				} 

				balanceLabel.setText(formatAmount());
			}

			if (saveFileB == event.getSource()) {
				//Adapted from JFileChooser JavaDoc
				JFileChooser chooser = new JFileChooser();
				int returnVal = chooser.showSaveDialog(null); 
				if(returnVal == JFileChooser.APPROVE_OPTION) {
					try {
						String file = chooser.getSelectedFile().getAbsolutePath();
						myChangeMaker.save(file);
					} catch (NullPointerException e) {
						
					} catch (IllegalArgumentException e) {
						JOptionPane.showMessageDialog(null, e.getMessage(),
								"Exception", JOptionPane.ERROR_MESSAGE);
					} 
				}
			}

			if (loadFileB == event.getSource()) {
				JFileChooser chooser = new JFileChooser();
				int returnVal = chooser.showOpenDialog(null);
				if(returnVal == JFileChooser.APPROVE_OPTION) {
					try {
						myChangeMaker.load(chooser.getSelectedFile().getAbsolutePath());
					} catch (IllegalArgumentException e) {
						JOptionPane.showMessageDialog(null, e.getMessage(),
								"IllegalArgumentException", JOptionPane.ERROR_MESSAGE);
					}
				}

				balanceLabel.setText(formatAmount());
			}

		}

	}

	private class MenuListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			if (quit == event.getSource()) {
				System.exit(0);
			}
			if (quarters == event.getSource()) {
				qSuspendedLabel.setVisible(!qSuspendedLabel.isVisible());
				ChangeMaker.setQuartersAvail(!ChangeMaker.getQuartersAvail());
			}
			if (dimes == event.getSource()) {
				dSuspendedLabel.setVisible(!dSuspendedLabel.isVisible());
				ChangeMaker.setDimesAvail(!ChangeMaker.getDimesAvail());
			} 
			if (nickels == event.getSource()) {
				nSuspendedLabel.setVisible(!nSuspendedLabel.isVisible());
				ChangeMaker.setNickelsAvail(!ChangeMaker.getNickelsAvail());
			}
			if (pennies == event.getSource()) {
				pSuspendedLabel.setVisible(!pSuspendedLabel.isVisible());
				ChangeMaker.setPenniesAvail(!ChangeMaker.getPenniesAvail());
			}
		}
	}

	private String formatAmount() {
		NumberFormat formatter = NumberFormat.getCurrencyInstance();
		String moneyString = formatter.format(myChangeMaker.getAmount());
		return moneyString;
	}
}
