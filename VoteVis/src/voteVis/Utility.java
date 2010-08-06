package voteVis;

import processing.core.*;

public class Utility {
	private VoteVisApp p_;

	public Utility(VoteVisApp p_) {
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
}
