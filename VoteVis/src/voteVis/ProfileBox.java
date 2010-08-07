package voteVis;



public class ProfileBox extends DynamicBox {
	private int user_id_;
	private static int PROFILE_BORDER_SIZE = 40;
	private static int PROFILE_PANE_SIZE = Settings.UNIT_DIM - PROFILE_BORDER_SIZE;
	
	public ProfileBox(VoteVisApp p_, float x_, float y_, int user_id_) {
		super(p_, x_, y_);
		
		// use this to get the image and name from a utility class
		this.user_id_ = user_id_;
		
		// create BoxFrame
		box_frame_ = new ProfileFrame(p_, this, Settings.VOTE_BOX_SIDE_DIM);
		
		box_pane_.add_transition_state(new PhotoState(p_, 
			Utility.instance().scale_to_pane_size(ImageLoader.instance().get_dummy_profile_image(),  PROFILE_PANE_SIZE)));
		box_pane_.add_transition_state(new TextState(p_, UserManager.instance().get_user(user_id_).name(), 
			Settings.instance().profile_color(), PROFILE_PANE_SIZE, Settings.instance().get_vote_box_font(),
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
	protected void stopped_falling() {
		expand_fully();
	}
	
	public int user_id() {
		return user_id_;
	}
}
