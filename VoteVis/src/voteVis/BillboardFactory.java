package voteVis;

import java.util.ArrayList;
import java.util.Iterator;

public class BillboardFactory {
	private BoxTransition box_transition_;
	private ArrayList<Box> start_boxes_;
	private ArrayList<Box> end_boxes_;
	
	private Box current_start_box_;
	private Box current_end_box_;
	
	private boolean transitioning_;
	
	public BillboardFactory() {
		transitioning_ = false;
	}
	
	public void update() {
		box_transition_.update();
		
		// TODO: check if this is overlapping with other update / draws
		/*
		Iterator<Box> it = start_boxes_.iterator();
		while (it.hasNext()) {
			it.next().update();
		}
		*/
	}
	
	public void draw() {
		box_transition_.draw();
		
		Iterator<Box> it = start_boxes_.iterator();		
		while (it.hasNext()) {
			it.next().draw();
		}		
	}
	
	public void load_transition(BoxTransition box_transition_) {
		this.box_transition_ = box_transition_;
	}
	
	public void load_boxes(ArrayList<Box> start_boxes_, ArrayList<Box> end_boxes_) {
		if (start_boxes_.size() != end_boxes_.size()) 
			VoteVisApp.instance().exit(); // assert correct transition number
		
		this.start_boxes_ = start_boxes_;
		this.end_boxes_ = end_boxes_;
	}
	
	// starts the transition
	public void begin_transition() {
		transitioning_ = true;
	}
}
