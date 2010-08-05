package voteVis;

import processing.core.*;

public class ImageLoader {
	private VoteVisApp p_;

	private PImage[][] candidate_images_;
	private String[][] candidate_names_;

	private PImage[] profile_images_;
	private String[] profile_names_;

	ImageLoader(VoteVisApp p_) {
		this.p_ = p_;
		load_images_and_names();
		load_profiles();
	}

	public PImage get_candidate_image(Type type, int index) {
		return candidate_images_[type.ordinal()][index];
	}
	
	public String get_candidate_name(Type type, int index) {
		return candidate_names_[type.ordinal()][index];
	}
	
	public PImage get_profile_image(int index) {
		return profile_images_[index];
	}
	
	public String get_profile_name(int index) {
		return profile_names_[index];
	}

	private void load_images_and_names() {
		candidate_images_ = new PImage[5][];
		
		candidate_names_[Type.ECO.ordinal()] = new String[5];
		candidate_images_[Type.ECO.ordinal()] = new PImage[5];
		candidate_names_[Type.ECO.ordinal()][0] = "California Academy of Sciences";
		candidate_images_[Type.ECO.ordinal()][0] = p_.loadImage("cas-110.png");
		candidate_names_[Type.ECO.ordinal()][1] = "Friends of the Urban Forest";
		candidate_images_[Type.ECO.ordinal()][1] = p_.loadImage("fuf-110.png");
		candidate_names_[Type.ECO.ordinal()][2] = "Music in Schools Today";
		candidate_images_[Type.ECO.ordinal()][2] = p_.loadImage("music-110.png");
		candidate_names_[Type.ECO.ordinal()][3] = "Oxfam America";
		candidate_images_[Type.ECO.ordinal()][3] = p_.loadImage("oxfam-110.png");
		candidate_names_[Type.ECO.ordinal()][4] = "Surfrider Foundation";
		candidate_images_[Type.ECO.ordinal()][4] = p_.loadImage("surfrider-110.png");

		candidate_names_[Type.MUSIC.ordinal()] = new String[5];
		candidate_images_[Type.MUSIC.ordinal()] = new PImage[5];
		candidate_names_[Type.MUSIC.ordinal()][0] = "Electric Six";
		candidate_images_[Type.MUSIC.ordinal()][0] = p_.loadImage("e6-110.png");
		candidate_names_[Type.MUSIC.ordinal()][1] = "Empire of the Sun";
		candidate_images_[Type.MUSIC.ordinal()][1] = p_.loadImage("eots-110.png");
		candidate_names_[Type.MUSIC.ordinal()][2] = "Kings of Leon";
		candidate_images_[Type.MUSIC.ordinal()][2] = p_.loadImage("kings_of_leon-110.png");
		candidate_names_[Type.MUSIC.ordinal()][3] = "Pheonix";
		candidate_images_[Type.MUSIC.ordinal()][3] = p_.loadImage("phoenix-110.png");
		candidate_names_[Type.MUSIC.ordinal()][4] = "Sierra Leone Refugees";
		candidate_images_[Type.MUSIC.ordinal()][4] = p_.loadImage("sierra-110.png");

		candidate_names_[Type.WINE.ordinal()] = new String[5];
		candidate_images_[Type.WINE.ordinal()] = new PImage[5];
		candidate_names_[Type.WINE.ordinal()][0] = "Bonny Doon Vineyard";
		candidate_images_[Type.WINE.ordinal()][0] = p_.loadImage("bonny_doon-110.png");
		candidate_names_[Type.WINE.ordinal()][1] = "Iron Horse Vineyards";
		candidate_images_[Type.WINE.ordinal()][1] = p_.loadImage("iron_horse-110.png");
		candidate_names_[Type.WINE.ordinal()][2] = "Parducci Wine Cellars";
		candidate_images_[Type.WINE.ordinal()][2] = p_.loadImage("parducci-110.png");
		candidate_names_[Type.WINE.ordinal()][3] = "Robert Sinskey Vineyards";
		candidate_images_[Type.WINE.ordinal()][3] = p_.loadImage("rsinskey-110.png");
		candidate_names_[Type.WINE.ordinal()][4] = "Tallulah";
		candidate_images_[Type.WINE.ordinal()][4] = p_.loadImage("tallulah-110.png");
	}

	private void load_profiles() {
		profile_names_ = new String[5];
		profile_images_ = new PImage[5];
		profile_names_[0] = "Jack Kibble";
		profile_images_[0] = p_.loadImage("profile_2-110.png");
		profile_names_[1] = "John Smith";
		profile_images_[1] = p_.loadImage("profile_3-110.png");
		profile_names_[2] = "Eileen Carr";
		profile_images_[2] = p_.loadImage("profile_1-110.png");
		profile_names_[3] = "Rick Bradley";
		profile_images_[3] = p_.loadImage("profile_4-110.png");
		profile_names_[4] = "Casey Cropper";
		profile_images_[4] = p_.loadImage("profile_5-110.png");
	}

}
