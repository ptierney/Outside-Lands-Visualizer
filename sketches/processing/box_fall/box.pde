
class Box {
  float x_;
  float y_;
  boolean falling_;
  int type_;
  int index_;
  boolean trend_box_; // if this is true it's a big box
  float side_dim_;
  int trend_timer_;
  boolean disappearing_;
  float scale_amount_; // used for disappearing trend boxes
  boolean delete_me_ = false;
  String box_text_;
  
  Box(float x_, float y_, int type_, int index_, boolean trend_box_) {
    this.x_ = x_;
    this.y_ = y_;
    this.type_ = type_;
    this.index_ = index_;
    this.trend_box_ = trend_box_;
    trend_timer_ = trend_life;
    disappearing_ = false;
    scale_amount_ = 1.0;
    
    box_text_ = get_box_text(type_, index_);
    
    falling_ = true;
    
    side_dim_ = trend_box_ ? box_size * 2 + box_spacing : box_size;
  }  
  
  void draw() {
    check_collisions();
    
    set_fill_from_type(type_, index_);
    pushMatrix();
      translate(x_, y_);
      if (disappearing_)
        scale(scale_amount_);
      rect(0, 0, side_dim_, side_dim_);
      draw_text();
    popMatrix();
    
    update_position();
    
    if (trend_box_ && !falling_)
      update_life();
  }
  
  void draw_text() {
    fill(255);
    if (trend_box_)
      draw_trend_box_text();
    else
      draw_box_text();
  }
  
  void draw_trend_box_text() {
    textFont(trend_font, trend_text_size);
    text(box_text_, 0, trend_text_size, 3 * side_dim_ / 4, 3 * side_dim_ / 4);
  }
  
  void draw_box_text() {
    textFont(box_font, box_text_size);
    text(box_text_, 0, box_text_size, 3 * side_dim_ / 4, 3 * side_dim_ / 4);
  }
  
  void update_position() {
    if (falling_) {
      y_ += fall_rate;
    }
  }
  
  void check_collisions() {
    for (int i = 0; i < boxes.size(); ++i) {
      Box b = (Box) boxes.get(i);
      int test_y = int(y_ + side_dim_ / 2 + box_spacing);
      
      // if it's a trend box you have to probe both sides because there could be 2 collisions
      if (trend_box_) {
        int test_x = int(x_ + side_dim_ / 4);
        if (b.is_inside(test_x, test_y)) {
          falling_ = false;
          break;
        }
        test_x = int(x_ - side_dim_ / 4);
        if (b.is_inside(test_x, test_y)) {
          falling_ = false;
          break;
        }
      } 
      // if it's a normal box just look for an intersection with the box immediately below it
      else {
        int test_x = int(x_);
        if (b.is_inside(test_x, test_y)) {
          falling_ = false;
          break;
        }
      }
    }
    
    if (y_ > height - box_spacing - side_dim_ / 2)
      falling_ = false;
  }
  
  boolean is_inside(float ox, float oy) {
    return ox < (x_ + side_dim_ / 2) && ox > (x_ - side_dim_ / 2) 
      && oy < (y_ + side_dim_ / 2) && oy > (y_ - side_dim_ / 2);
  }
  
  void update_life() {
    --trend_timer_;
    
    if (trend_timer_ <= 0) {
      disappearing_ = true;
      scale_amount_ *= scale_rate;
    }
    
    if (scale_amount_ < 0.1) {
      delete_me_ = true;
      check_for_deletes = true;
    }
  }
  
  float x() {
    return x_;
  }
  
  float y() {
    return y_;
  }
  
  boolean delete_me() {
    return delete_me_;
  }
  
  void set_falling(boolean falling_) {
    this.falling_ = falling_;
  }
}
