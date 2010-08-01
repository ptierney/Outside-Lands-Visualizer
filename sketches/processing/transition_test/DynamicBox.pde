
abstract class Box {
  float x_;
  float y_;
  
  Box(float x_, float y_) {
    this.x_ = x_;
    this.y_ = y_;
  }
  
  abstract void draw();
}

class DynamicBox extends Box {
  ArrayList transition_states_;
  BoxTransition box_transition_;
  
  DynamicBox(float x_, float y_) {
    super(x_, y_);
    
    transition_states_ = new ArrayList();
    transition_states_.add(new PhotoState(photo_1));
    transition_states_.add(new TextState("hi"));
    //transition_states_.add(new PhotoState(photo_2));
    box_transition_ = new HorizontalSlideTransition();
    box_transition_.load_transition( (TransitionState) transition_states_.get(0),
      (TransitionState) transition_states_.get(1));
  }
  
  void update() {
    box_transition_.perform_transition();
  }
  
  void draw() {
    pushMatrix();
      translate(x_, y_);
      box_transition_.draw();
    popMatrix();
  } 
}

// A thin wrapper around DyramicBox
class VoteBox extends DynamicBox {
  VoteBox(float x_, float y_) {
    super(x_, y_);
  }
}
