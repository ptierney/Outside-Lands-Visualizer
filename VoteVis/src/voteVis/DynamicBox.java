package voteVis;

import java.util.ArrayList;
import processing.core.*;

public class DynamicBox extends Box {
	protected int box_color_;
	protected PImage background_image_;
	protected BoxPane box_pane_;
	protected BoxFrame box_frame_;
	
	public DynamicBox(VoteVisApp p_, float x_, float y_) {
		super(p_, x_, y_);
		
		box_pane_ = new BoxPane(p_);
	}
	
	@Override
	public int get_width() {
		return box_frame_.get_width();
	}
	
	@Override
	public int get_height() {
		return box_frame_.get_height();
	}

	@Override
	public void update() {
		super.update();
		
		if (!visible_)
			return;

		if (falling_ == false)
			box_pane_.update();
	}

	@Override
	public void draw() {
		if (!visible_)
			return;
		
		p_.pushMatrix();
			p_.translate(x_, y_);
			box_frame_.draw();
			p_.pushMatrix();
				p_.translate(box_frame_.get_pane_x(), box_frame_.get_pane_y());
				box_pane_.draw();
			p_.popMatrix();
		p_.popMatrix();
	}
	
	public BoxFrame box_frame() {
		return box_frame_;
	}
	
	public BoxPane box_pane() {
		return box_pane_;
	}

}
