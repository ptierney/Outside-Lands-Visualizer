package voteVis;

public class SceneManager {
	private static SceneManager instance_;
	// the number rows to display before switching over to the billboard scene
	private static int NUM_VOTE_ROWS = VoteBoxFactory.BEGIN_TRANSITION_COUNT;
	private MoveSpeed move_speed_;
	private Type current_type_;
	private boolean display_all_labels_ = true;
	private boolean twitter_mode_ = false;
	private boolean moving_all_off_screen_ = false;
	private Box top_box_;
	private boolean outside_turnoff_ = false;
	
	public SceneManager() {
		instance_ = this;
		move_speed_ = MoveSpeed.STOP;
		
		current_type_ = Type.MUSIC;
		
		start_cycle();
	}
	
	public void start_cycle() {
		BallotRetriever.instance().get_ballots_from_server();
		
		display_all_labels_ = true;
		VoteBoxFactory.instance().switched_to();
	}
	
	public MoveSpeed move_speed() {
		return move_speed_;
	}
	
	public void set_move_speed(MoveSpeed speed) {
		this.move_speed_ = speed;
	}
	
	public static SceneManager instance() {
		return instance_;
	}
	
	public void move_from_vote_to_billboard() {
		display_all_labels_ = false;
		BillboardFactory.instance().load_vote_to_top_transition(current_type_);
		BillboardFactory.instance().begin_transition();
	}
	
	private void move_from_billboard_to_trend() {
		// while the billboards are transitioning, 
		// VoteBoxFactory is still "active". Elements are being shown
		VoteBoxFactory.instance().switching_from();
		TrendFactory.instance().switched_to(current_type_);
	}
	
	private void move_from_trend_to_tweet() {
		TrendFactory.instance().switching_from();
		
		// this is broken
		if (current_type_ == Type.ART) {
			move_from_tweet_to_vote();
			return;
		}
		
		TweetBoxFactory.instance().switched_to(current_type_);
		twitter_mode_ = true;
		
		if (current_type_ == Type.ART)
			outside_turnoff_ = true;
	}
	
	private void move_from_tweet_to_vote() {
		TweetBoxFactory.instance().switching_from();
		
		outside_turnoff_ = false;
		
		move_all_off_screen();
	}
	
	// called when the billboards of type have been created
	public void finished_billboard(Type type) {
		move_from_billboard_to_trend();
	}
	
	public void finished_trend() {
		move_from_trend_to_tweet();
	}
	
	public void finished_tweet() {
		move_from_tweet_to_vote();
	}
	
	private void increment_type() {
		int n = current_type_.ordinal();
		n++;
		if (n > Type.max_num())
			n = 0;
		current_type_ = Type.deserialize(n);
	}
	
	public Type current_type() {
		return current_type_;
	}
	
	public boolean display_all_labels() {
		return display_all_labels_;
	}
	
	public boolean twitter_mode() {
		return twitter_mode_;
	}
	
	public void update() {
		if (moving_all_off_screen_) {
			if (top_box_.y() - top_box_.get_height() / 2 > VoteVisApp.instance().height)
				all_moved_off_screen();
		}
	}
	
	private void move_all_off_screen() {
		top_box_ = (Box) BoxManager.instance().boxes().toArray()[ BoxManager.instance().boxes().size() - 1];
		moving_all_off_screen_ = true;
	}
	
	private void all_moved_off_screen() {
		moving_all_off_screen_ = false;
		
		twitter_mode_ = false;
		
		increment_type();
		
		VoteVisApp.instance().create_worker_classes();
		
		display_all_labels_ = true;
		
		start_cycle();
	}
	
	public boolean outside_turnoff() {
		return outside_turnoff_;
	}
}
