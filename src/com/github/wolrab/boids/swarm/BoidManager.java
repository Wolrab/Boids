package com.github.wolrab.boids.swarm;

import java.util.ArrayList;
import java.util.List;

import com.github.wolrab.boids.components.*;
import com.github.wolrab.boids.main.Config;
import com.github.wolrab.boids.math.Point2D;
import com.github.wolrab.boids.math.Vector2D;
import com.github.wolrab.boids.swarm.components.*;

public class BoidManager {
	private List<Boid> boids;
	private List<Integer> neighborhoodCount;
	private List<GraphicsComponent> boidsGraphicsComponents;
	private List<Vector2D> cumulativeHeading, avgPosition;
	private List<Vector2D> cumulativeAcceleration;
	private double neighborhoodRadius, repulsionConstant;
	
	public BoidManager(int boidNum, double neighborhoodRadius, double repulsionConstant) {
		boids = new ArrayList<Boid>();
		neighborhoodCount = new ArrayList<Integer>();
		boidsGraphicsComponents = new ArrayList<GraphicsComponent>();
		cumulativeHeading = new ArrayList<Vector2D>();
		avgPosition = new ArrayList<Vector2D>();
		cumulativeAcceleration = new ArrayList<Vector2D>();
		
		this.neighborhoodRadius = neighborhoodRadius;
		this.repulsionConstant = repulsionConstant;
		
		for (int i = 0; i < boidNum; i++) {
			boidsGraphicsComponents.add((GraphicsComponent) new BoidGraphicsComponent()); // Initialize boidGraphicsComponent with all the defaults
			neighborhoodCount.add(null); // Back when I didn't understand ArrayLists, oof
			cumulativeHeading.add(null);
			avgPosition.add(null);
			cumulativeAcceleration.add(null);
			boids.add(new Boid((PhysicsComponent) new BoidPhysicsComponent(), // Initialize boids at random position with random velocity
							   boidsGraphicsComponents.get(i)));
		}
	}
	
	public BoidManager(int boidNum) {
		this(boidNum, Config.BOID_NEIGHBORHOOD_RADIUS, Config.BOID_REPULSION_CONSTANT);
	}
	
	// The heart of the algo
	public void update(double dt) {
		resetLists();
		for (int i = 0; i < boids.size(); i++) {
			for (int j = i + 1; j < boids.size(); j++) {
				Vector2D distance = getDistance(boids.get(i).getPosition(), boids.get(j).getPosition());
				if (distance.getMagnitude() <= neighborhoodRadius) {
					neighborhoodCount.set(i, neighborhoodCount.get(i) + 1);
					neighborhoodCount.set(j, neighborhoodCount.get(j) + 1);
					
					Vector2D repulsionAcceleration = repulsionAcceleration(distance);
					cumulativeAcceleration.set(i, cumulativeAcceleration.get(i).add(repulsionAcceleration));
					cumulativeAcceleration.set(j, cumulativeAcceleration.get(j).add(repulsionAcceleration.scale(-1.0)));

					avgPosition.set(i, cumulativeHeading.get(i).add(boids.get(j).getPosition()));
					avgPosition.set(j, cumulativeHeading.get(j).add(boids.get(i).getPosition()));
					
					cumulativeHeading.set(i, cumulativeHeading.get(i).add(boids.get(j).getVelocity()));
					cumulativeHeading.set(j, cumulativeHeading.get(j).add(boids.get(i).getVelocity()));					
				}
			}
			computeAverage(i);
			
			cumulativeAcceleration.set(i, cumulativeAcceleration.get(i).add(cohesionAcceleration(boids.get(i), avgPosition.get(i))));
			cumulativeAcceleration.set(i, cumulativeAcceleration.get(i).add(headingAcceleration(boids.get(i), cumulativeAcceleration.get(i))));
			
			boids.get(i).setAcceleration(cumulativeAcceleration.get(i)); // Acceleration is squashed in physics calculations
		}
		
		for (int i = 0; i < boids.size(); i++) {
			boids.get(i).update(dt);
		}
	}
	
	// There MUST be a better way (clone existing lists from memory?)
	private void resetLists() {
		for (int i = 0; i < boids.size(); i++) {
			neighborhoodCount.set(i, 0);
			cumulativeHeading.set(i, new Vector2D(new Point2D(0.0, 0.0)));
			avgPosition.set(i, new Vector2D(new Point2D(0.0, 0.0)));
			cumulativeAcceleration.set(i, new Vector2D(new Point2D(0.0, 0.0)));
		}
	}
	
	public Vector2D getDistance(Vector2D position1, Vector2D position2) {
		return position2.subtract(position1);
	}
	
	private void computeAverage(int boid) {
		if (neighborhoodCount.get(boid) != 0) {
			avgPosition.get(boid).setCoordinates(new Point2D(avgPosition.get(boid).getCoordinates().x / (double) neighborhoodCount.get(boid),
													  avgPosition.get(boid).getCoordinates().y / (double) neighborhoodCount.get(boid)));
		}
	}
	
	// To have things scale right, I'm making the maximum the neighborhoodRadius because that scales much better with the values
	// the other accelerations will give.
	public Vector2D headingAcceleration(Boid b1, Vector2D cumulativeHeading) {
		Vector2D acceleration = b1.getVelocity().normalize(); // Keep original heading
		// Rotate both vectors clockwise until b1 is at zero
		double diff = -acceleration.getTheta();
		double offAngle = cumulativeHeading.getTheta() + diff;
		
		// Constrain offAngle to [PI, -PI]
		if (offAngle > Math.PI) {
			offAngle -= 2.0 * Math.PI;
		}
		else if (offAngle < -Math.PI) {
			offAngle += 2.0 * Math.PI;
		}
		
		// Point acceleration in the right direction
		if (offAngle >= 0.0) {
			acceleration.addTheta(Math.PI / 2.0);
		}
		else {
			acceleration.addTheta(-Math.PI / 2.0);
		}
		
		double scaling = Math.abs(offAngle / Math.PI) * neighborhoodRadius;
		return acceleration.scale(scaling);
		
	}
	
	public Vector2D repulsionAcceleration(Vector2D distance) {
		return distance.scale(-Math.pow(repulsionConstant, 2.0) / Math.pow(distance.getMagnitude(), 2.0));
	}
	
	public Vector2D cohesionAcceleration(Boid b1, Vector2D avgPosition) {
		return getDistance(b1.getPosition(), avgPosition);
	}
	
	public List<GraphicsComponent> getBoidsGraphicsComponents() {
		return boidsGraphicsComponents;
	}
}
