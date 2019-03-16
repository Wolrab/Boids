package com.github.wolrab.main;

// A static mess that defines all useful behavior
public final class Config {
	// FPS Calculations
	public static final double FPS = 60.0;
	public static final double SECONDS_PER_FRAME = 1.0 / FPS;
	public static final double MILLIS_PER_FRAME = SECONDS_PER_FRAME * 1000.0;
	
	// Coordinates Defaults
	public static final int DEFAULT_WINDOW_WIDTH = 1200;
	public static final int DEFAULT_WINDOW_HEIGHT = 600;
	public static final double DEFAULT_X_MAX = 20.0;
	public static final double DEFAULT_X_MIN = -20.0;
	public static final double DEFAULT_Y_MAX = 10.0;
	public static final double DEFAULT_Y_MIN = -10.0;
	
	// Physics Defaults
	public static final double BOID_MAX_VELOCITY = 10.0; // Pixels/Second
	public static final double BOID_MAX_ACCELERATION = 100.0; // Pixels/Second^2
	
	// Graphics Defaults
	public static final int BOID_LENGTH = 15; // Length in pixels
	public static final int BOID_WIDTH = 9; // Base width
	
	// Boid Calculation Defaults
	public static final double BOID_NEIGHBORHOOD_RADIUS = 5.0;
	public static final double BOID_REPULSION_CONSTANT = 2.0;
}
