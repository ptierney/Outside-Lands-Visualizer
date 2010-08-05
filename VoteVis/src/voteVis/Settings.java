package voteVis;

import processing.core.*;

public class Settings {
	private VoteVisApp p_;
	
	public static int UNIT_DIM = 110;
	public static int BOX_GAP = 15;
	public static int FALL_SPEED = 10;
	public static int VOTE_BOX_SIDE_DIM = UNIT_DIM;
	
	public Settings(VoteVisApp p_) {
		this.p_ = p_;
	}
	
	public static int HALF_UNIT_DIM () {
		return UNIT_DIM / 2;
	}
	
	/*
	public static TextState get_vote_box_text_state(Type type, int index) {
		
		
	}
	
	public static PhotoState get_vote_box_photo_state(Type type, int index) {
		
	}
	*/

}
