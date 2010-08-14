package voteVis;

import java.io.File;

import processing.core.*;
import twitpull.*;

public class TweetBox extends Box {
	private static int PROFILE_IMAGE_HEIGHT = 150;
	
	private String user_name_;
	private String tweet_;
	private PImage user_photo_ = null;
	private PGraphics render_;
	private int render_width_;
	private int render_height_;

	private static final float USER_SCALE = 1.5f;
	private static final float FOLLOW_SCALE = 1.0f;
	
	private static final int PROFILE_IMAGE_GAP = 6;
	private static final int PROFILE_TEXT_TOP_GAP = 6;
	private static final int PROFILE_IMAGE_DIM = 80;
	
	private static final int FOLLOW_IMAGE_GAP = 15;
	private static final int FOLLOW_IMAGE_TOP_GAP = 23;
	private static final int FOLLOW_IMAGE_DIM = 110;
	
	private float fade_counter_ = 0.0f;
	private float max_scale_ = 1.0f;
	private boolean scaling_up_ = false;
	private int fade_in_speed_ = 250; // in millis
	private int fade_in_counter_;
	
	private TweetType tweet_type_;
	private String tweet_name_;
	
	private Type type_;
	
	public enum TweetType {
		USER,
		FOLLOW
	}
	
	public TweetBox(VoteVisApp p, float x, float y, ParsedTweet parsed_tweet_,
		TweetType tweet_type_) {
		super(p, x, y);
		
		user_name_ = parsed_tweet_.getFrom_user();
		tweet_ = parsed_tweet_.getText();
		
		File user_photo_file = parsed_tweet_.getUserimage();
		if (user_photo_file == null)
			user_photo_ = p.loadImage("img.jpg");
		else
			user_photo_ = p.loadImage(user_photo_file.getAbsolutePath());
		
		type_ = SceneManager.instance().current_type();
		
		fade_in_counter_ = p.millis();
		
		scaling_up_ = true;
		
		this.tweet_type_ = tweet_type_;
		
		if (tweet_type_ == TweetType.FOLLOW)
			create_follow_box(p);
		else
			create_user_box(p);
	}
	
	private void create_follow_box(VoteVisApp p) {
		//max_scale_ = p.random(1.0f, 2.0f);
		max_scale_ = 1.0f;
		
		render_width_ = (int)(481 * FOLLOW_SCALE);
		render_height_ = (int)(313 * FOLLOW_SCALE);
		
		tweet_type_ = TweetType.FOLLOW;
	}
	
	private void create_user_box(VoteVisApp p) {
		//max_scale_ = p.random(1.25f, 2.5f);
		max_scale_ = 1.0f;
		
		render_width_ = (int)(314 * USER_SCALE);
		render_height_ = (int)(149 * USER_SCALE);
		
		tweet_type_ = TweetType.USER;
	}
	
	public void render_box() {
		VoteVisApp p = VoteVisApp.instance();
		
		if (tweet_type_ == TweetType.FOLLOW) {
			render_ = p.createGraphics(render_width_, render_height_, PApplet.JAVA2D);
			render_.beginDraw();
			render_.image(ImageLoader.instance().get_tweet_background_follow(type_), 0, 0,
				render_width_, render_height_);
			render_.image(Utility.instance().scale_to_pane_size(user_photo_,
				FOLLOW_IMAGE_DIM), FOLLOW_IMAGE_GAP, FOLLOW_IMAGE_GAP);
			render_.textFont(Settings.TWEET_BOX_FONT, Settings.TWEET_BOX_FONT_SIZE);
			render_.fill(255);
			render_.text(tweet_, FOLLOW_IMAGE_GAP * 2 + FOLLOW_IMAGE_DIM, FOLLOW_IMAGE_TOP_GAP,
				render_width_ - FOLLOW_IMAGE_GAP * 3 - FOLLOW_IMAGE_DIM, render_height_ - FOLLOW_IMAGE_GAP * 2);
			render_.endDraw();
		} else {
			render_ = p.createGraphics(render_width_, render_height_, PApplet.JAVA2D);
			render_.beginDraw();
			render_.image(ImageLoader.instance().get_tweet_background_user(type_), 0, 0,
				render_width_, render_height_);
			render_.image(Utility.instance().scale_to_pane_size(user_photo_,
				PROFILE_IMAGE_DIM), PROFILE_IMAGE_GAP, PROFILE_IMAGE_GAP);
			render_.noStroke();
			render_.fill(0);
			
			p_.textFont(Settings.TWEET_USER_BOX_BOLD_FONT);
			float text_width = p_.textWidth(user_name_);
			
			final int text_side_margin = 2;
			final int rect_increase_x = 8;
			final int rect_increase_y = 6;
			
			render_.rect(PROFILE_IMAGE_GAP * 2 + PROFILE_IMAGE_DIM, PROFILE_TEXT_TOP_GAP,
				/*render_width_ - (PROFILE_IMAGE_GAP * 3 + PROFILE_IMAGE_DIM), 26);*/
				text_width + text_side_margin * 2 + rect_increase_x, 24 + rect_increase_y);
			render_.fill(255);
			
			//p_.textFont(TWEET_USER_BOX_BOLD_FONT);
			//float text_width = p_.textWidth(user_name_);
			
			render_.textFont(Settings.TWEET_USER_BOX_BOLD_FONT, Settings.TWEET_USER_BOX_FONT_SIZE);
			render_.text(user_name_, PROFILE_IMAGE_GAP * 2 + PROFILE_IMAGE_DIM + text_side_margin + rect_increase_x / 2, 
				PROFILE_TEXT_TOP_GAP + 20 + rect_increase_y / 2);
			render_.fill(0);
			render_.textFont(Settings.TWEET_USER_BOX_FONT, Settings.TWEET_USER_BOX_FONT_SIZE);
			render_.text(tweet_, 
				PROFILE_IMAGE_GAP * 2 + PROFILE_IMAGE_DIM, 
				PROFILE_TEXT_TOP_GAP + Settings.TWEET_USER_BOX_FONT_SIZE + 10 + rect_increase_y, 
				render_width_ - (PROFILE_IMAGE_GAP * 3 + PROFILE_IMAGE_DIM), 
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
