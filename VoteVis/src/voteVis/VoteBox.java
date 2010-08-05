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
		box_pane_.add_transition_state(get_text_state());
		box_pane_.add_transition_state(get_photo_state());
	}
	
	private TextState get_text_state() {
		return new TextState(p_, p_.loader().get_candidate_name(type_, index_),
			p_.settings().get_vote_box_color_for_type(type_),
			Settings.VOTE_PANE_DIM, );
	}
	
	private PhotoState get_photo_state() {
		
	}
}
