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

// Expanding Frame expands to multiples of UNIT_DIM
// It will contract to one unit wide
// It can only be UNIT_DIM tall

public abstract class ExpandingFrame extends BoxFrame {
	protected VoteVisApp p_;
	protected int max_unit_width_; // how many multiples of UNIT_DIM
	protected float counter_; // ranges from 0.0 (full retraction) to 1.0 (full extension)
	
	public static int EXPAND_TIME = 600; // time in millis
	private int action_start_time_;
	
	protected boolean expanding_;
	protected boolean contracting_;
	
	public ExpandingFrame(VoteVisApp p_, int max_unit_width_) {
		super();
		
		this.p_ = p_;
		this.max_unit_width_ = max_unit_width_;
		counter_ = 0.0f; // start at retracted
		action_start_time_ = 0;
	}
	
	protected void update() {
		if (expanding_) {
			set_counter(get_counter_from_time());
			if (counter_ == 1.0f) {
				expanding_ = false;
				done_expanding();
			}
		} else if (contracting_) {
			set_counter(1.0f - get_counter_from_time());
			if (counter_ == 0.0f) {
				contracting_ = false;
				done_contracting();
			}
		}
	}
	
	private float get_counter_from_time() {
		return (float)(p_.millis() - action_start_time_) / (float)EXPAND_TIME;
	}
	
	public void expand_fully() {
		if (counter_ < 1.0f)
			expanding_ = true;
		
		action_start_time_ = p_.millis();
	}
	
	public void contract_fully() {
		if (counter_ > 0.0f)
			contracting_ = true;
		
		action_start_time_ = p_.millis();
	}
	
	public void set_counter(float new_counter) {
		if (new_counter > 1.0f)
			counter_ = 1.0f;
		else if (new_counter < 0.0f)
			counter_ = 0.0f;
		else
			counter_ = new_counter;
	}
	
	public float counter() {
		return counter_;
	}
	
	// Override this to get done_expanding events
	protected void done_expanding() {
		
	}
	// Override this to get done_expanding events
	protected void done_contracting() {
		
	}
}
