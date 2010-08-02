
abstract class BoxTransition {
  PImage start_image_;
  PImage end_image_;
  boolean finished_;

  BoxTransition() {
    finished_ = false;
  }

  void load_transition(TransitionState start, 
  TransitionState end) {
    start_image_ = start.get_image();
    end_image_ = end.get_image();
  }

  abstract void perform_transition();
  abstract PImage get_current_image();
  abstract void draw();

  boolean finished() {
    return finished_;
  }
}

// TODO: add direction, ie left to right, or right to left, up to down, etc
abstract class SlideTransition extends BoxTransition {
  float slide_speed_;
  int counter_; // goes from 0 to unit_dim (for # of pixels transitioned)
  PImage transition_buffer_;
  boolean normal_direction_;
  int trans_unit_dim_;

  SlideTransition() {
    super();

    normal_direction_ = true;
    this.slide_speed_ = 1.0;
  }

  void load_transition(TransitionState start, 
  TransitionState end) {
    super.load_transition(start, end);

    trans_unit_dim_ = start_image_.width;

    transition_buffer_ = start_image_;
  }

  void perform_transition() {
    if(counter_ > trans_unit_dim_) {
      finished_ = true;
      return;
    }

    clear_buffer();
    copy_start_into_buffer(counter_);
    copy_end_into_buffer(counter_);
    ++counter_;
  }

  void clear_buffer() {
    transition_buffer_ = createImage(unit_dim, unit_dim, ARGB);
  }

  PImage get_current_image() {
    return transition_buffer_;
  }

  void draw() {   
    image(transition_buffer_, -h_unit_dim, -h_unit_dim);
  }

  abstract void copy_start_into_buffer(int c);
  abstract void copy_end_into_buffer(int c);
}

class VerticalSlideTransition extends SlideTransition {
  VerticalSlideTransition() {
    super();
  }

  void copy_start_into_buffer(int c) {
    int start_counter = (start_image_.height - 1) * start_image_.width; // always start copying from the last row of the start image
    int trans_counter = (start_image_.height -1 - c) * start_image_.width;

    // Outer loop walks the start image array rows
    for (int i = start_counter; i >= (c * trans_unit_dim_); i -= trans_unit_dim_) {
      int start_pix = i;
      // inner loop copies the start image column into a transition_buffer column
      for (int j = trans_counter; j < trans_counter + transition_buffer_.width; ++j, ++start_pix) {
        transition_buffer_.pixels[j] = start_image_.pixels[start_pix];
      }
      start_counter -= trans_unit_dim_;
      trans_counter -= trans_unit_dim_;
    }
  }

  void copy_end_into_buffer(int c) {
    int end_counter = 0; // always start at the first row of the end image
    int trans_counter = (transition_buffer_.height - c) * transition_buffer_.width;

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

class HorizontalSlideTransition extends SlideTransition {
  HorizontalSlideTransition() {
    super();
  }

  void copy_start_into_buffer(int c) {
    int start_counter = start_image_.width - 1; // Always start copying from the last column of the start image
    int trans_counter = start_image_.width -1 - c;
    int num_pix = trans_unit_dim_ * trans_unit_dim_;
    // Walk along each column of the start image
    // Outer loop walks the start image array
    for (int i = start_counter; i >= c; --i) {
      int start_pix = i;
      // inner loop copies the start image column into a transition_buffer column
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

