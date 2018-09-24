package com.github.wolrab.components;

import java.awt.Graphics;

public abstract class GraphicsComponent {
	public abstract void update(GameObject go);
	public abstract void paint(Graphics g);
}
