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
