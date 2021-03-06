/* Copyright (c) 2010, Patrick Tierney 
 * All rights reserved.
 * 
 * This file is part of the "Live Top Five" vote visualizer 
 * created for Mr Youth August 2010 
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A
 * PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED
 * TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 * */
package voteVis;

import processing.core.*;

public class BannerDisplay {
	private static BannerDisplay instance_;
	private PImage banner_;
	private PImage[] labels_;
	private PImage[] labels_off_;
	private PImage twitter_callout_;
	private PImage[] twitter_callouts_;
	private PImage profile_label_;
	private int[] x_positions_;
	private int y_position_;
	private int profile_label_x_pos_;
	private int label_width_;
	private int label_height_;
	private static int GAP = 0;
	private boolean[] active_labels_;
	private int banner_height_;
	
	public BannerDisplay() {
		instance_ = this;
		
		PApplet p = VoteVisApp.instance();
		banner_ = p.loadImage("banner.png");
		banner_height_ = banner_.height;
		
		labels_ = new PImage[5];
		labels_[Type.MUSIC.ordinal()] = p.loadImage("music-label.png");
		labels_[Type.FOOD.ordinal()] = p.loadImage("food-label.png");
		labels_[Type.WINE.ordinal()] = p.loadImage("wine-label.png");
		labels_[Type.ECO.ordinal()] = p.loadImage("eco-label.png");
		labels_[Type.ART.ordinal()] = p.loadImage("art-label.png");
		
		labels_off_ = new PImage[5];
		labels_off_[Type.MUSIC.ordinal()] = p.loadImage("music-label-off.png");
		labels_off_[Type.FOOD.ordinal()] = p.loadImage("food-label-off.png");
		labels_off_[Type.WINE.ordinal()] = p.loadImage("wine-label-off.png");
		labels_off_[Type.ECO.ordinal()] = p.loadImage("eco-label-off.png");
		labels_off_[Type.ART.ordinal()] = p.loadImage("art-label-off.png");
		
		profile_label_ = p.loadImage("profile-label.png");
		
		twitter_callouts_ = new PImage[5];
		twitter_callouts_[Type.MUSIC.ordinal()] = p.loadImage("twitter-callout-music.png");
		twitter_callouts_[Type.FOOD.ordinal()] = p.loadImage("twitter-callout-food.png");
		twitter_callouts_[Type.WINE.ordinal()] = p.loadImage("twitter-callout-wine.png");
		twitter_callouts_[Type.ECO.ordinal()] = p.loadImage("twitter-callout-eco.png");
		twitter_callouts_[Type.ART.ordinal()] = p.loadImage("twitter-callout-out.png");
		
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
		
		profile_label_x_pos_ = Utility.get_aligned_position(Settings.UNIT_DIM, 0);
		
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
		SceneManager s = SceneManager.instance();
		if (s.twitter_mode()) {
			p.image(twitter_callouts_[s.current_type().ordinal()], 
				36, banner_height_ + label_height_ + 2);
		}
		
		for (int i = 0; i < 5; ++i) {
			p.image(profile_label_, profile_label_x_pos_ - profile_label_.width / 2, banner_height_);
			p.image(get_label_image(Type.deserialize(i)), x_positions_[i] -label_width_ / 2,  y_position_ -label_height_ / 2);
		}
	}
	
	private PImage get_label_image(Type type) {
		SceneManager m = SceneManager.instance();
		if ((m.display_all_labels() || m.current_type() == type) && 
			!m.outside_turnoff())
			return labels_[type.ordinal()];
		else
			return labels_off_[type.ordinal()];
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
	
	public int banner_height() {
		return banner_height_;
	}
}
