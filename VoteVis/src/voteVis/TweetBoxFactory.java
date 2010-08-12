package voteVis;

import processing.core.PApplet;
import twitpull.ParsedTweet;

public class TweetBoxFactory {
	private static TweetBoxFactory instance_; 
	private Type current_type_;
	private static final int NUM_CREATE = 10; // the number of tweet boxes to create;
	private int boxes_made_;
	private ParsedTweet[] parsed_tweets_ = null;
	
	public TweetBoxFactory() {
		instance_ = this;
		init();
	}
	
	private void init() {
		boxes_made_ = 0;
	}
	
	public void switched_to(Type current_type) {
		init();
		
		current_type_ = current_type;
		PApplet.print("Getting parsed tweets");
		while (parsed_tweets_ == null || parsed_tweets_.length == 0) {
			parsed_tweets_ = get_parsed_tweets();
			PApplet.print(".");
		}
		
		for (int i = 0; i < NUM_CREATE; ++i) {
			make_next_box();
		}
	}
	
	public void switching_from() {
		parsed_tweets_ = null;
	}
	
	public void make_next_box() {
		if (boxes_made_ > NUM_CREATE) {
			// move to next scene
			SceneManager.instance().finished_tweet();
			return;
		}
		TweetBox b = new TweetBox(VoteVisApp.instance(), VoteVisApp.instance().random(VoteVisApp.instance().width),
			VoteVisApp.instance().random(VoteVisApp.instance().height), 
			parsed_tweets_[boxes_made_]);
		
		b.set_falling(false);
		
		BoxManager.instance().add_box(b);
		
		++boxes_made_;
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
