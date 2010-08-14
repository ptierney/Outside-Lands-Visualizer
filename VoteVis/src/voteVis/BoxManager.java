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
	private static int MOVE_DELAY = 500; // add more to make slower
	// 0.01f =  10 pixels a second
	// move one row in the time it takes to show a user photo
	private static float MOVE_SPEED = (float)(Settings.UNIT_DIM + Settings.BOX_GAP) / 
		(float)(VoteBoxFactory.CREATE_DELAY + ExpandingFrame.EXPAND_TIME * 2 + 
		ProfileFrame.TEXT_DISPLAY_TIME + MOVE_DELAY);
	
	private float last_fall_speed_;
	private float current_fall_speed_;
	private float last_move_amount_;
	
	private float move_accellerator_;
	
	public BoxManager(VoteVisApp p_) {
		instance_ = this;
		this.p_ = p_;
		
		boxes_ = new LinkedHashSet<Box>();
		delete_list_ = new ArrayList<Box>();
		
		move_accellerator_ = 0.0f;
	}
	
	public void update_boxes() {
		current_fall_speed_ = Settings.FALL_SPEED * 
			(VoteVisApp.instance().millis() - VoteVisApp.instance().last_frame());
		
		Iterator<Box> it = boxes_.iterator();
		while (it.hasNext()) {
			it.next().update();
		}
		
		if (SceneManager.instance().move_speed() == MoveSpeed.NORMAL)
			move_boxes_normal();
		else if (SceneManager.instance().move_speed() == MoveSpeed.PIXEL)
			move_boxes_pixel();
		
		last_fall_speed_ = current_fall_speed_;
	}
	
	private void move_boxes_normal() {
		last_move_amount_ = MOVE_SPEED * (p_.millis() - VoteVisApp.instance().last_frame()) + move_accellerator_;
		
		move_boxes(last_move_amount_);
	}
	
	private void move_boxes_pixel() {
		move_boxes(1.0f);
	}
	
	public float current_fall_speed() {
		return current_fall_speed_;
	}
	
	public float last_fall_speed() {
		return last_fall_speed_;
	}
	
	private void move_boxes(float amount) {
		Iterator<Box> it = boxes_.iterator();
		while (it.hasNext()) {
			Box b = it.next();
			if (b.being_driven())
				continue;
			
			b.move_down(amount);
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
	
	public float last_move_amount() {
		return last_move_amount_;
	}
	
	public void set_move_accellerator(float a) {
		move_accellerator_ = a;
	}
	
}
