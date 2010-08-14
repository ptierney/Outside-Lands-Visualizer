package voteVis;

import processing.core.*;

public class Ballot {
	private int[] votes_;
	private int user_id_;
	private PImage user_photo_ = null;
	private String user_name_;
	private Gender gender_;
	private String image_string_;
	
	Ballot(int user_id_, String user_name_, Gender gender_, int[] votes_, String image_string_) {
		this.votes_ = votes_;
		this.user_id_ = user_id_;
		this.image_string_ = image_string_;
		this.user_name_ = user_name_;
		this.gender_ = gender_;
	}
	
	public void load_image() {
		user_photo_ = VoteVisApp.instance().loadImage(image_string_);
	}
	
	int get_index(Type type) {
		return votes_[type.ordinal()];
	}
	
	Gender gender() {
		return gender_;
	}
	
	int[] votes() {
		return votes_;
	}
	
	int user_id() {
		return user_id_;
	}
	
	String user_name() {
		return user_name_;
	}
	
	PImage user_photo() {
		return user_photo_;
	}
}
