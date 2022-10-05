package p1;

import java.awt.GridLayout;


import javax.swing.*;

//Creates the larger main panel by adding three ChangeMakerPanel objects to it
public class ChangeMakerPanelMain extends JPanel{

	private ChangeMakerPanel p1;
	private ChangeMakerPanel p2;
	private ChangeMakerPanel p3;

	public ChangeMakerPanelMain(JMenuItem quitItem, JCheckBoxMenuItem quartersItem, 
			JCheckBoxMenuItem dimesItem, JCheckBoxMenuItem nickelsItem, JCheckBoxMenuItem penniesItem) {

		GridLayout layout = new GridLayout(1, 3);
		layout.setHgap(10); 
		setLayout(layout);


		p1 = new ChangeMakerPanel(quitItem, quartersItem, 
				dimesItem, nickelsItem, penniesItem);
		p2 = new ChangeMakerPanel(quitItem, quartersItem, 
				dimesItem, nickelsItem, penniesItem);
		p3 = new ChangeMakerPanel(quitItem, quartersItem, 
				dimesItem, nickelsItem, penniesItem);

		add(p1);
		add(p2);
		add(p3);
	}
}
