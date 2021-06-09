package com.github.wolrab.boids.math;

/**
 * TRIGONOMETRY! We are sanitizing everything so the theta can only be between 0 and 2PI
 * @author Connor
 */
public class Vector2D {
	Point2D coordinates;
	double theta, magnitude;
	
	public Vector2D(Point2D coordinates) {
		this.coordinates = coordinates;
		coordinatesChanged();
	}
	
	public Vector2D(double theta, double magnitude) {
		this.theta = theta;
		sanitizeTheta();
		this.magnitude = magnitude;
		thetaOrMagnitudeChanged();
	}
	
	private void coordinatesChanged() {
		theta = Math.atan2(coordinates.y, coordinates.x);
		sanitizeTheta();
		magnitude = Math.sqrt(Math.pow(coordinates.x, 2) + Math.pow(coordinates.y, 2));
	}
	
	private void thetaOrMagnitudeChanged() {
		coordinates = new Point2D(Math.cos(theta) * magnitude, Math.sin(theta) * magnitude);
	}
	
	public Vector2D add(Vector2D v) {
		return new Vector2D(new Point2D(coordinates.x + v.getCoordinates().x, coordinates.y + v.getCoordinates().y));
	}
	
	public Vector2D subtract(Vector2D v) {
		return new Vector2D(new Point2D(coordinates.x - v.getCoordinates().x, coordinates.y - v.getCoordinates().y));
	}
	
	public Vector2D scale(double scalar) {
		return new Vector2D(new Point2D(coordinates.x * scalar, coordinates.y * scalar));
	}
	
	public double dot(Vector2D v) {
		return coordinates.x * v.getCoordinates().x + coordinates.y * v.getCoordinates().y;
	}
	
	public Vector2D normalize() {
		return new Vector2D(theta, 1.0);
	}
	
	public void setCoordinates(Point2D coordinates) {
		this.coordinates = coordinates;
		coordinatesChanged();
	}
	
	public Point2D getCoordinates() {
		return new Point2D(coordinates.x, coordinates.y);
	}
	
	public void sanitizeTheta() {
		if (theta > 2 * Math.PI)
			theta -= 2 * Math.PI;
		else if (theta <= -0.0)
			theta += 2 * Math.PI;
	}
	
	public void addTheta(double theta) {
		this.theta += theta;
		sanitizeTheta();
		thetaOrMagnitudeChanged();
	}

	public double getTheta() {
		return theta;
	}

	public double getMagnitude() {
		return magnitude;
	}
}
