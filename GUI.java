package p1;

import javax.swing.*;

public class GUI {
	public static void main(String arg[]){

		JMenu fileMenu;
		JMenuItem quitItem;
		JCheckBoxMenuItem quartersItem;
		JCheckBoxMenuItem dimesItem;
		JCheckBoxMenuItem nickelsItem;
		JCheckBoxMenuItem penniesItem;
		JMenuBar menus;

		fileMenu = new JMenu("File");
		quitItem = new JMenuItem ("Quit!");
		quartersItem = new JCheckBoxMenuItem ("Suspend Quarters");
		dimesItem = new JCheckBoxMenuItem ("Suspend Dimes");
		nickelsItem = new JCheckBoxMenuItem ("Suspend Nickels");
		penniesItem = new JCheckBoxMenuItem ("Suspend Pennies");

		fileMenu.add(quartersItem);
		fileMenu.add(dimesItem);
		fileMenu.add(nickelsItem);
		fileMenu.add(penniesItem);
		fileMenu.add(quitItem);
		menus = new JMenuBar();

		menus.add(fileMenu);

		JFrame gui = new JFrame("Money Maker 1.0");
		gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//ChangeMakerPanel panel = new ChangeMakerPanel( );

		ChangeMakerPanelMain panel = new ChangeMakerPanelMain(quitItem, quartersItem, 
				dimesItem, nickelsItem, penniesItem);
		gui.getContentPane().add(panel);

		gui.setSize(1100,450);
		gui.setJMenuBar(menus);
		gui.setVisible(true);
	}

}


