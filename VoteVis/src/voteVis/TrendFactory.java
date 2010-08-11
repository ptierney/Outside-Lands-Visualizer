package voteVis;

import processing.core.*;

public class TrendFactory {
	private static TrendFactory instance_;
	private Type current_type_;
	private boolean[] created_types_;
	private int created_boxes_;
	private int[] top_five_;
	
	public TrendFactory() {
		instance_ = this;
		
		created_types_ = new boolean[5];
	}
	
	public void switched_to(Type type_) {
		current_type_ = type_;
		
		for (int i = 0; i < 5; ++i) {
			created_types_[i] = false;
		}
		
		top_five_ = BallotCounter.instance().get_top_five(current_type_);
		
		create_box();
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
	
	private int get_random_rank() {
		PApplet p_ = VoteVisApp.instance();
		while (true) {
			int ran_index = (int) p_.random(5);
			
			if (created_types_[ran_index] == false) {
				created_types_[ran_index] = true;
				return ran_index;
			}
		}
	}
	
	
	public void create_box() {
		int rank = get_random_rank();
		int index = top_five_[rank];
		
		Size b_size = Size.get_size_from_rank(rank);
		
		Box b = new TrendBox(current_type_, index, b_size,
			get_random_x_pos(b_size), 0);
		
		BoxManager.instance().add_box(b);
		
		++created_boxes_;
	}
	
	public static TrendFactory instance() {
		return instance_;
	}
	
	private int get_random_x_pos(Size size) {
		if (size == Size.S) {
			int ran_index = (int) VoteVisApp.instance().random(6);
			return Utility.get_aligned_position(Settings.UNIT_DIM, ran_index);
		// TODO: these should be aligned 
		} else if (size == Size.M) {
			int offset = Size.get_dim_from_size(Size.M) / 2 + Settings.BOX_GAP;
			return (int) VoteVisApp.instance().random(offset,
				VoteVisApp.instance().width - offset);
		} else {
			int offset = Size.get_dim_from_size(Size.L) / 2 + Settings.BOX_GAP;
			return (int) VoteVisApp.instance().random(offset,
					VoteVisApp.instance().width - offset);
		}
	}
}
