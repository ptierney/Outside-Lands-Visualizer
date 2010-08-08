package voteVis;

import processing.core.*;

public abstract class BoxTransition {
	protected TransitionReceiver signal_receiver_;
	protected Box start_box_;
	protected Box end_box_;
	protected PImage start_image_;
	protected PImage end_image_;
	protected boolean transitioning_;
	
	protected float start_width_;
	protected float h_start_width_;
	protected float start_height_;
	protected float h_start_height_;
	protected float end_width_;
	protected float h_end_width_;
	protected float end_height_;
	protected float h_end_height_;
	
	public BoxTransition(TransitionReceiver signal_receiver_) {
		transitioning_ = false;
		this.signal_receiver_ = signal_receiver_;
	}
	
	public void load_boxes(Box start_box_, Box end_box_) {
		this.start_box_ = start_box_;
		start_image_ = start_box_.get_image();
		this.end_box_ = end_box_;
		end_image_ = end_box_.get_image();
		
		start_width_ = start_image_.width;
		h_start_width_ = start_width_ / 2;
		start_height_ = start_image_.height;
		h_start_height_ = start_height_ / 2;
		end_width_ = end_image_.width;
		h_end_width_ = end_width_ / 2;
		end_height_ = end_image_.height;
		h_end_height_ = end_height_ / 2;
	}
	
	public void begin_transition() {
		transitioning_ = true;
	}
	
	public abstract void update();
	
	// draws the transition centered
	public abstract void draw();
}
