package com.github.wolrab.components;

import com.github.wolrab.math.Point2D;
import com.github.wolrab.math.Vector2D;

public abstract class GameObject {
	protected PhysicsComponent pc;
	protected GraphicsComponent gc;
	protected Vector2D position, velocity, acceleration;
	
	public GameObject(PhysicsComponent pc, GraphicsComponent gc) {
		this.pc = pc;
		this.gc = gc;
	}
	
	public void update(double dt) {
		pc.update(this, dt);
		gc.update(this);
	}
	
	public Vector2D getPosition() {
		return position;
	}
	
	public Vector2D getVelocity() {
		return velocity;
	}
	
	public Vector2D getAcceleration() {
		return new Vector2D(new Point2D(acceleration.getCoordinates().x, acceleration.getCoordinates().y));
	}
	
	public void setPosition(Vector2D position) {
		this.position = position;
	}
	
	public void setVelocity(Vector2D velocity) {
		this.velocity = velocity;
	}
	
	public void setAcceleration(Vector2D acceleration) {
		this.acceleration = acceleration;
	}
 }
