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

import java.util.ArrayList;
import processing.core.*;

public abstract class DynamicBox extends Box {
	protected int box_color_;
	protected PImage background_image_;
	protected BoxPane box_pane_;
	protected BoxFrame box_frame_ = null;
	protected boolean transitioning_ = true;
	
	protected static final int SLIDE_SPEED = 3;
	
	public DynamicBox(VoteVisApp p_, float x_, float y_) {
		super(p_, x_, y_);
		
		box_pane_ = new BoxPane(p_);
		box_pane_.set_slide_speed(SLIDE_SPEED);
	}
	
	@Override
	public int get_width() {
		return box_frame_.get_width();
	}
	
	@Override
	public int get_height() {
		return box_frame_.get_height();
	}

	@Override
	public void update() {
		super.update();
		
		if (!visible_)
			return;

		if (falling_ == false && transitioning_)
			box_pane_.update();
	}
	
	public void set_transitioning(boolean t) {
		transitioning_ = t;
	}

	@Override
	public void draw() {
		if (!visible_)
			return;
		
		p_.pushMatrix();
			p_.translate(x_, y_);
			box_frame_.draw();
			p_.pushMatrix();
				p_.translate(box_frame_.get_pane_x(), box_frame_.get_pane_y());
				box_pane_.draw();
			p_.popMatrix();
		p_.popMatrix();
	}
	
	public BoxFrame box_frame() {
		return box_frame_;
	}
	
	public BoxPane box_pane() {
		return box_pane_;
	}

}
