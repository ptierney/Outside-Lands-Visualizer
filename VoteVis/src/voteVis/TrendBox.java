package voteVis;

import processing.core.PImage;

public class TrendBox extends DynamicBox {
	private Type type_; // musician, food, etc
	private int index_; // which musician, food, etc
	private Size size_; // XL, L, M, S, XS
	
	public TrendBox(Type type_, int index_, Size size_,
		float x_, float y_) {
		super(VoteVisApp.instance(), x_, y_);
		
		this.type_ = type_;
		this.index_ = index_;
		this.size_ = size_;
		
		box_frame_ = new VoteFrame(p_, type_);
	}

	@Override
	public void draw() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int get_width() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int get_height() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public PImage get_image() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
