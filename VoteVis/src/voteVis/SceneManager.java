package voteVis;

public class SceneManager {
	private static SceneManager instance_;
	private static int NUM_VOTE_ROWS = 8;
	private boolean move_boxes_;
	private Type next_type_;
	
	public SceneManager() {
		instance_ = this;
		move_boxes_ = false;
		
		next_type_ = Type.MUSIC;
		
		start_cycle();
	}
	
	public void start_cycle() {
		VoteBoxFactory.instance().switched_to();
	}
	
	public boolean move_boxes() {
		return move_boxes_;
	}
	
	public void set_move_boxes(boolean move_boxes_) {
		this.move_boxes_ = move_boxes_;
	}
	
	public static SceneManager instance() {
		return instance_;
	}
	
	public void move_from_vote_to_billboard() {
		BillboardFactory.instance().load_vote_to_top_transition(next_type_);
		BillboardFactory.instance().begin_transition();
		
		int n = next_type_.ordinal();
		n++;
		if (n > Type.max_num())
			n = 0;
		next_type_ = Type.deserialize(n);
	}

}
