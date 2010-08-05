package voteVis;

import processing.core.*;

public abstract class TransitionState {
	protected VoteVisApp p_;
	protected int state_dim_;
	
	TransitionState(VoteVisApp p_, int state_dim_) {
		this.p_ = p_;
		this.state_dim_ = state_dim_;
	}

	abstract PImage get_image();
}
