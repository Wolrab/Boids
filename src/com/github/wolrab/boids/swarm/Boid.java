package com.github.wolrab.boids.swarm;

import java.util.Random;

import com.github.wolrab.boids.components.*;
import com.github.wolrab.boids.main.Config;
import com.github.wolrab.boids.math.*;

public class Boid extends GameObject {
	Random rand;
	
	public Boid(PhysicsComponent pc, GraphicsComponent gc, double maxVelocity,  
				Vector2D position, Vector2D velocity) {
		super(pc, gc);
		rand = new Random();
		if (position == null) {
			position = new Vector2D(new Point2D(rand.nextDouble() * Coordinates.getXSpan() + Coordinates.getXMin(), 
												rand.nextDouble() * Coordinates.getYSpan() + Coordinates.getYMin()));
		}
		if (velocity == null) {
			velocity = new Vector2D(rand.nextDouble() * 2 * Math.PI, maxVelocity);
		}
		
		this.position = position;
		this.velocity = velocity;
		acceleration = new Vector2D(new Point2D(0.0, 0.0));
	}
	
	public Boid(PhysicsComponent pc, GraphicsComponent gc, double maxVelocity) {
		this(pc, gc, maxVelocity, null, null);
	}
	
	public Boid(PhysicsComponent pc, GraphicsComponent gc) {
		this(pc, gc, Config.BOID_MAX_VELOCITY);
	}
}
