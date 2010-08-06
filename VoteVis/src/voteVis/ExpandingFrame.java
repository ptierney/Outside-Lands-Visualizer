package voteVis;

import processing.core.*;

// Expanding Frame expands to multiples of UNIT_DIM
// It will contract to one unit wide
// It can only be UNIT_DIM tall

public abstract class ExpandingFrame extends BoxFrame {
	protected int max_unit_width_; // how many multiples of UNIT_DIM
	protected float counter_; // ranges from 0.0 (full retraction) to 1.0 (full extension)
	
	private static float EXPAND_SPEED = 0.1f;
	
	private boolean expanding_;
	private boolean contracting_;
	
	public ExpandingFrame(int max_unit_width_) {
		super();
		
		this.max_unit_width_ = max_unit_width_;
		counter_ = 0.0f; // start at retracted
	}
	
	protected void update() {
		if (expanding_) {
			set_counter(counter_ + EXPAND_SPEED);
			if (counter_ == 1.0f)
				expanding_ = false;
		} else if (contracting_) {
			set_counter(counter_ - EXPAND_SPEED);
			if (counter_ == 0.0f)
				contracting_ = false;
		}
	}
	
	public void expand_fully() {
		if (counter_ < 1.0f)
			expanding_ = true;
	}
	
	public void retract_fully() {
		if (counter_ > 0.0f)
			contracting_ = true;
	}
	
	public void set_counter(float new_counter) {
		if (new_counter > 1.0)
			counter_ = 1.0f;
		else if (new_counter < 0.0)
			counter_ = 0.0f;
		else
			counter_ = new_counter;
	}
	
	public float counter() {
		return counter_;
	}
}
