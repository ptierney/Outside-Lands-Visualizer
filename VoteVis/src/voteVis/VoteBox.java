package voteVis;

import processing.core.*;

public class VoteBox extends DynamicBox {
	private Type type_;
	private int index_;
	
	public VoteBox(VoteVisApp p_, float x_, float y_, Type type_, int index_) {
		super(p_, x_, y_);
		
		this.type_ = type_;
		this.index_ = index_;
		
		set_pane_transition_states();
		box_pane_.load_transition(); // prep the box for a transition
		box_frame_ = new VoteFrame(p_, type_);
	}
	
	private void set_pane_transition_states() {
		box_pane_.add_transition_state(get_text_state());
		box_pane_.add_transition_state(get_photo_state());
	}
	
	private TextState get_text_state() {
		return new TextState(p_, p_.loader().get_candidate_name(type_, index_),
			p_.settings().get_vote_box_color_for_type(type_),
			Settings.VOTE_PANE_DIM, p_.settings().get_vote_box_font(), 
			Settings.VOTE_BOX_FONT_SIZE);
	}
	
	private PhotoState get_photo_state() {
		return new PhotoState(p_, p_.utility().scale_to_vote_pane_size(
			p_.loader().get_candidate_image(type_, index_)));
	}
	
}
