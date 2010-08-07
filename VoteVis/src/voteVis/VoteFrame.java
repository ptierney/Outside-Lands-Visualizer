package voteVis;

import processing.core.*;

public class VoteFrame extends BoxFrame {
	private VoteVisApp p_;
	private int side_dim_;
	private static int BORDER_WIDTH = (Settings.VOTE_BOX_SIDE_DIM - Settings.VOTE_PANE_DIM) / 2 + 2;
	private Type type_;
	private PImage frame_image_;
	
	VoteFrame(VoteVisApp p_, Type type_) {
		super();

		this.type_ = type_;
		this.p_ = p_;
		
		side_dim_ = Settings.UNIT_DIM;
		
		generate_frame();
	}
	
	private void generate_frame() {
		// maybe this should be loaded from an image instead
		PGraphics graphics = p_.createGraphics(side_dim_, side_dim_, PApplet.JAVA2D);
		
		graphics.beginDraw();
		graphics.background(Settings.instance().get_vote_box_color_for_type(type_));
		graphics.endDraw();
		
		frame_image_ = graphics;
	}
	
	public void draw() {
		p_.image(frame_image_, -side_dim_ / 2, -side_dim_ / 2);
	}
	
	public int get_pane_x() {
		return 0;
	}
	
	public int get_pane_y() {
		return 0;
	}
	
	public int get_width() {
		return side_dim_;
	}
	
	public int get_height() {
		return side_dim_;
	}
}
