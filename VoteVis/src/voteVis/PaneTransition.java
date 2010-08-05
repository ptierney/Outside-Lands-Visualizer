package voteVis;

import processing.core.*;

public abstract class PaneTransition {
	protected PImage start_image_;
	protected PImage end_image_;
	protected boolean finished_;
	protected VoteVisApp p_;

	public PaneTransition(VoteVisApp p_) {
		this.p_ = p_;
		finished_ = false;
	}

	public void load_transition(TransitionState start, TransitionState end) {
		start_image_ = start.get_image();
		end_image_ = end.get_image();
	}

	abstract public void perform_transition();

	abstract public PImage get_current_image();

	abstract public void draw();

	public boolean finished() {
		return finished_;
	}
}
