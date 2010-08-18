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

import processing.core.*;

public class CompositeState extends TransitionState {
	private PhotoState photo_state_;
	private TextState text_state_;
	private PGraphics composite_graphics_;

	CompositeState(VoteVisApp p_, PhotoState photo_state_, TextState text_state_) {
		super(p_, photo_state_.get_image().width);
		
		this.photo_state_ = photo_state_;
		this.text_state_ = text_state_;

		composite_graphics_ = p_.createGraphics(state_dim_, state_dim_, PApplet.JAVA2D);
		composite_graphics_.smooth();
		composite_graphics_.image(photo_state_.get_image(), 0, 0);
		composite_graphics_.image(text_state_.get_image(), 0, 0);
		composite_graphics_.endDraw();
	}

	PImage get_image() {
		return composite_graphics_;
	}
}
