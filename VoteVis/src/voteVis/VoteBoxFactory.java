package voteVis;

import java.util.ArrayList;
import java.util.Iterator;

import processing.core.*;

public class VoteBoxFactory {
	private static VoteBoxFactory instance_;
	private VoteVisApp p_;
	private static int BOX_STAGGER = Settings.UNIT_DIM;
	private ArrayList<VoteRow> vote_rows_;
	private ArrayList<Box> current_row_;
	
	public ProfileBox profile_test;

	public VoteBoxFactory(VoteVisApp p_) {
		instance_ = this;
		this.p_ = p_;
		vote_rows_ = new ArrayList<VoteRow>();
	}
	

	// this make all the boxes at the
	public void make_vote_row(Ballot ballot) {
		 current_row_ = new ArrayList<Box>();
		
		Box v1 = new VoteBox(p_, Utility.get_aligned_position(Settings.UNIT_DIM, 1), 
			0, Type.MUSIC, ballot.votes()[Type.MUSIC.ordinal()]);
		
		v1.set_visible(false);
		BoxManager.instance().add_box(v1);
		current_row_.add(v1);
		
		Box v2 = new VoteBox(p_, Utility.get_aligned_position(Settings.UNIT_DIM, 2), 
			0, Type.FOOD, ballot.votes()[Type.FOOD.ordinal()]);
		
		v2.set_visible(false);
		BoxManager.instance().add_box(v2);
		current_row_.add(v2);
			
		Box v3 = new VoteBox(p_, Utility.get_aligned_position(Settings.UNIT_DIM, 3), 
			0, Type.WINE, ballot.votes()[Type.WINE.ordinal()]);
		
		v3.set_visible(false);
		BoxManager.instance().add_box(v3);
		current_row_.add(v3);

		Box v4 = new VoteBox(p_, Utility.get_aligned_position(Settings.UNIT_DIM, 4), 
			0, Type.ECO, ballot.votes()[Type.ECO.ordinal()]);
		
		v4.set_visible(false);
		BoxManager.instance().add_box(v4);
		current_row_.add(v4);
		
		Box v5 = new VoteBox(p_, Utility.get_aligned_position(Settings.UNIT_DIM, 5), 
			0, Type.ART, ballot.votes()[Type.ART.ordinal()]);
		
		v5.set_visible(false);
		BoxManager.instance().add_box(v5);
		current_row_.add(v5);
		
		// this should be drawn on top of the other boxes
		Box profile = new ProfileBox(p_, Utility.get_aligned_position(Settings.UNIT_DIM, 0),
			0, 0);
			
		BoxManager.instance().add_box(profile);
		current_row_.add(profile);
		profile_test = (ProfileBox) profile;
		
		vote_rows_.add(new VoteRow(current_row_));
	}
	
	public void drop_bottom_row() {
		if (vote_rows_.size() == 0)
			return;
		
		vote_rows_.get(0).drop_row();
		vote_rows_.remove(0);
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
	}
	
	public static VoteBoxFactory instance() {
		return instance_;
	}
}
