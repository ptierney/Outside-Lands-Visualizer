package voteVis;

import java.util.ArrayList;

import processing.core.*;
//import processing.opengl.*;
//import javax.media.opengl.GL;
import voteVis.RotateBoxTransition.Axis;
import control.*;
import twitpull.*;

@SuppressWarnings("serial")
public class VoteVisApp extends PApplet {
	private static VoteVisApp instance_;
	private PImage banner_;
	
	private static final int VERTICAL_SHIFT = 23; // equal to the # of px of OS X menu
	
	// Testing vars
	private Box box_;
	private PFont text_font_;
	private BoxTransition test_box_transition_;
	private int last_frame_;
	
	private boolean waiting_;
	
	private TweetFeed feed_;
	
	PImage foo;

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
		BallotRetriever ballot_retriever_ = new BallotRetriever();
		
		create_worker_classes();
		
		/*
		for (int i = 0; i < 30; ++i) {
			BallotCounter.instance().add_random_ballot();
		}
		*/
		
		setup_tweet_feed();
		
		// SceneManager will start the cycle
		@SuppressWarnings("unused")
		SceneManager scene_manager_ = new SceneManager();
		
		last_frame_ = millis();
	}
	
	public void create_worker_classes() {
		@SuppressWarnings("unused")
		BoxManager manager_ = new BoxManager(this);
		@SuppressWarnings("unused")
		BallotCounter counter_ = new BallotCounter(this);
		@SuppressWarnings("unused")
		VoteBoxFactory vote_factory_ = new VoteBoxFactory(this);
		@SuppressWarnings("unused")
		UserManager user_manager_ = new UserManager(this);
		@SuppressWarnings("unused")
		BillboardFactory billboard_factory_ = new BillboardFactory();
		@SuppressWarnings("unused")
		TrendFactory trend_factory_ = new TrendFactory();
		@SuppressWarnings("unused")
		TweetBoxFactory tweet_box_factory_ = new TweetBoxFactory();
	}

	@Override
	public void draw() {
		if (waiting_)
			return;
		
		background(Settings.instance().background_color());

		BoxManager manager = BoxManager.instance();
		manager.update_boxes();
		
		last_frame_ = millis();
		
		manager.draw_boxes();
		manager.clean_up();
		
		BillboardFactory bfact = BillboardFactory.instance();
		bfact.update();
		bfact.draw();
		
		VoteBoxFactory.instance().update();
		
		TrendFactory.instance().update();
		
		TweetBoxFactory.instance().update();
		
		SceneManager.instance().update();
		
		pushMatrix();
			translate(0, VERTICAL_SHIFT);
			BannerDisplay.instance().draw();
		popMatrix();
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
		} else if (key == '3') {
			PApplet.println("Requesting tweets: " + millis());
			ParsedTweet[] f = feed_.getMusicTweets(7);
			PApplet.println(f.length);
			for (int i = 0; i < f.length; ++i) {
				PApplet.println(f[i].getText());
				PApplet.println(f[i].getUserimage().getAbsolutePath());
				PApplet.println(f[i].getFrom_user());
			}
			PApplet.println("Done requesting tweets: " + millis());
		}
	}
	
	public static VoteVisApp instance() {
		return instance_;
	}
	
	public int last_frame() {
		return last_frame_;
	}
	
	private void setup_tweet_feed() {
		feed_ = new TweetFeed();
		String server = "http://qa-twitterfeed.mryouth.com/TwitQueue/ApprovedTwitFeedJson";
		int pollinterval = 5;
		int mincache = 20;
		int maxcache = 500;
		feed_.startPolling(server, pollinterval, mincache, maxcache);
	}
	
	public TweetFeed feed() {
		return feed_;
	}
	
	
	 public static void main(String args[]) {
		 PApplet.main(new String[] { "--present", "voteVis.VoteVisApp" });
	 }
	 
	 
	 /*
	 public void init() {
		 frame.setUndecorated(true);
		 
		 super.init();
	 }
	 */
}
