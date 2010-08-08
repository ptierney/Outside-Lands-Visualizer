package voteVis;

import processing.core.*;

public class ProfileFrame extends ExpandingFrame {
	private ProfileBox profile_box_;
	private PImage left_cap_;
	private PImage right_cap_;
	private PImage spacer_;
	private float last_counter_; // used to determine if I need to regenerate
									// the spacer
	private int frame_height_;

	private static int RIGHT_GRAPHIC_WIDTH = 10;
	private int left_graphic_width_;
	private int spacer_width_;

	public static int TEXT_DISPLAY_TIME = 2000; // in millis

	private boolean displaying_text_;
	private int text_display_counter_;
	private int text_size_;
	private int text_height_;
	private PGraphics text_graphics_;
	private static String TEXT_BASE = "'S TOP FIVE";
	private String text_string_;
	private PImage text_buffer_;

	public ProfileFrame(VoteVisApp p_, ProfileBox box_, int frame_height_) {
		super(p_, 6); // these expand the entire length of the window

		profile_box_ = box_;
		this.frame_height_ = frame_height_;
		left_graphic_width_ = frame_height_ - RIGHT_GRAPHIC_WIDTH;
		last_counter_ = 0.0f;
		spacer_width_ = 0;
		displaying_text_ = false;
		determine_text_size();

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

		if (expanding_ || contracting_ || displaying_text_ ) {
			draw_text();
		}
		
		if (displaying_text_)
			update_text_counter();
	}
	
	@Override
	public PImage get_image() {
		PGraphics render = VoteVisApp.instance().createGraphics(left_graphic_width_ + RIGHT_GRAPHIC_WIDTH, frame_height_, PApplet.JAVA2D);
		
		render.beginDraw();
		render.image(left_cap_, 0, 0);
		render.image(right_cap_, left_graphic_width_, 0);
		render.endDraw();
		
		return render;
	}
	
	@Override
	public PVector get_image_center() {
		return new PVector(frame_height_ / 2, frame_height_ / 2);
	}

	private void draw_left_cap() {
		p_.image(left_cap_, -frame_height_ / 2, -frame_height_ / 2);
	}

	private void draw_right_cap() {
		p_.image(right_cap_, -frame_height_ / 2 + left_graphic_width_
				+ spacer_width_, -frame_height_ / 2);
	}

	private void draw_spacer() {
		if (counter_ == 0.0) {
			spacer_width_ = 0; // make sure for sanity reasons
			return;
		}

		generate_spacer();

		p_.image(spacer_, -frame_height_ / 2 + left_graphic_width_,
				-frame_height_ / 2);
	}

	private void generate_spacer() {
		spacer_width_ = (int) ((max_unit_width_ - 1)
				* (Settings.UNIT_DIM + Settings.BOX_GAP) * counter_ + RIGHT_GRAPHIC_WIDTH);
		PGraphics spacer = p_.createGraphics(spacer_width_, frame_height_,
				PApplet.JAVA2D);

		spacer.beginDraw();
		spacer.background(Settings.instance().profile_color());
		spacer.endDraw();

		spacer_ = spacer;
	}

	private void load_media() {
		PGraphics left_graphic = p_.createGraphics(left_graphic_width_,
				frame_height_, PApplet.JAVA2D); // assume square for now

		left_graphic.beginDraw();
		left_graphic.background(Settings.instance().profile_color());
		left_graphic.endDraw();

		left_cap_ = left_graphic;

		PGraphics right_graphic = p_.createGraphics(RIGHT_GRAPHIC_WIDTH,
				frame_height_, PApplet.JAVA2D);

		right_graphic.beginDraw();
		right_graphic.smooth();
		right_graphic.noStroke();
		right_graphic.fill(Settings.instance().profile_color());
		right_graphic.beginShape();
		right_graphic.vertex(0, 0);
		right_graphic.vertex(RIGHT_GRAPHIC_WIDTH, frame_height_ / 2);
		right_graphic.vertex(0, frame_height_);
		right_graphic.endShape(PApplet.CLOSE);
		right_graphic.endDraw();

		right_cap_ = right_graphic;
	}

	@Override
	protected void done_expanding() {
		VoteBoxFactory.instance().display_top_row();

		displaying_text_ = true;
		text_display_counter_ = p_.millis();
	}
	
	@Override
	protected void done_contracting() {
		// set the pane to start advancing
		profile_box_.box_pane().set_advancing(true);
		VoteBoxFactory.instance().setup_finished(profile_box_);
	}

	private void draw_text() {
		/*
		 * VoteVisApp.instance().fill(255);
		 * VoteVisApp.instance().textFont(Settings
		 * .instance().profile_box_font(), text_size_);
		 * VoteVisApp.instance().text(text_string_, frame_height_ / 2,
		 * text_height_ / 2);
		 */
		copy_text_into_buffer();
		VoteVisApp.instance().image(text_buffer_, frame_height_ / 2,
			-text_graphics_.height / 2);
	}

	private void update_text_counter() {
		if (VoteVisApp.instance().millis() - text_display_counter_ > TEXT_DISPLAY_TIME) {
			displaying_text_ = false;
			contract_fully();
		}
	}

	private void determine_text_size() {
		int user_id = profile_box_.user_id();
		text_string_ = UserManager.instance().get_user(user_id).name()
				.toUpperCase()
				+ TEXT_BASE;

		int size = 2;
		int max_width = (int) (4.8 * (Settings.UNIT_DIM + Settings.BOX_GAP));
		int shadow_offset = 4;

		while (true) {
			VoteVisApp.instance().textFont(
					Settings.instance().profile_box_font(), size);
			float text_width = VoteVisApp.instance().textWidth(text_string_);

			if (text_width > max_width)
				break;

			size += 2;
		}

		VoteVisApp.instance().textFont(Settings.instance().profile_box_font(),
				size);
		float height = VoteVisApp.instance().textAscent() + 3 + shadow_offset; // add 3 extra
																// pixels for
																// safety (so
																// nothing's cut
																// off)
		float width = VoteVisApp.instance().textWidth(text_string_) + shadow_offset;

		text_size_ = size;

		text_graphics_ = VoteVisApp.instance().createGraphics((int) width,
				(int) height, PApplet.JAVA2D);

		text_graphics_.beginDraw();
		text_graphics_.textFont(Settings.instance().profile_box_font(), size);
		text_graphics_.noStroke();
		text_graphics_.fill(27);
		text_graphics_.text(text_string_, 0, height - 1);
		text_graphics_.fill(255);
		text_graphics_.text(text_string_, shadow_offset, height - 1 - shadow_offset); // leave 1 px margin at the bottom
		text_graphics_.endDraw();

		text_height_ = (int) height;
	}

	private void copy_text_into_buffer() {
		text_buffer_ = VoteVisApp.instance().createImage(text_graphics_.width,
			text_graphics_.height, PApplet.ARGB);

		int column_stop = (int) (text_graphics_.width * counter_);
		// Walk along each column of the start image
		for (int i = 0; i < column_stop; ++i) {
			// inner loop copies the start image column into a transition_buffer
			for (int j = i; j <  (text_graphics_.height - 1) * text_graphics_.width + i; j += text_graphics_.width) {
				text_buffer_.pixels[j] = text_graphics_.pixels[j];
			}
		}
	}
}
