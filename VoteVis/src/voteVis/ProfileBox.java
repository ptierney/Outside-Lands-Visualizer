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

public class ProfileBox extends DynamicBox {
	private long user_id_;
	private static int PROFILE_BORDER_SIZE = 30;
	private static int PROFILE_PANE_SIZE = Settings.UNIT_DIM - PROFILE_BORDER_SIZE;
	
	public ProfileBox(VoteVisApp p_, float x_, float y_, long user_id_) {
		super(p_, x_, y_);
		
		// use this to get the image and name from a utility class
		this.user_id_ = user_id_;
		UserManager.User user = UserManager.instance().get_user(user_id_);
		user.display();
		// create BoxFrame
		box_frame_ = new ProfileFrame(p_, this);
		
		box_pane_.add_transition_state(new PhotoState(p_, 
			Utility.instance().scale_to_pane_size(user.profile_photo(),  PROFILE_PANE_SIZE)));
		box_pane_.add_transition_state(new TextState(p_, user.name(), 
			Settings.instance().profile_color(), ImageLoader.instance().get_profile_square(), 
			PROFILE_PANE_SIZE, Settings.instance().get_vote_box_font(),
			Settings.PROFILE_BOX_SMALL_FONT_SIZE));
		box_pane_.load_transition();
		box_pane_.set_advancing(false);
	}
	
	public void expand_fully() {
		((ProfileFrame) box_frame_).expand_fully();
	}
	
	public void contract_fully() {
		((ProfileFrame) box_frame_).contract_fully();
	}
	
	@Override
	public PImage get_image() {
		PGraphics flatten = VoteVisApp.instance().createGraphics(box_frame_.get_width(), box_frame_.get_height(), PApplet.P3D);
		
		flatten.beginDraw();
		flatten.image(box_frame_.get_image(), 0, 0);
		PImage pane_image = box_pane_.get_image();
		flatten.translate(box_frame_.get_image_center().x, box_frame_.get_image_center().y);
		flatten.image(pane_image, -pane_image.width / 2, -pane_image.height / 2);
		flatten.endDraw();
		
		return flatten;
	}
	
	@Override
	protected void stopped_falling() {
		expand_fully();
	}
	
	public long user_id() {
		return user_id_;
	}
	
	@Override
	public int get_height() {
		return Settings.UNIT_DIM;
	}
}
