package voteVis;

import java.util.ArrayList;
import java.util.Iterator;

import processing.core.*;

public class VoteBoxFactory implements BoxListener {
	private static VoteBoxFactory instance_;
	private VoteVisApp p_;
	private static int BOX_STAGGER = Settings.UNIT_DIM;
	private ArrayList<VoteRow> vote_rows_;
	private ArrayList<Box> current_row_;
	public static int CREATE_DELAY = 250; // in millis;
	private int create_delay_counter_;
	private boolean delaying_create_;
	private static int START_SCROLLING_HEIGHT = 4; // start scrolling after the 4th row has been added
	public static int BEGIN_TRANSITION_COUNT = 6;
	private int row_count_ = 0; // the number of rows created
	private VoteRow bottom_stop_row_ = null;
	private Box bottom_stop_box_ = null; // I use this to determine when to stop
	
	public ProfileBox profile_test;

	public VoteBoxFactory(VoteVisApp p_) {
		instance_ = this;
		this.p_ = p_;
		vote_rows_ = new ArrayList<VoteRow>();
		delaying_create_ = false;
	}
	
	// this is called by the SceneManager when we've transitioned into
	// a VoteBox scene
	public void switched_to() {
		if (vote_rows_.size() != 0) {
			PApplet.println("Error in VoteBoxFactory switched_to");
			VoteVisApp.instance().exit();
		}
		row_count_ = 0;
		bottom_stop_box_ = null;
		bottom_stop_row_ = null;
		
		SceneManager.instance().set_move_speed(MoveSpeed.STOP);
		
		make_vote_row(BallotCounter.instance().get_next_ballot());
	}

	// this make all the boxes at the
	public void make_vote_row(Ballot ballot) {
		 current_row_ = new ArrayList<Box>();
		
		Box v1 = new VoteBox(p_, Utility.get_aligned_position(Settings.UNIT_DIM, 1), 
			0, Type.MUSIC, ballot.votes()[Type.MUSIC.ordinal()]);
		
		v1.set_visible(false);
		BoxManager.instance().add_box(v1);
		
		Box v2 = new VoteBox(p_, Utility.get_aligned_position(Settings.UNIT_DIM, 2), 
			0, Type.FOOD, ballot.votes()[Type.FOOD.ordinal()]);
		
		v2.set_visible(false);
		BoxManager.instance().add_box(v2);
			
		Box v3 = new VoteBox(p_, Utility.get_aligned_position(Settings.UNIT_DIM, 3), 
			0, Type.WINE, ballot.votes()[Type.WINE.ordinal()]);
		
		v3.set_visible(false);
		BoxManager.instance().add_box(v3);

		Box v4 = new VoteBox(p_, Utility.get_aligned_position(Settings.UNIT_DIM, 4), 
			0, Type.ECO, ballot.votes()[Type.ECO.ordinal()]);
		
		v4.set_visible(false);
		BoxManager.instance().add_box(v4);
		
		Box v5 = new VoteBox(p_, Utility.get_aligned_position(Settings.UNIT_DIM, 5), 
			0, Type.ART, ballot.votes()[Type.ART.ordinal()]);
		
		v5.set_visible(false);
		BoxManager.instance().add_box(v5);

		
		// this should be drawn on top of the other boxes
		Box profile = new ProfileBox(p_, Utility.get_aligned_position(Settings.UNIT_DIM, 0),
			0, 0);
		BoxManager.instance().add_box(profile);
		
		current_row_.add(profile);
		current_row_.add(v1);
		current_row_.add(v2);
		current_row_.add(v3);
		current_row_.add(v4);
		current_row_.add(v5);
		//profile_test = (ProfileBox) profile;
		
		vote_rows_.add(new VoteRow(current_row_));
		
		if (vote_rows_.size() > 4)
			vote_rows_.remove(0); // drop the last row
		
		row_count_++;
	}
	
	public void drop_bottom_row() {
		if (vote_rows_.size() == 0)
			return;
		
		vote_rows_.get(0).drop_row();
		vote_rows_.remove(0);
	}
	
	public VoteRow pop_bottom_row() {
		if (vote_rows_.size() == 0)
			return null;
		
		VoteRow row = vote_rows_.get(0);
		vote_rows_.remove(0);
		return row;
	}
	
	public void display_top_row() {
		Iterator<Box> it = current_row_.iterator();
		while (it.hasNext()) {
			it.next().set_visible(true);
		}
	}
	
	public class VoteRow {
		ArrayList<Box> row_;
		
		public VoteRow(ArrayList<Box> row_) {
			this.row_ = row_;
		}
		
		public void drop_row() {
			Iterator<Box> it = row_.iterator();
			while (it.hasNext()) {
				it.next().fall_off_screen();
			}
			
			BoxManager.instance().set_all_falling();
			
			row_.clear();
		}
		
		ArrayList<Box> row() {
			return row_;
		}
	}
	
	public static VoteBoxFactory instance() {
		return instance_;
	}

	public void setup_finished(Box box) {
		// so far only called by ProfileBoxes when they have finished
		// contracting their frame
		if (row_count_ == BEGIN_TRANSITION_COUNT) { // start vana white transition
			SceneManager.instance().move_from_vote_to_billboard();
			return;
		}
		
		create_delay_counter_ = VoteVisApp.instance().millis();
		delaying_create_ = true;
	}
	
	public void update() {
		if (row_count_ >= START_SCROLLING_HEIGHT) {
			SceneManager.instance().set_move_speed(MoveSpeed.NORMAL);
		}
		
		if (bottom_stop_box_ != null && 
			(bottom_stop_box_.y() + bottom_stop_box_.get_height() / 2 + Settings.BOX_GAP - 5 // dunno why the five is here but it works
			> VoteVisApp.instance().height)) {
			SceneManager.instance().set_move_speed(MoveSpeed.STOP);
		}
		
		if (!delaying_create_) 
			return; // no need to do anything unless we're waiting to create a new row
		
		if (VoteVisApp.instance().millis() - create_delay_counter_ > CREATE_DELAY) {
			make_vote_row(BallotCounter.instance().get_next_ballot());
			delaying_create_ = false;
		}
		
		if (row_count_ == BEGIN_TRANSITION_COUNT - 3 && bottom_stop_row_ == null) {
			bottom_stop_row_ = vote_rows_.get(vote_rows_.size() - 1);
			bottom_stop_box_ = bottom_stop_row_.row().get(1); // skip the profile box that could be weird
		}
	}
}
