package com.github.wolrab.boids.math;

import com.github.wolrab.boids.main.Config;

public final class Coordinates {
	private static int windowWidth = Config.DEFAULT_WINDOW_WIDTH, windowHeight = Config.DEFAULT_WINDOW_HEIGHT;
	private static double xMax = Config.DEFAULT_X_MAX, xMin = Config.DEFAULT_X_MIN, 
						  yMax = Config.DEFAULT_Y_MAX, yMin = Config.DEFAULT_Y_MIN;
	private static double xSpan = xMax - xMin, ySpan = yMax - yMin;
	private static double coordsPerPixel = xSpan / windowWidth;
	private static double pixelsPerCoord = 1.0 / coordsPerPixel;
	
	/**
	 * Pre-zooming grid-fixin-technology
	 */
	private static void fitAspectRatio() {
		coordsPerPixel = xSpan / windowWidth;
		pixelsPerCoord = 1.0 / coordsPerPixel;
		double ySpanDiff = windowHeight * coordsPerPixel - ySpan;
		yMax += ySpanDiff / 2;
		yMin += ySpanDiff / 2;
	}
	
	public static void setWindowDimensions(int windowWidth, int windowHeight) {
		Coordinates.windowWidth = windowWidth;
		Coordinates.windowHeight = windowHeight;
		Coordinates.fitAspectRatio();
	}
	
	public static int getWindowWidth() {
		return windowWidth;
	}
	
	public static int getWindowHeight() {
		return windowHeight;
	}
	
	public static double getXSpan() {
		return xSpan;
	}
	
	public static double getYSpan() {
		return ySpan;
	}
	
	public static double getXMax() {
		return xMax;
	}
	
	public static double getXMin() {
		return xMin;
	}
	
	public static double getYMax() {
		return yMax;
	}
	
	public static double getYMin() {
		return yMin;
	}
	
	public static double getPixelsPerCoord() {
		return pixelsPerCoord;
	}
	
	public static double getCoordsPerPixel() {
		return coordsPerPixel;
	}
	
	public static PointWindow point2DToPointWindow(Point2D p) {
		return new PointWindow((int) Math.round((p.x - xMin) * pixelsPerCoord), 
							   (int) Math.round(Math.abs((p.y - yMin) - ySpan) * pixelsPerCoord));
	}
}
