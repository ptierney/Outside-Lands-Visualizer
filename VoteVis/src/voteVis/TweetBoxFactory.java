package voteVis;

import processing.core.PApplet;
import twitpull.ParsedTweet;

public class TweetBoxFactory {
	private static TweetBoxFactory instance_; 
	private Type current_type_;
	private static final int NUM_CREATE = 4; // the number of tweet boxes to create;
	private static int CREATE_DELAY;
	private static int CREATE_DELAY_MIN = 1500;
	private static int CREATE_DELAY_MAX = 3000;
	private boolean delaying_;
	private int delay_counter_;
	private int boxes_made_;
	private ParsedTweet[] parsed_tweets_ = null;
	private static int BOX_SIDE_INSET = 175;
	
	
	public TweetBoxFactory() {
		instance_ = this;
		init();
	}
	
	private void init() {
		boxes_made_ = 0;
		delaying_ = false;
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
		
		TweetBox b = new TweetBox(VoteVisApp.instance(), VoteVisApp.instance().random(BOX_SIDE_INSET, 
			VoteVisApp.instance().width - BOX_SIDE_INSET),
			VoteVisApp.instance().random(VoteVisApp.instance().height / 2), 
			parsed_tweets_[boxes_made_]);
		
		b.set_falling(false);
		
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
}
