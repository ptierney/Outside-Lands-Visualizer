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

public abstract class PaneTransition {
	protected PImage start_image_;
	protected PImage end_image_;
	protected boolean finished_;
	protected VoteVisApp p_;
	protected int counter_; // goes from 0 to unit_dim (for # of pixels transitioned)
	protected int trans_unit_dim_;
	protected int slide_speed_;
	
	public PaneTransition(VoteVisApp p_) {
		this.p_ = p_;
		finished_ = false;
	}

	public void load_transition(TransitionState start, TransitionState end) {
		start_image_ = start.get_image();
		end_image_ = end.get_image();
	}
	
	public void advance_random() {
		counter_ = (int) p_.random(trans_unit_dim_);
	}

	abstract public void perform_transition();

	abstract public PImage get_current_image();

	abstract public void draw();

	public boolean finished() {
		return finished_;
	}
	
	public void set_slide_speed(int new_speed) {
		slide_speed_ = new_speed;
	}
}
