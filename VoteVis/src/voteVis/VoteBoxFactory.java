package voteVis;

import java.util.ArrayList;
import java.util.Iterator;

import processing.core.*;

public class VoteBoxFactory {
	private VoteVisApp p_;
	private static int BOX_STAGGER = Settings.UNIT_DIM;
	private ArrayList<VoteRow> vote_rows_;

	public VoteBoxFactory(VoteVisApp p_) {
		this.p_ = p_;
		vote_rows_ = new ArrayList<VoteRow>();
	}

	// this make all the boxes at the
	void make_vote_row(Ballot ballot) {
		ArrayList<Box> row = new ArrayList<Box>();
		
		// TODO: should be profile box
		Box profile = new VoteBox(p_, Utility.get_aligned_position(Settings.UNIT_DIM, 0), 
			-BOX_STAGGER * 5, Type.MUSIC, ballot.votes()[Type.MUSIC.ordinal()]);
		
		p_.manager().add_box(profile);
		row.add(profile);
		
		Box v1 = new VoteBox(p_, Utility.get_aligned_position(Settings.UNIT_DIM, 1), 
			-BOX_STAGGER * 4, Type.MUSIC, ballot.votes()[Type.MUSIC.ordinal()]);
		
		p_.manager().add_box(v1);
		row.add(v1);
		
		Box v2 = new VoteBox(p_, Utility.get_aligned_position(Settings.UNIT_DIM, 2), 
			-BOX_STAGGER * 3, Type.FOOD, ballot.votes()[Type.FOOD.ordinal()]);
		
		p_.manager().add_box(v2);
		row.add(v2);
			
		Box v3 = new VoteBox(p_, Utility.get_aligned_position(Settings.UNIT_DIM, 3), 
			-BOX_STAGGER * 2, Type.WINE, ballot.votes()[Type.WINE.ordinal()]);
		
		p_.manager().add_box(v3);
		row.add(v3);

		Box v4 = new VoteBox(p_, Utility.get_aligned_position(Settings.UNIT_DIM, 4), 
			-BOX_STAGGER, Type.ECO, ballot.votes()[Type.ECO.ordinal()]);
		
		p_.manager().add_box(v4);
		row.add(v4);
		
		Box v5 = new VoteBox(p_, Utility.get_aligned_position(Settings.UNIT_DIM, 5), 
			0, Type.ART, ballot.votes()[Type.ART.ordinal()]);
		
		p_.manager().add_box(v5);
		row.add(v5);
		
		vote_rows_.add(new VoteRow(row));
	}
	
	public void drop_bottom_row() {
		if (vote_rows_.size() == 0)
			return;
		
		vote_rows_.get(0).drop_row();
		vote_rows_.remove(0);
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
			
			p_.manager().set_all_falling();
			
			row_.clear();
		}
	}
}
