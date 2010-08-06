package voteVis;

import java.util.ArrayList;

import processing.core.*;
import processing.opengl.*;
import javax.media.opengl.GL;

@SuppressWarnings("serial")
public class VoteVisApp extends PApplet {
	private Settings settings_;
	private ImageLoader loader_;
	private ArrayList<Box> boxes_;
	private PImage banner_;
	private Box box_;
	private BallotCounter counter_;
	
	@Override
	public void setup() {
		size(1024, 768, OPENGL);
		background(0);
		
		settings_ = new Settings(this);
		//loader_ = new ImageLoader(this);
		boxes_ = new ArrayList<Box>();
		
		banner_ = loadImage("banner.png");
		
		//box_ = new VoteBox(this, width / 2, 0, Type.MUSIC, 0);
		
		counter_ = new BallotCounter(this);
		
		for (int i = 0; i < 20; ++i) {
			counter_.add_random_ballot();
		}
		
		int[] top_five = counter_.get_top_five(Type.MUSIC);
		
		for (int i = 0; i < 5; ++i) {
			PApplet.println(top_five[i]);
		}
		
	}
	
	@Override
	public void draw() {
		background(0);
		stroke(255);
		line(0, 0, mouseX, mouseY);
		//box_.update();
		//box_.draw();
		// required by law to be here
		image(banner_, 0, 0); 
	}
	
	public Settings settings() {
		return settings_;
	}
	
	public ImageLoader loader() {
		return loader_;
	}
	
	public ArrayList<Box> boxes() {
		return boxes();
	}
}
