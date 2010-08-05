package voteVis;

import processing.core.*;

public class VoteBox extends DynamicBox {
	private Type type_;
	private int index_;
	
	public VoteBox(VoteVisApp p_, float x_, float y_, Type type_, int index_) {
		super(p_, x_, y_, Settings.VOTE_BOX_SIDE_DIM);
		
		this.type_ = type_;
		this.index_ = index_;
		
		set_pane_transition_states();
		box_pane_.load_transition(); // prep the box for a transition
	}
	
	private void set_pane_transition_states() {
		/*
		box_pane_.add_transition_state(
			Settings.get_vote_box_text_state(type_, index_));
		box_pane_.add_transition_state(
			Settings.get_vote_box_photo_state(type_, index_));
			*/
	}
}
