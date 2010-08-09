package voteVis;

import processing.core.*;

public class BannerDisplay {
	private static BannerDisplay instance_;
	private PImage banner_;
	private PImage[] labels_;
	private int[] x_positions_;
	private int y_position_;
	private int label_width_;
	private int label_height_;
	private static int GAP = 0;
	private boolean[] active_labels_;
	
	public BannerDisplay() {
		instance_ = this;
		
		PApplet p = VoteVisApp.instance();
		banner_ = p.loadImage("banner.png");
		
		labels_ = new PImage[5];
		labels_[Type.MUSIC.ordinal()] = p.loadImage("music-label.png");
		labels_[Type.FOOD.ordinal()] = p.loadImage("food-label.png");
		labels_[Type.WINE.ordinal()] = p.loadImage("wine-label.png");
		labels_[Type.ECO.ordinal()] = p.loadImage("eco-label.png");
		labels_[Type.ART.ordinal()] = p.loadImage("art-label.png");
		
		// all these should be the same (checked it out in photoshop)
		label_width_ = labels_[0].width;
		label_height_ = labels_[0].height;
		
		y_position_ = banner_.height + label_height_ / 2 + GAP;
		
		x_positions_ = new int[5];
		x_positions_[Type.MUSIC.ordinal()] = Utility.get_aligned_position(Settings.UNIT_DIM, 1);
		x_positions_[Type.FOOD.ordinal()] = Utility.get_aligned_position(Settings.UNIT_DIM, 2);
		x_positions_[Type.WINE.ordinal()] = Utility.get_aligned_position(Settings.UNIT_DIM, 3);
		x_positions_[Type.ECO.ordinal()] = Utility.get_aligned_position(Settings.UNIT_DIM, 4);
		x_positions_[Type.ART.ordinal()] = Utility.get_aligned_position(Settings.UNIT_DIM, 5);
		
		active_labels_ = new boolean[5];
		// set all active
		for (int i = 0; i < 5; ++i) {
			active_labels_[i] = true;
		}
	}
	
	public void draw() {	
		PApplet p = VoteVisApp.instance();
		
		// required by law to be here
		p.image(banner_, 0, 0);
		
		for (int i = 0; i < 5; ++i) {
			p.image(labels_[i], x_positions_[i] -label_width_ / 2,  y_position_ -label_height_ / 2);
		}
	}
	
	public boolean get_label_active(Type type_) {
		return active_labels_[type_.ordinal()];
	}
	
	public void set_label_active(Type type_, boolean active) {
		active_labels_[type_.ordinal()] = active;
	}
	
	public static BannerDisplay instance() {
		return instance_;
	}
}
