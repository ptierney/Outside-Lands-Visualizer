package voteVis;

import processing.core.PApplet;
import java.awt.Rectangle;
import java.awt.Point;
import java.awt.Dimension;
import twitpull.ParsedTweet;
import java.util.*;

public class TweetBoxFactory {
	private static TweetBoxFactory instance_; 
	private Type current_type_;
	private static final int NUM_CREATE = 8; // the number of tweet boxes to create;
	private static int CREATE_DELAY;
	private static int CREATE_DELAY_MIN = 1000;
	private static int CREATE_DELAY_MAX = 1500;
	private boolean delaying_;
	private int delay_counter_;
	private int boxes_made_;
	private ParsedTweet[] parsed_tweets_ = null;
	private static int BOX_SIDE_INSET = 175;
	private ArrayList<TweetBox> tweet_boxes_;
	
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
		
		init();
	}
	
	private void init() {
		boxes_made_ = 0;
		delaying_ = false;
		tweet_boxes_ = new ArrayList<TweetBox>();
	}
	
	public void update() {
		if (delaying_) {
			if (VoteVisApp.instance().millis() - delay_counter_ > CREATE_DELAY)
				make_next_box();
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

		make_next_box();
	}
	
	public void switching_from() {
		parsed_tweets_ = null;
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
		do {
			++attempts;
			b = new TweetBox(VoteVisApp.instance(), VoteVisApp.instance().random(VoteVisApp.instance().width),
				VoteVisApp.instance().random(2 * VoteVisApp.instance().height / 3), 
				parsed_tweets_[boxes_made_]);
			
			// bail out if in a bad position
			if (attempts > 1000) {
				delaying_ = false;
				SceneManager.instance().finished_tweet();
				return;
			}
			
			
		} while (off_screen(b) || collides_with_existing(b));
		
		b.set_falling(false);
		b.init();
		tweet_boxes_.add(b);
		BoxManager.instance().add_box(b);
		
		++boxes_made_;
		CREATE_DELAY = (int) VoteVisApp.instance().random(CREATE_DELAY_MIN, CREATE_DELAY_MAX);
		delay_counter_ = VoteVisApp.instance().millis();
		delaying_ = true;
	}
	
	ParsedTweet[] get_parsed_tweets() {
		switch (current_type_) {
		case FOOD:
			return VoteVisApp.instance().feed().getMusicTweets(NUM_CREATE);
		case ART:
			return VoteVisApp.instance().feed().getArtTweets(NUM_CREATE);
		case ECO:
			return VoteVisApp.instance().feed().getEcoTweets(NUM_CREATE);
		case WINE:
			return VoteVisApp.instance().feed().getWineTweets(NUM_CREATE);
		case MUSIC:
		default:
			return VoteVisApp.instance().feed().getMusicTweets(NUM_CREATE);	
		}
	}
	
	public static TweetBoxFactory instance() {
		return instance_;
	}
	
	public boolean collides_with_existing(TweetBox new_box) {
		Iterator<TweetBox> it = tweet_boxes_.iterator();
		
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
