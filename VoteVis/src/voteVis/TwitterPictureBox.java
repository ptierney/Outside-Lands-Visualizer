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

import processing.core.PImage;
import processing.core.*;
// simple box that just displays a picture

public class TwitterPictureBox extends Box {
	private PImage image_;
	float width_;
	float height_;
	private int pop_in_time_ = 450;
	private int pop_counter_;
	private boolean popping_;
	
	public TwitterPictureBox(VoteVisApp p, float x, float y, PImage image_) {
		super(p, x, y);
		
		this.image_ = image_;
		width_ = image_.width;
		height_ = image_.height;
		
		x_ += width_ / 2;
		y_ += height_ / 2;
		
		pop_counter_ = p.millis();
		popping_ = true;
		
		falling_ = false;
	}
	
	@Override
	public void update() {
		super.update();
		
		if (p_.millis() - pop_counter_ > pop_in_time_)
			popping_ = false;
	}

	@Override
	public void draw() {
		if (popping_) {
			p_.pushMatrix();
				p_.translate(x_, y_);
				p_.scale(PApplet.map(p_.millis() - pop_counter_, 0.0f, pop_in_time_, 0.0f, 1.0f));
				p_.image(image_, -width_ / 2, -height_ / 2);
			p_.popMatrix();
		} else
			p_.image(image_, x_ - width_ / 2, y_ - height_ / 2);
	}

	@Override
	public int get_height() {
		return (int) height_;
	}

	@Override
	public PImage get_image() {
		return image_;
	}

	@Override
	public int get_width() {
		return (int) width_;
	}

}
