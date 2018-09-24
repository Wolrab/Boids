package com.github.wolrab.main;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class WindowState implements WindowListener{
	private boolean windowClosing = false;

	@Override
	public void windowActivated(WindowEvent e) { }

	@Override
	public void windowClosed(WindowEvent e) { }

	@Override
	public void windowClosing(WindowEvent e) { 
		windowClosing = true;
	}

	@Override
	public void windowDeactivated(WindowEvent e) { }

	@Override
	public void windowDeiconified(WindowEvent e) { }

	@Override
	public void windowIconified(WindowEvent e) { }

	@Override
	public void windowOpened(WindowEvent e) { }
	
	public boolean isWindowClosing() {
		return windowClosing;
	}
}
