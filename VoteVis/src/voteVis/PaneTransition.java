package voteVis;

import processing.core.*;

public abstract class PaneTransition {
	protected PImage start_image_;
	protected PImage end_image_;
	protected boolean finished_;
	protected VoteVisApp p_;
	protected int counter_; // goes from 0 to unit_dim (for # of pixels transitioned)
	protected int trans_unit_dim_;
	
	public PaneTransition(VoteVisApp p_) {
		this.p_ = p_;
		finished_ = false;
	}

	public void load_transition(TransitionState start, TransitionState end) {
		start_image_ = start.get_image();
		end_image_ = end.get_image();
	}
	
	public void advance_random() {
		counter_ = (int) p_.random(trans_unit_dim_);
	}

	abstract public void perform_transition();

	abstract public PImage get_current_image();

	abstract public void draw();

	public boolean finished() {
		return finished_;
	}
}
