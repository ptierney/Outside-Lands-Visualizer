package voteVis;

import processing.core.*;

public class ProfileFrame extends ExpandingFrame {
	private VoteVisApp p_;
	private PImage left_cap_;
	private PImage right_cap_;
	private PImage spacer_;
	private float last_counter_; // used to determine if I need to regenerate the spacer
	private int frame_height_;
	
	public ProfileFrame(VoteVisApp p_, int frame_height_) {
		super(6); // these expand the entire length of the window
		
		this.p_ = p_;
		this.frame_height_ = frame_height_;
		last_counter_ = 0.0f;
		
		load_media();
	}
	
	public int get_pane_x() {
		return 0;
	}
	
	public int get_pane_y() {
		return 0;
	}
	
	public int get_width() {
		return 0;
	}
	
	public int get_height() {
		return 0;
	}

	@Override
	public void draw() {
		update();
		
		draw_left_cap();
		draw_right_cap();
		draw_spacer();
	}
	
	private void draw_left_cap() {
		
	}
	
	private void draw_right_cap() {
		
	}
	
	private void draw_spacer() {
		if (counter_ == 0.0)
			return;
		
		generate_spacer();
	}
	
	private void generate_spacer() {
			
	}
	
	private void load_media() {
		PGraphics left_graphic = p_.createGraphics(frame_height_, frame_height_, PApplet.JAVA2D); // assume square for now
		
		left_graphic.beginDraw();
		left_graphic.background(p_.settings().profile_color());
		left_graphic.endDraw();
		
		left_cap_ = left_graphic;
		int left_graphic_width = 20;
		
		PGraphics right_graphic = p_.createGraphics(left_graphic_width, frame_height_, PApplet.JAVA2D);
		
		right_graphic.beginDraw();
		right_graphic.smooth();
		right_graphic.noStroke();
		right_graphic.fill(p_.settings().profile_color());
		right_graphic.beginShape();
		right_graphic.vertex(0, 0);
		right_graphic.vertex(left_graphic_width, frame_height_ / 2);
		right_graphic.vertex(0, frame_height_);
		right_graphic.endShape(PApplet.CLOSE);
		right_graphic.endDraw();
		
		right_cap_ = right_graphic;
	}
}
