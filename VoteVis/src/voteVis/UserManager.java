package voteVis;



public class UserManager {
	private VoteVisApp p_;
	
	public UserManager(VoteVisApp p_) {
		this.p_ = p_;
	}
	
	public void add_user(String name, String image_name) {
		
	}
	
	public PImage get_profile_photo(int id) {
		
	}
	
	public class User {
		private int id_;
		private String name_;
		private int display_count_;
		
		public User(int id_, String name_) {
			this.id_ = id_;
			this.name_ = name_;
			display_count_ = 0;
		}
		
		public int id() {
			return id_;
		}
		
		public String name() {
			return name_;
		}
		
		public int display_count() {
			return display_count_;
		}
	}
}
