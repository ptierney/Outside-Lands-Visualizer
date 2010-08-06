package voteVis;

import java.util.ArrayList;
import java.util.HashMap;
import processing.core.*;

public class BallotCounter {
	private ArrayList<Ballot> ballots_;
	private VoteVisApp p_;
	private PImage stock_profile_;
	
	BallotCounter(VoteVisApp p_) {
		ballots_ = new ArrayList<Ballot>();
		this.p_ = p_;
		stock_profile_ = p_.loadImage(Settings.DEFAULT_PROFILE_IMAGE);
	}
	
	public ArrayList<Ballot> ballots() {
		return ballots_;
	}
	
	public void add_ballot(Ballot ballot) {
		ballots_.add(ballot);
	}
	
	// generates a random ballot for testing
	public void add_random_ballot() {
		int[] votes = new int[5];
		
		// iterate through the 5 types of things to vote on
		votes[Type.MUSIC.ordinal()] = (int) p_.random(Settings.NUM_MUSIC);
		votes[Type.ART.ordinal()] = (int) p_.random(Settings.NUM_ART);
		votes[Type.FOOD.ordinal()] = (int) p_.random(Settings.NUM_FOOD);
		votes[Type.WINE.ordinal()] = (int) p_.random(Settings.NUM_WINE);
		votes[Type.ECO.ordinal()] = (int) p_.random(Settings.NUM_ECO);
		
		int user_id = ballots_.size() + 1;
		
		add_ballot(new Ballot(user_id, votes, stock_profile_));
	}
	
	public int[] get_top_five(Type type) {
		PApplet.println("get_top_five_top");
		// This holds the index (which musician, etc), and the number of votes for him/her.
		HashMap<Integer, Integer> counter = new HashMap<Integer, Integer>();
		PApplet.println("about to for");
		for (int i = 0; i < ballots_.size(); ++i) {
			Integer key = ballots_.get(i).votes()[type.ordinal()];
			Integer last_number = counter.get(key);
			counter.put(key, new Integer(last_number + 1));
		}
		
		int[] ret_int = new int[5];
		PApplet.println("about to for 2");
		for(int i = 0; i < 5; ++i) {
			Integer top_key = 0;
			Integer top_amount = 0;
			
			for (Integer key : counter.keySet()) {
				if (counter.get(key) > top_amount) {
					top_key = key;
					top_amount = counter.get(key);
				}
			}
			ret_int[i] = top_key.intValue();
			if (counter.containsKey(top_key)) // this is needed if there are under 5 ballots
					counter.remove(top_key);
		}
		
		return ret_int;
	}
	
	
}