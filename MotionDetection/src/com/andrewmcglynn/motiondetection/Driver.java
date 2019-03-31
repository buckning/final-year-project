package com.andrewmcglynn.motiondetection;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;

public class Driver {
	DrawingApplication app;
	
	/**
	 * @param args Command Line arguments
	 */
	public static void main(String[] args) {
		new Driver().run();
	}
	
	/**
	 * 
	 */
	public void run(){
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		System.out.println(screenSize.getWidth());
		app = new DrawingApplication(this);
		JFrame frame = new JFrame();
		frame.setSize(screenSize);
		frame.add(app);
		app.init();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setUndecorated(true);
		frame.setVisible(true);

		//call the destroy method to safely exit and disconnect the wii remote
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent event){
				app.destroy();
			}
		});
	}
}
