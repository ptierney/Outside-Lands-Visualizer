package voteVis;

import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

public class NoFrame extends BoxFrame {
	public void draw() {
		
	}
	
	public PImage get_image() {
		return null;
	}
	
	public PVector get_image_center() {
		return new PVector(0.0f, 0.0f);
	}
	
	// used for pane placement
	public int get_pane_x() {
		return 0;
	}
	
	public int get_pane_y() {
		return 0;
	}
	
	// used for collisions
	public int get_width() {
		return Settings.UNIT_DIM;
	}
	
	public int get_height() {
		return Settings.UNIT_DIM;
	}

}
