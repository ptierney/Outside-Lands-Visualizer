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
		//box_pane_.advance_random();
		box_pane_.pane_transition().advance_random();
		box_frame_ = new VoteFrame(p_, type_);
	}
	
	@Override
	public PImage get_image() {
		PGraphics flatten = VoteVisApp.instance().createGraphics(box_frame_.get_width(), box_frame_.get_height(), PApplet.P3D);
		
		flatten.beginDraw();
		flatten.image(box_frame_.get_image(), 0, 0);
		flatten.translate(box_frame_.get_image_center().x, box_frame_.get_image_center().y);
		PImage pane_image = box_pane_.get_image();
		flatten.image(pane_image, -pane_image.width / 2, -pane_image.height / 2);
		flatten.endDraw();
		
		return flatten;
	}
	
	private void set_pane_transition_states() {
		box_pane_.add_transition_state(get_text_state());
		box_pane_.add_transition_state(get_photo_state());
	}
	
	private TransitionState get_text_state() {
		return new TextState(p_, ImageLoader.instance().get_candidate_name(type_, index_),
			p_.color(1, 0), Utility.instance().scale_to_vote_pane_size(ImageLoader.instance().get_vote_background(type_)),
			Settings.VOTE_PANE_DIM, Settings.instance().get_vote_box_font(), 
			Settings.VOTE_BOX_FONT_SIZE);
	}
	
	private PhotoState get_photo_state() {
		return new PhotoState(p_, Utility.instance().scale_to_vote_pane_size(
			ImageLoader.instance().get_candidate_image(type_, index_)));
	}
}
