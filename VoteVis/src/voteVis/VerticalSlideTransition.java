package voteVis;

public class VerticalSlideTransition extends SlideTransition {
	VerticalSlideTransition(VoteVisApp p_) {
		super(p_);
	}

	void copy_start_into_buffer(int c) {
		// always start copying from the last row of the image
		int start_counter = (start_image_.height - 1) * start_image_.width; 
		
		int trans_counter = (start_image_.height - 1 - c) * start_image_.width;

		// Outer loop walks the start image array rows
		for (int i = start_counter; i >= (c * trans_unit_dim_); i -= trans_unit_dim_) {
			int start_pix = i;
			// inner loop copies the start image column into a transition_buffer
			// column
			for (int j = trans_counter; j < trans_counter
					+ transition_buffer_.width; ++j, ++start_pix) {
				transition_buffer_.pixels[j] = start_image_.pixels[start_pix];
			}
			start_counter -= trans_unit_dim_;
			trans_counter -= trans_unit_dim_;
		}
	}

	void copy_end_into_buffer(int c) {
		int end_counter = 0; // always start at the first row of the end image
		int trans_counter = (transition_buffer_.height - c)
				* transition_buffer_.width;

		// Outer loop walks the end image from row 0 downward
		for (int i = end_counter; i < c * trans_unit_dim_; i += trans_unit_dim_) {
			int end_pix = i;
			// Inner loop copies a row from end image into transition buffer
			for (int j = trans_counter; j < trans_counter + trans_unit_dim_; ++j, ++end_pix) {
				transition_buffer_.pixels[j] = end_image_.pixels[end_pix];
			}
			end_counter += trans_unit_dim_;
			trans_counter += trans_unit_dim_;
		}
	}
}
