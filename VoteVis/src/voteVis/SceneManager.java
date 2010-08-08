package voteVis;

public class SceneManager {
	private static SceneManager instance_;
	private static int NUM_VOTE_ROWS = 8;
	private boolean move_boxes_;
	
	public SceneManager() {
		instance_ = this;
		move_boxes_ = false;
		
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

}
