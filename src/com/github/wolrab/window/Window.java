package com.github.wolrab.window;

import java.awt.*;
import java.awt.event.*;
import java.util.List;
import javax.swing.*;

import com.github.wolrab.components.GraphicsComponent;
import com.github.wolrab.math.Coordinates;

@SuppressWarnings("serial")
public class Window extends JFrame {
	JPanel contentPane;
	JPanel graphicsSurface;
	
	public Window(int windowWidth, int windowHeight, int window_x, int window_y, List<GraphicsComponent> gc) {
		super("Boids");
		if (Coordinates.getWindowWidth() != windowWidth || Coordinates.getWindowHeight() != windowHeight) {
			Coordinates.setWindowDimensions(windowWidth, windowHeight);
		}
		
		contentPane = new JPanel();
		graphicsSurface = new DrawSurface(windowWidth, windowHeight, gc);
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		
		contentPane.add(graphicsSurface);

		this.setContentPane(contentPane);
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
