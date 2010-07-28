
// type:
// 0 = food
// 1 = wine
// 2 = music

class Box {
  int side_dim_;
  int type_; // should be an enum, but processing doesn't support enums
  int index_; // specific musician, restaurant, vinyard
  float glow_counter_;
  float max_glow_ = 100.0;
  float glow_decay_ = 0.97;
  int x_;
  int y_;
  
  Box(int type_, int index_, int x_, int y_) {
    this.type_ = type_;
    this.index_ = index_;
    this.x_ = x_; 
    this.y_ = y_;
    glow_counter_ = max_glow_;
    side_dim_ = side_dim;
  }
  
  void draw() {
    int fill_h;
    int fill_s;
    
    switch (type_) {
      case 0:
        fill_h = food_h;
        fill_s = food_s;
      break;
      case 1:
        fill_h = wine_h;
        fill_s = wine_s;
      break;
      case 2:
        fill_h = music_h;
        fill_s = music_s;
      break;
      default:
        fill_h = 180;
        fill_s = 50;
        println("oh no!");
      break;      
    }
    
    int fill_b = int(map(glow_counter_, 0, max_glow_, min_bright, 100));
    
    fill(fill_h, fill_s, fill_b);
    
    pushMatrix();
      translate(x_, y_);
      rect(0, 0, side_dim_, side_dim_);
    popMatrix();
    
    glow_counter_ *= glow_decay_;
  }
  
  void register_new_box(int new_type, int new_index) {
    // Check if the new box is the same as this one
    if (new_type == type_) {// && new_index == index_) {
      glow_counter_ = max_glow_;
    }    
  }
  
  int x() {
    return x_;
  }
  
  void set_x(int new_x) {
    x_ = new_x;
  }
  
  int y() {
    return y_;
  }
  
  void set_y(int new_y) {
    y_ = new_y;
  }
  
  int type() {
    return type_;
  }
  
  int index() {
    return index_;
  }
}
