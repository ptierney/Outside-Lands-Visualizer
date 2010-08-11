package voteVis;

public class SceneManager {
	private static SceneManager instance_;
	// the number rows to display before switching over to the billboard scene
	private static int NUM_VOTE_ROWS = VoteBoxFactory.BEGIN_TRANSITION_COUNT;
	private MoveSpeed move_speed_;
	private Type current_type_;
	private boolean display_all_labels_ = true;
	
	public SceneManager() {
		instance_ = this;
		move_speed_ = MoveSpeed.STOP;
		
		current_type_ = Type.MUSIC;
		
		start_cycle();
	}
	
	public void start_cycle() {
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
	
	private void move_from_trend_to_vote() {
		TrendFactory.instance().switching_from();
		increment_type();
		
		VoteVisApp.instance().create_worker_classes();
		
		display_all_labels_ = true;
		
		start_cycle();
	}
	
	// called when the billboards of type have been created
	public void finished_billboard(Type type) {
		move_from_billboard_to_trend();
	}
	
	public void finished_trend() {
		move_from_trend_to_vote();	
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
}
