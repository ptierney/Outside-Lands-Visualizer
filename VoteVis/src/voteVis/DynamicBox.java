package voteVis;

import java.util.ArrayList;
import processing.core.*;

public class DynamicBox extends Box {
	protected int box_color_;
	protected PImage background_image_;
	protected BoxPane box_pane_;
	protected BoxFrame box_frame_;
	
	DynamicBox(VoteVisApp p_, float x_, float y_, int side_dim_) {
		super(p_, x_, y_, side_dim_);
		
		box_pane_ = new BoxPane(p_);
	}

	@Override
	public void update() {
		super.update();

		if (falling_ == false)
			box_pane_.update();
	}

	@Override
	public void draw() {
		p_.pushMatrix();
			p_.translate(x_, y_);
			box_frame_.draw();
			p_.pushMatrix();
				p_.translate(box_frame_.get_pane_x(), box_frame_.get_pane_y());
				box_pane_.draw();
			p_.popMatrix();
		p_.popMatrix();
	}

}
