package voteVis;

import processing.core.*;

public class ProfileFrame extends ExpandingFrame {
	private VoteVisApp p_;
	private PImage left_cap_;
	private PImage right_cap_;
	private PImage spacer_;
	private float last_counter_; // used to determine if I need to regenerate the spacer
	private int frame_height_;
	
	private static int RIGHT_GRAPHIC_WIDTH = 10;
	private int left_graphic_width_;
	private int spacer_width_;
	
	public ProfileFrame(VoteVisApp p_, int frame_height_) {
		super(6); // these expand the entire length of the window
		
		this.p_ = p_;
		this.frame_height_ = frame_height_;
		left_graphic_width_ = frame_height_ - RIGHT_GRAPHIC_WIDTH;
		last_counter_ = 0.0f;
		spacer_width_ = 0;
		
		load_media();
	}
	
	public int get_pane_x() {
		return 0;
	}
	
	public int get_pane_y() {
		return 0;
	}
	
	public int get_width() {
		return frame_height_;
	}
	
	public int get_height() {
		return frame_height_;
	}

	@Override
	public void draw() {
		update();
		
		draw_left_cap();
		draw_spacer();
		draw_right_cap();
	}
	
	private void draw_left_cap() {
		p_.image(left_cap_, -frame_height_ / 2, -frame_height_ / 2);
	}
	
	private void draw_right_cap() {
		p_.image(right_cap_, -frame_height_ / 2 + + left_graphic_width_ + spacer_width_, -frame_height_ / 2);
	}
	
	private void draw_spacer() {
		if (counter_ == 0.0)
			return;
		
		generate_spacer();
		
		p_.image(spacer_, -frame_height_ / 2 + left_graphic_width_, -frame_height_ / 2);
	}
	
	private void generate_spacer() {
		spacer_width_ = (int)((max_unit_width_ - 1) * Settings.UNIT_DIM * counter_);
		PGraphics spacer = p_.createGraphics(spacer_width_, frame_height_, PApplet.JAVA2D);
		
		spacer.beginDraw();
		spacer.background(p_.settings().profile_color());
		spacer.endDraw();
		
		spacer_ = spacer;
	}
	
	private void load_media() {
		PGraphics left_graphic = p_.createGraphics(left_graphic_width_, frame_height_, PApplet.JAVA2D); // assume square for now
		
		left_graphic.beginDraw();
		left_graphic.background(p_.settings().profile_color());
		left_graphic.endDraw();
		
		left_cap_ = left_graphic;
		
		PGraphics right_graphic = p_.createGraphics(RIGHT_GRAPHIC_WIDTH, frame_height_, PApplet.JAVA2D);
		
		right_graphic.beginDraw();
		right_graphic.smooth();
		right_graphic.noStroke();
		right_graphic.fill(p_.settings().profile_color());
		right_graphic.beginShape();
		right_graphic.vertex(0, 0);
		right_graphic.vertex(RIGHT_GRAPHIC_WIDTH, frame_height_ / 2);
		right_graphic.vertex(0, frame_height_);
		right_graphic.endShape(PApplet.CLOSE);
		right_graphic.endDraw();
		
		right_cap_ = right_graphic;
	}
}
