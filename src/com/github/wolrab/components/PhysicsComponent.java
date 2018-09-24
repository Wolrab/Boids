package com.github.wolrab.components;

import com.github.wolrab.math.Vector2D;

public abstract class PhysicsComponent {
	protected double maxVelocity, maxAcceleration;
	
	public PhysicsComponent(double maxVelocity, double maxAcceleration) {
		this.maxVelocity = maxVelocity;
		this.maxAcceleration = maxAcceleration;
	}
	
	public abstract void update(GameObject go, double dt);
	
	public Vector2D squashAcceleration(Vector2D acceleration) {
		if (acceleration.getMagnitude() > maxAcceleration) {
			return new Vector2D(acceleration.getTheta(), maxAcceleration);
		}
		else {
			return acceleration;
		}
	}
	
	public Vector2D squashVelocity(Vector2D velocity) {
		if (velocity.getMagnitude() > maxVelocity) {
			return new Vector2D(velocity.getTheta(), maxVelocity);
		}
		else {
			return velocity;
		}
	}
}
