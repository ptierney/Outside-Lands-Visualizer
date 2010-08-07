package voteVis;

import java.util.ArrayList;

import processing.core.*;
//import processing.opengl.*;
//import javax.media.opengl.GL;

@SuppressWarnings("serial")
public class VoteVisApp extends PApplet {
	private Settings settings_;
	private ImageLoader loader_;
	private Utility utility_;
	private BoxManager manager_;
	private BallotCounter counter_;
	private VoteBoxFactory vote_factory_;
	
	private PImage banner_;
	
	// Testing vars
	private Box box_;
	private PFont text_font_;

	@Override
	public void setup() {
		size(1024, 768, P3D);
		background(0);

		settings_ = new Settings(this);
		loader_ = new ImageLoader(this);
		utility_ = new Utility(this);
		manager_ = new BoxManager(this);
		counter_ = new BallotCounter(this);
		vote_factory_ = new VoteBoxFactory(this);
		
		banner_ = loadImage("banner.png");
	}

	@Override
	public void draw() {
		background(settings().background_color());

		manager_.update_boxes();
		manager_.draw_boxes();
		manager_.clean_up();
		
		// required by law to be here
		image(banner_, 0, 0);
	}
	
	@Override
	public void keyPressed() {
		if (key == ' ') {
			counter_.add_random_ballot();
			vote_factory_.make_vote_row(counter_.get_last_ballot());
		} else if (key == '1') {
			vote_factory_.drop_bottom_row();
		}
	}

	public Settings settings() {
		return settings_;
	}

	public ImageLoader loader() {
		return loader_;
	}
	
	public Utility utility() {
		return utility_;
	}

	public BoxManager manager() {
		return manager_;
	}
	
	public VoteBoxFactory vote_box_factory() {
		return vote_factory_;
	}
}
