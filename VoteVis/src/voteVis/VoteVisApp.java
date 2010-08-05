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
	
	@Override
	public void setup() {
		size(1024, 768, OPENGL);
		background(0);
		
		settings_ = new Settings(this);
		loader_ = new ImageLoader(this);
		boxes_ = new ArrayList<Box>();
		
		banner_ = loadImage("banner.png");
		
		box_ = new VoteBox(this, width / 2, 0, Type.MUSIC, 0);
	}
	
	@Override
	public void draw() {
		box_.update();
		box_.draw();
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
