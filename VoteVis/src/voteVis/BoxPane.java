package voteVis;

import java.util.ArrayList;
import processing.core.*;

public class BoxPane {
	private VoteVisApp p_;
	private ArrayList<TransitionState> transition_states_;
	private PaneTransition pane_transition_;
	private int transition_counter_;
	private static int ADVANCE_DELAY = 2000; // in millis
	private int delay_counter_;
	private boolean delaying_;
	private boolean advancing_;
	private int slide_speed_ = 1;

	BoxPane(VoteVisApp p_) {
		this.p_ = p_;
		transition_states_ = new ArrayList<TransitionState>();
		transition_counter_ = 0;
		advancing_ = true;
		delaying_ = false;
	}
	
	public PaneTransition pane_transition() {
		return pane_transition_;
	}
	
	public void set_slide_speed(int s) {
		slide_speed_ = s;
	}
	
	public void advance_random() {
		//delay_counter_ = (int) p_.random(advance_delay_);
	}
	
	public void add_transition_state(TransitionState state) {
		transition_states_.add(state);
	}

	public void load_transition() {
		TransitionState start = transition_states_.get(transition_counter_);

		if (transition_counter_ + 1 >= transition_states_.size())
			transition_counter_ = 0;
		else
			++transition_counter_;

		TransitionState end = transition_states_.get(transition_counter_);

		if ((int) (p_.random(2)) == 0)
			pane_transition_ = new HorizontalSlideTransition(p_);
		else
			pane_transition_ = new VerticalSlideTransition(p_);

		pane_transition_.set_slide_speed(slide_speed_);
		pane_transition_.load_transition(start, end);
	}

	public void update() {
		if (!advancing_)
			return;
		
		pane_transition_.perform_transition();
		
		if (!delaying_ && pane_transition_.finished()) {
			delaying_ = true;
			delay_counter_ = p_.millis();
		}
		
		if (delaying_) {
			if (p_.millis() - delay_counter_ > ADVANCE_DELAY) {
				load_transition();
				delaying_ = false;
			}
		}
	}

	public void draw() {
		pane_transition_.draw();
	}
	
	public PImage get_image() {
		return pane_transition_.get_current_image();
	}
	
	public void set_advancing(boolean new_advance) {
		advancing_ = new_advance;
	}
}
