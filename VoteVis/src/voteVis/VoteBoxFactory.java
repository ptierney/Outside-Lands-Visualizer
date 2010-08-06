package voteVis;

import processing.core.*;

public class VoteBoxFactory {
	private VoteVisApp p_;
	private static int BOX_STAGGER = Settings.UNIT_DIM;

	public VoteBoxFactory(VoteVisApp p_) {
		this.p_ = p_;
	}

	// this make all the boxes at the
	void make_vote_row(Ballot ballot) {
		// create profile box
		
		
		p_.manager().add_box(
			new VoteBox(p_, Utility.get_aligned_position(Settings.UNIT_DIM, 1), 
			-BOX_STAGGER * 4, Type.MUSIC, ballot.votes()[Type.MUSIC.ordinal()]));
		
		p_.manager().add_box(
			new VoteBox(p_, Utility.get_aligned_position(Settings.UNIT_DIM, 2), 
			-BOX_STAGGER * 3, Type.FOOD, ballot.votes()[Type.FOOD.ordinal()]));

		p_.manager().add_box(
			new VoteBox(p_, Utility.get_aligned_position(Settings.UNIT_DIM, 3), 
			-BOX_STAGGER * 2, Type.WINE, ballot.votes()[Type.WINE.ordinal()]));

		p_.manager().add_box(
			new VoteBox(p_, Utility.get_aligned_position(Settings.UNIT_DIM, 4), 
			-BOX_STAGGER, Type.ECO, ballot.votes()[Type.ECO.ordinal()]));
		
		p_.manager().add_box(
			new VoteBox(p_, Utility.get_aligned_position(Settings.UNIT_DIM, 5), 
			0, Type.ART, ballot.votes()[Type.ART.ordinal()]));
	}
}
