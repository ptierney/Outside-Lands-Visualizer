/* Copyright (c) 2010, Patrick Tierney 
 * All rights reserved.
 * 
 * This file is part of the "Live Top Five" vote visualizer 
 * created for Mr Youth August 2010 
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A
 * PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED
 * TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 * */

package voteVis;

import java.util.HashMap;
import processing.core.*;

public class UserManager {
	private static UserManager instance_;
	private VoteVisApp p_;
	private HashMap<Long, User> user_map_;
	
	public UserManager(VoteVisApp p_) {
		instance_ = this;
		this.p_ = p_;
		
		user_map_ = new HashMap<Long, User>();
	}
	
	public void add_user(Long ballot_id, String name, PImage photo) {
		user_map_.put(ballot_id, new User(ballot_id, name, photo));
	}
	
	public PImage get_profile_photo(int user_id) {
		return user_map_.get(user_id).profile_photo();
	}
	
	public User get_user(long user_id) {
		return user_map_.get(user_id);
	}
	
	public static UserManager instance() {
		return instance_;
	}
	
	public class User {
		private long id_;
		private String name_;
		private String first_name_;
		private String last_name_;
		private String last_initial_;
		private int display_count_;
		private PImage profile_photo_;
		
		public User(long id_, String name_, PImage profile_photo_) {
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
		
		public Long id() {
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
