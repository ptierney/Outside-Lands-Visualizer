
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

  boolean falling() {
    return falling_;
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
  int transition_counter_;
  int advance_delay_ = 260; // in frames
  int delay_counter_;

  DynamicBox(float x_, float y_) {
    super(x_, y_);
    box_color_ = color(int(random(255)), int(random(255)),
    int(random(255)));
    generate_background();
    transition_states_ = new ArrayList();

    transition_counter_ = 0;
  }

  void load_transition() {
    TransitionState start = (TransitionState) transition_states_.get(transition_counter_);

    if (transition_counter_ + 1 >= transition_states_.size())
      transition_counter_ = 0;
    else
      ++transition_counter_;

    TransitionState end = (TransitionState) transition_states_.get(transition_counter_);

    if (int(random(2)) == 0)
      box_transition_ = new HorizontalSlideTransition();
    else
      box_transition_ = new VerticalSlideTransition();

    box_transition_.load_transition(start, end);
  }

  void generate_background() {
    background_image_ = createImage(unit_dim, unit_dim, RGB);
    for (int i = 0; i < unit_pixels; ++i) {
      background_image_.pixels[i] = box_color_;
    }
  }

  void update() {
    super.update();
    
    if (falling_ == false)
      box_transition_.perform_transition();

    if (box_transition_.finished()) {
      ++delay_counter_;
      if (delay_counter_ > advance_delay_) {
        load_transition();
        delay_counter_ = 0;
      }
    }
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
  int profile_index_;
  int music_index_;
  int eco_index_;
  int wine_index_;

  VoteBox(float x_, float y_) {
    super(x_, y_);
  }

  void set_information(int profile_index_, int music_index_,
    int eco_index_, int wine_index_) {
    this.profile_index_ = profile_index_;
    this.music_index_ = music_index_;
    this.eco_index_ = eco_index_;
    this.wine_index_ = wine_index_;

    transition_states_.add(new TextState(profile_names[profile_index_], profile_color)); 
    transition_states_.add(new PhotoState(profile_images[profile_index_]));
    transition_states_.add(new TextState(favorite_music_string, music_color));
    transition_states_.add(new TextState(music_names[music_index_], music_color));
    transition_states_.add(new PhotoState(music_images[music_index_]));
    transition_states_.add(new TextState(favorite_eco_string, eco_color));
    transition_states_.add(new TextState(eco_names[eco_index_], eco_color));
    transition_states_.add(new PhotoState(eco_images[eco_index_]));
    transition_states_.add(new TextState(favorite_wine_string, wine_color));
    transition_states_.add(new TextState(wine_names[wine_index_], wine_color));
    transition_states_.add(new PhotoState(wine_images[wine_index_]));

    load_transition();
  }
}

