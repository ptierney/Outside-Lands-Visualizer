package voteVis;

import processing.core.PApplet;
import processing.core.PImage;
import processing.core.*;
import twitpull.*;

public class TweetBox extends Box {
	private static int PROFILE_IMAGE_HEIGHT = 150;
	
	private String user_name_;
	private String tweet_;
	private PImage user_photo_;
	private PGraphics render_;
	private int render_width_;
	private int render_height_;

	public TweetBox(VoteVisApp p, float x, float y, ParsedTweet parsed_tweet_) {
		super(p, x, y);
		
		user_name_ = parsed_tweet_.getFrom_user();
		tweet_ = parsed_tweet_.getText();
		user_photo_ = p.loadImage(parsed_tweet_.getUserimage().getAbsolutePath());
		
		render_width_ = 300;
		render_height_ = 200;
		
		render_ = p.createGraphics(render_width_, render_height_, PApplet.JAVA2D);
		render_.beginDraw();
		render_.background(100);
		render_.strokeWeight(2.0f);
		render_.stroke(255);
		render_.noFill();
		render_.rect(0, 0, 300, 100);
		render_.fill(255);
		render_.image(user_photo_, 30, 30);
		render_.textFont(Settings.instance().get_vote_box_font());
		render_.text(user_name_, 100, 30);
		render_.text(tweet_, 100, 50, 150, 150);
		render_.endDraw();
	}

	@Override
	public void draw() {
		// TODO Auto-generated method stub
		VoteVisApp.instance().image(render_, -render_width_ / 2, -render_height_ / 2);
	}

	@Override
	public int get_height() {
		// TODO Auto-generated method stub
		return render_height_;
	}

	@Override
	public PImage get_image() {
		// TODO Auto-generated method stub
		return render_;
	}

	@Override
	public int get_width() {
		// TODO Auto-generated method stub
		return render_width_;
	}
}
