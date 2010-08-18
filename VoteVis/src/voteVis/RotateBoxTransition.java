/* Copyright (c) 2010, Patrick Tierney 
 * 
 * This file is part of the "Live Top Five" vote visualizer 
 * created for Mr Youth August 2010 
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A
 * PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED
 * TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 * */

package voteVis;

import processing.core.*;

public class RotateBoxTransition extends BoxTransition {
	private Axis axis_;
	private float rotate_amount_;
	private static int ROTATE_TIME = 200; // in millis;
	private float pos_x_;
	private float pos_y_;
	private boolean flipped_;
	private int start_timer_;

	public RotateBoxTransition(Axis axis_, TransitionReceiver tr) {
		super(tr);

		this.axis_ = axis_;
	}
	
	@Override
	public void load_boxes(Box b1, Box b2) {
		super.load_boxes(b1, b2);

		rotate_amount_ = 0;
		flipped_ = false;
		transitioning_ = true;
		start_timer_ = VoteVisApp.instance().millis();
		
		pos_x_ = b1.x();
		pos_y_ = b1.y();
		
		// the end boxes may be been created at an earlier time
		end_box_.set_x(start_box_.x());
		end_box_.set_y(start_box_.y());
		end_box_.set_falling(true);
	}

	public void update() {
		if (!transitioning_)
			return;
	}

	public void draw() {
		if (!transitioning_)
			return;
		
		PApplet p = VoteVisApp.instance();
		int time_diff = p.millis() - start_timer_;
		if (time_diff < ROTATE_TIME / 2)
			rotate_amount_ = PApplet.map(time_diff, 0, ROTATE_TIME / 2, 0, PApplet.PI / 2); 
		else 
			rotate_amount_ = PApplet.map(time_diff, ROTATE_TIME / 2, ROTATE_TIME, 
				PApplet.TWO_PI - PApplet.PI / 2, PApplet.TWO_PI); 
		
		//PApplet.println(VoteVisApp.instance().millis());
		// this code is all jacked up due to a bug in processing
		// if we were rendering with OPENGL (which is jerkey)
		// the texture code word work as normal.
		
		PGraphics g = p.createGraphics((int)start_width_ * 2, (int)start_height_ * 2, PApplet.P3D);
		g.beginDraw();
		g.noStroke();
		g.pushMatrix();
			g.translate(start_width_, start_height_);
			if (axis_ == Axis.VERTICAL)
				g.rotateY(rotate_amount_);
			else if (axis_ == Axis.HORIZONTAL)
				g.rotateX(rotate_amount_);
			
			if (rotate_amount_ < PApplet.PI) {
				g.beginShape();
					g.texture(start_image_);
					g.vertex(-h_start_width_, -h_start_height_, 0, 0);
					g.vertex(-h_start_width_, h_start_height_, 0, start_height_);
					g.vertex(h_start_width_, h_start_height_,  start_width_, start_height_);
					g.vertex(h_start_width_, -h_start_height_, start_width_, 0);
				g.endShape();
			} else {
				g.beginShape();
					g.texture(end_image_);
					g.vertex(-h_end_width_, -h_end_height_,  0, 0);
					g.vertex(-h_end_width_, h_end_height_,  0, start_height_);
					g.vertex(h_end_width_, h_end_height_,  end_width_, end_height_);
					g.vertex(h_end_width_, -h_end_height_,  start_width_, 0);
				g.endShape();
			}
			
		g.popMatrix();
		
		p.pushMatrix();
			p.translate(pos_x_, pos_y_);
			p.image(g, -start_width_, -start_height_);
		p.popMatrix();
		
		/*
		p.noStroke();
		p.pushMatrix();
			p.translate(p.width / 2, p.height / 2);
			if (axis_ == Axis.VERTICAL)
				p.rotateY(rotate_amount_);
			else if (axis_ == Axis.HORIZONTAL)
				p.rotateX(rotate_amount_);
			
			if (rotate_amount_ < PApplet.PI) {
				p.beginShape();
					p.texture(start_image_);
					p.vertex(-h_start_width_, -h_start_height_, 0, 0);
					p.vertex(-h_start_width_, h_start_height_, 0, start_height_);
					p.vertex(h_start_width_, h_start_height_,  start_width_, start_height_);
					p.vertex(h_start_width_, -h_start_height_, start_width_, 0);
				p.endShape();
			} else {
				p.beginShape();
					p.texture(end_image_);
					p.vertex(-h_end_width_, -h_end_height_,  0, 0);
					p.vertex(-h_end_width_, h_end_height_,  0, start_height_);
					p.vertex(h_end_width_, h_end_height_,  end_width_, end_height_);
					p.vertex(h_end_width_, -h_end_height_,  start_width_, 0);
				p.endShape();
			}
			p.translate(pos_x_ - p.width / 2, pos_y_ - p.height / 2);
			
		p.popMatrix();
		*/
		
		if (rotate_amount_ > PApplet.PI / 2 && !flipped_) {
			//rotate_amount_ = PApplet.TWO_PI - PApplet.PI / 2;
			flipped_ = true;
		}
		
		if (rotate_amount_ > PApplet.TWO_PI) {
			rotate_amount_ = PApplet.TWO_PI;
			transitioning_ = false;
			signal_receiver_.finished_transition(end_box_);
		}
		
	}

	public enum Axis {
		HORIZONTAL, 
		VERTICAL
	}
}
