package com.github.wolrab.boids.window;

import java.awt.*;
import java.awt.event.*;
import java.util.List;
import javax.swing.*;

import com.github.wolrab.boids.components.GraphicsComponent;
import com.github.wolrab.boids.math.Coordinates;

@SuppressWarnings("serial")
public class Window extends JFrame {
	// it's easier to stuff everything into a panel and set it to be the 
	//     window's content pane
	JPanel contentPane;
	// where all the magic happens
	JPanel graphicsSurface;
	
	public Window(int windowWidth, int windowHeight, int window_x, int window_y, List<GraphicsComponent> gc) {
		// set title
		super("Boids");
		// make sure Coordinates has the right dimensions (static variables amirite?)
		if (Coordinates.getWindowWidth() != windowWidth || Coordinates.getWindowHeight() != windowHeight)
			Coordinates.setWindowDimensions(windowWidth, windowHeight);
		
		contentPane = new JPanel();
		graphicsSurface = new DrawSurface(windowWidth, windowHeight, gc);
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// I should manage events better lel
		this.setResizable(false);
		
		// fill contentPane and add it to window
		contentPane.add(graphicsSurface);
		this.setContentPane(contentPane);
		
		// set location and finish initialization
		this.setLocation(window_x, window_y); 
		this.pack();
		this.setVisible(true);
	}
	
	public Window(int windowWidth, int windowHeight, List<GraphicsComponent> gc) {
		this(windowWidth, windowHeight, 0, 0, gc);
	}
	
	public Window(List<GraphicsComponent> gc) {
		this(Coordinates.getWindowWidth(), Coordinates.getWindowHeight(), gc);
	}
}
