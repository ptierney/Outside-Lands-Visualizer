package voteVis;

public abstract class BoxFrame {
	BoxFrame() {
	}
	
	public abstract void draw();
	
	// used for pane placement
	public abstract int get_pane_x();
	public abstract int get_pane_y();
	
	// used for collisions
	public abstract int get_width();
	public abstract int get_height();
}
