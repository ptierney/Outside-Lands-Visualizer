package voteVis;

import processing.core.*;

public abstract class BoxFrame {
	BoxFrame() {
	}
	
	public abstract void draw();
	public abstract PImage get_image();
	
	public PVector get_image_center() {
		return new PVector(0.0f, 0.0f);
	}
	
	// used for pane placement
	public abstract int get_pane_x();
	public abstract int get_pane_y();
	
	// used for collisions
	public abstract int get_width();
	public abstract int get_height();
}
