
abstract class BoxTransition {
  PImage start_image_;
  PImage end_image_;

  BoxTransition() {
    // Nothing here 
  }
  
  void load_transition(TransitionState start, 
    TransitionState end) {
    start_image_ = start.get_image();
    end_image_ = end.get_image();
  }
  
  abstract void perform_transition();
  abstract PImage get_current_image();
  abstract void draw();
}

abstract class SlideTransition extends BoxTransition {
  float slide_speed_;
  int counter_; // goes from 0 to unit_dim (for # of pixels transitioned)
  PImage transition_buffer_;
  
  SlideTransition() {
    super();
    
    this.slide_speed_ = 1.0;
  }
  
  void load_transition(TransitionState start, 
    TransitionState end) {
    super.load_transition(start, end);
    //transition_buffer_ = createImage(unit_dim, unit_dim, ARGB);
    transition_buffer_ = start_image_;
    //transition_buffer_ = createImage(unit_dim, unit_dim, ARGB);
    //transition_buffer_ =
    //for (int i = 0; i < unit_pixels; ++i) {
    //  transition_buffer_.pixels[i] = start_image_.pixels[i];
    //}
  }
  
  void perform_transition() {
    if(counter_ > unit_dim)
      return;
    
    clear_buffer();
    copy_start_into_buffer(counter_);
    copy_end_into_buffer(counter_);
    ++counter_;
  }
  
  void clear_buffer() {
    transition_buffer_ = createImage(unit_dim, unit_dim, ARGB);
    /*
    // Set a background color for the transition
    int num_pixels = transition_buffer_.width * transition_buffer_.height;
    for (int i = 0; i < num_pixels; ++i) {
      transition_buffer_.pixels[i] = color(0, 255, 0);
    }
    */
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

class HorizontalSlideTransition extends SlideTransition {
  HorizontalSlideTransition() {
    super();
  }
  
  void copy_start_into_buffer(int c) {
    int start_counter = 0; // Always start copying from the first column of the start image
    // Walk along each column of the start image
    for (int i = c; i < transition_buffer_.width; ++i) {
      int trans_pix = i;
      for (int j = start_counter; j < unit_pixels; j += start_image_.width) {
        transition_buffer_.pixels[trans_pix] = start_image_.pixels[j];
        trans_pix += transition_buffer_.width;
      }
      ++start_counter;
    }
  }
  
  void copy_end_into_buffer(int c) {
    int end_counter = unit_dim - c; // Determine the start column of the end image
    
    for (int i = 0; i < c; ++i) {
      int trans_pix = i;
      for (int j = end_counter; j < unit_pixels; j += end_image_.width) {
        transition_buffer_.pixels[trans_pix] = end_image_.pixels[j];
        trans_pix += transition_buffer_.width;
      }
      ++end_counter;
    }
  }
}
