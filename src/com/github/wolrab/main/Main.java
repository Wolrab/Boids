package com.github.wolrab.main;

import com.github.wolrab.swarm.BoidManager;
import com.github.wolrab.window.Window;

public class Main {
	private static class Timer implements Runnable {
		@Override
		public void run() {
			long start = System.currentTimeMillis();
			while(System.currentTimeMillis() - start < Config.MILLIS_PER_FRAME) { }
		}
	}
	
	public static void main(String args[]) {
		// TODO: Initialize the BOIDS
		BoidManager boidManager = new BoidManager(200);
		Window window = new Window(boidManager.getBoidsGraphicsComponents());
		WindowState windowState = new WindowState();
		
		// Game Loop
		while (!windowState.isWindowClosing()) {
			Thread timer = new Thread(new Timer());
			timer.start();
			boidManager.update(Config.SECONDS_PER_FRAME);
			window.repaint();
			try {
				timer.join();
			} catch (InterruptedException e) { }
		}
		// I don't think anything below here gets run...
	}
}
