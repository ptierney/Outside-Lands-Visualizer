package voteVis;

import processing.core.*;

public class TrendFrame extends BoxFrame {
	private Size size_;
	public static int BORDER_WIDTH = VoteFrame.BORDER_WIDTH;
	private PImage frame_image_;
	private int frame_width_;
	private int frame_height_;
	private Type type_;
	
	public TrendFrame(Type type_, Size size_) {
		this.size_ = size_;
		this.type_ = type_;
		
		frame_image_ = Utility.instance().scale_to_pane_size(
			ImageLoader.instance().get_vote_background(type_), 
			Size.get_dim_from_size(size_));
		
		frame_width_ = frame_height_ = Size.get_dim_from_size(size_);
	}
	
	public Size size() {
		return size_;
	}
	
	public Type type() {
		return type_;
	}
	
	@Override
	public void draw() {
		VoteVisApp.instance().image(frame_image_, -frame_width_ / 2, -frame_height_ / 2);
	}

	@Override
	public int get_height() {
		return frame_height_;
	}

	@Override
	public int get_width() {
		return frame_width_;
	}

	@Override
	public PImage get_image() {
		return frame_image_;
	}

	@Override
	public int get_pane_x() {
		return 0;
	}

	@Override
	public int get_pane_y() {
		return 0;
	}
}
