package voteVis;

import processing.core.PImage;

public class BillboardBox extends DynamicBox {
	private Type type_;
	int x_index_;
	int y_index_;
	
	// indexes are used to determine which icon to show
	public BillboardBox(Type type_, int x_index_, int y_index_,
		float x_pos_, float y_pos_) {
		super(VoteVisApp.instance(), x_pos_, y_pos_);
		this.type_ = type_;
		this.x_index_ = x_index_;
		this.y_index_ = y_index_;
		
		falling_ = false;
		box_pane_.set_advancing(false);
		visible_ = false;
		
		box_pane_.add_transition_state(new PhotoState(VoteVisApp.instance(), 
			Settings.instance().get_billboard_image(type_, x_index_, y_index_)));
		box_pane_.load_transition();
		box_pane_.set_advancing(false);
		
		box_frame_ = new NoFrame();
	}
	
	@Override
	public PImage get_image() {
		// TODO Auto-generated method stub
		return box_pane_.get_image();
	}
}
