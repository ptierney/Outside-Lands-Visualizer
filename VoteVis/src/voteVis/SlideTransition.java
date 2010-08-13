package voteVis;

import processing.core.*;

public abstract class SlideTransition extends PaneTransition {
	PImage transition_buffer_;
	boolean normal_direction_;

	public SlideTransition(VoteVisApp p_) {
		super(p_);
		normal_direction_ = true;
		this.slide_speed_ = 1;
	}

	@Override
	public void load_transition(TransitionState start, TransitionState end) {
		super.load_transition(start, end);

		trans_unit_dim_ = start_image_.width;

		transition_buffer_ = start_image_;
	}

	@Override
	public void perform_transition() {
		if (counter_ > trans_unit_dim_) {
			force_end();
			finished_ = true;
			return;
		}

		clear_buffer();
		copy_start_into_buffer(counter_);
		copy_end_into_buffer(counter_);
		counter_ += slide_speed_;
	}
	
	private void force_end() {
		transition_buffer_ = end_image_;
	}

	void clear_buffer() {
		transition_buffer_ = p_.createImage(trans_unit_dim_, trans_unit_dim_, PApplet.ARGB);
	}
	
	@Override
	public PImage get_current_image() {
		return transition_buffer_;
	}

	@Override
	public void draw() {
		p_.image(transition_buffer_, -trans_unit_dim_ / 2, -trans_unit_dim_ / 2);
	}
	


	abstract void copy_start_into_buffer(int c);
	abstract void copy_end_into_buffer(int c);
}
