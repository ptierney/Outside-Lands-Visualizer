package voteVis;

import java.util.HashMap;
import processing.core.*;

public class UserManager {
	private static UserManager instance_;
	private VoteVisApp p_;
	private HashMap<Integer, User> user_map_;
	private int next_user_id_;
	
	public UserManager(VoteVisApp p_) {
		instance_ = this;
		this.p_ = p_;
		
		user_map_ = new HashMap<Integer, User>();
		next_user_id_ = 0;
	}
	
	public void add_user(String name, String image_name) {
		user_map_.put(next_user_id_, new User(next_user_id_, name, p_.loadImage(image_name)));
		next_user_id_++;
	}
	
	public PImage get_profile_photo(int user_id) {
		return user_map_.get(user_id).profile_photo();
	}
	
	public User get_user(int user_id) {
		return user_map_.get(user_id);
	}
	
	public static UserManager instance() {
		return instance_;
	}
	
	public void create_test_user() {
		add_user("Patrick Tierney", "profile_2-110.png");
	}
	
	public class User {
		private int id_;
		private String name_;
		private String first_name_;
		private String last_name_;
		private String last_initial_;
		private int display_count_;
		private PImage profile_photo_;
		
		public User(int id_, String name_, PImage profile_photo_) {
			this.id_ = id_;
			this.name_ = name_;
			String[] name_split = name_.split(" ");
			
			last_name_ = "";
			last_initial_ = "";
			
			if (name_split.length == 0) {
				first_name_ = "Anonymous";
			} else if (name_split.length == 1) {
				first_name_ = name_split[0];
			} else {
				first_name_ = name_split[0];
				last_name_ = name_split[1];
				last_initial_ = last_name_.substring(0, 1);
			}

			this.profile_photo_ = profile_photo_;
			display_count_ = 0;
		}
		
		public int id() {
			return id_;
		}
		
		public String name() {
			return first_name_ + " " + last_initial_;
		}
		
		public PImage profile_photo() {
			return profile_photo_;
		}
		
		public int display_count() {
			return display_count_;
		}
		
		public void display() {
			display_count_++;
		}
	}
}
