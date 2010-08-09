package voteVis;

import processing.core.*;

public class CompositeState extends TransitionState {
	private PhotoState photo_state_;
	private TextState text_state_;
	private PGraphics composite_graphics_;

	CompositeState(VoteVisApp p_, PhotoState photo_state_, TextState text_state_) {
		super(p_, photo_state_.get_image().width);
		
		this.photo_state_ = photo_state_;
		this.text_state_ = text_state_;

		composite_graphics_ = p_.createGraphics(state_dim_, state_dim_, PApplet.JAVA2D);
		composite_graphics_.smooth();
		composite_graphics_.image(photo_state_.get_image(), 0, 0);
		composite_graphics_.image(text_state_.get_image(), 0, 0);
		composite_graphics_.endDraw();
	}

	PImage get_image() {
		return composite_graphics_;
	}
}
