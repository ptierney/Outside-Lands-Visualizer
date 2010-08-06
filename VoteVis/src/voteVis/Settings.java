package voteVis;

import processing.core.*;

public class Settings {
	private VoteVisApp p_;
	
	public static int UNIT_DIM = 110;
	public static int VOTE_PANE_DIM = 100;
	public static int BOX_GAP = 15;
	public static int FALL_SPEED = 10;
	public static int VOTE_BOX_SIDE_DIM = UNIT_DIM;
	public static String DEFAULT_PROFILE_IMAGE = "profile_1-110.png";
	
	// TODO: update this to real values
	public static int NUM_ECO = 10;
	public static int NUM_MUSIC = 10;
	public static int NUM_ART = 10;
	public static int NUM_FOOD = 10;
	public static int NUM_WINE = 10;
	
	private int music_color_;
	private int eco_color_;
	private int wine_color_;
	private int food_color_;
	private int art_color_;
	
	
	private PFont vote_box_font_;
	public static int VOTE_BOX_FONT_SIZE = 18;
	
	public Settings(VoteVisApp p_) {
		this.p_ = p_;
		
		music_color_ = p_.color(237, 30, 121);
		eco_color_ = p_.color(114, 160, 42);
		wine_color_ = p_.color(102, 14, 60);
		food_color_ = p_.color(247, 147, 30);
		art_color_ = p_.color(49, 193, 182);
		
		// TODO: load this font with processing
		vote_box_font_ = p_.loadFont("GillSansMT-18.vlw");
	}
	
	public static int HALF_UNIT_DIM () {
		return UNIT_DIM / 2;
	}
	
	public int get_vote_box_color_for_type(Type type) {
		switch (type) {
		case MUSIC:
			return music_color_;
		case ECO:
			return eco_color_;
		case WINE:
			return wine_color_;
		case FOOD:
			return food_color_;
		case ART:
		default:
			return art_color_;
		}
	}
	
	public PFont get_vote_box_font() {
		return vote_box_font_;
	}

}
