package voteVis;

import java.util.ArrayList;

import processing.core.*;
import processing.opengl.*;
import javax.media.opengl.GL;

@SuppressWarnings("serial")
public class VoteVisApp extends PApplet {
	private Settings settings_;
	private ArrayList<Box> boxes_;
	private PImage banner_;
	
	@Override
	public void setup() {
		size(1024, 768, OPENGL);
		background(0);
		
		settings_ = new Settings(this);
		boxes_ = new ArrayList<Box>();
		
		banner_ = loadImage("banner.png");
	}
	
	@Override
	public void draw() {
		
		// required by law to be here
		image(banner_, 0, 0); 
	}
	
	public Settings settings() {
		return settings_;
	}
	
	public ArrayList<Box> boxes() {
		return boxes();
	}
}
