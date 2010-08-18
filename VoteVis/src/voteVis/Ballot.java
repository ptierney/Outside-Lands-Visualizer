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

public class Ballot {
	private int[] votes_;
	private long user_id_;
	private PImage user_photo_ = null;
	private String user_name_;
	private Gender gender_;
	private String image_string_;
	
	Ballot(long user_id_, String user_name_, Gender gender_, int[] votes_, String image_string_) {
		this.votes_ = votes_;
		this.user_id_ = user_id_;
		this.image_string_ = image_string_;
		this.user_name_ = user_name_;
		this.gender_ = gender_;
	}
	
	public void load_image() {
		user_photo_ = VoteVisApp.instance().loadImage(image_string_);
	}
	
	int get_index(Type type) {
		return votes_[type.ordinal()];
	}
	
	Gender gender() {
		return gender_;
	}
	
	int[] votes() {
		return votes_;
	}
	
	long user_id() {
		return user_id_;
	}
	
	String user_name() {
		return user_name_;
	}
	
	PImage user_photo() {
		return user_photo_;
	}
}
