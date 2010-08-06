package voteVis;

import java.util.ArrayList;
import processing.core.*;

public class DynamicBox extends Box {
	int box_color_;
	PImage background_image_;
	BoxPane box_pane_;

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
			box_pane_.draw();
		p_.popMatrix();
	}

}
