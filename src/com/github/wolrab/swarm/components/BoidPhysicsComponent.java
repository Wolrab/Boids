package com.github.wolrab.swarm.components;

import com.github.wolrab.components.GameObject;
import com.github.wolrab.components.PhysicsComponent;
import com.github.wolrab.main.Config;
import com.github.wolrab.math.Coordinates;
import com.github.wolrab.math.Point2D;
import com.github.wolrab.math.Vector2D;

public class BoidPhysicsComponent extends PhysicsComponent {
	public BoidPhysicsComponent(double maxVelocity, double maxAcceleration) {
		super(maxVelocity, maxAcceleration);
	}
	
	public BoidPhysicsComponent() {
		this(Config.BOID_MAX_VELOCITY, Config.BOID_MAX_ACCELERATION);
	}

	private static final Vector2D TOP_SURFACE_NORMAL = new Vector2D(new Point2D(0.0, -1.0));
	private static final Vector2D BOTTOM_SURFACE_NORMAL = new Vector2D(new Point2D(0.0, 1.0));
	private static final Vector2D LEFT_SURFACE_NORMAL = new Vector2D(new Point2D(1.0, 0.0));
	private static final Vector2D RIGHT_SURFACE_NORMAL = new Vector2D(new Point2D(-1.0, 0.0));
	
	@Override
	public void update(GameObject go, double dt) {
		go.setAcceleration(squashAcceleration(go.getAcceleration()));
		Vector2D dv = go.getAcceleration().scale(dt);
		// Gross, but necessary when you don't have real physics
		go.setVelocity(squashVelocity(go.getVelocity().add(dv)));
		
		Vector2D dp = go.getVelocity().scale(dt);
		go.setPosition(go.getPosition().add(dp));
		
		collision(go);
	}
	
	// Makes the Boids "bounce" off the walls
	private void collision(GameObject go) {
		Vector2D position = go.getPosition();
		// Left wall
		if (position.getCoordinates().x < Coordinates.getXMin()) {
			go.setPosition(new Vector2D(new Point2D(Coordinates.getXMin(), position.getCoordinates().y)));
			go.setVelocity(reflect(go.getVelocity(), LEFT_SURFACE_NORMAL));
		}
		// Right wall
		else if (position.getCoordinates().x > Coordinates.getXMax()) {
			go.setPosition(new Vector2D(new Point2D(Coordinates.getXMax(), position.getCoordinates().y)));
			go.setVelocity(reflect(go.getVelocity(), RIGHT_SURFACE_NORMAL));
		}
		// Bottom wall
		if (position.getCoordinates().y < Coordinates.getYMin()) {
			go.setPosition(new Vector2D(new Point2D(position.getCoordinates().x, Coordinates.getYMin())));
			go.setVelocity(reflect(go.getVelocity(), BOTTOM_SURFACE_NORMAL));
		}
		// Top wall
		else if (position.getCoordinates().y > Coordinates.getYMax()) {
			go.setPosition(new Vector2D(new Point2D(position.getCoordinates().x, Coordinates.getYMax())));
			go.setVelocity(reflect(go.getVelocity(), TOP_SURFACE_NORMAL));
		}
	}
	
	private Vector2D reflect(Vector2D incident, Vector2D normal) {
		return incident.add(normal.scale(-2 * incident.dot(normal)));
	}
}
