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

public abstract class BoxTransition {
	protected TransitionReceiver signal_receiver_;
	protected Box start_box_;
	protected Box end_box_;
	protected PImage start_image_;
	protected PImage end_image_;
	protected boolean transitioning_;
	
	protected float start_width_;
	protected float h_start_width_;
	protected float start_height_;
	protected float h_start_height_;
	protected float end_width_;
	protected float h_end_width_;
	protected float end_height_;
	protected float h_end_height_;
	
	public BoxTransition(TransitionReceiver signal_receiver_) {
		transitioning_ = false;
		this.signal_receiver_ = signal_receiver_;
	}
	
	public void load_boxes(Box start_box_, Box end_box_) {
		this.start_box_ = start_box_;
		start_image_ = start_box_.get_image();
		this.end_box_ = end_box_;
		end_image_ = end_box_.get_image();
		
		start_width_ = start_image_.width;
		h_start_width_ = start_width_ / 2;
		start_height_ = start_image_.height;
		h_start_height_ = start_height_ / 2;
		end_width_ = end_image_.width;
		h_end_width_ = end_width_ / 2;
		end_height_ = end_image_.height;
		h_end_height_ = end_height_ / 2;
	}
	
	public void begin_transition() {
		transitioning_ = true;
	}
	
	public abstract void update();
	
	// draws the transition centered
	public abstract void draw();
}
