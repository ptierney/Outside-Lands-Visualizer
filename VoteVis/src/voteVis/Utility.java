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

public class Utility {
	private static Utility instance_;
	private VoteVisApp p_;

	public Utility(VoteVisApp p_) {
		instance_ = this;
		this.p_ = p_;
	}
	
	public PImage scale_to_vote_pane_size(PImage pimage) {
		return scale_to_pane_size(pimage, Settings.VOTE_PANE_DIM);
	}

	public PImage scale_to_pane_size(PImage pimage, int side_dim) {
		PGraphics graphics = p_.createGraphics(side_dim, side_dim, PApplet.JAVA2D);
		
		float new_width = 0.0f;
		float new_height = 0.0f;
		
		if (pimage.width > pimage.height) {
			new_width = side_dim;
			new_height = (float)new_width / (float)pimage.width * (float)pimage.height;
		} else {
			new_height = side_dim;
			new_width = (float)new_height / (float)pimage.height * (float)pimage.width;
		}
		
		graphics.beginDraw();
		graphics.image(pimage, side_dim / 2 - new_width / 2, side_dim / 2 - new_height / 2, new_width, new_height);
		graphics.endDraw();
		
		return graphics;
	}
	
	// takes a unit width of a box (its side length) and an index, and returns the starting x position
	// of the box
	public static int get_aligned_position(int unit_width, int index) {
		return (unit_width + Settings.BOX_GAP) * index + Settings.BOX_GAP + unit_width / 2;
	}
	
	public static Utility instance() {
		return instance_;
	}
}
