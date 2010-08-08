package voteVis;

import processing.core.*;

public class RotateBoxTransition extends BoxTransition {
	private Axis axis_;
	private float rotate_amount_;
	private static float SEAM = 0.0f; // the amount of space between two images
	private static float ROTATE_SPEED = 0.03f; // maybe make this time based?
	private int pos_x_;
	private int pos_y_;

	public RotateBoxTransition(Axis axis_) {
		super();

		this.axis_ = axis_;
	}
	
	@Override
	public void load_boxes(Box b1, Box b2) {
		super.load_boxes(b1, b2);

		rotate_amount_ = -PApplet.PI;
		
		pos_x_ = (int) b1.x();
		pos_y_ = (int) b1.y();
	}

	public void update() {
		if (!transitioning_)
			return;
		if (VoteVisApp.instance().key == 'a') {
			rotate_amount_ += ROTATE_SPEED;
			PApplet.println(rotate_amount_);
		}
	}

	public void draw() {
		if (false && !transitioning_)
			return;
		
		PApplet p = VoteVisApp.instance();
		p.noStroke();
		p.pushMatrix();
			p.translate(pos_x_, pos_y_);
			if (axis_ == Axis.VERTICAL)
				p.rotateY(rotate_amount_);
			else if (axis_ == Axis.HORIZONTAL)
				p.rotateX(rotate_amount_);
			
			if (true || rotate_amount_ < 0) {
				p.beginShape();
					p.texture(start_image_);
					p.vertex(-h_start_width_, -h_start_height_, 0, 0);
					p.vertex(-h_start_width_, h_start_height_, 0, start_height_);
					p.vertex(h_start_width_, h_start_height_,  start_width_, start_height_);
					p.vertex(h_start_width_, -h_start_height_, start_width_, 0);
				p.endShape();
			} else {
				p.beginShape();
					p.texture(end_image_);
					p.vertex(-h_end_width_, -h_end_height_,  0, 0);
					p.vertex(-h_end_width_, h_end_height_,  0, start_height_);
					p.vertex(h_end_width_, h_end_height_,  end_width_, end_height_);
					p.vertex(h_end_width_, -h_end_height_,  start_width_, 0);
				p.endShape();
			}
			
		p.popMatrix();
		
		if (rotate_amount_ > PApplet.PI / 2) {
			rotate_amount_ = PApplet.PI / 2;
			transitioning_ = false;
		}
	}

	public enum Axis {
		HORIZONTAL, VERTICAL
	}
}
