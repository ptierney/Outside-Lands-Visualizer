package voteVis;

import java.util.Iterator;
import processing.core.*;
import java.awt.*;

public abstract class Box {
	protected VoteVisApp p_;
	protected float x_;
	protected float y_;
	protected boolean falling_;
	protected float lower_bound_; // the ground plane it should look for intersections
	protected boolean falling_off_screen_;
	protected boolean visible_;
	protected float last_fall_speed_;
	protected Box colliding_box_ = null; // the box this is supposed to collide with
	protected Box left_driving_box_ = null; // the box to the left of this 
	protected boolean ignore_collisions_; // don't intersect with this box
	protected Box height_driver_ = null;
	protected Box drive_this_height_ = null;
	
	public Box(VoteVisApp p_, float x_, float y_) {
		this.p_ = p_;
		this.x_ = x_;
		this.y_ = y_;
		
		falling_ = true;
		lower_bound_ = p_.height;
		falling_off_screen_ = false;
		visible_ = true;
		last_fall_speed_ = 0.0f;
		ignore_collisions_ = false;
	}
	
	public boolean being_driven() {
		return left_driving_box_ != null;
	}
	
	public void set_colliding_box(Box b) {
		colliding_box_ = b;
	}
	
	public void set_left_driving_box(Box b) {
		left_driving_box_ = b;
	}
	
	public void set_height_driving_box(Box b) {
		height_driver_ = b;
	}
	
	public void drive_height_on_collision(Box b) {
		drive_this_height_ = b;
	}
	
	public void set_ignore_collisions(boolean i) {
		ignore_collisions_ = i;
	}
	
	public boolean ignore_collisions() {
		return ignore_collisions_;
	}
	
	public void update() {
		if (left_driving_box_ != null) {
			x_ = left_driving_box_.x() + left_driving_box_.get_width() / 2 + Settings.BOX_GAP + get_width() / 2;
			y_ = left_driving_box_.y();
		} else if (falling_)
			update_position();
		
		if (height_driver_ != null)
			y_ = height_driver_.y();
		
		check_off_screen();
	}
	
	private void update_position() {
		Collision collision_result = check_collisions();

		switch (collision_result) {
		case NONE:
			y_ += BoxManager.instance().current_fall_speed();
			break;

		case ABOUT:
			y_ += 1;
			break;

		case YES:
		default:
			falling_ = false;
			if (falling_off_screen_)
				BoxManager.instance().delete_me(this);
			else
				stopped_falling();
			break;
		}
	}

	float x() {
		return x_;
	}

	void set_x(float x_) {
		this.x_ = x_;
	}

	float y() {
		return y_;
	}

	void set_y(float y_) {
		this.y_ = y_;
	}
	
	void move_down(float amount) {
		y_ += amount;
	}

	boolean falling() {
		return falling_;
	}

	void set_falling(boolean falling_) {
		this.falling_ = falling_;
		
		if (!falling_)
			stopped_falling();
	}
	
	public boolean visible() {
		return visible_;
	}
	
	public enum Collision {
		NONE,
		ABOUT,
		YES
	}

	public Collision check_collisions() {
		return check_collisions_with_center(x_, y_);
	}
	
	/*
	// check a collision against the bottom of the screen
	if (y_ + get_height() / 2 + Settings.BOX_GAP >= lower_bound_)
		return Collision.YES;
	else if (y_ + get_height() / 2 + last_fall_speed_ * 1.5 >= lower_bound_)
		return Collision.ABOUT;
	*/
	
	
	// used by the larger boxes to check collisions
	protected Collision check_collisions_with_center(float new_x, float new_y) {
		Iterator<Box> it = BoxManager.instance().boxes().iterator();
		
		while (it.hasNext()) {
			Box b = it.next();
			
			if (b == this)
				continue;
			
			if (b.ignore_collisions())
				continue;

			if (b.is_inside(new_x, new_y + get_height() / 2 + Settings.BOX_GAP)) {
				colliding_box_ = b;
				return Collision.YES;
			}
			else if (false && b.is_inside(new_x, (int)(new_y + get_height() / 2 + last_fall_speed_ * 2.0)))
				return Collision.ABOUT;
		}
		
		return Collision.NONE;
	}

	boolean is_inside(float ox, float oy) {
		return ox < (x_ + get_width() / 2) && ox > (x_ - get_width() / 2)
			&& oy < (y_ + get_height() / 2) && oy > (y_ - get_height() / 2);
	}
	
	// this sets the lower_bound to a level so that it will fall off the screen
	public void fall_off_screen() {
		lower_bound_ = p_.height + get_height() * 2;
		falling_off_screen_ = true;
		falling_ = true;
	}
	
	public void set_visible(boolean new_vis) {
		visible_ = new_vis;
	}

	public abstract void draw();
	public abstract int get_width();
	public abstract int get_height();
	
	public abstract PImage get_image();
	// override this to catch stop falling events
	protected void stopped_falling() {
		if (colliding_box_ != null) {
			y_ = colliding_box_.y() - colliding_box_.get_height() / 2 - Settings.BOX_GAP - get_height() / 2;
		}
		
		if (drive_this_height_ != null)
			drive_this_height_.set_y(y_);
	}
	
	private void check_off_screen() {
		if (x_ - get_height() > VoteVisApp.instance().height) 
			BoxManager.instance().delete_me(this); // TODO: check for all references in other managers
	}
	
	public Rectangle get_rectangle() {
		int w = get_width();
		int h = get_height();
		
		return new Rectangle(new Point((int)(x_ - w / 2), (int)(y_ - h /2)), 
			new Dimension(w, h));
	}
	
	public boolean collides_with_box(Box box) {
		if (box == this)
			return false;
		
		return get_rectangle().intersects(box.get_rectangle());
	}
}
