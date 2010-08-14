package voteVis;

import processing.core.PApplet;
import processing.core.*;
import java.awt.Rectangle;
import java.awt.Point;
import java.awt.Dimension;
import twitpull.ParsedTweet;
import java.util.*;

public class TweetBoxFactory {
	private static TweetBoxFactory instance_; 
	private Type current_type_;
	private static final int NUM_CREATE = 4; // the number of tweet boxes to create;
	private static int CREATE_DELAY;
	private static int CREATE_DELAY_MIN = 1000;
	private static int CREATE_DELAY_MAX = 1500;
	private boolean delaying_;
	private int delay_counter_;
	private int boxes_made_;
	private ParsedTweet[] parsed_tweets_ = null;
	private ParsedTweet[] parsed_follow_tweets_ = null;
	private static int BOX_SIDE_INSET = 175;
	private ArrayList<TweetBox> tweet_boxes_;
	private Box top_intro_box_;
	private boolean intro_mode_;
	private static final int MAX_ATTEMPT_TIME = 750; // in millis
	private int attempt_counter_;
	
	private int user_tweets_made_;
	private int followed_tweets_made_;
	
	private boolean delaying_and_scrolling_;
	private static final int SCROLL_DELAY_TIME = 6000;
	private int scroll_delay_counter_;
	
	private enum IntroType {
		TOP,
		NAME,
		TWEETS,
		LOGO
	}
	
	private PVector[] intro_box_pos_;
	
	private Rectangle left_rect;
	private Rectangle right_rect;
	private Rectangle top_rect;
	private Rectangle bottom_rect;
	
	public TweetBoxFactory() {
		instance_ = this;
		
		int rect_width = 100;
		int w = VoteVisApp.instance().width;
		int h = VoteVisApp.instance().height;
		left_rect = new Rectangle(new Point(-rect_width, 0),
			new Dimension(rect_width, h));
		right_rect = new Rectangle(new Point(w, 0),
			new Dimension(rect_width, h));
		top_rect = new Rectangle(new Point(0, 0),
			new Dimension(w, BannerDisplay.instance().banner_height()));
		bottom_rect = new Rectangle(new Point(0, h),
			new Dimension(w, rect_width));
		
		intro_box_pos_ = new PVector[4];
		intro_box_pos_[IntroType.NAME.ordinal()] = new PVector(521, 126);
		intro_box_pos_[IntroType.TOP.ordinal()] = new PVector(189, 290);
		intro_box_pos_[IntroType.TWEETS.ordinal()] = new PVector(688, 455);
		intro_box_pos_[IntroType.LOGO.ordinal()] = new PVector(23, 456);
		
		init();
	}
	
	private void init() {
		boxes_made_ = 0;
		delaying_ = false;
		followed_tweets_made_ = 0;
		user_tweets_made_ = 0;
	}
	
	public void update() {
		if (delaying_ && !delaying_and_scrolling_) {
			if (VoteVisApp.instance().millis() - delay_counter_ > CREATE_DELAY)
				make_next_box();
		}
		
		if (intro_mode_) {
			if (top_intro_box_.y() > 500) {
				make_next_box();
				intro_mode_ = false;
				delaying_ = true;
			}
		}
		
		if (delaying_and_scrolling_) {
			if (VoteVisApp.instance().millis() - scroll_delay_counter_ > SCROLL_DELAY_TIME) {
				delaying_and_scrolling_ = false;
				make_next_box();
			}
		}
	}
	
	public void switched_to(Type current_type) {
		init();
		
		current_type_ = current_type;
		
		PApplet.print("Getting parsed tweets");
		while (parsed_tweets_ == null || parsed_tweets_.length == 0
			|| parsed_tweets_.length != NUM_CREATE) {
			parsed_tweets_ = get_parsed_tweets();
			PApplet.print(".");
		}
		
		PApplet.print("Getting parsed follow tweets");
		while (parsed_follow_tweets_ == null || parsed_follow_tweets_.length == 0
			|| parsed_follow_tweets_.length != NUM_CREATE) {
			parsed_follow_tweets_ = get_parsed_followed_tweets();
			PApplet.print(".");
		}

		//make_next_box();
		delaying_ = false;
		intro_mode_ = true;
		make_intro_squares();
	}
	
	public void make_intro_squares() {
		VoteVisApp p = VoteVisApp.instance();
		
		// place the 4 boxes
		
		BoxManager.instance().add_box(new TwitterPictureBox(p, intro_box_pos_[IntroType.LOGO.ordinal()].x,
			intro_box_pos_[IntroType.LOGO.ordinal()].y, ImageLoader.instance().twitter_logo_bubble));

		BoxManager.instance().add_box(new TwitterPictureBox(p, intro_box_pos_[IntroType.TOP.ordinal()].x,
				intro_box_pos_[IntroType.TOP.ordinal()].y, ImageLoader.instance().get_twitter_intro_top(current_type_)));
		
		top_intro_box_ = new TwitterPictureBox(p, intro_box_pos_[IntroType.NAME.ordinal()].x,
			intro_box_pos_[IntroType.NAME.ordinal()].y, ImageLoader.instance().get_twitter_intro_main(current_type_));
		
		BoxManager.instance().add_box(top_intro_box_);
		
		BoxManager.instance().add_box(new TwitterPictureBox(p, intro_box_pos_[IntroType.TWEETS.ordinal()].x,
				intro_box_pos_[IntroType.TWEETS.ordinal()].y, ImageLoader.instance().get_twitter_intro_tweets(current_type_)));
	}
	
	public void switching_from() {
		parsed_tweets_ = null;
		top_intro_box_ = null;
	}
	
	public void make_next_box() {
		if (boxes_made_ >= NUM_CREATE) {
			delaying_ = false; // a bit of a hack should have bool called active or something
			// move to next scene
			SceneManager.instance().finished_tweet();
			return;
		}
		TweetBox b = null;
		int attempts = 0;
		TweetBox.TweetType tweet_type;
		ParsedTweet[] parsed_tweets;
		int index;
		
		// randomly decide between a follow tweet and a user tweet
		if ((int)VoteVisApp.instance().random(2) == 0) {
			tweet_type = TweetBox.TweetType.FOLLOW;
			parsed_tweets = parsed_follow_tweets_;
			index = followed_tweets_made_;
		} else {
			tweet_type = TweetBox.TweetType.USER;
			parsed_tweets = parsed_tweets_;
			index = user_tweets_made_;
		}
		
		do {
			++attempts;
			b = new TweetBox(VoteVisApp.instance(), VoteVisApp.instance().random(VoteVisApp.instance().width),
				VoteVisApp.instance().random(2 * VoteVisApp.instance().height / 3), 
				parsed_tweets[index], tweet_type);
			
			// bail out if in a bad position
			if (attempts > 10) {
				delaying_ = false;
				delaying_and_scrolling_ = true;
				scroll_delay_counter_ = VoteVisApp.instance().millis();
				//SceneManager.instance().finished_tweet();
				return;
			}
		} while (off_screen(b) || collides_with_existing(b));
		
		b.set_falling(false);
		b.init();
		BoxManager.instance().add_box(b);
		
		if (tweet_type == TweetBox.TweetType.FOLLOW)
			followed_tweets_made_++;
		else
			user_tweets_made_++;
		
		++boxes_made_;
		CREATE_DELAY = (int) VoteVisApp.instance().random(CREATE_DELAY_MIN, CREATE_DELAY_MAX);
		delay_counter_ = VoteVisApp.instance().millis();
		delaying_ = true;
	}
	
	ParsedTweet[] get_parsed_tweets() {
		switch (current_type_) {
		case FOOD:
			return VoteVisApp.instance().feed().getFoodTweets(NUM_CREATE);
		case ART:
			return VoteVisApp.instance().feed().getOutsideLandsTweets(NUM_CREATE);
		case ECO:
			return VoteVisApp.instance().feed().getEcoTweets(NUM_CREATE);
		case WINE:
			return VoteVisApp.instance().feed().getWineTweets(NUM_CREATE);
		case MUSIC:
		default:
			return VoteVisApp.instance().feed().getMusicTweets(NUM_CREATE);	
		}
	}
	
	ParsedTweet[] get_parsed_followed_tweets() {
		switch (current_type_) {
		case FOOD:
			return VoteVisApp.instance().feed().getFollowedFoodTweets(NUM_CREATE);
		case ART:
			return VoteVisApp.instance().feed().getFollowedOutsideLandsTweets(NUM_CREATE);
		case ECO:
			return VoteVisApp.instance().feed().getFollowedEcoTweets(NUM_CREATE);
		case WINE:
			return VoteVisApp.instance().feed().getFollowedWineTweets(NUM_CREATE);
		case MUSIC:
		default:
			return VoteVisApp.instance().feed().getFollowedMusicTweets(NUM_CREATE);
		}
	}
	
	public static TweetBoxFactory instance() {
		return instance_;
	}
	
	public boolean collides_with_existing(TweetBox new_box) {
		Iterator<Box> it = BoxManager.instance().boxes().iterator();
		
		while (it.hasNext()) {
			if (it.next().collides_with_box(new_box))
				return true;
		}
		
		return false;
	}
	
	public boolean off_screen(TweetBox b) {
		Rectangle rect = b.get_rectangle();
		return rect.intersects(top_rect) || 
			rect.intersects(bottom_rect) ||
			rect.intersects(left_rect) ||
			rect.intersects(right_rect);
	}
}
