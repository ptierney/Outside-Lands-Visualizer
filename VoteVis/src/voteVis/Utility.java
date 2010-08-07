package voteVis;

import processing.core.*;

public class Utility {
	private static Utility instance_;
	private VoteVisApp p_;

	public Utility(VoteVisApp p_) {
		instance_ = this;
		this.p_ = p_;
	}
	
	public PImage scale_to_vote_pane_size(PImage pimage) {
		return scale_to_pane_size(pimage, Settings.VOTE_PANE_DIM);
	}

	public PImage scale_to_pane_size(PImage pimage, int side_dim) {
		PGraphics graphics = p_.createGraphics(side_dim, side_dim, PApplet.JAVA2D);
		
		float new_width = 0.0f;
		float new_height = 0.0f;
		
		if (pimage.width > pimage.height) {
			new_width = side_dim;
			new_height = pimage.height / pimage.width * side_dim;
		} else {
			new_height = side_dim;
			new_width = pimage.width / pimage.height * side_dim;
		}
		
		graphics.beginDraw();
		graphics.background(0);
		graphics.image(pimage, 0, 0, new_width, new_height);
		graphics.endDraw();
		
		return graphics;
	}
	
	// takes a unit width of a box (its side length) and an index, and returns the starting x position
	// of the box
	public static int get_aligned_position(int unit_width, int index) {
		return (unit_width + Settings.BOX_GAP) * index + Settings.BOX_GAP + unit_width / 2;
	}
	
	public static Utility instance() {
		return instance_;
	}
}
