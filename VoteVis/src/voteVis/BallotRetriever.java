package voteVis;

import processing.core.*;
import java.util.*;

public class BallotRetriever {
	private static BallotRetriever instance_;
	public static final String SERVER_NAME = "mmmii.net";
	
	private static final String URL_BASE = "http://" + SERVER_NAME + "/osl-test/";
	private static final String VOTE_FILE = "server-get.txt";
	
	private static final String TEXT_MASTER = "http://www.glebden.com/osl/ipad/1pg/get_data.php";
	private static final String IMAGE_MASTER_BASE = "http://www.glebden.com/osl/ipad/1pg/user_photos/";
	
	public BallotRetriever() {
		instance_ = this;
	}
	
	public void get_ballots_from_server() {
		String[] s = VoteVisApp.instance().loadStrings(TEXT_MASTER);
		
		merge_ballots_with_existing(get_ballots_from_strings(s));
	}
	
	ArrayList<Ballot> get_ballots_from_strings(String[] strings) {
		ArrayList<Ballot> ballots = new ArrayList<Ballot>();
		
		for (int i = 0; i < strings.length; ++i) {
			String[] string_split = strings[i].split("#");
			
			/*
			for (int k = 0; k < string_split.length; k++) {
				PApplet.println(string_split[k]);
			}
			*/
			
			long id = Long.parseLong(string_split[0]);
			String user_name = string_split[1];
			
			Gender gender;
			if (string_split[2].equalsIgnoreCase("m"))
				gender = Gender.MALE;
			else
				gender = Gender.FEMALE;
			
			int[] votes = new int[5];
			votes[Type.MUSIC.ordinal()] = Integer.parseInt(string_split[3]) - 1;
			votes[Type.FOOD.ordinal()] = Integer.parseInt(string_split[4]) - 1;
			votes[Type.WINE.ordinal()] = Integer.parseInt(string_split[5]) - 1;
			votes[Type.ECO.ordinal()] = Integer.parseInt(string_split[6]) - 1;
			votes[Type.ART.ordinal()] = Integer.parseInt(string_split[7]) - 1;
				
			String image;
			
			//PApplet.println(string_split.length);
			
			if (string_split.length > 8)
				image = IMAGE_MASTER_BASE + Long.parseLong(string_split[8]) + ".jpg";
			else {
				if (gender == Gender.MALE)
					image = "default-male.png";
				else
					image = "default-female.png";
			}
			
			ballots.add(new Ballot(id, user_name, gender, votes, image));
			
			//PApplet.println("Added ballot");
		}
		
		return ballots;
	}
	
	private void merge_ballots_with_existing(ArrayList<Ballot> ballots) {
		Iterator<Ballot> it = ballots.iterator();
		
		BallotCounter bc = BallotCounter.instance();
		
		while (it.hasNext()) {
			bc.add_ballot(it.next());
		}
	}
	
	public static BallotRetriever instance() {
		return instance_;
	}
	
	
}
