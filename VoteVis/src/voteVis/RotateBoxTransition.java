package voteVis;

import processing.core.*;

public class RotateBoxTransition extends BoxTransition {
	private Axis axis_;
	private float rotate_amount_;
	private static float SEAM = 20.0f; // the amount of space between two images
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

		rotate_amount_ = 0.0f;
		
		pos_x_ = (int) b1.x();
		pos_y_ = (int) b1.y();
	}

	public void update() {
		if (!transitioning_)
			return;
		
		rotate_amount_ += ROTATE_SPEED;
	}

	public void draw() {
		if (!transitioning_)
			return;
		
		PApplet p = VoteVisApp.instance();
		p.noStroke();
		p.pushMatrix();
			p.translate(pos_x_, pos_y_);
			if (axis_ == Axis.VERTICAL)
				p.rotateY(rotate_amount_);
			else if (axis_ == Axis.HORIZONTAL)
				p.rotateX(rotate_amount_);
			
			p.beginShape();
				p.texture(start_image_);
				p.vertex(-h_start_width_, -h_start_height_, SEAM, 0, 0);
				p.vertex(-h_start_width_, h_start_height_, SEAM, 0, start_height_);
				p.vertex(h_start_width_, h_start_height_, SEAM, start_width_, start_height_);
				p.vertex(h_start_width_, -h_start_height_, SEAM, start_width_, 0);
			p.endShape(PApplet.CLOSE);
			
			p.beginShape();
				p.texture(end_image_);
				p.vertex(-h_end_width_, -h_end_height_, -SEAM, 0, 0);
				p.vertex(h_end_width_, -h_end_height_, -SEAM, end_width_, 0);
				p.vertex(h_end_width_, h_end_height_, -SEAM, end_width_, end_height_);
				p.vertex(-h_end_width_, h_end_height_, -SEAM, 0, end_height_);
			p.endShape();
			
		p.popMatrix();
	}

	public enum Axis {
		HORIZONTAL, VERTICAL
	}
}
