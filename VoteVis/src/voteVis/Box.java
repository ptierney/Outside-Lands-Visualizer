package voteVis;

import java.util.Iterator;

public abstract class Box {
	protected VoteVisApp p_;
	protected Settings settings_;
	protected float x_;
	protected float y_;
	protected boolean falling_;
	protected int side_dim_;
	protected float lower_bound_; // the ground plane it should look for intersections
	protected boolean falling_off_screen_;
	// TODO: introduce an intro falling speed, and a normal falling speed
	public Box(VoteVisApp p_, float x_, float y_, int side_dim_) {
		this.p_ = p_;
		this.settings_ = p_.settings();
		this.x_ = x_;
		this.y_ = y_;
		this.side_dim_ = side_dim_;
		
		falling_ = true;
		lower_bound_ = p_.height;
		falling_off_screen_ = false;
	}

	public void update() {
		if (falling_)
			update_position();
	}

	private void update_position() {
		int collision_result = check_collisions();

		switch (collision_result) {
		case 0:
			y_ += Settings.FALL_SPEED;
			break;

		case 1:
			y_ += 1;
			break;

		case 2:
		default:
			falling_ = false;
			if (falling_off_screen_)
				p_.manager().delete_me(this);
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

	int side_dim() {
		return side_dim_;
	}

	boolean falling() {
		return falling_;
	}

	void set_falling(boolean falling_) {
		this.falling_ = falling_;
	}

	// 0 = no collision
	// 1 = collision inside 2 * fall_speed
	// 2 = collision inside 1 * fall_speed
	int check_collisions() {
		Iterator<Box> it = p_.manager().boxes().iterator();
		
		while (it.hasNext()) {
			Box b = it.next();
			
			if (b == this)
				continue;

			if (b.is_inside(x_, y_ + side_dim_ / 2 + Settings.BOX_GAP))
				return 2;
			else if (b.is_inside(x_, y_ + side_dim_ / 2 + Settings.FALL_SPEED * 2))
				return 1;
		}

		// check a collision against the bottom of the screen
		if (y_ + side_dim_ / 2 + Settings.BOX_GAP >= lower_bound_)
			return 2;
		else if (y_ + side_dim_ / 2 + Settings.FALL_SPEED * 2 >= lower_bound_)
			return 1;

		return 0;
	}

	boolean is_inside(float ox, float oy) {
		return ox < (x_ + side_dim_ / 2) && ox > (x_ - side_dim_ / 2)
			&& oy < (y_ + side_dim_ / 2) && oy > (y_ - side_dim_ / 2);
	}
	
	// this sets the lower_bound to a level so that it will fall off the screen
	public void fall_off_screen() {
		lower_bound_ = p_.height + side_dim_ * 2;
		falling_off_screen_ = true;
		falling_ = true;
	}

	public abstract void draw();

}
