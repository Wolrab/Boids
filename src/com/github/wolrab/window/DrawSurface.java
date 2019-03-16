package com.github.wolrab.window;

import java.util.List;
import java.awt.*;
import javax.swing.*;

import com.github.wolrab.components.GraphicsComponent;

@SuppressWarnings("serial")
public class DrawSurface extends JPanel {
	List<GraphicsComponent> gc;
	int windowWidth, windowHeight;
	
	public DrawSurface(int windowWidth, int windowHeight, List<GraphicsComponent> gc) {
		super();
		this.windowWidth = windowWidth;
		this.windowHeight = windowHeight;
		this.gc = gc;
		this.setPreferredSize(new Dimension(windowWidth, windowHeight));
		this.setDoubleBuffered(true);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		for (int i = 0; i < gc.size(); i++) {
			gc.get(i).paint(g);
		}
	}
}
