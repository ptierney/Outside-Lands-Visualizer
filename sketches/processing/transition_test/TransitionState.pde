
abstract class TransitionState {
  TransitionState() {
    // Nothing here
  }
  
  abstract PImage get_image();
}

class PhotoState extends TransitionState {
  PImage state_image_;
  
  PhotoState(PImage photo) {
    // Assert correct image
    if (photo.width != unit_dim || photo.height != unit_dim)
      exit();
      
    state_image_ = photo;
  }
  
  PImage get_image() {
    return state_image_;
  }
  
}

class TextState extends TransitionState {
  String state_text_;
  PGraphics state_graphics_;
  
  TextState(String state_text_) {
    this.state_text_ = state_text_;
    state_graphics_ = createGraphics(unit_dim, unit_dim, P3D);
    state_graphics_.background(0, 0, 0); // Assume additive blending for now
    state_graphics_.noStroke();
    state_graphics_.fill(255, 255, 255);
    state_graphics_.textFont = text_font;
    state_graphics_.textMode(CENTER);
    state_graphics_.text(state_text_, unit_dim / 2, unit_dim / 2);
  }
  
  PImage get_image() {
    return state_graphics_;
  }
}
