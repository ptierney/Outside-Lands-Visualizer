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
	private Box box_;
	private PFont text_font_;
	private BoxTransition test_box_transition_;
	private int last_frame_;
	
	private boolean waiting_;

	@Override
	public void setup() {
		size(1024, 768, P3D);
		background(0);
		//smooth();
		
		instance_ = this;

		waiting_ = false;

		
		// references are stored in static instance_ var
		@SuppressWarnings("unused")
		Settings settings_ = new Settings(this);
		@SuppressWarnings("unused")
		ImageLoader loader_ = new ImageLoader(this);
		@SuppressWarnings("unused")
		BannerDisplay banner_display_ = new BannerDisplay();
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
		@SuppressWarnings("unused")
		BillboardFactory billboard_factory_ = new BillboardFactory();
		
		// SceneManager will start the cycle
		@SuppressWarnings("unused")
		SceneManager scene_manager_ = new SceneManager();
		
		last_frame_ = millis();
	}

	@Override
	public void draw() {
		if (waiting_)
			return;
		
		background(Settings.instance().background_color());

		BoxManager manager = BoxManager.instance();
		manager.update_boxes();
		manager.draw_boxes();
		manager.clean_up();
		
		BillboardFactory bfact = BillboardFactory.instance();
		bfact.update();
		bfact.draw();
		
		VoteBoxFactory.instance().update();
		
		BannerDisplay.instance().draw();
		
		last_frame_ = millis();
	}
	
	@Override
	public void keyPressed() {
		if (key == ' ') {
			waiting_ = false;
		} else if (key == '1') {
			VoteBoxFactory.instance().drop_bottom_row();
		} else if (key == '2') {
			BillboardFactory.instance().load_vote_to_top_transition(Type.FOOD);
			BillboardFactory.instance().begin_transition();
		}
	}
	
	public static VoteVisApp instance() {
		return instance_;
	}
	
	public int last_frame() {
		return last_frame_;
	}
}
