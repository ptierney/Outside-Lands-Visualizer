package voteVis;

import processing.core.*;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Iterator;

// This class manages the boxes. It tells the appropriate boxes
// to fall off screen, and deletes them when they have informed it
// to delete them.

public class BoxManager {
	private static BoxManager instance_;
	private VoteVisApp p_;
	private LinkedHashSet<Box> boxes_; // LinkedHashSet is supposed to maintain order
	private ArrayList<Box> delete_list_;
	private static float MOVE_SPEED = 0.01f; // 10 pixels a second
	private int last_frame_;
	
	public BoxManager(VoteVisApp p_) {
		instance_ = this;
		this.p_ = p_;
		
		boxes_ = new LinkedHashSet<Box>();
		delete_list_ = new ArrayList<Box>();
	}
	
	public void update_boxes() {
		Iterator<Box> it = boxes_.iterator();
		while (it.hasNext()) {
			it.next().update();
		}
		
		if (SceneManager.instance().move_boxes())
			move_boxes();
		
		last_frame_ = p_.millis();
	}
	
	private void move_boxes() {
		float move_amount = MOVE_SPEED * (p_.millis() - last_frame_);
		
		Iterator<Box> it = boxes_.iterator();
		while (it.hasNext()) {
			it.next().move_down(move_amount);
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
	
	public void delete_box(Box box) {
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
	
	public static BoxManager instance() {
		return instance_;
	}
	
}
