package voteVis;

import processing.core.*;

public class PhotoState extends TransitionState {
	PImage state_image_;

	public PhotoState(VoteVisApp p_, PImage photo) {
		super(p_, photo.width);
		// Assert correct image dims
		if (photo.width != photo.height) {
			PApplet.println("PhotoState CTOR assert failed. Exiting.");
			p_.exit();
		}

		state_image_ = photo;
	}

	PImage get_image() {
		return state_image_;
	}
}
