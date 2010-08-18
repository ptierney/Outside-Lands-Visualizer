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

import processing.core.PImage;

public class BillboardBox extends DynamicBox {
	private Type type_;
	int x_index_;
	int y_index_;
	
	// indexes are used to determine which icon to show
	public BillboardBox(Type type_, int x_index_, int y_index_,
		float x_pos_, float y_pos_) {
		super(VoteVisApp.instance(), x_pos_, y_pos_);
		this.type_ = type_;
		this.x_index_ = x_index_;
		this.y_index_ = y_index_;
		
		falling_ = false;
		box_pane_.set_advancing(false);
		visible_ = false;
		ignore_collisions_ = true;
		
		box_pane_.add_transition_state(new PhotoState(VoteVisApp.instance(), 
			Settings.instance().get_billboard_image(type_, x_index_, y_index_)));
		box_pane_.load_transition();
		box_pane_.set_advancing(false);
		
		box_frame_ = new NoFrame();
	}
	
	@Override
	public PImage get_image() {
		// TODO Auto-generated method stub
		return box_pane_.get_image();
	}
}
