package voteVis;

import processing.core.*;

public class ImageLoader {
	private static ImageLoader instance_;
	private VoteVisApp p_;

	private PImage[][] candidate_images_;
	private String[][] candidate_names_;

	private PImage[] profile_images_;
	private String[] profile_names_;
	
	private PImage profile_right_image_;
	private PImage profile_left_image_;
	private PImage profile_square_;
	public PImage profile_text_background_left;
	public PImage profile_text_background_square;
	public PImage profile_text_background_right;
	public PImage profile_left_scaled;
	public PImage profile_right_scaled;
	public PImage profile_left_text_scaled;
	public PImage profile_square_text_scaled;
	public PImage profile_right_text_scaled;
	
	private PImage[] vote_background_images_;
	
	private PImage[][] billboards_;

	public static PImage TWEET_BACKGROUND_USER;
	public static PImage TWEET_BACKGROUND_CANDIDATE;
	
	ImageLoader(VoteVisApp p_) {
		instance_ = this;
		this.p_ = p_;
		load_images_and_names();
		load_profiles();
		load_background_images();
		load_profile_images();
		load_billboards(); // must be after background images
		load_twitter();
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
	
	// TODO: implement this really
	public PImage get_dummy_profile_image() {
		return profile_images_[0];
	}

	private void load_images_and_names() {
		candidate_images_ = new PImage[5][];
		candidate_names_ = new String[5][];
		
		String[] eco_strings = p_.loadStrings("eco/eco.txt");
		
		candidate_names_[Type.ECO.ordinal()] = new String[eco_strings.length];
		candidate_images_[Type.ECO.ordinal()] = new PImage[eco_strings.length];
		Settings.NUM_ECO = eco_strings.length;
		
		for (int i = 0; i < eco_strings.length; ++i) {
			candidate_names_[Type.ECO.ordinal()][i] = eco_strings[i];
			candidate_images_[Type.ECO.ordinal()][i] = p_.loadImage("eco/eco-" + (i+1) + ".png");
		}
		
		String[] food_strings = p_.loadStrings("food/food.txt");
		
		candidate_names_[Type.FOOD.ordinal()] = new String[food_strings.length];
		candidate_images_[Type.FOOD.ordinal()] = new PImage[food_strings.length];
		Settings.NUM_FOOD = food_strings.length;
		
		for (int i = 0; i < food_strings.length; ++i) {
			candidate_names_[Type.FOOD.ordinal()][i] = food_strings[i];
			candidate_images_[Type.FOOD.ordinal()][i] = p_.loadImage("food/food-" + (i+1) + ".png");
		}
		
		String[] wine_strings = p_.loadStrings("wine/wine.txt");
		
		candidate_names_[Type.WINE.ordinal()] = new String[wine_strings.length];
		candidate_images_[Type.WINE.ordinal()] = new PImage[wine_strings.length];
		Settings.NUM_WINE = wine_strings.length;
		
		for (int i = 0; i < wine_strings.length; ++i) {
			candidate_names_[Type.WINE.ordinal()][i] = wine_strings[i];
			candidate_images_[Type.WINE.ordinal()][i] = p_.loadImage("wine/wine-" + (i+1) + ".png");
		}
		
		String[] music_strings = p_.loadStrings("music/music.txt");
		
		candidate_names_[Type.MUSIC.ordinal()] = new String[music_strings.length];
		candidate_images_[Type.MUSIC.ordinal()] = new PImage[music_strings.length];
		Settings.NUM_MUSIC = music_strings.length;
		
		for (int i = 0; i < music_strings.length; ++i) {
			candidate_names_[Type.MUSIC.ordinal()][i] = music_strings[i];
			candidate_images_[Type.MUSIC.ordinal()][i] = p_.loadImage("music/music-" + (i+1) + ".png");
		}

		candidate_names_[Type.ART.ordinal()] = new String[5];
		candidate_images_[Type.ART.ordinal()] = new PImage[5];
		candidate_names_[Type.ART.ordinal()][0] = "Jeff Koons";
		candidate_images_[Type.ART.ordinal()][0] = p_.loadImage("art-1.png");
		candidate_names_[Type.ART.ordinal()][1] = "Jeff Koons";
		candidate_images_[Type.ART.ordinal()][1] = p_.loadImage("art-2.png");
		candidate_names_[Type.ART.ordinal()][2] = "Jeff Koons";
		candidate_images_[Type.ART.ordinal()][2] = p_.loadImage("art-3.png");
		candidate_names_[Type.ART.ordinal()][3] = "Jeff Koons";
		candidate_images_[Type.ART.ordinal()][3] = p_.loadImage("art-4.png");
		candidate_names_[Type.ART.ordinal()][4] = "Jeff Koons";
		candidate_images_[Type.ART.ordinal()][4] = p_.loadImage("art-5.png");
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
	
	public static ImageLoader instance() {
		return instance_;
	}
	
	public PImage[][] billboards() {
		return billboards_;
	}
	
	// TODO: actually load images
	private void load_billboards() {
		billboards_ = new PImage[5][];
		int n = 0;
		
		for (int i = 0; i < 5; ++i) {
			billboards_[i] = new PImage[24];
		}
		
		n = 0;
		for (int i = 3; i >= 0; --i) {
			for (int j = 0; j < 6; ++j) {
				int index = i * 6 + j;
				String name;
				if (index < 9)
					name = "panel/panel_art-0" + (index + 1) + ".png";
				else
					name = "panel/panel_art-" + (index + 1) + ".png";
			
				billboards_[Type.ART.ordinal()][n] = p_.loadImage(name);
				++n;
			}
		}
		
		n = 0;
		for (int i = 3; i >= 0; --i) {
			for (int j = 0; j < 6; ++j) {
				int index = i * 6 + j;
				String name;
				if (index < 9)
					name = "panel/panel_eco-0" + (index + 1) + ".png";
				else
					name = "panel/panel_eco-" + (index + 1) + ".png";
			
				billboards_[Type.ECO.ordinal()][n] = p_.loadImage(name);
				++n;
			}
		}
		
		n = 0;
		for (int i = 3; i >= 0; --i) {
			for (int j = 0; j < 6; ++j) {
				int index = i * 6 + j;
				String name;
				if (index < 9)
					name = "panel/panel_food-0" + (index + 1) + ".png";
				else
					name = "panel/panel_food-" + (index + 1) + ".png";
			
				billboards_[Type.FOOD.ordinal()][n] = p_.loadImage(name);
				++n;
			}
		}
		
		n = 0;
		for (int i = 3; i >= 0; --i) {
			for (int j = 0; j < 6; ++j) {
				int index = i * 6 + j;
				String name;
				if (index < 9)
					name = "panel/panel_music-0" + (index + 1) + ".png";
				else
					name = "panel/panel_music-" + (index + 1) + ".png";
			
				billboards_[Type.MUSIC.ordinal()][n] = p_.loadImage(name);
				++n;
			}
		}
		
		n = 0;
		for (int i = 3; i >= 0; --i) {
			for (int j = 0; j < 6; ++j) {
				int index = i * 6 + j;
				String name;
				if (index < 9)
					name = "panel/panel_wine-0" + (index + 1) + ".png";
				else
					name = "panel/panel_wine-" + (index + 1) + ".png";
			
				billboards_[Type.WINE.ordinal()][n] = p_.loadImage(name);
				++n;
			}
		}
	}
	
	private void load_background_images() {
		vote_background_images_ = new PImage[5];
		
		vote_background_images_[Type.MUSIC.ordinal()] = p_.loadImage("music-background.png");
		vote_background_images_[Type.FOOD.ordinal()] = p_.loadImage("food-background.png");
		vote_background_images_[Type.ECO.ordinal()] = p_.loadImage("eco-background.png");
		vote_background_images_[Type.WINE.ordinal()] = p_.loadImage("wine-background.png");
		vote_background_images_[Type.ART.ordinal()] = p_.loadImage("art-background.png");
	}
	
	public PImage get_vote_background(Type type) {
		return Utility.instance().scale_to_pane_size(
			vote_background_images_[type.ordinal()], Settings.UNIT_DIM);
	}
	
	public void load_profile_images() {
		profile_left_image_ = p_.loadImage("profile-left-background.png");
		profile_right_image_ = p_.loadImage("profile-right-background.png");
		profile_square_ = p_.loadImage("profile-square-background.png");
		
		profile_text_background_left = p_.loadImage("profile-text-background-left.png");
		profile_text_background_right = p_.loadImage("profile-text-background-right.png");
		profile_text_background_square = p_.loadImage("profile-text-background-square.png");
		
		PImage left_image = profile_left_image_;
		PImage right_image = profile_right_image_;
		
		float load_scale = (float)ProfileFrame.FRAME_HEIGHT / (float) right_image.height;
		
		int left_graphic_width_ = (int)(left_image.width * load_scale);
		
		PGraphics left_graphic = p_.createGraphics(left_graphic_width_,
				ProfileFrame.FRAME_HEIGHT, PApplet.JAVA2D); // assume square for now

		left_graphic.beginDraw();
		left_graphic.scale(load_scale);
		left_graphic.image(left_image, 0, 0);
		left_graphic.endDraw();

		profile_left_scaled = left_graphic;
		
		int right_graphic_width = (int)(right_image.width * load_scale);
		PGraphics right_graphic = p_.createGraphics(right_graphic_width,
				ProfileFrame.FRAME_HEIGHT, PApplet.JAVA2D);

		right_graphic.beginDraw();
		right_graphic.scale(load_scale);
		right_graphic.image(right_image, 0, 0);
		right_graphic.endDraw();

		profile_right_scaled = right_graphic;
		
		float text_background_scale = (float)ProfileFrame.TEXT_BACKGROUND_HEIGHT / (float)ImageLoader.instance().profile_text_background_left.height;
		
		PGraphics ltext = p_.createGraphics((int)(text_background_scale * ImageLoader.instance().profile_text_background_left.width), 
				ProfileFrame.TEXT_BACKGROUND_HEIGHT, PApplet.JAVA2D);
		
		ltext.beginDraw();
		ltext.scale(text_background_scale);
		ltext.image(ImageLoader.instance().profile_text_background_left, 0, 0);
		ltext.endDraw();
		
		profile_left_text_scaled = ltext;
		
		PGraphics rtext = p_.createGraphics((int)(text_background_scale * ImageLoader.instance().profile_text_background_right.width), 
				ProfileFrame.TEXT_BACKGROUND_HEIGHT, PApplet.JAVA2D);
			
		rtext.beginDraw();
		rtext.scale(text_background_scale);
		rtext.image(ImageLoader.instance().profile_text_background_right, 0, 0);
		rtext.endDraw();
			
		profile_right_text_scaled = rtext;
		
		
		PGraphics ctext = p_.createGraphics((int)(text_background_scale * ImageLoader.instance().profile_text_background_square.width), 
				ProfileFrame.TEXT_BACKGROUND_HEIGHT, PApplet.JAVA2D);
			
		ctext.beginDraw();
		ctext.scale(text_background_scale);
		ctext.image(ImageLoader.instance().profile_text_background_square, 0, 0);
		ctext.endDraw();
			
		profile_square_text_scaled = ctext;
	}
	
	public PImage get_profile_right_image() {
		return profile_right_image_;
	}
	
	public PImage get_profile_left_image() {
		return profile_left_image_;
	}
	
	public PImage get_profile_square() {
		return profile_square_;
	}
	
	private void load_twitter() {
		TWEET_BACKGROUND_USER = p_.loadImage("twitter-regular-box.png");
		TWEET_BACKGROUND_CANDIDATE = p_.loadImage("twitter-candidate-box.png");
	}

}
