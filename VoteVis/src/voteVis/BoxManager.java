package voteVis;

import processing.core.*;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Iterator;

// This class manages the boxes. It tells the appropriate boxes
// to fall off screen, and deletes them when they have informed it
// to delete them.

public class BoxManager {
	private VoteVisApp p_;
	private LinkedHashSet<Box> boxes_; // LinkedHashSet is supposed to maintain order
	private ArrayList<Box> delete_list_;
	
	public BoxManager(VoteVisApp p_) {
		this.p_ = p_;
		
		boxes_ = new LinkedHashSet<Box>();
		delete_list_ = new ArrayList<Box>();
	}
	
	public void update_boxes() {
		Iterator<Box> it = boxes_.iterator();
		while (it.hasNext()) {
			it.next().update();
		}
	}
	
	public void draw_boxes() {
		Iterator<Box> it = boxes_.iterator();
		while (it.hasNext()) {
			it.next().draw();
		}
	}

	public LinkedHashSet<Box> boxes() {
		return boxes_;
	}
	
	public void add_box(Box box) {
		boxes_.add(box);
	}
	
	public void delet_box(Box box) {
		boxes_.remove(box);
	}
	
	public void delete_me(Box box) {
		delete_list_.add(box);
	}
	
	public void set_all_falling() {
		Iterator<Box> it = boxes_.iterator();
		while (it.hasNext()) {
			it.next().set_falling(true);
		}
	}
	
	// run through the delete list and delete all the appropriate boxes
	public void clean_up() {
		if (delete_list_.size() == 0)
			return;
		
		for (int i = 0; i < delete_list_.size(); ++i) {
			boxes_.remove(delete_list_.get(i));
		}
		
		delete_list_.clear();
	}
	
}
