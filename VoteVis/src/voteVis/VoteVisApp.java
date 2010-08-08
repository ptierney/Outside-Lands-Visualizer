package voteVis;

import java.util.ArrayList;

import processing.core.*;
//import processing.opengl.*;
//import javax.media.opengl.GL;
import voteVis.RotateBoxTransition.Axis;

@SuppressWarnings("serial")
public class VoteVisApp extends PApplet {
	private static VoteVisApp instance_;
	private PImage banner_;
	
	// Testing vars
	private Box box_, v1, v2;
	private PFont text_font_;
	private BoxTransition test_box_transition_;

	@Override
	public void setup() {
		size(1024, 768, P3D);
		background(0);
		
		instance_ = this;

		// references are stored in static instance_ var
		@SuppressWarnings("unused")
		Settings settings_ = new Settings(this);
		@SuppressWarnings("unused")
		ImageLoader loader_ = new ImageLoader(this);
		@SuppressWarnings("unused")
		Utility utility_ = new Utility(this);
		@SuppressWarnings("unused")
		BoxManager manager_ = new BoxManager(this);
		@SuppressWarnings("unused")
		BallotCounter counter_ = new BallotCounter(this);
		@SuppressWarnings("unused")
		VoteBoxFactory vote_factory_ = new VoteBoxFactory(this);
		@SuppressWarnings("unused")
		UserManager user_manager_ = new UserManager(this);
		user_manager_.create_test_user();
		
		banner_ = loadImage("banner.png");
		
		test_box_transition_ = new RotateBoxTransition(Axis.VERTICAL);

		v1 = new VoteBox(this, Utility.get_aligned_position(Settings.UNIT_DIM, 1), 
				0, Type.MUSIC, 0);
		v1.set_x(width / 2);
		v1.set_y(height / 2);
		v1.set_falling(false);
		v1.set_visible(false);
		v2 = new VoteBox(this, Utility.get_aligned_position(Settings.UNIT_DIM, 2), 
				0, Type.FOOD, 0);
		v2.set_x(width / 2);
		v2.set_y(height / 2);
		v2.set_visible(false);
		v2.set_falling(false);
		
		test_box_transition_.load_boxes(v1, v2);
		test_box_transition_.begin_transition();
		
	}

	@Override
	public void draw() {
		background(Settings.instance().background_color());

		BoxManager manager = BoxManager.instance();
		manager.update_boxes();
		manager.draw_boxes();
		manager.clean_up();
		
		test_box_transition_.update();
		test_box_transition_.draw();
		
		image(v1.get_image(), width / 4, height / 4);
		image(v2.get_image(), 3 * width / 4, 3 * height / 4);
		
		// required by law to be here
		image(banner_, 0, 0);
	}
	
	@Override
	public void keyPressed() {
		if (key == ' ') {
			BallotCounter.instance().add_random_ballot();
			VoteBoxFactory.instance().make_vote_row(BallotCounter.instance().get_last_ballot());
		} else if (key == '1') {
			VoteBoxFactory.instance().drop_bottom_row();
		}
	}
	
	public static VoteVisApp instance() {
		return instance_;
	}
}
