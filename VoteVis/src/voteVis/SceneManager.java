package voteVis;

public class SceneManager {
	private static SceneManager instance_;
	// the number rows to display before switching over to the billboard scene
	private static int NUM_VOTE_ROWS = VoteBoxFactory.BEGIN_TRANSITION_COUNT;
	private MoveSpeed move_speed_;
	private Type current_type_;
	
	public SceneManager() {
		instance_ = this;
		move_speed_ = MoveSpeed.STOP;
		
		current_type_ = Type.MUSIC;
		
		start_cycle();
	}
	
	public void start_cycle() {
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
		BillboardFactory.instance().load_vote_to_top_transition(current_type_);
		BillboardFactory.instance().begin_transition();
	}
	
	public void move_from_billboard_to_trend() {
		
	}
	
	public void move_from_trend_to_vote() {
		
		
		
		increment_type();
	}
	
	private void increment_type() {
		int n = current_type_.ordinal();
		n++;
		if (n > Type.max_num())
			n = 0;
		current_type_ = Type.deserialize(n);
	}

}
