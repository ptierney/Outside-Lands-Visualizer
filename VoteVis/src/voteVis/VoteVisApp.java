package voteVis;

import java.util.ArrayList;

import processing.core.*;
//import processing.opengl.*;
//import javax.media.opengl.GL;

@SuppressWarnings("serial")
public class VoteVisApp extends PApplet {
	private static VoteVisApp instance_;
	private PImage banner_;
	
	// Testing vars
	private Box box_;
	private PFont text_font_;

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
	}

	@Override
	public void draw() {
		background(Settings.instance().background_color());

		BoxManager manager = BoxManager.instance();
		manager.update_boxes();
		manager.draw_boxes();
		manager.clean_up();
		
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
