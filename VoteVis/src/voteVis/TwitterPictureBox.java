package voteVis;

import processing.core.PImage;
import processing.core.*;
// simple box that just displays a picture

public class TwitterPictureBox extends Box {
	private PImage image_;
	float width_;
	float height_;
	private int pop_in_time_ = 750;
	private int pop_counter_;
	private boolean popping_;
	
	public TwitterPictureBox(VoteVisApp p, float x, float y, PImage image_) {
		super(p, x, y);
		
		this.image_ = image_;
		width_ = image_.width;
		height_ = image_.height;
		
		x_ += width_ / 2;
		y_ += height_ / 2;
		
		pop_counter_ = p.millis();
		popping_ = true;
		
		falling_ = false;
	}
	
	@Override
	public void update() {
		super.update();
		
		if (p_.millis() - pop_counter_ > pop_in_time_)
			popping_ = false;
	}

	@Override
	public void draw() {
		if (popping_) {
			p_.pushMatrix();
				p_.translate(x_, y_);
				p_.scale(PApplet.map(p_.millis() - pop_counter_, 0.0f, pop_in_time_, 0.0f, 1.0f));
				p_.image(image_, -width_ / 2, -height_ / 2);
			p_.popMatrix();
		} else
			p_.image(image_, x_ - width_ / 2, y_ - height_ / 2);
	}

	@Override
	public int get_height() {
		return (int) height_;
	}

	@Override
	public PImage get_image() {
		return image_;
	}

	@Override
	public int get_width() {
		return (int) width_;
	}

}
