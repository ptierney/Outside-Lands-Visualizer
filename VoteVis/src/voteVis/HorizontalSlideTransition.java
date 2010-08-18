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

public class HorizontalSlideTransition extends SlideTransition {
	HorizontalSlideTransition(VoteVisApp p_) {
		super(p_);
	}

	void copy_start_into_buffer(int c) {
		int start_counter = start_image_.width - 1; // Always start copying from
													// the last column of the
													// start image
		int trans_counter = start_image_.width - 1 - c;
		int num_pix = trans_unit_dim_ * trans_unit_dim_;
		// Walk along each column of the start image
		// Outer loop walks the start image array
		for (int i = start_counter; i >= c; --i) {
			int start_pix = i;
			// inner loop copies the start image column into a transition_buffer
			// column
			for (int j = trans_counter; j < num_pix; j += trans_unit_dim_, start_pix += trans_unit_dim_) {
				transition_buffer_.pixels[j] = start_image_.pixels[start_pix];
			}
			--start_counter;
			--trans_counter;
		}
	}

	void copy_end_into_buffer(int c) {
		int end_counter = 0; // Determine the start column of the end image
		int trans_counter = start_image_.width - c;
		int num_pix = trans_unit_dim_ * trans_unit_dim_;

		// Outer loop walks the end image from column 0 rightward
		for (int i = 0; i < c; ++i) {
			int end_pix = i;
			// Inner loop copies a column from end image into transition buffer
			for (int j = trans_counter; j < num_pix; j += trans_unit_dim_, end_pix += trans_unit_dim_) {
				transition_buffer_.pixels[j] = end_image_.pixels[end_pix];
			}
			++end_counter;
			++trans_counter;
		}
	}
}
