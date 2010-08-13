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

	private static final int PROFILE_IMAGE_GAP = 6;
	private static final int PROFILE_TEXT_TOP_GAP = 12;
	private static final int PROFILE_IMAGE_DIM = 105;
	
	private float fade_counter_ = 0.0f;
	private float max_scale_ = 1.0f;
	private boolean scaling_up_ = false;
	private int fade_in_speed_ = 250; // in millis
	private int fade_in_counter_;
	
	private TweetType tweet_type_;
	
	private Type type_;
	
	public enum TweetType {
		USER,
		FOLLOW
	}
	
	public TweetBox(VoteVisApp p, float x, float y, ParsedTweet parsed_tweet_) {
		super(p, x, y);
		
		user_name_ = parsed_tweet_.getFrom_user();
		tweet_ = parsed_tweet_.getText();
		user_photo_ = p.loadImage(parsed_tweet_.getUserimage().getAbsolutePath());
		type_ = SceneManager.instance().current_type();
		
		fade_in_counter_ = p.millis();
		
		scaling_up_ = true;
		
		if (parsed_tweet_.getCaptureType() == "follow")
			create_follow_box(p);
		else
			create_user_box(p);
	}
	
	private void create_follow_box(VoteVisApp p) {
		max_scale_ = p.random(1.0f, 2.0f);
		
		render_width_ = 314;
		render_height_ = 149;
		
		tweet_type_ = TweetType.FOLLOW;
	}
	
	private void create_user_box(VoteVisApp p) {
		max_scale_ = p.random(1.25f, 2.5f);
		
		render_width_ = 314;
		render_height_ = 149;
		
		tweet_type_ = TweetType.USER;
	}
	
	public void render_box() {
		VoteVisApp p = VoteVisApp.instance();
		
		if (tweet_type_ == TweetType.FOLLOW) {
			render_ = p.createGraphics(render_width_, render_height_, PApplet.JAVA2D);
			render_.beginDraw();
			render_.image(ImageLoader.instance().get_tweet_background_candidate(type_), 0, 0);
			render_.image(Utility.instance().scale_to_pane_size(user_photo_,
				PROFILE_IMAGE_DIM), PROFILE_IMAGE_GAP, PROFILE_IMAGE_GAP);
			render_.textFont(Settings.TWEET_BOX_FONT, Settings.TWEET_BOX_FONT_SIZE);
			render_.fill(255);
			render_.text(tweet_, PROFILE_IMAGE_GAP * 2 + PROFILE_IMAGE_DIM, PROFILE_TEXT_TOP_GAP, 
				render_width_ - (PROFILE_IMAGE_GAP * 2 + PROFILE_IMAGE_DIM),
				render_height_ - PROFILE_IMAGE_GAP * 2);
			render_.endDraw();
		} else {
			render_ = p.createGraphics(render_width_, render_height_, PApplet.JAVA2D);
			render_.beginDraw();
			render_.image(ImageLoader.instance().get_tweet_background_user(type_), 0, 0);
			render_.image(Utility.instance().scale_to_pane_size(user_photo_,
				PROFILE_IMAGE_DIM), PROFILE_IMAGE_GAP, PROFILE_IMAGE_GAP);
			render_.textFont(Settings.TWEET_BOX_FONT, Settings.TWEET_BOX_FONT_SIZE);
			render_.fill(0);
			render_.text(tweet_, PROFILE_IMAGE_GAP * 2 + PROFILE_IMAGE_DIM, PROFILE_TEXT_TOP_GAP, 
				render_width_ - (PROFILE_IMAGE_GAP * 2 + PROFILE_IMAGE_DIM), 
				render_height_ - PROFILE_IMAGE_GAP * 2);
			render_.endDraw();
		}
	}
	
	public void init() {
		fade_in_counter_ = p_.millis();
		render_box();
	}

	@Override
	public void draw() {
		p_.pushMatrix();
			p_.translate(x_, y_);
			if (scaling_up_) {
				int time_diff = p_.millis() - fade_in_counter_;
				if (time_diff > fade_in_speed_)
					scaling_up_ = false;
				p_.scale(PApplet.map(time_diff, 
					0, fade_in_speed_, 0, max_scale_));
			} else
				p_.scale(max_scale_);
			VoteVisApp.instance().image(render_, -render_width_ / 2, -render_height_ / 2);
		p_.popMatrix();
	}

	@Override
	public int get_height() {
		return (int)(render_height_ * max_scale_);
	}

	@Override
	public int get_width() {
		return (int)(render_width_ * max_scale_);
	}
	
	@Override
	public PImage get_image() {
		return render_;
	}
}