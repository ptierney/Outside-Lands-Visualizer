/* Copyright (c) 2010, Patrick Tierney 
 * All rights reserved.
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

public class BoxPane {
	private VoteVisApp p_;
	private ArrayList<TransitionState> transition_states_;
	private PaneTransition pane_transition_;
	private int transition_counter_;
	private static int ADVANCE_DELAY = 2000; // in millis
	private int delay_counter_;
	private boolean delaying_;
	private boolean advancing_;
	private int slide_speed_ = 1;

	BoxPane(VoteVisApp p_) {
		this.p_ = p_;
		transition_states_ = new ArrayList<TransitionState>();
		transition_counter_ = 0;
		advancing_ = true;
		delaying_ = false;
	}
	
	public PaneTransition pane_transition() {
		return pane_transition_;
	}
	
	public void set_slide_speed(int s) {
		slide_speed_ = s;
	}
	
	public void advance_random() {
		//delay_counter_ = (int) p_.random(advance_delay_);
	}
	
	public void add_transition_state(TransitionState state) {
		transition_states_.add(state);
	}

	public void load_transition() {
		TransitionState start = transition_states_.get(transition_counter_);

		if (transition_counter_ + 1 >= transition_states_.size())
			transition_counter_ = 0;
		else
			++transition_counter_;

		TransitionState end = transition_states_.get(transition_counter_);

		if (end.state_type() == StateType.TEXT)
			pane_transition_ = new HorizontalSlideTransition(p_);
		else if (end.state_type() == StateType.PHOTO)
			pane_transition_ = new VerticalSlideTransition(p_);	

		pane_transition_.set_slide_speed(slide_speed_);
		pane_transition_.load_transition(start, end);
	}

	public void update() {
		if (!advancing_)
			return;
		
		pane_transition_.perform_transition();
		
		if (!delaying_ && pane_transition_.finished()) {
			delaying_ = true;
			delay_counter_ = p_.millis();
		}
		
		if (delaying_) {
			if (p_.millis() - delay_counter_ > ADVANCE_DELAY) {
				load_transition();
				delaying_ = false;
			}
		}
	}

	public void draw() {
		pane_transition_.draw();
	}
	
	public PImage get_image() {
		return pane_transition_.get_current_image();
	}
	
	public void set_advancing(boolean new_advance) {
		advancing_ = new_advance;
	}
}
