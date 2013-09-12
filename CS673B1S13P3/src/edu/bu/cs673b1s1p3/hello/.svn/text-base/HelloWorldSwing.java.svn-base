package edu.bu.cs673b1s1p3.hello;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * This is a very simple Java Swing Hello World application. It Displays
 * "Welcome CS673B1S1P3 Team!" in a Frame. Kill the window by clicking the
 * window close button and the application exits.
 * 
 * @author ton
 * 
 */
public class HelloWorldSwing extends Frame {

	private static final long serialVersionUID = 8773173038123937983L;
	public static final String WELCOME_TEAM = "Welcome CS673B1S1P3 Team";

	/**
	 * This is the main entry point of the application.
	 * 
	 * @param args program arguments passed from the command line
	 */
	public static void main(String[] args) {
		HelloWorldSwing window = new HelloWorldSwing();
		window.setSize(new Dimension(300, 200));
		window.setTitle(WELCOME_TEAM);
		window.setVisible(true);
	}

	/**
	 * Constructor.
	 * 
	 */
	public HelloWorldSwing() {
		addWindowListener(new MyWindowAdapter());
	}
	
	/**
	 * Paint the window on the screen.
	 * @see java.awt.Window#paint(java.awt.Graphics)
	 */
	public void paint(Graphics g) {
		g.drawString(WELCOME_TEAM, 50, 40);
	}

	/**
	 * Inner class that handles the window closing event.
	 *
	 */
	class MyWindowAdapter extends WindowAdapter {
		public void windowClosing(WindowEvent we) {
			System.exit(0);
		}

	}
}
