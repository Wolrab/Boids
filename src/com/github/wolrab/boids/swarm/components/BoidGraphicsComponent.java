package com.github.wolrab.boids.swarm.components;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;

import com.github.wolrab.boids.components.GameObject;
import com.github.wolrab.boids.components.GraphicsComponent;
import com.github.wolrab.boids.main.Config;
import com.github.wolrab.boids.math.Coordinates;
import com.github.wolrab.boids.math.Point2D;
import com.github.wolrab.boids.math.PointWindow;
import com.github.wolrab.boids.math.Vector2D;

public class BoidGraphicsComponent extends GraphicsComponent {
	private PointWindow start, tip, width1, width2;
	private Polygon boidShape;
	private double boidLengthCoords, boidWidthCoords;
	
	public BoidGraphicsComponent(int boidLength, int boidWidth) {
		boidLengthCoords = boidLength * Coordinates.getCoordsPerPixel();
		boidWidthCoords = boidWidth * Coordinates.getCoordsPerPixel();
	}
	
	public BoidGraphicsComponent() {
		this(Config.BOID_LENGTH, Config.BOID_WIDTH);
	}

	@Override
	public void update(GameObject go) {
		Vector2D start = go.getPosition();
		Vector2D direction = go.getVelocity().normalize().scale(boidLengthCoords);
		Vector2D tip = start.add(direction);
		Vector2D width1 = new Vector2D(new Point2D(-boidWidthCoords/2, 0.0));
		Vector2D width2 = new Vector2D(new Point2D(boidWidthCoords/2, 0.0));
		
		width1.addTheta(direction.getTheta() - (Math.PI/2));
		width2.addTheta(direction.getTheta() - (Math.PI/2));
		width1 = width1.add(start);
		width2 = width2.add(start);
		
		this.start = Coordinates.point2DToPointWindow(start.getCoordinates());
		this.tip = Coordinates.point2DToPointWindow(tip.getCoordinates());
		this.width1 = Coordinates.point2DToPointWindow(width1.getCoordinates());
		this.width2 = Coordinates.point2DToPointWindow(width2.getCoordinates());
		
		boidShape = new Polygon();
		boidShape.addPoint(this.start.x, this.start.y);
		boidShape.addPoint(this.width1.x, this.width1.y);
		boidShape.addPoint(this.tip.x, this.tip.y);
		boidShape.addPoint(this.width2.x, this.width2.y);
		boidShape.addPoint(this.start.x, this.start.y);
	}

	@Override
	public void paint(Graphics g) {
		g.setColor(Color.BLACK);
		g.drawPolygon(boidShape);
	}
}
