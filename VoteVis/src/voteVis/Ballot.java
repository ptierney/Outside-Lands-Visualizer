package voteVis;

import processing.core.*;

public class Ballot {
	private int[] votes_;
	private int user_id_;
	private PImage user_photo_;
	
	Ballot(int user_id_, int[] votes_, PImage user_photo_) {
		this.votes_ = votes_;
		this.user_id_ = user_id_;
		this.user_photo_ = user_photo_;
	}
	
	int get_index(Type type) {
		return votes_[type.ordinal()];
	}
	
	int[] votes() {
		return votes_;
	}
	
	int user_id() {
		return user_id_;
	}
	
	PImage user_photo() {
		return user_photo_;
	}
}
