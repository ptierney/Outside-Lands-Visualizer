
abstract class Box {
  float x_;
  float y_;
  boolean falling_;
  int side_dim_;
  
  Box(float x_, float y_) {
    this.x_ = x_;
    this.y_ = y_;
    side_dim_ = unit_dim;
    falling_ = true;
  }
  
  void update() {
    if (falling_)
      update_position();
  }
  
  void update_position() {
    int collision_result = check_collisions();
    
    switch (collision_result) {
      case 0:
        y_ += fall_speed;
      break;
      
      case 1:
        y_ += 1;
      break;
      
      case 2:
      default:
        falling_ = false;
        scrolling = check_scroll(this);
      break;
    }
  }
  
  float x() {
    return x_;
  }
  
  void set_x(float x_) {
    this.x_ = x_;
  }
  
  float y() {
    return y_;
  }
  
  void set_y(float y_) {
    this.y_ = y_;
  }
  
  int side_dim() {
    return side_dim_;
  }
  
  void set_falling(boolean falling_) {
    this.falling_ = falling_;
  }
  
  // 0 = no collision
  // 1 = collision inside 2 * fall_speed
  // 2 = collision inside 1 * fall_speed
  int check_collisions() {
    for (int i = 0; i < vote_boxes.size(); ++i) {
      Box b = (Box) vote_boxes.get(i);
      if (b == this)
        continue;
      
      if (b.is_inside(x_, y_ + side_dim_ / 2 + box_gap))
        return 2;
      else if(b.is_inside(x_, y_ + side_dim_ / 2 + fall_speed * 2))
        return 1;
    }
    
    // check a collision againt the bottom of the screen
    if (y_ + side_dim_ / 2 + box_gap >= height)
      return 2;
    else if (y_ + side_dim_ / 2 + fall_speed * 2 >= height)
      return 1;
     
    return 0;
  }
  
  boolean is_inside(float ox, float oy) {
    return ox < (x_ + side_dim_ / 2) && ox > (x_ - side_dim_ / 2) 
      && oy < (y_ + side_dim_ / 2) && oy > (y_ - side_dim_ / 2);
  }
  
  abstract void draw();
}

class DynamicBox extends Box {
  ArrayList transition_states_;
  BoxTransition box_transition_;
  color box_color_;
  PImage background_image_;
  
  DynamicBox(float x_, float y_) {
    super(x_, y_);
    box_color_ = color(int(random(255)), int(random(255)),
      int(random(255)));
    generate_background();
    
    transition_states_ = new ArrayList();
    transition_states_.add(new PhotoState(photo_1));
    transition_states_.add(new CompositeState(new PhotoState(photo_2),
      new TextState("Patrick Tierney")));
    //transition_states_.add(new PhotoState(photo_2));
    box_transition_ = new HorizontalSlideTransition();
    //box_transition_ = new VerticalSlideTransition();
    box_transition_.load_transition( (TransitionState) transition_states_.get(0),
      (TransitionState) transition_states_.get(1));
  }
  
  void generate_background() {
    background_image_ = createImage(unit_dim, unit_dim, RGB);
    for (int i = 0; i < unit_pixels; ++i) {
      background_image_.pixels[i] = box_color_;
    }
  }
  
  void update() {
    super.update();
    box_transition_.perform_transition();
  }
  
  void draw() {
    pushMatrix();
      translate(x_, y_);                  
      image(background_image_, -h_unit_dim, -h_unit_dim);
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
