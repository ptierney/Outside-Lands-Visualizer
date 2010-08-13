package voteVis;

import processing.core.PImage;

// simple box that just displays a picture

public class PictureBox extends Box {
	private PImage image_;
	float width_;
	float height_;
	
	public PictureBox(VoteVisApp p, float x, float y, PImage image_) {
		super(p, x, y);
		
		this.image_ = image_;
		width_ = image_.width;
		height_ = image_.height;
	}

	@Override
	public void draw() {
		p_.image(image_, x_ - width_, y_ - height_);
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
