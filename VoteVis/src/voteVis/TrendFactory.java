package voteVis;

public class TrendFactory {
	private static TrendFactory instance_;
	private Type current_type_;
	private boolean[] created_types_;
	private int created_boxes_;
	private int[] top_five_;
	
	public TrendFactory() {
		instance_ = this;
	}
	
	public void switched_to(Type type_) {
		current_type_ = type_;
		
		for (int i = 0; i < 5; ++i) {
			created_types_[i] = false;
		}
		
		top_five_ = BallotCounter.instance().get_top_five(current_type_);
	}
	
	public void switching_from() {
		
	}
	
	public void box_collided(TrendBox box) {
		if (created_boxes_ == 5) {
			SceneManager.instance().finished_trend();
			return;
		}
		
		create_box();
	}
	
	public void create_box() {
		
	}
	
	public static TrendFactory instance() {
		return instance_;
	}
	
	
	
}
